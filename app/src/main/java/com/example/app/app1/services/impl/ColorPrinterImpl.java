package com.example.app.app1.services.impl;

import com.example.app.app1.services.BluePrinter;
import com.example.app.app1.services.ColorPrinter;
import com.example.app.app1.services.GreenPrinter;
import com.example.app.app1.services.RedPrinter;

public class ColorPrinterImpl implements ColorPrinter {

    public BluePrinter bluePrinter;
    public RedPrinter redPrinter;
    public GreenPrinter greenPrinter;

    public ColorPrinterImpl(GreenPrinter greenPrinter, RedPrinter redPrinter, BluePrinter bluePrinter){
        this.redPrinter = redPrinter;
        this.greenPrinter = greenPrinter;
        this.bluePrinter = bluePrinter;

    }
    @Override
    public String print() {
        return String.join(", ", bluePrinter.print(), greenPrinter.print(), redPrinter.print());
    }
}
