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

package com.cn.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.common.annotation.SysLog;
import com.cn.common.constant.Constant;
import com.cn.common.exception.RRException;
import com.cn.common.utils.R;
import com.cn.common.utils.RedisUtils;
import com.cn.modules.sys.entity.SysMenuEntity;
import com.cn.modules.sys.service.SysMenuService;
import com.cn.modules.sys.service.SystemUserService;
import com.cn.modules.sys.vo.MenuDTO;
import com.cn.modules.sys.vo.MenuItemNodeVO;
import com.cn.modules.sys.vo.MenuVo;
import com.cn.modules.sys.vo.TreeVo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 系统菜单
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SystemUserService shiroService;


	@Autowired
	private RedisUtils redisUtils;
	public static final String MENU = "MENU_";

	public static final String PERMISSION = "PERMISSION_";

	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public R nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}

	/**
	 * 构建前端路由所需要的菜单
	 * @return
	 */
	@GetMapping(value = "/build")
	public Object buildMenus(){

		List<MenuVo> voList = Lists.newArrayList();
		Set<String> permissions = new HashSet<>();
		if (!redisUtils.exists(MENU+getUserId()) || !redisUtils.exists(PERMISSION+getUserId())){
			List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList(getUserId());
			List<MenuDTO> vos = sysMenuService.buildTree(menuList);
			permissions = shiroService.getUserPermissions(getUserId());

			voList = sysMenuService.buildMenus(vos);

			redisUtils.set(PERMISSION+getUserId(),permissions);
			redisUtils.set(MENU+getUserId(),voList);

		} else {
			voList =  redisUtils.get(MENU+getUserId(),List.class);
			permissions =  redisUtils.get(PERMISSION+getUserId(),Set.class);
		}
		return R.ok().put("menuList", voList).put("permissions", permissions);
	}
	/**
	 * 所有菜单列表
	 */
	@GetMapping("/list")
	public List<SysMenuEntity> list(){
		QueryWrapper<SysMenuEntity> sysMenuEntityQueryWrapper = new QueryWrapper<>();
		sysMenuEntityQueryWrapper.orderByDesc("order_num");
		List<SysMenuEntity> menuList = sysMenuService.list(sysMenuEntityQueryWrapper);

		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = (SysMenuEntity) sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}
		return menuList;
	}


	/**
	 * 导航菜单
	 */
	@GetMapping("/mainNav")
	public R mainNav(){
		List<SysMenuEntity> menuList = Lists.newArrayList();
		if(getUserId() == com.cn.common.utils.Constant.SUPER_ADMIN){
			QueryWrapper<SysMenuEntity> sysMenuEntityQueryWrapper = new QueryWrapper<>();
			sysMenuEntityQueryWrapper.eq("PARENT_ID","0");
			sysMenuEntityQueryWrapper.orderByDesc("order_num");
			menuList = sysMenuService.list(sysMenuEntityQueryWrapper);
		} else {
			menuList = sysMenuService.getUserMainMenu(getUserId());
		}

		return R.ok().put("list",menuList);
	}
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@GetMapping("/select")
	public R select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList(getUserId());
		
		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = (SysMenuEntity) sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	public R save(@RequestBody SysMenuEntity menu){
		//数据校验
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);
		//数据校验
		if (menu.getParentId().equals(-1L)){
			menu.setParentId(0L);
		}
		sysMenuService.save(menu);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@PostMapping("/deleteAll/{menuId}")
	public R deleteAll(@PathVariable("menuId") long menuId){

		//数据校验
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);
		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			List<Long> longs = Lists.newArrayList();
			for (SysMenuEntity menuEntity : menuList) {
				longs.add(menuEntity.getMenuId());
			}

			sysMenuService.removeByIds(longs);
		}
		sysMenuService.delete(menuId);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	public R update(@RequestBody SysMenuEntity menu){
		//数据校验
//		verifyForm(menu);
		//数据校验
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);
		sysMenuService.updateById(menu);
		return R.ok().put("menu", menu);
	}

	@SysLog("新建菜单")
	@PostMapping("/add")
    @RequiresPermissions("test:add")
	public R add(@RequestBody SysMenuEntity menu){
		//数据校验
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);
		//数据校验
		menu.setType(1);
		if (StringUtils.isBlank(menu.getStatus())){
			menu.setStatus("1");
		}
		if (menu.getParentId()!= null && menu.getParentId() == -1L ){
			menu.setParentId(0L);
		}
		sysMenuService.save(menu);
		return R.ok().put("menu", menu);
	}

	@GetMapping("/all")
	public Object all(){
		QueryWrapper<SysMenuEntity> sysMenuEntityQueryWrapper = new QueryWrapper<>();
//		sysMenuEntityQueryWrapper.ne("TYPE",2);
		sysMenuEntityQueryWrapper.orderByDesc("order_num");
		List<SysMenuEntity> menuList = sysMenuService.list(sysMenuEntityQueryWrapper);
		List<TreeVo> jsTreeChildren = new ArrayList<>();
		TreeVo top = new TreeVo("-1","菜单管理","",false);
		for (SysMenuEntity entity : menuList) {
			if (entity.getParentId() != null && entity.getParentId() == 0){
				TreeVo node = new TreeVo(String.valueOf(entity.getMenuId()),entity.getName(),"fa fa-file-text-o",false);
				node.setChildren(this.getResChrild(menuList,entity));
				jsTreeChildren.add(node);
			}
			top.setChildren(jsTreeChildren);
		}

		return top;
	}

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/updateNode")
    public Object updateNode(@RequestBody MenuItemNodeVO nodeVO){
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);
        UpdateWrapper<SysMenuEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("MENU_ID",nodeVO.getDraggingNodeId());
        SysMenuEntity menuEntity = (SysMenuEntity) sysMenuService.getById(nodeVO.getDraggingNodeId());

        if (nodeVO.getDraggingNodeId() != null){
            if( nodeVO.getDropType().equals(Constant.DropStatus.INNER.getValue()) ){

                if (nodeVO.getDropNodeId() == -1){
                    updateWrapper.set("PARENT_ID",0);
                    sysMenuService.update(updateWrapper);
                }else {

                    updateWrapper.set("PARENT_ID",nodeVO.getDropNodeId());
                    sysMenuService.update(updateWrapper);

                    UpdateWrapper<SysMenuEntity> updateParentWrapper = new UpdateWrapper<>();
                    updateParentWrapper.eq("MENU_ID",nodeVO.getDropNodeId());
                    updateParentWrapper.set("TYPE",0);

                    sysMenuService.update(updateParentWrapper);
                }

            } else if (nodeVO.getDropType().equals(Constant.DropStatus.BEFORE.getValue())){
                SysMenuEntity entity = (SysMenuEntity) sysMenuService.getById(nodeVO.getDropNodeId());
                updateWrapper.set("PARENT_ID",entity.getParentId());

                updateWrapper.set("ORDER_NUM",entity.getOrderNum()+1);
                sysMenuService.update(updateWrapper);
            } else {

                SysMenuEntity entity = (SysMenuEntity) sysMenuService.getById(nodeVO.getDropNodeId());
                QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("PARENT_ID",entity.getParentId());

                queryWrapper.ge("ORDER_NUM",entity.getOrderNum() );
                List<SysMenuEntity> entities = sysMenuService.list(queryWrapper);

                entities.forEach(sysMenuEntity -> {
                    sysMenuEntity.setOrderNum(sysMenuEntity.getOrderNum()+1);
                });

                updateWrapper.set("PARENT_ID",entity.getParentId());

                updateWrapper.set("ORDER_NUM",entity.getOrderNum());
                sysMenuService.update(updateWrapper);
                sysMenuService.updateBatchById(entities);
            }
//            最后结束所有的
            QueryWrapper<SysMenuEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PARENT_ID",menuEntity.getParentId());

            List<SysMenuEntity> entities = sysMenuService.list(queryWrapper);
            if (entities.isEmpty()){

                UpdateWrapper<SysMenuEntity> entityUpdateWrapper = new UpdateWrapper<>();
                entityUpdateWrapper.eq("MENU_ID",menuEntity.getParentId());
                entityUpdateWrapper.set("TYPE",1);
                sysMenuService.update(entityUpdateWrapper);
            }
        }

        return R.ok();
    }

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@PostMapping("/delete/{menuId}")
	public R delete(@PathVariable("menuId") long menuId){
		redisUtils.deleteKeys(PERMISSION);
		redisUtils.deleteKeys(MENU);

		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);
		return R.ok();
	}


	private List<TreeVo> getResChrild(List<SysMenuEntity> list, SysMenuEntity sysRes){
		List<TreeVo> treeVos = new ArrayList<>();
		for (SysMenuEntity res:list){
			if (res.getParentId() != null && sysRes.getMenuId().equals(res.getParentId())  ){
				/*if (res.getParentId() == 1118541535952863233L){
					System.out.println(res.getParentId());
				}*/
				TreeVo node = new TreeVo(String.valueOf(res.getMenuId()),res.getName(),"",false);
				node.setChildren(getResChrild(list,res));
				treeVos.add(node);
			}
		}
		return treeVos;
	}

	@GetMapping("/roleall")
	public Object roleall(){
		QueryWrapper<SysMenuEntity> sysMenuEntityQueryWrapper = new QueryWrapper<>();
		sysMenuEntityQueryWrapper.orderByDesc("order_num");
		List<SysMenuEntity> menuList = sysMenuService.list(sysMenuEntityQueryWrapper);
		List<TreeVo> jsTreeChildren = new ArrayList<>();
		for (SysMenuEntity entity : menuList) {
			if (entity.getParentId() != null && entity.getParentId() == 0){
				TreeVo node = new TreeVo(String.valueOf(entity.getMenuId()),entity.getName(),"fa fa-file-text-o",false);
				node.setChildren(this.getResChrild(menuList,entity));
				jsTreeChildren.add(node);
			}
		}
		return jsTreeChildren;
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == com.cn.common.utils.Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = com.cn.common.utils.Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = (SysMenuEntity) sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == com.cn.common.utils.Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == com.cn.common.utils.Constant.MenuType.MENU.getValue()){
			if(parentType != com.cn.common.utils.Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == com.cn.common.utils.Constant.MenuType.BUTTON.getValue()){
			if(parentType != com.cn.common.utils.Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
