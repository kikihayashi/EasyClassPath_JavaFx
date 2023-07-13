package com.acer.main.service;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.config.ConfigManager;
import com.acer.main.model.config.Property;
import com.acer.main.model.tool.SearchTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchService extends BTService {

    /**
     * 外部呼叫方法
     */
    //查詢單一檔案名稱
    public List<ProjectFile> search(String searchPath, String searchFileName) {
        setSearchPath(searchPath);
        return SearchTool.searchFile(this.IDE, this.searchPath, searchFileName);
    }

    //查詢匯入的檔案名稱
    public List<ProjectFile> importSearch(String searchPath, String searchFileNames) {
        setSearchPath(searchPath);
        //將.java檔的字串換成.class檔
        String[] searchFileNameArray = searchFileNames.replaceAll(".java", ".class").split("\n");
        //解析searchFileNames取得要搜尋的檔案
        return Arrays.stream(searchFileNameArray)
                .filter(searchFileName -> searchFileName.matches("^\\w+[-]?\\w+\\.\\w+$"))//過濾掉沒有副檔名，非檔案格式的字串
                .map(searchFileName -> SearchTool.searchFile(this.IDE, this.searchPath, searchFileName).get(0))//查詢檔案(唯一)
                .collect(Collectors.collectingAndThen(
                        /**
                         * Collectors.toMap(keyMapper, valueMapper, mergeFunction)
                         * keyMapper：ProjectFile::getFileName
                         * valueMapper：Function.identity()->ProjectFile本身，可以有其他寫法(chatGPT)
                         * mergeFunction：處理遇到相同key的情況(使用者可能多打)，只保留一個ProjectFile
                         * */
                        Collectors.toMap(ProjectFile::getFileName, Function.identity(), (a, b) -> a),
                        map -> new ArrayList<>(map.values())
                ));
    }

    /**
     * 內部呼叫方法
     */
    //設置SearchPath
    private void setSearchPath(String searchPath) {
        this.searchPath = (searchPath.isEmpty()) ? getDefaultPath() : searchPath;
        updatePropertyDefaultPath();
    }

    //覆寫config.properties裡面的預設路徑
    private void updatePropertyDefaultPath() {
        switch (IDE) {
            case INTELLIJ_IDEA -> {
                ConfigManager.updatePropertyValue(Property.INTELLIJ_IDEA_PATH, searchPath);
            }
            case ECLIPSE -> {
                ConfigManager.updatePropertyValue(Property.ECLIPSE_PATH, searchPath);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}