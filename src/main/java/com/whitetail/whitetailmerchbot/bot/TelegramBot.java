package com.whitetail.whitetailmerchbot.bot;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.whitetail.whitetailmerchbot.bot.buttons.CartKeyboardBuilder;
import com.whitetail.whitetailmerchbot.bot.buttons.OrdersKeyboardBuilder;
import com.whitetail.whitetailmerchbot.bot.buttons.ProductKeyboardBuilder;
import com.whitetail.whitetailmerchbot.dao.CartItemRepository;
import com.whitetail.whitetailmerchbot.dao.OrderRepository;
import com.whitetail.whitetailmerchbot.dao.ProductsRepository;
import com.whitetail.whitetailmerchbot.entity.CartItem;
import com.whitetail.whitetailmerchbot.entity.Order;
import com.whitetail.whitetailmerchbot.entity.Product;
import static com.whitetail.whitetailmerchbot.bot.buttons.BackButton.*;
import static com.whitetail.whitetailmerchbot.bot.buttons.CartKeyboardBuilder.createCartKeyboard;
import static com.whitetail.whitetailmerchbot.bot.buttons.MainMenuKeyboardBuilder.createMenuKeyboard;
import static com.whitetail.whitetailmerchbot.bot.constants.BotConstantButtonCallback.*;
import static com.whitetail.whitetailmerchbot.bot.constants.BotMessages.WELCOME_MESSAGE;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    // TODO как делать бекап БД???
    final BotConfig botConfig;
    private final ProductsRepository productsRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public TelegramBot(BotConfig botConfig, ProductsRepository productsRepository,
                       CartItemRepository cartItemRepository,
                       OrderRepository orderRepository) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.productsRepository = productsRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;

        List<BotCommand> botCommandList = List.of(
                new BotCommand("/start", "Welcome message"),
                new BotCommand("/menu", "Меню бота"),
                new BotCommand("/help", "Supported commands")
        );

        try {
            this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            User user = update.getMessage().getFrom();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String fullName = firstName + (lastName != null ? " " + lastName : "");

            if (messageText.startsWith("/send") && botConfig.getOwnerId() == chatId) {
                String text = messageText.substring("/send".length());
//                var users = userRepository.findAll();
//                for (User user : users) {
//                    sendMessage(user.getChatId(), text);
//                }

                // TODO
                // пока нет репозитория отправляю себе же
                // потом мб из БД делать ??
                sendMessage(chatId, text);

            } else {
                switch (messageText) {
                    case "/start":
                        // TODO изменить приветственное сообщение
                        log.info("Starting bot: chatID: {}, username: {}", chatId, user.getUserName());
                        sendMessage(chatId, "Привет, " + fullName + "! \uD83D\uDE42\n" + WELCOME_MESSAGE);
                    case "/menu":
                        sendMenu(chatId);
                        break;
                    default:
                        sendMessage(chatId, "Пока команда не поддерживается");
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            // TODO Какие-то обработчики товаров так можно сделать
            if (callbackData.startsWith("cart_item_")) {
                String cartItemId = callbackData.substring("cart_item_".length());
                handleCartItemSelection(chatId, Long.parseLong(cartItemId));
            } else if ("back".equals(callbackData)) {
                sendMenu(chatId); // Например, вернуться в главное меню
            }

            switch (callbackData) {
                case CATALOG_CALLBACK:
                    var buttonsCatalog = ProductKeyboardBuilder.createKeyboard(sendCatalog());
                    executeEditMessageText(chatId, messageId, "Вы перешли в каталог товаров\n", buttonsCatalog);
                    break;
                case BASKET_CALLBACK:
                    List<CartItem> cartItems = sendCart(chatId);
                    if (!cartItems.isEmpty()) {
                        String cartItemsString = CartKeyboardBuilder.cartItemsToString(cartItems);
                        executeEditMessageText(chatId, messageId, cartItemsString, createCartKeyboard());
                    } else {
                        executeEditMessageText(chatId, messageId, "Корзина пуста", createBackAndMainButtons());
                    }
                    break;
                case HISTORY_CALLBACK:
                    List<Order> orders = orderRepository.findOrdersByUserChatId(chatId);
                    if (!orders.isEmpty()) {
                        String ordersString = OrdersKeyboardBuilder.cartItemsToString(orders);
                        executeEditMessageText(chatId, messageId, ordersString, createBackAndMainButtons());
                    } else {
                        executeEditMessageText(chatId, messageId, "Заказов пока не было", createBackAndMainButtons());
                    }
                    break;
                case BACK_TO_MENU_CALLBACK:
                    executeEditMessageText(chatId, messageId, "Главное меню", createMenuKeyboard());
                    break;
            }

        }
    }

    private void handleCartItemSelection(long chatId, long cartItemId) {
        // Логика обработки выбора товара из корзины
        sendMessage(chatId, "Вы выбрали товар с ID: " + cartItemId);
    }

    private void executeEditMessageText(long chatId, int messageId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);

        editMessageText.setText(text);
        if (inlineKeyboardMarkup != null) {
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        } else {
            editMessageText.setReplyMarkup(buttonToMarkup(createBackButton()));
        }

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    private void sendMenu(long chatId) {
        sendMessage(chatId, "Вы в главном меню бота", createMenuKeyboard());
    }

    private List<Product> sendCatalog() {
        return productsRepository.findAll();
    }

    private List<CartItem> sendCart(long chatId) {
        return cartItemRepository.findCartItemsByUserChatId(chatId);
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
