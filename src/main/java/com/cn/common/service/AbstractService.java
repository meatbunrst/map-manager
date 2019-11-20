package com.cn.common.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.cn.common.entity.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangheng
 * @date 2018-12-05  10:25:00
 */
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
public abstract class AbstractService<D extends BaseMapper<T>,T extends AbstractModel<T>>  extends ServiceImpl {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;
    /**
     * <p>
     * 执行 SQL
     * </p>
     */
    public SqlRunner sql() {
        return new SqlRunner(currentModelClass());
    }


    public T selectOne (T entity){
        return dao.selectOne(new QueryWrapper<>(entity));
    }



}
