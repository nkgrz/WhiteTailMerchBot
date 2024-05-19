package com.whitetail.whitetailmerchbot.bot;

import com.whitetail.whitetailmerchbot.bot.buttons.ProductKeyboardBuilder;
import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Order;
import com.whitetail.whitetailmerchbot.entity.Product;
import com.whitetail.whitetailmerchbot.entity.User;
import com.whitetail.whitetailmerchbot.service.CartService;
import com.whitetail.whitetailmerchbot.service.OrderService;
import com.whitetail.whitetailmerchbot.service.ProductService;
import com.whitetail.whitetailmerchbot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.whitetail.whitetailmerchbot.bot.buttons.AddToCartButtons.createAddToCartButtons;
import static com.whitetail.whitetailmerchbot.bot.buttons.AddToCartButtons.createQuantityKeyboard;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButtons.*;
import static com.whitetail.whitetailmerchbot.bot.buttons.CartKeyboardBuilder.changeCartKeyboard;
import static com.whitetail.whitetailmerchbot.bot.buttons.CartKeyboardBuilder.createCartKeyboard;
import static com.whitetail.whitetailmerchbot.bot.buttons.MainMenuKeyboardBuilder.createMenuKeyboard;
import static com.whitetail.whitetailmerchbot.bot.buttons.ProductKeyboardBuilder.createProductKeyboard;
import static com.whitetail.whitetailmerchbot.bot.constants.BotMessages.*;
import static com.whitetail.whitetailmerchbot.bot.constants.ButtonsCallback.*;
import static com.whitetail.whitetailmerchbot.service.CartBuilder.*;
import static com.whitetail.whitetailmerchbot.service.OrdersBuilder.orderItemsToString;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    // TODO как делать бекап БД???
    final BotConfig botConfig;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public TelegramBot(BotConfig botConfig, ProductService productService,
                       OrderService orderService, UserService userService, CartService cartService) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;

        List<BotCommand> botCommandList = List.of(
                new BotCommand("/start", "Запуск бота"),
                new BotCommand("/menu", "Меню бота"),
                new BotCommand("/help", "Получить помощь")
        );

        try {
            this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
        this.cartService = cartService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleTextMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        var tgUser = update.getMessage().getFrom();
        String lastName = tgUser.getLastName();
        String fullName = tgUser.getFirstName() + (lastName != null ? " " + lastName : "");

        if (messageText.startsWith("/send") && botConfig.getOwnerId() == chatId) {
            sendMessageToAllUsers(messageText);
        } else {
            switch (messageText) {
                case "/start":
                    userService.registerUser(chatId, tgUser.getUserName() == null ? fullName : tgUser.getUserName());
                    sendMessage(chatId, "Привет, " + fullName + "! \uD83D\uDE42\n" + WELCOME_MESSAGE);
                case "/menu":
                    sendMessage(chatId, MAIN_MENU_TEXT, createMenuKeyboard());
                    break;
                case "/help":
                    sendMessage(chatId, HELP_MESSAGE);
                    break;
                default:
                    sendMessage(chatId, "Пока команда не поддерживается");
            }
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        if (callbackData.startsWith(PRODUCT_DETAILS_CALLBACK)) {
            int productId = Integer.parseInt(callbackData.substring(PRODUCT_DETAILS_CALLBACK.length()));
            sendProductDetails(chatId, messageId, productId);
        } else if (callbackData.startsWith(ADD_TO_CART_CALLBACK)) {
            int productId = Integer.parseInt(callbackData.substring(ADD_TO_CART_CALLBACK.length()));
            int quantityProduct = productService.getQuantityOfProduct(productId);
            deleteOldMessages(chatId, messageId);
            sendMessage(chatId, ADD_TO_CART_TEXT + quantityProduct,
                    createQuantityKeyboard(productId, quantityProduct, QUANTITY_CALLBACK));
        } else if (callbackData.startsWith(QUANTITY_CALLBACK)) {
            setQuantityProductInCart(update, QUANTITY_CALLBACK);
        } else if (callbackData.startsWith(CHANGE_QUANTITY_FROM_CART_CALLBACK)) {
            int productId = Integer.parseInt(callbackData.substring(CHANGE_QUANTITY_FROM_CART_CALLBACK.length()));
            int quantityProduct = productService.getQuantityOfProduct(productId);
            String msgToChange = "Введите количество товара «" +
                    productService.getProductById(productId).getName() + "»\nДоступно: " + quantityProduct;
            executeEditMessageText(chatId, messageId, msgToChange, createQuantityKeyboard(productId, quantityProduct, CHANGE_QUANTITY_ITEM_FROM_CART_CALLBACK, 0));
        } else if (callbackData.startsWith(CHANGE_QUANTITY_ITEM_FROM_CART_CALLBACK)) {
            setQuantityProductInCart(update, CHANGE_QUANTITY_ITEM_FROM_CART_CALLBACK);
        }

        switch (callbackData) {
            case CATALOG_CALLBACK:
                List<Product> products = productService.getAllProducts();
                if (update.getCallbackQuery().getMessage().hasText()) {
                    var buttonsCatalog = createProductKeyboard(products);
                    executeEditMessageText(chatId, messageId, CATALOG_MESSAGE, buttonsCatalog);
                } else {
                    deleteOldMessages(chatId, messageId);
                    sendMessage(chatId, CATALOG_MESSAGE, createProductKeyboard(products));
                }
                break;
            case CART_CALLBACK:
                returnCart(chatId, messageId);
                break;
            case HISTORY_CALLBACK:
                List<Order> orders = orderService.findAll(chatId);
                if (!orders.isEmpty()) {
                    String ordersString = orderItemsToString(orders);
                    executeEditMessageText(chatId, messageId, ordersString, createBackAndMainButtons());
                } else {
                    executeEditMessageText(chatId, messageId, ORDERS_IS_EMPTY, createBackAndMainButtons());
                }
                break;
            case BACK_TO_MENU_CALLBACK:
                if (update.getCallbackQuery().getMessage().hasText()) {
                    executeEditMessageText(chatId, messageId, MAIN_MENU_TEXT, createMenuKeyboard());
                } else {
                    deleteOldMessages(chatId, messageId);
                    sendMessage(chatId, MAIN_MENU_TEXT, createMenuKeyboard());
                }
                break;
            case CHANGE_BASKET_CALLBACK:
                List<CartItem> cartItemsChange = cartService.findCartItemsByUserId(chatId);
                String messageToChange = changeCartItemsToString(cartItemsChange);
                executeEditMessageText(chatId, messageId, messageToChange, changeCartKeyboard(cartItemsChange));
                break;
            case BLANK_BUTTONS:
                break;
        }
    }

    private void returnCart(long chatId, int messageId) {
        List<CartItem> cartItems = cartService.findCartItemsByUserId(chatId);
        if (!cartItems.isEmpty()) {
            executeEditMessageText(chatId, messageId, cartItemsToString(cartItems), createCartKeyboard());
        } else {
            executeEditMessageText(chatId, messageId, CART_IS_EMPTY, createBackAndMainButtons());
        }
    }

    private void setQuantityProductInCart(Update update, String callback) {
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callbackData = update.getCallbackQuery().getData();
        String[] parts = callbackData.substring(callback.length()).split("_");
        int productId = Integer.parseInt(parts[0]);
        int quantity = Integer.parseInt(parts[1]);
        if (callbackData.startsWith(CHANGE_QUANTITY_ITEM_FROM_CART_CALLBACK)) {
            cartService.setQuantity(chatId, productId, quantity);
            returnCart(chatId, messageId);
        } else {
            cartService.addToCart(chatId, productId, quantity);
            executeEditMessageText(chatId, messageId, PRODUCT_ADD_TO_CART_TEXT, createMenuKeyboard());
        }
    }

    private void sendMessageToAllUsers(String messageText) {
        String text = messageText.substring("/send".length());
        for (User user : userService.getAllUsers()) {
            sendMessage(user.getChatId(), text);
        }
    }

    private void deleteOldMessages(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendProductDetails(long chatId, int messageId, int productId) {
        Product product = productService.getProductById(productId);
        deleteOldMessages(chatId, messageId);
        String caption = product.getName() + "\n\n" + product.getDescription()
                + "\n\nЦена: " + formatPrice(product.getPrice());

        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(product.getImageLink()));
        photo.setCaption(caption);
        photo.setReplyMarkup(createAddToCartButtons(productId));

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void executeEditMessageText(long chatId, int messageId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);

        editMessageText.setText(text);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup == null ? buttonToMarkup(createBackButton()) : inlineKeyboardMarkup);

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        sendMessage(chatId, textToSend, null);
    }

    private void sendMessage(long chatId, String textToSend, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        if (markup != null) {
            message.setReplyMarkup(markup);
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
}
