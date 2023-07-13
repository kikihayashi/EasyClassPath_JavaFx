package com.acer.main.model.console;

public class JavaConsole extends Console {

    private String errorMessage;

    public JavaConsole(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return getDate() + "[WARN] 沒有找到" + errorMessage + "對應的java檔！\n";
    }
}
