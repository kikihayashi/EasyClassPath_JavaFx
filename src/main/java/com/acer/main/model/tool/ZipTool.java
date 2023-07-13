package com.acer.main.model.tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTool {
    //建立專案名稱.zip檔案
    public static String createZipFile(String folderPath) {
        String zipFilePath = folderPath + ".zip";
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            Path folder = Paths.get(folderPath);
            Files.walk(folder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            String filePath = file.toAbsolutePath().toString();
                            String entryPath = folder.relativize(file).toString();
                            zipOut.putNextEntry(new ZipEntry(entryPath));

                            FileInputStream fis = new FileInputStream(filePath);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                zipOut.write(buffer, 0, length);
                            }

                            fis.close();
                            zipOut.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            zipOut.close();
            fos.close();
            return "[INFO] zip檔建立成功！檔案路徑：" + folderPath + ".zip";
        } catch (IOException e) {
            return "[ERROR] 發生錯誤，zip檔建立失敗！" + e;
        }
    }
}
