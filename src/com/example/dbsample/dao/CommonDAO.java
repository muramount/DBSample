package com.example.dbsample.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public abstract class CommonDAO<T> {

    protected static String TABLE_NAME;
    protected static String COLUMN_ID;
    protected static String[] COLUMNS;

    protected SQLiteDatabase mDatabase;

    protected abstract List<T> getAll();
    protected abstract T getRecordById(int id);
    protected abstract long insert(T entity);
    protected abstract int update(T entity);

    public int delete(int id) {
        return mDatabase.delete(TABLE_NAME, COLUMN_ID + " = ? ", new String[]{""+id});
    }

    /**
     * 全件数を取得
     * @return int
     */
    public int getAllCount() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor c = mDatabase.rawQuery(query, null);
        if (c.moveToFirst()) {
            int count = c.getCount();
            c.close();
            return count;
        }
        c.close();
        return 0;
    }

}
