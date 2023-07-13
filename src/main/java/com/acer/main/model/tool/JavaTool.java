package com.acer.main.model.tool;

import com.acer.main.bean.ProjectFile;
import com.acer.main.model.config.Property;
import com.acer.main.model.console.JavaConsole;

import java.util.ArrayList;
import java.util.List;

public class JavaTool {

    //搜尋class檔對應的java檔，並加到fileList中
    public static <T> List<T> appendJavaFile(Property IDE, StringBuilder sb, List<T> fileList, String searchPath, String projectName) {
        List<T> list = new ArrayList<>();
        //可用flatMap代替forEach簡化程式碼(chatGPT)但可讀性不高
        fileList.stream().forEach(pf -> {
            ProjectFile projectFile = (ProjectFile) pf;
            list.add(pf);
            if (projectFile.getFileType().equals("class")) {
                List<ProjectFile> javaFileList = getJavaFileList(IDE, projectFile, searchPath, projectName);
                try {
                    list.add((T) javaFileList.get(0));
                } catch (IndexOutOfBoundsException e) {
                    sb.append(new JavaConsole(projectFile.getFileName()).getMessage());
                }
            }
        });
        return list;
    }

    //取得java檔案列表
    private static List<ProjectFile> getJavaFileList(Property IDE, ProjectFile projectFile, String searchPath, String projectName) {
        String searchFileName = projectFile.getFileName().replace(".class", ".java");
        switch (IDE) {
            case INTELLIJ_IDEA:
                String intellijPath = searchPath;
                return SearchTool.searchFile(IDE, intellijPath, searchFileName);
            case ECLIPSE:
                int index = searchPath.indexOf(".metadata");
                String eclipsePath = searchPath.substring(0, index) + projectName;
                return SearchTool.searchFile(IDE, eclipsePath, searchFileName);
            default:
                throw new IllegalArgumentException();
        }
    }
}
