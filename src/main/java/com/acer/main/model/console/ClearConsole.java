package com.acer.main.model.console;

public class ClearConsole extends Console {

    @Override
    public String getMessage() {
        return getDate() + "[INFO] 已清空列表資料！\n";
    }
}
