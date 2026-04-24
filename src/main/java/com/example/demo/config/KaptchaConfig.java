package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
  @Bean
  public DefaultKaptcha producer() {
    Properties properties = new Properties();
    properties.put("kaptcha.border", "no");
    properties.put("kaptcha.textproducer.font.color", "black");
    properties.put("kaptcha.image.width", "100");
    properties.put("kaptcha.image.height", "40");
    properties.put("kaptcha.textproducer.font.size", "30");
    properties.put("kaptcha.session.key", "code");
    properties.put("kaptcha.textproducer.char.length", "4");

    Config config = new Config(properties);
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    defaultKaptcha.setConfig(config);

    return defaultKaptcha;
  }
}
