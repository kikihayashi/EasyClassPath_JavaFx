package com.acer.main.model.console;

public class InputConsole extends Console {

    @Override
    public String getMessage() {
        return getDate() + "[WARN] 沒有輸入資料！\n";
    }
}
