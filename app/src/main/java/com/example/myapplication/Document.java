package com.example.myapplication;

public class Document {
    private int id;
    private int requestId;
    private String fileName;
    private String filePath;
    private String fileType;
    private String createdAt;

    // Конструкторы
    public Document() {
    }

    public Document(int id, int requestId, String fileName, String filePath, String fileType, String createdAt) {
        this.id = id;
        this.requestId = requestId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}