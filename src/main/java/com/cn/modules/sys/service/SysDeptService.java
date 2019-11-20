package com.cn.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cn.common.service.AbstractService;
import com.cn.modules.sys.dao.SysDeptDao;
import com.cn.modules.sys.entity.SysDeptEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
* 部门表Service  业务接口
*
* @author zhangheng
* @date 2018-10-26 10:45:51
*/
@Service
@Transactional(readOnly = true,rollbackFor={RuntimeException.class})
public class SysDeptService extends AbstractService<SysDeptDao,SysDeptEntity> {


    /**
    * <p>
    * 根据 model 条件，查询总记录数
    * </p>
    *
    * @param model 实体对象
    * @return int
    */
    public Integer selectCount(SysDeptEntity model){
        return dao.selectCountByModel(model);
    }

    /**
    * 根据 model 条件，删除
    *
    * @param model 实体对象
    * @return boolean
    */
    @Transactional(rollbackFor={RuntimeException.class})
    public boolean deleteByModel(SysDeptEntity model){
        return SqlHelper.delBool(dao.deleteByModel(model));
    }

    /**
    * <p>
    * 根据 entity 条件，查询全部记录
    * </p>
    *
    * @param model 实体对象封装操作类（可以为 null）
    * @return List<SysDeptEntity>
    */
    public List<SysDeptEntity> selectList(SysDeptEntity model){
        return dao.selectListModel(model);
    }
    /**
    * <p>
    * 根据 entity 条件，查询全部记录（并翻页）
    * </p>
    *
    * @param pagination 分页查询条件
    * @param model   实体对象封装操作可以为 null）
    * @param wrapper   SQL包装
    * @return List<SysDeptEntity>
    */
    public List<SysDeptEntity> selectPage(Page pagination, SysDeptEntity model,Wrapper<SysDeptEntity> wrapper){
        return dao.selectPage(pagination,model,wrapper);
    }

    /**
    * 根据 entity 条件，查询全部记录（并翻页）
    *
    * @param pagination 分页查询条件
    * @param wrapper   SQL包装
    * @return List<SysDeptEntity>
    */
    public List<SysDeptEntity> selectPage(Page pagination,Wrapper<SysDeptEntity> wrapper){
        return dao.queryPage(pagination,wrapper);
    }

    public void deleteDept(String deptId) {
        SysDeptEntity dept = dao.selectById(deptId);

        Wrapper<SysDeptEntity> wrapper = new QueryWrapper<SysDeptEntity>().like("pids", "%[" + dept.getId() + "]%");;
        List<SysDeptEntity> subDepts = dao.selectList(wrapper);
        for (SysDeptEntity temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }

}
