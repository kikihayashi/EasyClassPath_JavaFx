package com.acer.main.model.tool;

import com.acer.main.bean.*;
import com.acer.main.model.config.Property;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class SearchTool {
    private static final String TOMCAT_PATH_PREFIX = "\\WEB-INF\\classes\\";
    private static final String OUT_PRODUCTION_PATH = "\\out\\production\\";
    private static final String WEBROOT_FOLDER = "WebRoot";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static List<ProjectFile> searchFile(Property ide, String searchPath, String searchFileName) {
        return searchFile(searchPath, searchFileName, path -> {
            //使用getProjectFile方法來建立ProjectFile物件
            String projectName = searchPath.substring(searchPath.lastIndexOf("\\") + 1);
            String fileName = path.getFileName().toString();
            String filePath = path.toString();
            int dotIndex = fileName.lastIndexOf('.');
            String fileType = (dotIndex == -1 || dotIndex == fileName.length() - 1) ?
                    "未知的檔案或資料夾" : fileName.substring(dotIndex + 1);
            long fileSize = 0;
            File file = new File(path.toString());
            if (file.exists() && file.isFile()) {
                fileSize = file.length();
            }

            String lastModifiedTime = "";
            try {
                BasicFileAttributes bfa = Files.readAttributes(path, BasicFileAttributes.class);
                Date date = new Date(bfa.lastModifiedTime().toMillis());
                lastModifiedTime = sdf.format(date);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return getProjectFile(ide, searchPath, projectName, fileName, filePath, fileType, fileSize, lastModifiedTime);
        });
    }

    //搜尋searchPath路徑下的檔案
    public static <T> List<T> searchFile(String searchPath, String searchFileName, Function<Path, T> mapper) {
        List<T> fileList = new ArrayList<>();
        try {
            Path startDir = Paths.get(searchPath);
            Files.walk(startDir)
                    .filter(path -> path.getFileName().toString().startsWith(searchFileName))
                    .forEach(path -> {
                        try {
                            T item = mapper.apply(path);
                            fileList.add(item);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            return fileList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static ProjectFile getProjectFile(Property ide, String projectPath, String projectName, String fileName,
                                              String filePath, String fileType, long fileSize, String lastModifiedTime) {
        ProjectFile pf = new ProjectFile();
        pf.setProjectPath(projectPath);
        pf.setProjectName(projectName);
        pf.setFileName(fileName);
        pf.setRealPath(filePath);
        pf.setFileType(fileType);
        pf.setFileSize(fileSize);
        pf.setLastModifiedTime(lastModifiedTime);
        pf.setTomcatPath(getTomcatPath(ide, filePath, pf));
        pf.setSourceCodePath(getSourceCodePath(pf));
        return pf;
    }

    private static String getTomcatPath(Property ide, String filePath, ProjectFile pf) {
        switch (ide) {
            case INTELLIJ_IDEA:
                filePath = getFilePathForIntellijIDEA(filePath, pf);
                break;
            case ECLIPSE:
                filePath = getFilePathForEclipse(filePath, pf);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return filePath;
    }

    private static String getFilePathForEclipse(String filePath, ProjectFile pf) {
        String REAL_PATH_PREFIX = pf.getProjectPath();
        return filePath.replace(REAL_PATH_PREFIX, "");
    }

    private static String getFilePathForIntellijIDEA(String filePath, ProjectFile pf) {
        if (filePath.contains(OUT_PRODUCTION_PATH)) {
            String REAL_PATH_PREFIX = pf.getProjectPath()
                    + OUT_PRODUCTION_PATH
                    + pf.getProjectName()
                    + "\\";
            filePath = filePath.replace(REAL_PATH_PREFIX, TOMCAT_PATH_PREFIX);
        }
        if (filePath.contains(WEBROOT_FOLDER)) {
            int indexWebRoot = filePath.lastIndexOf(WEBROOT_FOLDER);
            if (indexWebRoot != -1) {
                filePath = filePath.substring(indexWebRoot + WEBROOT_FOLDER.length());
            }
        }
        return filePath;
    }

    private static String getSourceCodePath(ProjectFile pf) {
        return pf.getProjectName() + "\\" + pf.getRealPath().replaceFirst(".*" + pf.getProjectName() + "\\\\", "");
    }
}
