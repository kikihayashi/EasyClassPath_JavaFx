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
import java.util.stream.Stream;

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
        List<ProjectFile> resultList = Arrays.stream(searchFileNameArray)
                .map(searchFileName -> searchFileName.trim())
                .filter(searchFileName -> searchFileName.matches("^\\w+([-]\\w+)*(\\$\\w+)*\\.\\w+$"))
                .flatMap(searchFileName -> getProjectFileStream(this.IDE, this.searchPath, searchFileName))
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
        return resultList;
    }

    private Stream<ProjectFile> getProjectFileStream(Property IDE,String searchPath, String searchFileName) {
        String searchFileNameWithoutExtension = searchFileName.replace(".class","");
        List<ProjectFile> searchFileList = SearchTool.searchFile(IDE, searchPath, searchFileNameWithoutExtension);

        if (searchFileName.contains(".class")) {
            List<ProjectFile> relationFileList = new ArrayList<>();
            System.out.println("嘗試搜尋" + searchFileName + "檔，以及是否有其子類別檔案：");
            for (ProjectFile pf : searchFileList) {
                if (pf.getFileType().equals("class")) {
                    System.out.println("找到Class檔案，將" + pf.getFileName() + "加入到清單中");
                    relationFileList.add(pf);
                }
            }
            System.out.println("---------------------------------------------------------");
            return relationFileList.stream();
        } else {
            return searchFileList.stream();
        }
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