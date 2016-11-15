package net.lzzy.sql;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class SqlUtil {

    public static String insert(String table, Map<String, Object> column_value) {

        StringBuilder sb_column = new StringBuilder();
        StringBuilder sb_value = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = column_value.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            sb_column.append(entry.getKey() + ",");
            if (entry.getValue() instanceof Double)
                sb_value.append(entry.getValue() + ",");
            else
                sb_value.append("'" + entry.getValue() + "'" + ",");
        }
        return "insert into" + " " + table + "(" + sb_column.substring(0, sb_column.length() - 1) + ")values(" + sb_value.substring(0, sb_value.length() - 1) + ")";
    }

    public static String update(String table, Map<String, Object> column_value, String col, Object val) {
        String value;
        String field = "";
        Iterator<Map.Entry<String, Object>> iterator = column_value.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String cur_val;
            if (entry.getValue() instanceof String)
                cur_val = "'" + entry.getValue() + "'";
            else
                cur_val = entry.getValue() + "";
            field = field + entry.getKey() + "=" + cur_val + ",";
        }
        field = field.substring(0, field.length() - 1);
        if (val instanceof String)
            value = "'" + val + "'";
        else
            value = val + "";
        return "update " + table + " set " + field + " where " + col + "=" + value;

    }

    public static String searchQuery(String or_keyWord, String[] or_columns, String and_keyWord, String[] and_columns, boolean isAccurate) {


        String selection = "";
        if (or_columns != null && or_keyWord != null) {
            selection = "(";
            if (isAccurate) {
                for (String column : or_columns)
                    selection = selection.concat(column + " like '" + or_keyWord + "' or ");
            } else
                for (String column : or_columns)
                    selection = selection.concat(column + " like '%" + or_keyWord + "%' or ");
            selection = selection.substring(0, selection.length() - 3);
            selection = selection + ")";
        }
        if (and_keyWord != null && and_columns != null) {
            selection = selection + " and ";
            if (isAccurate) {
                for (String column : and_columns)
                    selection = selection.concat(column + " like '" + and_keyWord + "' and ");
            } else
                for (String column : and_columns)
                    selection = selection.concat(column + " like '%" + and_keyWord + "%' and ");
            selection = selection.substring(0, selection.length() - 4);
        }
        return selection;
    }

    public static String filterQuery(String[] cols, String[] vals, boolean isAccurate) {
        if (cols == null || vals == null || cols.length != vals.length)
            return null;
        String selection = "(";
        if (isAccurate) {
            for (int i = 0; i < cols.length; i++) {
                selection = selection + cols[i] + " like '" + vals[i] + "' and ";
            }
        } else for (int i = 0; i < cols.length; i++) {
            selection = selection + cols[i] + " like '%" + vals[i] + "%' and ";
        }
        selection = selection.substring(0, selection.length() - 5);
        selection += ")";
        return selection;

    }

    public static String delete(String table, String column, Object value) {
        String v;
        if (value instanceof String)
            v = "'" + value + "'";
        else
            v = value + "";
        return "delete from " + table + " where " + column + "=" + v;
    }

}
