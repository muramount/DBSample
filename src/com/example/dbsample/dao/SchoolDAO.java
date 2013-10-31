package com.example.dbsample.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dbsample.db.DBHelper;
import com.example.dbsample.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

public class SchoolDAO extends CommonDAO<SchoolEntity> {

    public SchoolDAO(Context context) {
        TABLE_NAME = DBHelper.TABLE_NAME_SCHOOL;
        COLUMN_ID  = DBHelper.COL_NAME_ID;
        COLUMNS    = DBHelper.COL_LIST_SCHOOL;

        DBHelper helper = new DBHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    @Override
    public List<SchoolEntity> getAll() {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM " + TABLE_NAME);
        query.append(" ORDER BY " + COLUMN_ID + " DESC ");

        Cursor c = mDatabase.rawQuery(query.toString(), null);
        if (c.moveToFirst()) {
            ArrayList<SchoolEntity> schoolList = new ArrayList<SchoolEntity>();

            do {
                SchoolEntity schoolEntity = new SchoolEntity(
                        c.getInt(0),   //id
                        c.getString(1),//name
                        c.getString(2),//phone
                        c.getString(3),//address
                        c.getString(4),//memo
                        c.getBlob(5)); //image
                schoolList.add(schoolEntity);
            } while(c.moveToNext());
            c.close();

            return schoolList;
        }
        c.close();
        return null;
    }

    @Override
    public SchoolEntity getRecordById(int id) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM " + TABLE_NAME);
        query.append(" WHERE " + COLUMN_ID + " = ? ");

        Cursor c = mDatabase.rawQuery(query.toString(), new String[]{""+id});
        if (c.moveToFirst()) {
            SchoolEntity schoolEntity;
            do {
                schoolEntity = new SchoolEntity(
                        c.getInt(0),   //id
                        c.getString(1),//name
                        c.getString(2),//phone
                        c.getString(3),//address
                        c.getString(4),//memo
                        c.getBlob(5)); //image
            } while(c.moveToNext());
            c.close();

            return schoolEntity;
        }
        c.close();
        return null;
    }

    @Override
    public long insert(SchoolEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_NAME_NAME, entity.getName());
        values.put(DBHelper.COL_NAME_PHONE, entity.getPhone());
        values.put(DBHelper.COL_NAME_ADDRESS, entity.getAddress());
        values.put(DBHelper.COL_NAME_MEMO, entity.getMemo());
        values.put(DBHelper.COL_NAME_IMAGE, entity.getImage());
        return mDatabase.insert(TABLE_NAME, null, values);
    }

    @Override
    public int update(SchoolEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_NAME_NAME, entity.getName());
        values.put(DBHelper.COL_NAME_PHONE, entity.getPhone());
        values.put(DBHelper.COL_NAME_ADDRESS, entity.getAddress());
        values.put(DBHelper.COL_NAME_MEMO, entity.getMemo());
        values.put(DBHelper.COL_NAME_IMAGE, entity.getImage());
        return mDatabase.update(TABLE_NAME, values, COLUMN_ID + " = ? ", new String[]{""+entity.getId()});
    }

}
