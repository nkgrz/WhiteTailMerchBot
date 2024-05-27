package com.whitetail.whitetailmerchbot.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    String name;

    String token;

    long owner;
}
