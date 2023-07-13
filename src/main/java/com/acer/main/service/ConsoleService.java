package com.acer.main.service;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.console.*;
import com.acer.main.model.folder.Folder;
import com.acer.main.model.tool.JavaTool;
import com.acer.main.model.tool.SearchTool;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsoleService extends BTService {

    /**
     * 外部呼叫方法
     */
    //沒有輸入資料
    public String inputCheck() {
        return new InputConsole().getMessage();
    }

    //搜尋檢查
    public <T> String searchFileCheck(List<T> list) {
        return new SearchConsole(list).getMessage();
    }

    //匯出檢查
    public <T> String exportFile(List<T> list) {
        return new ExportConsole(list).getMessage();
    }

    //刪除檢查
    public String deleteFolder() {
        return new DeleteConsole(OUTPUT_FOLDER).getMessage();
    }

    //複製檔案檢查
    public <T> String copyFile(List<T> fileList, Map<String, Folder> folderMap) {
        StringBuilder sb = new StringBuilder();
        List<T> list = JavaTool.appendJavaFile(IDE, sb, fileList, searchPath, getProjectName());
        sb.append(new CopyConsole(list, folderMap, OUTPUT_FOLDER).getMessage());
        return sb.toString();
    }

    //建立txt檔檢查
    public String createTxt(Map<String, Folder> folderMap) {
        return new TxtConsole(folderMap, OUTPUT_FOLDER).getMessage();
    }

    //建立zip檔檢查
    public String createZip() {
        return new ZipConsole(getProjectName(), OUTPUT_FOLDER).getMessage();
    }

    //專案輸出作業完成
    public String exportFinish() {
        return new FinishConsole(OUTPUT_FOLDER).getMessage();
    }

    //清空列表
    public String clearList() {
        return new ClearConsole().getMessage();
    }

    //開啟輸出資料夾
    public String openFolder() {
        File folder = new File(OUTPUT_FOLDER);
        if (folder.exists() && folder.isDirectory()) {
            try {
                Desktop.getDesktop().open(folder);
            } catch (IOException e) {
                return new OpenConsole(e.toString()).getMessage();
            }
        }
        return "\n";
    }
}