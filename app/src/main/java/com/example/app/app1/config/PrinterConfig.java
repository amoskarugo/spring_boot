package com.example.app.app1.config;

import com.example.app.app1.services.BluePrinter;
import com.example.app.app1.services.ColorPrinter;
import com.example.app.app1.services.GreenPrinter;
import com.example.app.app1.services.RedPrinter;
import com.example.app.app1.services.impl.ColorPrinterImpl;
import com.example.app.app1.services.impl.EnglishGreenPrinter;
import com.example.app.app1.services.impl.EnglishRedPrinter;
import com.example.app.app1.services.impl.SpanishBluePrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrinterConfig {

    // one way of providing beans by using a configuration


    @Bean
    public BluePrinter bluePrinter(){
        return new SpanishBluePrinter();
    }

    @Bean
    public GreenPrinter greenPrinter(){
        return new EnglishGreenPrinter();
    }
    @Bean
    public RedPrinter redPrinter(){
        return new EnglishRedPrinter();
    }
    @Bean
    public ColorPrinter colorPrinter(BluePrinter bluePrinter, RedPrinter redPrinter, GreenPrinter greenPrinter){
        return new ColorPrinterImpl(greenPrinter, redPrinter, bluePrinter);
    }
}
