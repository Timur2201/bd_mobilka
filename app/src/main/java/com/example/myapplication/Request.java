package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Request {
    private int id;
    private int userId;
    private String queryType;
    private String description;
    private String status;
    private String createdAt;

    // Конструкторы
    public Request() {
    }

    public Request(int id, int userId, String queryType, String description, String status, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.queryType = queryType;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }



    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private Document document;

    // Добавляем геттер и сеттер
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}