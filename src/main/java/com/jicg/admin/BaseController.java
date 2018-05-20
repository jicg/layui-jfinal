package com.jicg.admin;

import com.jfinal.core.Controller;
import com.jicg.common.model.User;
import com.jicg.common.utils.Consts;

/**
 * Created by jicg on 2018/4/8.
 */
public abstract class BaseController extends Controller {

    protected User getUser() {
        return (User) getSession().getAttribute(Consts.SESSION_USER_KEY);
    }

    protected void setUser(User user) {
        setSessionAttr(Consts.SESSION_USER_KEY, user);
    }
}
