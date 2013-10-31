package com.example.dbsample.db;



public class DBManager {

    //    private String mTableName = null;
    //
    //    private static DBManager sInstance = null;
    //    private static Context sContext = null;
    //    private static SQLiteDatabase sDatabase = null;
    //    private static ArrayList<DBManager> sManagerList;
    //
    //    private DBManager(String tableName) {
    //        this.mTableName = tableName;
    //    }
    //
    //    public static synchronized void setContext(Context context) {
    //        if (DBManager.sInstance == null) {
    //            DBManager.sInstance = new DBManager(null);
    //            DBManager.sContext = context;
    //
    //            DBHelper helper = new DBHelper(DBManager.sContext);
    //            DBManager.sDatabase = helper.getWritableDatabase();
    //            DBManager.sManagerList = new ArrayList<DBManager>();
    //        }
    //    }
    //
    //    public static synchronized DBManager getInstance (String tableName) {
    //        if (DBManager.sInstance == null) {
    //            throw new IllegalStateException("instance is null.");
    //        }
    //
    //        for (DBManager manager : sManagerList) {
    //            if (manager.mTableName.equals(tableName)) {
    //                return manager;
    //            }
    //        }
    //
    //        for (int i = 0; i < DBHelper.TABLE_LIST.length; i++) {
    //            if (DBHelper.TABLE_LIST[i].equals(tableName)) {
    //                DBManager manager = new DBManager(tableName);
    //                sManagerList.add(manager);
    //                return manager;
    //            }
    //        }
    //        return null;
    //    }


}
