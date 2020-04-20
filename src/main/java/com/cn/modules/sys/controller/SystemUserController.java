package com.cn.modules.sys.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.annotation.SysLog;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.Result;
import com.cn.common.utils.ShiroUtils;
import com.cn.common.validator.Assert;
import com.cn.common.validator.ValidatorUtils;
import com.cn.common.validator.group.AddGroup;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.entity.SysUserRoleEntity;
import com.cn.modules.sys.entity.SystemUserEntity;
import com.cn.modules.sys.form.PasswordForm;
import com.cn.modules.sys.service.SysUserRoleService;
import com.cn.modules.sys.service.SystemUserService;
import com.cn.modules.sys.utils.AttachUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
import static com.cn.common.constant.Constant.INIT_PASSWORD;

/**
* 用户信息表控制器
*
* @author zhangheng
* @date 2019-04-30 14:43:36
*/
@RestController
@RequestMapping("/systemuser")
@Slf4j
public class SystemUserController extends AbstractController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {
        Page<SystemUserEntity> page = new PageFactory<SystemUserEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);

        QueryWrapper<SystemUserEntity> entityQueryWrapper = (new CustomWrapper(SystemUserEntity.class)).parseSqlWhere(list);
        page.setRecords( systemUserService.selectPage(page,entityQueryWrapper));
        return Result.ok().put("page", new PageUtils(page));
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public Result password(@RequestBody PasswordForm form){
        Assert.isBlank(form.getNewApp(), "新密码不为能空");

        if (!StrUtil.hasEmpty(form.getNewApp(),form.getApp())){
            form.setNewApp(ShiroUtils.getDecrypt(form.getNewApp(),form.getUuid()));
            form.setApp(ShiroUtils.getDecrypt(form.getApp(),form.getUuid()));
        }else {
            return Result.error("密码输入不正确");
        }


        //sha256加密
        String password = new Sha256Hash(form.getApp(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewApp(), getUser().getSalt()).toHex();
        //更新密码
        boolean flag = systemUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return Result.error("原密码不正确");
        }

        return Result.ok();
    }

    /**
     * 获取分页信息
     * @param entity 对象
     * @return Object 分页信息
     */
    @GetMapping(value = "/listModel")
    public Object list(SystemUserEntity entity) {
        Page<SystemUserEntity> page = new PageFactory<SystemUserEntity>().defaultPage();
        page.setSize(100L);
        page.setRecords( systemUserService.selectPage(page,entity));
        return Result.ok().put("page", new PageUtils(page));
    }

    /**
     * 获取分页信息
     * @param entity 对象
     * @return Object 分页信息
     */
    @GetMapping(value = "/countName")
    public Object countName(SystemUserEntity entity) {
        boolean flag = systemUserService.selectCount(entity) > 0 ;
        return Result.ok().put("flag", flag);
    }

    /**
     * 禁用用户
     * @param ids 主键
     * @return Object 执行结果
     */
    @SysLog("禁用用户")
    @PostMapping(value = "/disable")
    @RequiresPermissions("systemuser:delete")
    public Object disable(@RequestBody List<Long> ids) {

        List<SystemUserEntity>  userEntities = Lists.newArrayList();
        for (Long id : ids) {
            SystemUserEntity userEntity = new SystemUserEntity();
            userEntity.setUserId(id);
            userEntity.setStatus(0L);
            userEntities.add(userEntity);
        }

        systemUserService.updateBatchById(userEntities);
        return Result.ok();
    }


    /**
     * 启用用户
     * @param ids 主键
     * @return Object 执行结果
     */
    @SysLog("启用用户")
    @PostMapping(value = "/able")
    public Object able(@RequestBody List<Long> ids) {

        List<SystemUserEntity>  userEntities = Lists.newArrayList();
        for (Long id : ids) {
            SystemUserEntity userEntity = new SystemUserEntity();
            userEntity.setUserId(id);
            userEntity.setStatus(1L);
            userEntities.add(userEntity);
        }

        systemUserService.updateBatchById(userEntities);
        return Result.ok();
    }

    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    public Object add(@RequestBody SystemUserEntity entity) {

        ValidatorUtils.validateEntity(entity, AddGroup.class);
        entity.setCreateUserId(getUserId());
        entity.setCreateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        entity.setSalt(salt);
        entity.setPassword( new Sha256Hash(entity.getPassword(), salt).toHex());

        systemUserService.save(entity);

        if (entity.getRoleIdList().size() > 0){
            SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
            userRoleEntity.setUserId(entity.getUserId());
            sysUserRoleService.deleteByModel(userRoleEntity);

            List<SysUserRoleEntity> entities = Lists.newArrayList();
            entity.getRoleIdList().forEach(aLong -> {
                SysUserRoleEntity roleEntity = new SysUserRoleEntity();
                roleEntity.setRoleId(aLong);
                roleEntity.setUserId(entity.getUserId());
                entities.add(roleEntity);
            });
            sysUserRoleService.saveBatch(entities);
        }

        return Result.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    public Object delete(@RequestBody Long[] ids) {
        systemUserService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 初始化密码
     * @param ids 主键
     * @return Object 执行结果
     */
    @SysLog("初始化密码")
    @PostMapping(value = "/init")
    @RequiresPermissions("systemuser:delete")
    public Object init(@RequestBody List<Long> ids) {

        List<SystemUserEntity>  userEntities = Lists.newArrayList();
        for (Long id : ids) {
            SystemUserEntity userEntity = (SystemUserEntity) systemUserService.getById(id);
            userEntity.setUserId(id);

            //sha256加密
            String newPassword = new Sha256Hash(INIT_PASSWORD, userEntity.getSalt()).toHex();
            userEntity.setPassword(newPassword);
            userEntities.add(userEntity);
        }

        systemUserService.updateBatchById(userEntities);
        return Result.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody SystemUserEntity entity) {
        entity.setCreateUserId(getUserId());

        if(StringUtils.isBlank(entity.getPassword())){
            entity.setPassword(null);
        }else{
            entity.setPassword(new Sha256Hash(entity.getPassword(), entity.getSalt()).toHex());
        }

        systemUserService.updateById(entity);
        if (entity.getRoleIdList().size() > 0){
            SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
            userRoleEntity.setUserId(entity.getUserId());
            sysUserRoleService.deleteByModel(userRoleEntity);

            List<SysUserRoleEntity> entities = Lists.newArrayList();
            entity.getRoleIdList().forEach(aLong -> {
                SysUserRoleEntity roleEntity = new SysUserRoleEntity();
                roleEntity.setRoleId(aLong);
                roleEntity.setUserId(entity.getUserId());
                entities.add(roleEntity);
            });
            sysUserRoleService.saveBatch(entities);
        }
        return Result.ok();
    }

    /**
     * 详情
     * @param entity 对象
     * @return Object 详细对象
     */
    @GetMapping(value = "/info")
    public Object info(SystemUserEntity entity) {

        entity = systemUserService.selectOne(entity);
        entity.setRoleIdList(sysUserRoleService.queryRoleIdList(entity.getUserId()));
        return Result.ok().put("systemuser", entity);
    }
    /**
    *  基本附件表导入
    * @param file  上传的文件
    * @return
    */
    @RequestMapping(value="/import")
    public Object  importFile(@RequestParam("file")MultipartFile file){

        boolean flag = true;
        BatchAttachEntity model = new BatchAttachEntity();
        try {
            InputStream inputStream = file.getInputStream();
            ImportParams params = new ImportParams();
            params.setNeedVerfiy(true);
            params.setTitleRows(0);
            /* params.   (new ExcelVerifyHandlerImpl());
            * 自定义的验证
            * */
            ExcelImportResult<SystemUserEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream, SystemUserEntity.class, params);
            if (result.isVerfiyFail()){
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < result.getFailList().size(); i++) {
                    buffer.append(ReflectionToStringBuilder.toString(result.getFailList().get(i)));
                }
                String fileName = "导入失败"+ DateUtil.format(new Date(),PURE_DATETIME_PATTERN);
                //保存失败文件到临时目录
                model = AttachUtils.saveTmpFile(result.getFailWorkbook(),fileName);
            } else{
                //	成功的时候
                systemUserService.saveBatch(result.getList());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (flag){
            return Result.ok();
        }else {
            return Result.error("导入错误").put("batch",model);
        }
    }
    /**
    * 导出Excel文件
    * @param request http请求
    * @param response http返回
    * @param json 对象
    */
    @GetMapping(value="/export")
    public void export(HttpServletRequest request, HttpServletResponse response,String json){
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<SystemUserEntity> entityQueryWrapper = (new CustomWrapper(SystemUserEntity.class)).parseSqlWhere(list);

        try {
            int importNum  = systemUserService.count(entityQueryWrapper);
            Workbook workbook = null;
            //第一个是导出的接口
            ExportParams exportParams = new ExportParams();
            if (importNum > EXPORT_MAX){
                for (int i = 0; i < importNum/EXPORT_MAX + 1; i++) {
                    Page<SystemUserEntity> page = new Page<SystemUserEntity>(i+1, EXPORT_MAX);
                    List<SystemUserEntity> result = systemUserService.selectPage(page,entityQueryWrapper);
                    workbook = ExcelExportUtil.exportBigExcel(exportParams, SystemUserEntity.class,result);
                    result.clear();
                }
                ExcelExportUtil.closeExportBigExcel();
            }else {
                Page<SystemUserEntity> page = new Page<SystemUserEntity>(1, EXPORT_MAX);
                List<SystemUserEntity> result = systemUserService.selectPage(page,entityQueryWrapper);
                workbook = ExcelExportUtil.exportExcel(exportParams, SystemUserEntity.class, result);
                }
                String filename = "用户信息表("+DateUtil.today()+")";
                renderExcel(request,response,filename,workbook);
        } catch (Exception e) {
                e.printStackTrace();
                response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write(e.getMessage());
            } catch (Exception e2) {

            }
        }
  }
}