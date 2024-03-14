package com.acer.main.model.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextTool {

    //建立txt檔案
    public static String createTxtFile(String filePath, String content, String TXT_NAME) {
        try {
            Path file = Paths.get(filePath + "\\" + TXT_NAME);
            Files.write(file, content.getBytes());
            return "[INFO] txt檔建立成功！檔案路徑：" + filePath + "\\" + TXT_NAME;
        } catch (IOException e) {
            return "[ERROR] 發生錯誤，txt檔建立失敗！" + e;
        }
    }
}
