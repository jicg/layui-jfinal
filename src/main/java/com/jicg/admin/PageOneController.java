package com.jicg.admin;

import com.alibaba.druid.util.StringUtils;
import com.jicg.common.model.User;
import com.jicg.common.model.WebsiteData;
import com.jicg.common.vo.Result;

import java.util.List;

/**
 * Created by jicg on 2018/4/8.
 */
public class PageOneController extends BaseController {
    public void list() {
        List<WebsiteData> list =
                WebsiteData.dao.find("select * from website_data where type = 1");
        renderJson(new Result(0, "0k", list));
    }

    public void pageadd() {
        render("/view/admin/page/website/addTpl.html");
    }

    public void add() {
        int id = getParaToInt("id", -1);
        int datatype = getParaToInt("datatype", 0);
        String name = getPara("name", "");
        if (StringUtils.isEmpty(name)) {
            renderJson(new Result(Result.ERROR_CODE, "名称不能为空！"));
            return;
        }
        String value = getPara("value", "");
        if (StringUtils.isEmpty(value)) {
            renderJson(new Result(Result.ERROR_CODE, "值不能为空"));
            return;
        }
        String remark = getPara("remark", "");
        WebsiteData websiteData = null;
        if (id <= 0) {
            websiteData = new WebsiteData().setName(name).setType(1);
        } else {
            websiteData = WebsiteData.dao.findById(id);
        }
        websiteData.setValue(value).setDatatype(datatype).setRemark(remark);
        if (id <= 0) {
            if (websiteData.save()) {
                renderJson(new Result(Result.OK_CODE, "新增成功！"));
            } else {
                renderJson(new Result(Result.ERROR_CODE, "新增失败！"));
            }
        } else {
            if (websiteData.update()) {
                renderJson(new Result(Result.OK_CODE, "修改成功！"));
            } else {
                renderJson(new Result(Result.ERROR_CODE, "修改失败"));
            }
        }
    }

    public void del() {
        final WebsiteData websiteData = WebsiteData.dao.findById(getParaToInt("id", 0));
        User user = getUser();
        if (!"admin".equals(user.getName())) {
            renderJson(new Result(Result.ERROR_CODE, "不允许删除！"));
            return;
        }
        if (!websiteData.delete()) {
            renderJson(new Result(Result.ERROR_CODE, "删除失败！"));
            return;
        }
        renderJson(new Result(Result.OK_CODE, "删除成功！"));
    }
}
