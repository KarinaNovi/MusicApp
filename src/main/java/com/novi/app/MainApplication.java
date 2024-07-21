package com.novi.app;

import com.novi.app.util.MusicInstrumentLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MainApplication {

    private static final Logger logger = LoggerFactory.getLogger(
            MainApplication.class
    );

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        MusicInstrumentLoader.loadInstruments();
        if (context.isRunning()) {
            logger.info("Application started");
        }
    }
}