package com.cn.common.param.wrapper;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.common.param.enums.EFindType;
import com.cn.common.param.enums.EJoinType;
import com.cn.common.param.enums.EQDType;
import com.cn.common.utils.TableInfoExtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangheng
 * @date 2019-02-17  10:38:00
 */
@Slf4j
public class CustomWrapper<T> {

    private QueryWrapper<T> ew = new QueryWrapper();
    private int sqlParamIndex = 0;
    private StringBuilder sqlWhere = new StringBuilder("");
    private List<String> sqlParams = new ArrayList();
    private Class<T> entityClazz;

    public CustomWrapper(Class<T> clazz) {
        this.entityClazz = clazz;
    }



    public QueryWrapper<T> parseSqlWhere( List<CustomItem> list) {
        if (list.isEmpty()) {
            return this.ew;
        } else {
            boolean isFirst = true;
            for (CustomItem customItem : list) {
                if (isFirst) {
                    this.genSqlWhere(customItem, false);
                    isFirst = false;
                } else {
                    this.genSqlWhere(customItem, true);
                }
            }
            this.ew.apply(this.sqlWhere.toString(), this.sqlParams.toArray());
            log.info("sqlWhere -"+ this.sqlWhere.toString());
            log.info("sqlParams - " + JSONUtil.toJsonStr(this.sqlParams));
            return this.ew;
        }


    }

    private void genSqlWhere(CustomItem customItem,  boolean isAddJsonType) {
        EJoinType joinType = customItem.getJoinType();
        CustomItem[] conditions = customItem.getConditions();
        if (isAddJsonType) {
            if (joinType == EJoinType.Or) {
                this.sqlWhere.append(" OR ");
            } else {
                this.sqlWhere.append(" AND ");
            }
        }
        //        转换为查询的
        String name = customItem.getName();
        EQDType qDType = customItem.getDataType();
        ColumnInfo cInfo = TableInfoExtHelper.getColumnInfoByProperty(name, this.entityClazz);
        if (cInfo != null) {
            name = cInfo.getColumn();
            if (qDType == EQDType.Default && cInfo.getPropertyType() == "java.util.Date") {
                qDType = EQDType.Date;
            }
        }


        String[] values = customItem.getValues();
        EFindType findType = customItem.getFindType();

        if (qDType == EQDType.Date) {
            name = String.format("DATE_FORMAT(%s, '%Y-%m-%d')", name);
        } else if (qDType == EQDType.Time) {
            name = String.format("DATE_FORMAT(%s, '%Y-%m-%d %H:%i:%S')", name);
        }
        String itemWhereSql = "";
        String formatStr;
        if (findType == EFindType.BETWEEN) {
            formatStr = "%s BETWEEN {%s} AND {%s}";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 2);
        } else if (findType == EFindType.EQ) {
            formatStr = " %s = {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.NOT_EQ) {
            formatStr = " %s != {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.GT) {
            formatStr = " %s > {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.GTE) {
            formatStr = " %s >= {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.LT) {
            formatStr = " %s < {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.LTE) {
            formatStr = " %s <= {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1);
        } else if (findType == EFindType.LIKE) {
            formatStr = " %s LIKE {%s} ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1, "%", "%");
        } else if (findType == EFindType.NOT_LIKE) {
            formatStr = " %s NOT LIKE %{%s}% ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 1, "%", "%");
        } else if (findType == EFindType.IN) {
            formatStr = " %s IN (%s) ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 3);
        } else if (findType == EFindType.NOT_IN) {
            formatStr = " %s NOT IN (%s) ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 3);
        } else if (findType == EFindType.IS_NULL) {
            formatStr = " %s IS NULL";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 4);
        } else if (findType == EFindType.IS_NOT_NULL) {
            formatStr = " %s IS NOT NULL ";
            itemWhereSql = this.formatInnerWhereSql(formatStr, name, qDType, values, 4);
        }

        if (!StringUtils.isEmpty(itemWhereSql)) {
            this.sqlWhere.append(itemWhereSql);
        }

    }

    String formatInnerWhereSql(String formatStr, String name, EQDType qDtype, String[] values, int type) {
        return this.formatInnerWhereSql(formatStr, name, qDtype, values, type, "", "");
    }

    String formatInnerWhereSql(String formatStr, String name, EQDType qDtype, String[] values, int type, String start, String end) {
        String whereSql = "";
        if (type == 1) {
            if (values != null && values.length >= 1) {
                this.sqlParams.add(String.format("%s%s%s", start, values[0], end));
                whereSql = whereSql + String.format(formatStr, name, this.sqlParamIndex);
                ++this.sqlParamIndex;
            }
        } else if (type == 2) {
            if (values != null && values.length >= 2) {
                int tempIndex = this.sqlParamIndex++;
                this.sqlParams.add(String.format("%s%s%s", start, values[0], end));
                this.sqlParams.add(String.format("%s%s%s", start, values[1], end));
                whereSql = whereSql + String.format(formatStr, name, tempIndex, this.sqlParamIndex);
                ++this.sqlParamIndex;
            }
        } else if (type == 3) {
            String tempStr = "";

            for(int i = 0; i < values.length; ++i) {
                if (tempStr != "") {
                    tempStr = tempStr + ",";
                }

                tempStr = tempStr + String.format("{%s}", this.sqlParamIndex);
                this.sqlParams.add(String.format("%s%s%s", start, values[i], end));
                ++this.sqlParamIndex;
            }

            whereSql = whereSql + String.format(formatStr, name, tempStr);
        } else if (type == 4) {
            whereSql = whereSql + String.format(formatStr, name);
        }

        return whereSql;
    }
}
