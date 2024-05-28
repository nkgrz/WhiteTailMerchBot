package com.whitetail.whitetailmerchbot.service.configurations;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import lombok.Getter;

public class FreemarkerConfig {
    @Getter
    private static final Configuration configuration;

    // TODO: пробросить путь к шаблонам в конфигурацию, а не хардкодить
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_30);
        ClassTemplateLoader loader = new ClassTemplateLoader(
                FreemarkerConfig.class, "/templates");
        configuration.setTemplateLoader(loader);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
}
