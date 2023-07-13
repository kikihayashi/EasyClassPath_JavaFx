package com.acer.main.model.console;
import com.acer.main.model.tool.*;

public class DeleteConsole extends Console {

    private String outputFolder;

    public DeleteConsole(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public String getMessage() {
        return getDate() + FolderTool.deleteFolder(outputFolder) + "\n";
    }
}
