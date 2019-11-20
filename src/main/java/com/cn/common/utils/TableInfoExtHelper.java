//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.utils;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.cn.common.param.wrapper.ColumnInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dgzhangheng
 */
public class TableInfoExtHelper {
    public TableInfoExtHelper() {
    }

    public static ColumnInfo getColumnInfoByProperty(String property, Class<?> clazz) {
        ColumnInfo cInfo = null;
        List<ColumnInfo> cList = getColumnInfos(clazz);
        Iterator var4 = cList.iterator();

        ColumnInfo c;
        do {
            if (!var4.hasNext()) {
                return (ColumnInfo)cInfo;
            }
            c = (ColumnInfo)var4.next();
        } while(!c.getProperty().equalsIgnoreCase(property));

        return c;
    }

    public static List<ColumnInfo> getColumnInfos(Class<?> clazz) {
        TableInfo tbInfo = TableInfoHelper.getTableInfo(clazz);
        List<TableFieldInfo> fList = tbInfo.getFieldList();
        List<ColumnInfo> cList = new ArrayList();
        ColumnInfo keyColumn = new ColumnInfo();
        keyColumn.setColumn(tbInfo.getKeyColumn());
        keyColumn.setProperty(tbInfo.getKeyProperty());
        keyColumn.setPropertyType(tbInfo.getIdType().toString());
        cList.add(keyColumn);
        Iterator var5 = fList.iterator();

        while(var5.hasNext()) {
            TableFieldInfo f = (TableFieldInfo)var5.next();
            ColumnInfo cInfo = new ColumnInfo();
            cInfo.setColumn(f.getColumn());
            cInfo.setProperty(f.getProperty());
            cInfo.setPropertyType(f.getPropertyType().getTypeName());
            cList.add(cInfo);
        }

        return cList;
    }

    public static ColumnInfo getKeyColumnInfo(Class<?> clazz) {
        TableInfo tbInfo = TableInfoHelper.getTableInfo(clazz);
        ColumnInfo keyColumn = new ColumnInfo();
        keyColumn.setColumn(tbInfo.getKeyColumn());
        keyColumn.setProperty(tbInfo.getKeyProperty());
        keyColumn.setPropertyType(tbInfo.getIdType().toString());
        return keyColumn;
    }
}
