package com.example.pavel.rodionov.mybuy.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pavel.rodionov.mybuy.model.Buy;
import com.example.pavel.rodionov.mybuy.view.adapters.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper {
    private static DBHandler instance;
    private ListViewAdapter adapter;

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "DB_MyBuy";
    private final static String TABLE_BUY = "Buy";
    private final static String KEY_GOODS = "goods";
    private String CREATE_TABLE = String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
            TABLE_BUY, KEY_GOODS);

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        instance = this;
    }

    public void setAdapter(ListViewAdapter adapter){
        this.adapter = adapter;
    }

    public static DBHandler getInstance(){
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BUY);
        onCreate(sqLiteDatabase);
    }


    public void addBuy(Buy buy){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_GOODS,buy.getGoods());

        db.insert(TABLE_BUY,null,cv);
        db.close();

        Log.d("Pavel","DBHandler.addBuy(): " +buy.toString());
        adapter.changedData();
    }

    public List<Buy> getAllBuys() {
        List<Buy> goods = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUY, null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            Buy buy = new Buy();
            buy.setId(cursor.getInt(0));
            buy.setGoods(cursor.getString(1));
            goods.add(buy);
            while (cursor.moveToNext()) {
                buy = new Buy();
                buy.setId(cursor.getInt(0));
                buy.setGoods(cursor.getString(1));
                goods.add(buy);
            }
        }
        cursor.close();

        for(Buy buy:goods) Log.d("Pavel","DBHandler.getAllBuys(): "+buy.toString());

        return goods;
    }

    public void deleteTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_BUY);
        db.execSQL(CREATE_TABLE);
        db.close();

        Log.d("Pavel","DBHandler.deleteTable()");
        adapter.changedData();

    }

    public void deleteBuy(Buy buy){
        SQLiteDatabase db = this.getWritableDatabase();
        String str = String.format("DELETE FROM %s WHERE id=%d",TABLE_BUY,buy.getId());
        db.execSQL(str);
        db.close();

        Log.d("Pavel","DBHandler.deleteBuy(): " +buy.toString());
        adapter.changedData();

    }

    public void updateBuy(Buy buy,String text){
        SQLiteDatabase db = this.getWritableDatabase();
        String str = String.format("UPDATE %s SET %s=\"%s\" WHERE id=%d",TABLE_BUY,KEY_GOODS,text,buy.getId());
        db.execSQL(str);
        db.close();

        Log.d("Pavel","DBHandler.updateBuy(): " + buy.toString() + " new text: " + text);
        adapter.changedData();
    }

}
