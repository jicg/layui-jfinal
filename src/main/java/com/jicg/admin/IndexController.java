package com.jicg.admin;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.aop.Clear;
import com.jicg.common.model.Mean;
import com.jicg.common.model.User;
import com.jicg.common.vo.Result;
import com.jicg.common.vo.SideMean;

import java.util.ArrayList;
import java.util.List;

/**
 * IndexController
 */
public class IndexController extends BaseController {
    public void index() {
        System.out.println(getSession().getServletContext().getRealPath("/"));
        setAttr("user", getUser());
        render("index.html");
    }

    public void changePwd() {
        String pwd = getPara("pwd");
        if (StringUtils.isEmpty(pwd) || pwd.length() < 6) {
            renderJson(new Result(Result.ERROR_CODE, "密码非法【必须大于等于六位】！"));
            return;
        }
        User user = getUser();
        user.setPwd(pwd);
        if (user.update()) {
            renderJson(new Result(Result.OK_CODE, "密码修改成功！"));
        } else {
            renderJson(new Result(Result.ERROR_CODE, "密码修改失败！"));
        }
    }

    @Clear
    public void login() {
        if ("GET".equals(getRequest().getMethod())) {
            render("login.html");
            return;
        }
        String username = getPara("user");
        String pwd = getPara("password");
        List<User> users = User.dao.find("select * from user where email =? and pwd=?", username, pwd);
        if (users.size() > 0) {
            setUser(users.get(0));
            renderJson(new Result(Result.OK_CODE, "登陆成功！", "admin/index"));
        } else {
            renderJson(new Result(Result.ERROR_CODE, "用户不存在！"));
        }
    }

    public void logout() {
        setUser(null);
        redirect("/admin/login");
    }


    /**
     * page------------------------------------
     */
    public void nav() {
        User user = getUser();
        List<Mean> meanList = null;
        if ("admin".equals(user.getName())) {
            meanList = Mean.dao.find("select * from mean where pid = ? order by id desc", 0);
        } else {
            meanList = Mean.dao.find("select * from mean where pid = ? and issys!=0 order by id desc", 0);
        }
        setAttr("means", meanList);
        render("nav.html");
    }

    public void side() {
        List<Mean> meanList = Mean.dao.find("select * from mean where action = ?", getPara("s"));
        if (meanList.size() == 0) {
            render("side.html");
            return;
        }
        int id = meanList.get(0).getId();
        meanList = Mean.dao.find("select * from mean where pid = ?", id);
        List<SideMean> sideMeens = new ArrayList<SideMean>();
        for (Mean mean : meanList) {
            List<Mean> meanList2 = Mean.dao.find("select * from mean where pid = ?", mean.getId());
            sideMeens.add(new SideMean(mean, meanList2));
        }
        setAttr("sideMeens", sideMeens);
        render("side.html");
    }

    public void page() {
        render("page" + getPara("s") + ".html");
    }
}



