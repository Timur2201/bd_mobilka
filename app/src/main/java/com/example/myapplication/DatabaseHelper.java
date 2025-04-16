package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "law_app.db";
    private static final int DATABASE_VERSION = 2; // Увеличиваем версию базы данных

    // Таблица пользователей
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_PHONE = "phone";
    private static final String COLUMN_USER_ADDRESS = "address"; // Новый столбец
    private static final String COLUMN_USER_ROLE = "role";
    private static final String COLUMN_USER_CREATED_AT = "created_at";

    // Таблица запросов
    private static final String TABLE_REQUESTS = "requests";
    private static final String COLUMN_REQUEST_ID = "id";
    private static final String COLUMN_REQUEST_USER_ID = "user_id";
    private static final String COLUMN_REQUEST_QUERY_TYPE = "query_type";
    private static final String COLUMN_REQUEST_DESCRIPTION = "description";
    private static final String COLUMN_REQUEST_STATUS = "status";
    private static final String COLUMN_REQUEST_CREATED_AT = "created_at";

    // Таблица документов
    private static final String TABLE_DOCUMENTS = "documents";
    private static final String COLUMN_DOCUMENT_ID = "id";
    private static final String COLUMN_DOCUMENT_REQUEST_ID = "request_id";
    private static final String COLUMN_DOCUMENT_FILE_NAME = "file_name";
    private static final String COLUMN_DOCUMENT_FILE_PATH = "file_path";
    private static final String COLUMN_DOCUMENT_FILE_TYPE = "file_type";
    private static final String COLUMN_DOCUMENT_CREATED_AT = "created_at";

    // Таблица сообщений
    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_MESSAGE_ID = "id";
    private static final String COLUMN_MESSAGE_SENDER_ID = "sender_id";
    private static final String COLUMN_MESSAGE_RECEIVER_ID = "receiver_id";
    private static final String COLUMN_MESSAGE_TEXT = "message";
    private static final String COLUMN_MESSAGE_SENT_AT = "sent_at";

    // Таблица платежей
    private static final String TABLE_PAYMENTS = "payments";
    private static final String COLUMN_PAYMENT_ID = "id";
    private static final String COLUMN_PAYMENT_USER_ID = "user_id";
    private static final String COLUMN_PAYMENT_AMOUNT = "amount";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";
    private static final String COLUMN_PAYMENT_STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы пользователей
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USER_PHONE + " TEXT,"
                + COLUMN_USER_ADDRESS + " TEXT," // Новый столбец
                + COLUMN_USER_ROLE + " TEXT,"
                + COLUMN_USER_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Создание таблицы запросов
        String CREATE_REQUESTS_TABLE = "CREATE TABLE " + TABLE_REQUESTS + "("
                + COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_REQUEST_USER_ID + " INTEGER,"
                + COLUMN_REQUEST_QUERY_TYPE + " TEXT,"
                + COLUMN_REQUEST_DESCRIPTION + " TEXT,"
                + COLUMN_REQUEST_STATUS + " TEXT,"
                + COLUMN_REQUEST_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + COLUMN_REQUEST_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_REQUESTS_TABLE);

        // Создание таблицы документов
        String CREATE_DOCUMENTS_TABLE = "CREATE TABLE " + TABLE_DOCUMENTS + "("
                + COLUMN_DOCUMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DOCUMENT_REQUEST_ID + " INTEGER,"
                + COLUMN_DOCUMENT_FILE_NAME + " TEXT,"
                + COLUMN_DOCUMENT_FILE_PATH + " TEXT,"
                + COLUMN_DOCUMENT_FILE_TYPE + " TEXT,"
                + COLUMN_DOCUMENT_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + COLUMN_DOCUMENT_REQUEST_ID + ") REFERENCES " + TABLE_REQUESTS + "(" + COLUMN_REQUEST_ID + ")"
                + ")";
        db.execSQL(CREATE_DOCUMENTS_TABLE);

        // Создание таблицы сообщений
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MESSAGE_SENDER_ID + " INTEGER,"
                + COLUMN_MESSAGE_RECEIVER_ID + " INTEGER,"
                + COLUMN_MESSAGE_TEXT + " TEXT,"
                + COLUMN_MESSAGE_SENT_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY(" + COLUMN_MESSAGE_SENDER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
                + "FOREIGN KEY(" + COLUMN_MESSAGE_RECEIVER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);

        // Создание таблицы платежей
        String CREATE_PAYMENTS_TABLE = "CREATE TABLE " + TABLE_PAYMENTS + "("
                + COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PAYMENT_USER_ID + " INTEGER,"
                + COLUMN_PAYMENT_AMOUNT + " DECIMAL(10,2),"
                + COLUMN_PAYMENT_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + COLUMN_PAYMENT_STATUS + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_PAYMENT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_PAYMENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Добавляем столбец address в таблицу users
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_USER_ADDRESS + " TEXT");
        }
        // Если в будущем будут другие версии, добавьте дополнительные миграции здесь
    }

    // Добавление пользователя
    public long addUser(String name, String email, String password, String phone, String address, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        // Форматируем номер телефона: заменяем +7 на 8
        if (phone != null && phone.startsWith("+7")) {
            phone = "8" + phone.substring(2);
        }
        values.put(COLUMN_USER_PHONE, phone);
        values.put(COLUMN_USER_ADDRESS, address);
        values.put(COLUMN_USER_ROLE, role);

        long id = db.insert(TABLE_USERS, null, values);
        return id;
    }



    // Проверка пароля пользователя
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Получаем всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_EMAIL,
                        COLUMN_USER_PHONE, COLUMN_USER_ADDRESS, COLUMN_USER_ROLE, COLUMN_USER_CREATED_AT},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
                user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PHONE)));
                user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ADDRESS)));
                user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ROLE)));
                user.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_CREATED_AT)));
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    // Обновленный метод populateTestData с учетом address и форматирования телефона
    public void populateTestData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Очистка таблиц
            Log.d("Database", "Очистка таблиц...");
            db.execSQL("DELETE FROM " + TABLE_PAYMENTS);
            db.execSQL("DELETE FROM " + TABLE_MESSAGES);
            db.execSQL("DELETE FROM " + TABLE_DOCUMENTS);
            db.execSQL("DELETE FROM " + TABLE_REQUESTS);
            db.execSQL("DELETE FROM " + TABLE_USERS);

            // Добавление пользователей
            ContentValues user1 = new ContentValues();
            user1.put(COLUMN_USER_NAME, "Иван Иванов");
            user1.put(COLUMN_USER_EMAIL, "client@mail.com");
            user1.put(COLUMN_USER_PASSWORD, "123456");
            String phone1 = "+79161234567";
            if (phone1.startsWith("+7")) {
                phone1 = "8" + phone1.substring(2);
            }
            user1.put(COLUMN_USER_PHONE, phone1);
            user1.put(COLUMN_USER_ADDRESS, "Москва, ул. Ленина, д. 10");
            user1.put(COLUMN_USER_ROLE, "client");
            long userId1 = db.insert(TABLE_USERS, null, user1);
            Log.d("Database", "Добавлен пользователь 1 с ID: " + userId1);
            if (userId1 == -1) throw new Exception("Ошибка при добавлении пользователя 1");

            ContentValues user2 = new ContentValues();
            user2.put(COLUMN_USER_NAME, "Анна Петрова");
            user2.put(COLUMN_USER_EMAIL, "lawyer@mail.com");
            user2.put(COLUMN_USER_PASSWORD, "654321");

            String phone2 = "+79169876543";
            if (phone2.startsWith("+7")) {
                phone2 = "8" + phone2.substring(2);
            }
            user2.put(COLUMN_USER_PHONE, phone2);
            user2.put(COLUMN_USER_ADDRESS, "Москва, ул. Мира, д. 5");
            user2.put(COLUMN_USER_ROLE, "lawyer");
            long userId2 = db.insert(TABLE_USERS, null, user2);
            Log.d("Database", "Добавлен пользователь 2 с ID: " + userId2);
            if (userId2 == -1) throw new Exception("Ошибка при добавлении пользователя 2");

            // Добавление запросов
            ContentValues request1 = new ContentValues();
            request1.put(COLUMN_REQUEST_USER_ID, userId1);
            request1.put(COLUMN_REQUEST_QUERY_TYPE, "Консультация");
            request1.put(COLUMN_REQUEST_DESCRIPTION, "Нужна консультация по трудовому праву");
            request1.put(COLUMN_REQUEST_STATUS, "pending");
            long requestId1 = db.insert(TABLE_REQUESTS, null, request1);
            Log.d("Database", "Добавлен запрос 1 с ID: " + requestId1);

            ContentValues request2 = new ContentValues();
            request2.put(COLUMN_REQUEST_USER_ID, userId1);
            request2.put(COLUMN_REQUEST_QUERY_TYPE, "Договор");
            request2.put(COLUMN_REQUEST_DESCRIPTION, "Проверка договора аренды");
            request2.put(COLUMN_REQUEST_STATUS, "in_progress");
            long requestId2 = db.insert(TABLE_REQUESTS, null, request2);
            Log.d("Database", "Добавлен запрос 2 с ID: " + requestId2);

            // Добавление документов
            ContentValues doc1 = new ContentValues();
            doc1.put(COLUMN_DOCUMENT_REQUEST_ID, requestId1);
            doc1.put(COLUMN_DOCUMENT_FILE_NAME, "dogovor.pdf");
            doc1.put(COLUMN_DOCUMENT_FILE_PATH, "/storage/documents/dogovor.pdf");
            doc1.put(COLUMN_DOCUMENT_FILE_TYPE, "pdf");
            db.insert(TABLE_DOCUMENTS, null, doc1);

            ContentValues doc2 = new ContentValues();
            doc2.put(COLUMN_DOCUMENT_REQUEST_ID, requestId2);
            doc2.put(COLUMN_DOCUMENT_FILE_NAME, "scanned.jpg");
            doc2.put(COLUMN_DOCUMENT_FILE_PATH, "/storage/documents/scanned.jpg");
            doc2.put(COLUMN_DOCUMENT_FILE_TYPE, "image");
            db.insert(TABLE_DOCUMENTS, null, doc2);

            // Добавление сообщений
            ContentValues msg1 = new ContentValues();
            msg1.put(COLUMN_MESSAGE_SENDER_ID, userId1);
            msg1.put(COLUMN_MESSAGE_RECEIVER_ID, userId2);
            msg1.put(COLUMN_MESSAGE_TEXT, "Здравствуйте, когда сможете помочь?");
            db.insert(TABLE_MESSAGES, null, msg1);

            ContentValues msg2 = new ContentValues();
            msg2.put(COLUMN_MESSAGE_SENDER_ID, userId2);
            msg2.put(COLUMN_MESSAGE_RECEIVER_ID, userId1);
            msg2.put(COLUMN_MESSAGE_TEXT, "Добрый день, могу посмотреть сегодня после 18:00");
            db.insert(TABLE_MESSAGES, null, msg2);

            // Добавление платежей
            ContentValues payment1 = new ContentValues();
            payment1.put(COLUMN_PAYMENT_USER_ID, userId1);
            payment1.put(COLUMN_PAYMENT_AMOUNT, 5000.00);
            payment1.put(COLUMN_PAYMENT_STATUS, "completed");
            db.insert(TABLE_PAYMENTS, null, payment1);

            ContentValues payment2 = new ContentValues();
            payment2.put(COLUMN_PAYMENT_USER_ID, userId1);
            payment2.put(COLUMN_PAYMENT_AMOUNT, 3000.00);
            payment2.put(COLUMN_PAYMENT_STATUS, "pending");
            db.insert(TABLE_PAYMENTS, null, payment2);

            db.setTransactionSuccessful();
            Log.d("Database", "Данные успешно добавлены!");
        } catch (Exception e) {
            Log.e("Database", "Ошибка при заполнении данных: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    // ... остальные методы без изменений (addRequest, getUserRequests, etc.) ...
}