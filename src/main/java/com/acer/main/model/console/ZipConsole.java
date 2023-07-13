package com.acer.main.model.console;
import com.acer.main.model.tool.*;

public class ZipConsole extends Console {

    private String projectName;

    private String outputFolder;

    public ZipConsole(String projectName, String outputFolder) {
        this.projectName = projectName;
        this.outputFolder = outputFolder;
    }

    @Override
    public String getMessage() {
        return getDate() + createZipFile(projectName, outputFolder) + "\n";
    }

    //產生Zip檔到新的母資料夾
    private String createZipFile(String fileName, String outputFolder) {
        return ZipTool.createZipFile(outputFolder + "\\" + fileName);
    }
}
