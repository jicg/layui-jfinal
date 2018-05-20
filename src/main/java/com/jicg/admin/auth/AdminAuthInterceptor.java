package com.jicg.admin.auth;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jicg.common.model.User;
import com.jicg.common.utils.Consts;
import com.jicg.common.vo.Result;

import javax.servlet.http.HttpSession;

/**
 * Created by jicg on 2018/4/7.
 */
public class AdminAuthInterceptor implements Interceptor {


    public void intercept(Invocation ai) {
        HttpSession session = ai.getController().getSession();
        System.out.println("session" + session.getId());
        if (session == null) {
            ai.getController().redirect("/admin/login");
        } else {
            User user = (User) session.getAttribute(Consts.SESSION_USER_KEY);
            if (user != null && user.getId() > 0) {
                try {
                    ai.invoke();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (ai.getActionKey().contains("/admin/page")) {
                        ai.getController().renderHtml("<div>" + e.getMessage() + "</div>");
                    } else {
                        ai.getController().renderJson(new Result(Result.ERROR_CODE, "系统错误：" + e.getMessage()));
                    }
                }
            } else {
                if (ai.getActionKey().contains("/admin/page")) {
                    ai.getController().renderHtml("<script>window.location.href='/admin/login';</script>");
                } else {
                    ai.getController().redirect("/admin/login");
                }
            }
        }
    }
}
