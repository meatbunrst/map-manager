package com.cn.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.annotation.SysLog;
import com.cn.common.constant.Constant;
import com.cn.common.factory.PageFactory;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.Result;
import com.cn.common.utils.ToolUtil;
import com.cn.modules.sys.entity.SysDeptEntity;
import com.cn.modules.sys.service.SysDeptService;
import com.cn.modules.sys.vo.SysDeptVo;
import com.cn.modules.sys.vo.TreeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
* 部门表控制器
*
* @author zhangheng
* @date 2018-10-26 10:45:52
*/
@RestController
@RequestMapping("/sysdept")
public class SysDeptController extends AbstractController {


    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 获取分页信息
     * @param entity 对象
     * @return Object 分页信息
     */
    @GetMapping(value = "/list")
    public Object list(SysDeptEntity entity) {
        Page<SysDeptEntity> page = new PageFactory<SysDeptEntity>().defaultPage();
        page.setRecords(sysDeptService.selectPage(page,entity,new QueryWrapper<>()));

        return Result.ok().put("page", new PageUtils(page));
    }



    /**
     * 获取所有信息
     * @param entity 对象
     * @return Object 所有部门
     */
    @GetMapping(value = "/all")
    public Object all(SysDeptEntity entity) {
       List<SysDeptEntity> list = sysDeptService.selectList(entity);

        return Result.ok().put("list",list);
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("sysdept:add")
    public Object add(@RequestBody SysDeptEntity entity) {
        if (StringUtils.isNoneBlank(entity.getSimplename())){
            if (StrUtil.isBlank(entity.getFullname())){
                entity.setFullname(entity.getSimplename());
            }
            //完善pids,根据pid拿到pid的pids
            deptSetPids(entity);
        }
        sysDeptService.save(entity);
        return Result.ok();
    }

    @GetMapping(value = "/depttree")
    public Object tree(SysDeptEntity entity){

        TreeVo top = new TreeVo("-1","部门管理","fa fa-align-justify",false);
        List<SysDeptEntity> deptModels = sysDeptService.selectList(entity);
        List<TreeVo> jsTreeChildren = new ArrayList<>();
        for (SysDeptEntity res:deptModels) {
            if (StringUtils.isNoneBlank(res.getPid()) && res.getPid().equals("-1")){
                TreeVo node = new TreeVo(res.getId(),res.getFullname(),"fa fa-file-text-o",false);
                node.setChildren(this.getResChrild(deptModels,res));
                jsTreeChildren.add(node);
            }
        }
        top.setChildren(jsTreeChildren);
        return top;
    }

    /**
    * 删除多个
    * @param id 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("sysdept:delete")
    public Object delete(@RequestBody String id) {
        sysDeptService.deleteDept(id);
        return Result.ok();
    }

    /**
     * 更新
     * @param entity 对象
     * @return Object
     */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SysDeptEntity entity) {
        deptSetPids(entity);
        if (StrUtil.isBlank(entity.getFullname())){
            entity.setFullname(entity.getSimplename());
        }
        sysDeptService.updateById(entity);
        return Result.ok().put("sysdept",entity);
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(SysDeptEntity entity) {

        SysDeptEntity deptEntity = (SysDeptEntity) sysDeptService.getById(entity.getId());
        if (StrUtil.isNotBlank(deptEntity.getPid())){
            SysDeptEntity pentity = (SysDeptEntity) sysDeptService.getById(deptEntity.getPid());
            if (pentity != null && StrUtil.isNotBlank(pentity.getSimplename())){
                deptEntity.setpName(pentity.getSimplename());
            }
        }
        return Result.ok().put("sysdept", deptEntity);
    }


    private void deptSetPids(SysDeptEntity dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals("-1")) {
            dept.setPid("-1");
            dept.setPids("[-1],");
        } else {
            String pid = dept.getPid();
            SysDeptEntity temp = (SysDeptEntity) sysDeptService.getById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }

    private List<TreeVo> getResChrild(List<SysDeptEntity> list, SysDeptEntity sysRes){

        List<TreeVo> treeVos = new ArrayList<>();
        for (SysDeptEntity res:list){
            if (StringUtils.isNoneBlank(res.getPid()) && sysRes.getId().equals(res.getPid()) ){
                TreeVo node = new TreeVo(res.getId(),res.getFullname(),"fa fa-file-text-o",false);
                node.setChildren(getResChrild(list,res));
                treeVos.add(node);
            }
        }
        return treeVos;
    }
    /**
     * 删除
     */
    @SysLog("删除所有子部门以及本部门")
    @PostMapping("/deleteAll/{deptId}")
    public Result deleteAll(@PathVariable("deptId") long deptId){
        //判断是否有子部门
        QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PID",deptId);
        List<SysDeptEntity> list = sysDeptService.list(queryWrapper);

        if (list.size() > 0){
            for (SysDeptEntity deptEntity : list) {
                sysDeptService.removeById(deptEntity.getId());
            }
        }
        sysDeptService.removeById(deptId);
        return Result.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改部门")
    @PostMapping("/updateNode")
    public Object updateNode(@RequestBody SysDeptVo nodeVO){
        SysDeptEntity deptEntity = (SysDeptEntity) sysDeptService.getById(nodeVO.getDraggingNodeId());
        if (nodeVO.getDraggingNodeId() != null){
            if( nodeVO.getDropType().equals(Constant.DropStatus.INNER.getValue()) ){

                deptEntity.setPid(nodeVO.getDropNodeId());
                deptSetPids(deptEntity);
                sysDeptService.updateById(deptEntity);

            } else if (nodeVO.getDropType().equals(Constant.DropStatus.BEFORE.getValue())){
                SysDeptEntity entity = (SysDeptEntity) sysDeptService.getById(nodeVO.getDropNodeId());

                deptEntity.setPid(entity.getPid());
                deptSetPids(deptEntity);
                deptEntity.setNum(entity.getNum()+1);
                sysDeptService.updateById(deptEntity);
            } else {

                SysDeptEntity entity = (SysDeptEntity) sysDeptService.getById(nodeVO.getDropNodeId());
                QueryWrapper<SysDeptEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("PID",entity.getPid());

                queryWrapper.ge("NUM",entity.getNum() );
                List<SysDeptEntity> entities = sysDeptService.list(queryWrapper);

                entities.forEach(sysDeptEntity -> {
                    sysDeptEntity.setNum(sysDeptEntity.getNum()+1);
                });
                sysDeptService.updateBatchById(entities);


                deptEntity.setPid(entity.getPid());
                deptSetPids(deptEntity);
                deptEntity.setNum(entity.getNum());
                sysDeptService.updateById(deptEntity);
            }

        }

        return Result.ok();
    }

    /**
     * 详情 根据父节点获取组织架构所有子节点
     * @param
     * @return Object 详细对象
     */
    @GetMapping(value = "/allSon")
    public Object allSon(String fatherId) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setPid(fatherId);
        List<SysDeptEntity> list = sysDeptService.selectList(sysDeptEntity);
        System.out.println(list);
        return Result.ok().put("list",list);
    }


}