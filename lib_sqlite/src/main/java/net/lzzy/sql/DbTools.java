package net.lzzy.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public class DbTools extends SQLiteOpenHelper {

    private List<String> sqls;

    public DbTools(Context context, List<String> sqls, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.sqls = sqls;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (int i = 0; i < sqls.size(); i++)
            sqLiteDatabase.execSQL(sqls.get(i));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public void insert(String tableName, Map<String, Object> map) {
        String sql = SqlUtil.insert(tableName, map);
        getWritableDatabase().execSQL(sql);
    }

    public void update(String tableName, Map<String, Object> map, String col, String val) {
        String sql = SqlUtil.update(tableName, map, col, val);
        getWritableDatabase().execSQL(sql);
    }

    public void deleteAll(String tableName) {
        getWritableDatabase().delete(tableName, null, null);

    }

    public void delete(String tableName, String col, String val) {
        String sql = SqlUtil.delete(tableName, col, val);
        getWritableDatabase().execSQL(sql);
    }
}
