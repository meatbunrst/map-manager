/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.cn.modules.sys.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.cn.common.entity.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * 菜单表 entity 对象实体类
 * @author zhangheng
 * @date 2019-05-15 00:19:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SYS_MENU")
public class SysMenuEntity extends AbstractModel<SysMenuEntity> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.ID_WORKER)
	private Long menuId;

	/**
	 * 状态
	 */
	@TableField(value="STATUS")
	private String status;
	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;
	
	/**
	 * 父菜单名称
	 */
	@TableField(exist=false)
	private String parentName;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	private Integer type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;
	
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getMenuId() {
		return menuId;
	}

	@Override
	public void preInsert() {
		Sequence sequence = new Sequence(0, 0);
		this.menuId = sequence.nextId();
	}
	@Override
	public void preUpdate() {

	}
	@JSONField(serialize = false)
	@Override
	public boolean getIsNewRecord() {
		return isNewRecord || getMenuId() == null;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
