package net.lzzy.sql;

import android.database.Cursor;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/29.
 */
public abstract class Sqlable {

    public abstract String getTableName();

    public abstract String getKeyVal();

    public abstract String getKeyCol();

    public abstract Map<String, Object> getMap();


    public abstract void setCursor(Cursor cursor);

}
