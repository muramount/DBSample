package com.example.dbsample.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    // DB名
    public static final String DB_NAME = "sample_db";
    // DBバージョン
    public static final int DB_VER  = 1;

    // SQLiteのデータ型全て
    public static final String COL_TYPE_INTEGER = "INTEGER";//符号付整数型
    public static final String COL_TYPE_REAL    = "REAL";   //浮動小数点数型
    public static final String COL_TYPE_TEXT    = "TEXT";   //テキスト型
    public static final String COL_TYPE_BLOB    = "BLOB";   //バイナリ型
    public static final String COL_TYPE_NULL    = "NULL";   //NULL

    // テーブル一覧
    public static final String TABLE_NAME_SCHOOL  = "school_table";
    public static final String TABLE_NAME_STUDENT = "student_table";
    public static final String[] TABLE_LIST = new String[] {
        TABLE_NAME_SCHOOL,
        TABLE_NAME_STUDENT,
    };

    // カラム名一覧
    public static final String COL_NAME_ID      = "id";
    public static final String COL_NAME_NAME    = "name";
    public static final String COL_NAME_PHONE   = "phone";
    public static final String COL_NAME_ADDRESS = "address";
    public static final String COL_NAME_AGE     = "age";
    public static final String COL_NAME_MEMO    = "memo";
    public static final String COL_NAME_IMAGE   = "image";

    // テーブル毎のカラム
    public static final String[] COL_LIST_SCHOOL = new String[] {
        COL_NAME_NAME,
        COL_NAME_PHONE,
        COL_NAME_ADDRESS,
        COL_NAME_MEMO,
        COL_NAME_IMAGE
    };
    public static final String[] COL_LIST_STUDENT = new String[] {
        COL_NAME_NAME,
        COL_NAME_AGE,
        COL_NAME_MEMO,
        COL_NAME_IMAGE
    };

    // テーブルとカラムを紐付けたリスト
    public static HashMap<String, String[]> sTableColMap = new HashMap<String, String[]>();
    static {
        sTableColMap.put(TABLE_NAME_SCHOOL, COL_LIST_SCHOOL);
        sTableColMap.put(TABLE_NAME_STUDENT, COL_LIST_STUDENT);
    }

    // カラム名とデータ型を紐付けたリスト
    public static HashMap<String, String> sColTypeMap = new HashMap<String, String>();
    static {
        sColTypeMap.put(COL_NAME_ID,      COL_TYPE_INTEGER);
        sColTypeMap.put(COL_NAME_NAME,    COL_TYPE_TEXT);
        sColTypeMap.put(COL_NAME_PHONE,   COL_TYPE_TEXT);
        sColTypeMap.put(COL_NAME_ADDRESS, COL_TYPE_TEXT);
        sColTypeMap.put(COL_NAME_AGE,     COL_TYPE_INTEGER);
        sColTypeMap.put(COL_NAME_MEMO,    COL_TYPE_TEXT);
        sColTypeMap.put(COL_NAME_IMAGE,   COL_TYPE_BLOB);
    }


    /**
     * コンストラクタ
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    /**
     * DB作成時にコール
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            for (String tableName : TABLE_LIST) {
                StringBuffer query = new StringBuffer();
                String[] colList = sTableColMap.get(tableName);

                query.append(" CREATE TABLE IF NOT EXISTS " + tableName + " ( ");
                query.append(COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ");
                for (String colName : colList) {
                    query.append(", " + colName + " " + sColTypeMap.get(colName));
                }
                query.append(" )");

                db.execSQL(query.toString());
            }

            db.setTransactionSuccessful();

        } catch(SQLException e) {
            e.printStackTrace();
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        setSampleData(db, null);

    }

    /**
     * DBバージョンアップ時にコール
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Sample data insert.
     */
    public void setSampleData(SQLiteDatabase db, byte[] binary) {
        db.beginTransaction();

        try {
            for (String tableName : TABLE_LIST) {
                StringBuffer query = new StringBuffer();
                String[] colList = sTableColMap.get(tableName);
                String sp = "";

                query.append(" INSERT INTO " + tableName + " ( ");
                for (String colName : colList) {
                    query.append(sp + colName);
                    if (sp.equals("")) sp = ", ";
                }
                query.append(" ) VALUES ( ");
                sp = "";
                for (int i=0; i<colList.length; i++) {
                    query.append(sp + " ? ");
                    if (sp.equals("")) sp = ", ";
                }
                query.append(" ) ");

                for (int i=0; i<10; i++) {
                    SQLiteStatement stmt = db.compileStatement(query.toString());
                    if (tableName.equals(TABLE_NAME_SCHOOL)) {
                        stmt.bindString(1, "name " + i);//name
                        stmt.bindString(2, "03-0000-0000");//phone
                        stmt.bindString(3, "shinagawaku nishigotanda.");//address
                        stmt.bindString(4, "school");//memo
                        stmt.bindBlob(5, "".getBytes());//image
                    } else {
                        stmt.bindString(1, "name " + i);//name
                        stmt.bindString(2, "03-0000-0000");//age
                        stmt.bindString(3, "student");//memo
                        stmt.bindBlob(4, "".getBytes());//image
                    }

                    stmt.executeInsert();
                }

            }

            db.setTransactionSuccessful();

        } catch(SQLException e) {
            e.printStackTrace();
        } catch(RuntimeException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

}
