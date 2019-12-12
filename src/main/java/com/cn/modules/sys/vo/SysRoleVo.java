package com.cn.modules.sys.vo;

import com.cn.modules.sys.entity.SysRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* 角色 entity 对象实体类
* @author zhangheng
* @date 2019-05-07 11:00:18
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysRoleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SysRoleEntity> roleEntities;
    /**
    * CreateUserId
    */
    private Long userId;


}


