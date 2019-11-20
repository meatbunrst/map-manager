package com.cn.common.factory;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.constant.state.Order;
import com.cn.common.utils.HttpKit;
import com.cn.common.utils.ToolUtil;

import javax.servlet.http.HttpServletRequest;

//

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author zhangheng
 * @since  2017-04-05 22:25
 */
public class PageFactory<T> {



    public Page<T> defaultPage() {

        HttpServletRequest request = HttpKit.getRequest();
//        添加默认值，方便测试。
        long limit = (StrUtil.isBlank(request.getParameter("limit"))?20:Integer.valueOf(request.getParameter("limit")));
        //已经不用了
        long offset = (StrUtil.isBlank(request.getParameter("offset"))?0:Integer.valueOf(request.getParameter("offset")));

        long current = (StrUtil.isBlank(request.getParameter("page"))?1:Integer.valueOf(request.getParameter("page")));
        //测试

        String sort = request.getParameter("sort");
        if (StrUtil.isNotEmpty(sort)){
            sort = ToolUtil.HumpToUnderline(sort);
        }
        String order = (StrUtil.isBlank(request.getParameter("order"))?"descending":request.getParameter("order"));
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<T>(current, limit);
            return page;
        } else {
            Page<T> page = new Page<T>(current, limit, true);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(sort);
            } else {
                page.setDesc(sort);
            }
            return page;
        }
    }
}
