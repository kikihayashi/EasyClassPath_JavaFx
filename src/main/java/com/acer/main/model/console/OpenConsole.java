package com.acer.main.model.console;

public class OpenConsole extends Console {

    private String errorMessage;

    public OpenConsole(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return getDate() + "[ERROR] 開啟輸出資料夾失敗！(" + errorMessage + ")\n\n";
    }
}
