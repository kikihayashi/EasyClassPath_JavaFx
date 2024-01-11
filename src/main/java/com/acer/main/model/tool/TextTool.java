package com.acer.main.model.tool;

import com.acer.main.model.config.ConfigManager;
import com.acer.main.model.config.Property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextTool {

//    private static final String RESULT_TXT = ConfigManager.getPropertyValue(Property.OUTPUT_TXT_NAME);
//
//    //建立result.txt檔案(裡面放檔案的Tomcat路徑資料)
//    public static String createTxtFile(String filePath, String content) {
//        try {
//            Path file = Paths.get(filePath + "\\" + RESULT_TXT);
//            Files.write(file, content.getBytes());
//            return "[INFO] txt檔建立成功！檔案路徑：" + filePath + "\\" + RESULT_TXT;
//        } catch (IOException e) {
//            return "[ERROR] 發生錯誤，txt檔建立失敗！" + e;
//        }
//    }

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
