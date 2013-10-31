package com.example.dbsample.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dbsample.db.DBHelper;
import com.example.dbsample.entity.StudentEntity;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends CommonDAO<StudentEntity> {

    public StudentDAO(Context context) {
        TABLE_NAME = DBHelper.TABLE_NAME_STUDENT;
        COLUMN_ID  = DBHelper.COL_NAME_ID;

        DBHelper helper = new DBHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    @Override
    public List<StudentEntity> getAll() {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM " + TABLE_NAME);
        query.append(" ORDER BY " + COLUMN_ID + " DESC ");

        Cursor c = mDatabase.rawQuery(query.toString(), null);
        if (c.moveToFirst()) {
            ArrayList<StudentEntity> studentList = new ArrayList<StudentEntity>();

            do {
                StudentEntity studentEntity = new StudentEntity(
                        c.getInt(0),   //id
                        c.getString(1),//name
                        c.getInt(2),   //age
                        c.getString(3),//memo
                        c.getBlob(4)); //image
                studentList.add(studentEntity);
            } while(c.moveToNext());
            c.close();

            return studentList;
        }
        c.close();
        return null;

    }

    @Override
    public StudentEntity getRecordById(int id) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM " + TABLE_NAME);
        query.append(" WHERE " + COLUMN_ID + " = ? ");

        Cursor c = mDatabase.rawQuery(query.toString(), new String[]{""+id});
        if (c.moveToFirst()) {
            StudentEntity studentEntity;
            do {
                studentEntity = new StudentEntity(
                        c.getInt(0),   //id
                        c.getString(1),//name
                        c.getInt(2),   //age
                        c.getString(3),//memo
                        c.getBlob(4)); //image
            } while(c.moveToNext());
            c.close();

            return studentEntity;
        }
        c.close();
        return null;
    }

    @Override
    public long insert(StudentEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_NAME_NAME, entity.getName());
        values.put(DBHelper.COL_NAME_AGE, entity.getAge());
        values.put(DBHelper.COL_NAME_MEMO, entity.getMemo());
        values.put(DBHelper.COL_NAME_IMAGE, entity.getImage());
        return mDatabase.insert(TABLE_NAME, null, values);
    }

    @Override
    public int update(StudentEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_NAME_NAME, entity.getName());
        values.put(DBHelper.COL_NAME_AGE, entity.getAge());
        values.put(DBHelper.COL_NAME_MEMO, entity.getMemo());
        values.put(DBHelper.COL_NAME_IMAGE, entity.getImage());
        return mDatabase.update(TABLE_NAME, values, COLUMN_ID + " = ? ", new String[]{""+entity.getId()});
    }



}
