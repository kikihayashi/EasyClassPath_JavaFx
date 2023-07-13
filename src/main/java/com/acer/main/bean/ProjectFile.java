package com.acer.main.bean;

public class ProjectFile {
    private String projectName;
    private String projectPath;
    private String fileName;
    private String realPath;
    private String tomcatPath;
    private String sourceCodePath;
    private String fileType;

    private long fileSize;
    private String lastModifiedTime;

    public ProjectFile() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getTomcatPath() {
        return tomcatPath;
    }

    public void setTomcatPath(String tomcatPath) {
        this.tomcatPath = tomcatPath;
    }

    public String getSourceCodePath() {
        return sourceCodePath;
    }

    public void setSourceCodePath(String sourceCodePath) {
        this.sourceCodePath = sourceCodePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        if (fileSize / 1024 < 1024) {
            return String.format("%.02f", ((float) fileSize / 1024)) + " KB";
        } else {
            return String.format("%.02f", ((float) fileSize / 1024 / 1024)) + " MB";
        }
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return "檔案名稱：" + fileName + "\n" +
                "實際路徑：" + realPath + "\n" +
                "修改路徑：" + tomcatPath + "\n" +
                "最後修改日期：" + lastModifiedTime;
    }
}
