package com.example.myapplication;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuHandler {

    private final Context context;

    public MenuHandler(Context context) {
        this.context = context;
    }

    public void setupMenu(Menu menu) {
        menu.add(Menu.NONE, 101, Menu.NONE, "Настройки");
        menu.add(Menu.NONE, 102, Menu.NONE, "Профиль");
        menu.add(Menu.NONE, 103, Menu.NONE, "Поиск");
        menu.add(Menu.NONE, 104, Menu.NONE, "О приложении");
        menu.add(Menu.NONE, 105, Menu.NONE, "Выход");
        menu.add(Menu.NONE, 105, Menu.NONE, "новый пункт меню");
    }


    public boolean handleMenuItem(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case 101:
                Toast.makeText(context, "Открыть настройки", Toast.LENGTH_SHORT).show();
                return true;
            case 102:
                Toast.makeText(context, "Профиль пользователя", Toast.LENGTH_SHORT).show();
                return true;
            case 103:
                Toast.makeText(context, "Поиск запущен", Toast.LENGTH_SHORT).show();
                return true;
            case 104:
                Toast.makeText(context, "Информация о приложении", Toast.LENGTH_SHORT).show();
                return true;
            case 105:
                Toast.makeText(context, "новый", Toast.LENGTH_SHORT).show();
                return true;
            case 106:
                Toast.makeText(context, "новый", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
