package com.cn.modules.sys.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.exception.RRException;
import com.cn.common.factory.PageFactory;
import com.cn.common.param.wrapper.CustomItem;
import com.cn.common.param.wrapper.CustomWrapper;
import com.cn.common.utils.PageUtils;
import com.cn.common.utils.R;
import com.cn.modules.sys.entity.BatchAttachEntity;
import com.cn.modules.sys.service.BatchAttachService;
import com.cn.modules.sys.utils.AttachUtils;
import com.cn.modules.sys.utils.DownloadUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
/**
* 基本附件表控制器
*
* @author zhangheng
* @date 2019-07-26 14:43:48
*/
@RestController
@RequestMapping("/batchattach")
@Slf4j
public class BatchAttachController extends AbstractController {

    @Autowired
    private BatchAttachService batchAttachService;

    @Value("${map.listFile}")
    private String listFile;
    /**
    * 获取分页信息
    * @param json 对象
    * @return Object 分页信息
    */
    @GetMapping(value = "/list")
    public Object list(String json) {

        Page<BatchAttachEntity> page = new PageFactory<BatchAttachEntity>().defaultPage();
        List<CustomItem> list = JSONUtil.parseArray(json).toList(CustomItem.class);
        QueryWrapper<BatchAttachEntity> entityQueryWrapper = (new CustomWrapper(BatchAttachEntity.class)).parseSqlWhere(list);
        page.setRecords( batchAttachService.selectPage(page,entityQueryWrapper));

        return R.ok().put("page", new PageUtils(page));
    }


    /**
    * 新增
    * @param entity 对象
    * @return Object 执行结果
    */
    @PostMapping(value = "/add")
    @RequiresPermissions("batchattach:add")
    public Object add(@RequestBody BatchAttachEntity entity) {
        batchAttachService.save(entity);
        return R.ok();
    }

    /**
    * 删除多个
    * @param ids 主键
    * @return Object 执行结果
    */
    @PostMapping(value = "/delete")
    @RequiresPermissions("batchattach:delete")
    public Object delete(@RequestBody String[] ids) {
        batchAttachService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 更新
    * @param entity 对象
    * @return
    */
    @PostMapping(value = "/update")
    public Object update(@RequestBody BatchAttachEntity entity) {
        batchAttachService.updateById(entity);
        return R.ok();
    }

    /**
     * 上传文件
     * @param file  文件
     * @return 对象
     * @throws Exception
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        BatchAttachEntity entity = AttachUtils.saveTmpFile(file);
        return R.ok().put("batch",entity);
    }

    /**
     * 上传文件
     * @param file  文件
     * @return 对象
     * @throws Exception
     */
    @PostMapping("/multiupload")
    public R Multi(@RequestParam("file") MultipartFile[] file) throws Exception {

        List<BatchAttachEntity> entities = Lists.newArrayList();
        if (file.length > 0){
            for (MultipartFile multipartFile : file) {
                BatchAttachEntity entity = AttachUtils.saveTmpFile(multipartFile);
                entities.add(entity);
            }
        }
        return R.ok().put("batch",entities);
    }

    /**
     * 通用下载文件接口
     * @param request
     * @param response
     * @param id 文件id
     */
    @GetMapping(value="/download")
    public void download(HttpServletRequest request,
                             HttpServletResponse response,@RequestParam("id")String id) {

        BatchAttachEntity attach = (BatchAttachEntity) batchAttachService.getById(id);

        if (!FileUtil.exist(attach.getLocalSavePath())&&!AttachUtils.getDownloadFtp(id)){

        }
        DownloadUtils.downloadFile(request,response,attach.getUploadFileName(),attach.getLocalSavePath());
    }


    /**
     * 通用下载文件接口
     * @param request
     * @param response
     * @param name 文件id
     */
    @GetMapping(value="/downloadFile")
    public void downloadFile(HttpServletRequest request,
                             HttpServletResponse response,@RequestParam("name")String name) {

        String localPath = listFile + name;

//        if (!FileUtil.exist(attach.getLocalSavePath())&&!AttachUtils.getDownloadFtp(id)){
//
//        }
        DownloadUtils.downloadFile(request,response,name,localPath);
    }

    @RequestMapping(value="/viewImageFile")
    public void viewImageFile(HttpServletRequest request,
                          HttpServletResponse response,@RequestParam("name")String name) throws IOException {
        ServletContext cntx= request.getServletContext();
        String localFullPath =listFile + name;
        File localFile = new File(localFullPath);
        if (!localFile.exists()) {
            // 文件不不存在去FTP下载

        }
        String mime = cntx.getMimeType(localFullPath);
        if (mime == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        response.setContentLength((int)localFile.length());
        response.setContentType(mime);
        FileInputStream in = new FileInputStream(localFile);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
    }

    /**
    * 详情
    * @param entity 对象
    * @return Object 详细对象
    */
    @GetMapping(value = "/info")
    public Object info(BatchAttachEntity entity) {
        return R.ok().put("batchattach", batchAttachService.selectOne(entity));
    }

    @RequestMapping(value="/viewImage")
    public void viewImage(HttpServletRequest request,
                          HttpServletResponse response,@RequestParam("id")String id) throws IOException {
        ServletContext cntx= request.getServletContext();
        BatchAttachEntity attach = (BatchAttachEntity) batchAttachService.getById(id);
        String localFullPath = attach.getLocalSavePath();
        File localFile = new File(localFullPath);
        if (!localFile.exists()) {
            // 文件不不存在去FTP下载

        }
        String mime = cntx.getMimeType(localFullPath);
        if (mime == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        response.setContentLength((int)localFile.length());
        response.setContentType(mime);
        FileInputStream in = new FileInputStream(localFile);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();
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
            /* params.setVerifyHandler(new ExcelVerifyHandlerImpl());
            * 自定义的验证
            * */
            ExcelImportResult<BatchAttachEntity> result = ExcelImportUtil.importExcelMore(
                    inputStream,BatchAttachEntity.class, params);
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
                batchAttachService.saveBatch(result.getList());
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if (flag){
            return R.ok();
        }else {
            return R.error("导入错误").put("batch",model);
        }
    }
}