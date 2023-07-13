package com.acer.main.model.console;

public class FinishConsole extends Console {

    private String outputFolder;

    public FinishConsole(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public String getMessage() {
        return getDate() + "[SUCCESS] 輸出作業完成，資料夾路徑：" + outputFolder + "\n";
    }
}
