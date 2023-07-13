package com.acer.main.model.tool;

import com.acer.main.bean.ProjectFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FolderTool {

    //產生out資料夾(含有Tomcat路徑)
    public static String createOutputFolder(ProjectFile pf, String TARGET_PATH) {
        String message = "";
        try {
            //複製要提取的檔案
            Path sourceFile = Paths.get(pf.getRealPath());
            //目標檔案位置(包括檔名)
            Path targetFile = Paths.get(TARGET_PATH);
            //目標檔案位置(不包括檔名)
            Path targetFolder = targetFile.getParent();
            //建立目標檔案資料夾
            if (!Files.exists(targetFolder)) {
                Files.createDirectories(targetFolder);
            }
            //將sourceFile複製到targetFile，策略採用覆蓋模式
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            message = "[ERROR] 資料夾建立失敗！" + e + "\n";
        }
        return message;
    }

    //刪除前一次輸出的母資料夾
    public static String deleteFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            delete(folder);
            return "[SUCCESS] 資料夾刪除成功！";
        } else {
            return "[WARN] 資料夾不存在或路徑不對！";
        }
    }

    //執行刪除
    private static void delete(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    delete(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

}
