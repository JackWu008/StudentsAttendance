package net.lzzy.sql;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class Repository<T extends Sqlable> {
    private DbTools dbTools;
    private final Context context;
    private List<String> sqls;
    private final String dbName;
    private int version;
    private Class<T> className;
    String tableName;

    public Repository(Context context, List<String> sqls, String dbName, int version, Class<T> className) throws IllegalAccessException, InstantiationException {
        this.context = context;
        this.sqls = sqls;
        this.dbName = dbName;
        this.version = version;
        this.className = className;
        tableName = className.newInstance().getTableName();

        dbTools = new DbTools(context, sqls, dbName, null, version);
    }


    public void update(T t) {
        dbTools.update(tableName, t.getMap(), t.getKeyCol(), t.getKeyVal());

    }

    public int getCount(String[] cols, String[] vals, boolean isAccurate) {
        String selection = SqlUtil.filterQuery(cols, vals, isAccurate);
        Cursor cursor = dbTools.getReadableDatabase().query(tableName, null, selection, null, null, null, null);
        return cursor.getCount();

    }

    public List<T> getByKeyWordFilter(String[] cols, String[] vals, boolean isAccurate) throws IllegalAccessException, InstantiationException {

        String selection = SqlUtil.filterQuery(cols, vals, isAccurate);
        Cursor cursor = dbTools.getReadableDatabase().query(tableName, null, selection, null, null, null, null);
        List<T> lists = new ArrayList<>();
        while (cursor.moveToNext()) {
            T t = className.newInstance();
            t.setCursor(cursor);
            lists.add(t);
        }
        return lists;
    }

    //此方法会新产生一个list集合
    public List<T> getByKeyWord(String or_keyWord, String[] or_columns, String and_keyWord, String[] and_columns, boolean isAccurate) throws IllegalAccessException, InstantiationException {
        String selection = SqlUtil.searchQuery(or_keyWord, or_columns, and_keyWord, and_columns, isAccurate);
        List<T> lists = new ArrayList<>();
        Cursor cursor;
        cursor = dbTools.getReadableDatabase().query(tableName, null, selection, null, null, null, null);
        while (cursor.moveToNext()) {
            T t = className.newInstance();
            t.setCursor(cursor);
            lists.add(t);
        }

        return lists;
    }

    public String getDatabasePath() {
        return dbTools.getReadableDatabase().getPath();

    }

    public void insert(T t) {
        dbTools.insert(t.getTableName(), t.getMap());
    }

    public void deleteAll(String name) {
        dbTools.deleteAll(name);

    }

    public void delete(T t) {
        dbTools.delete(t.getTableName(), t.getKeyCol(), t.getKeyVal());
    }
}
