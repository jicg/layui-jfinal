package com.jicg.admin;

import com.alibaba.druid.util.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jicg.common.model.Mean;
import com.jicg.common.vo.Result;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by jicg on 2018/4/9.
 */
public class MeanController extends BaseController {
    public void list() {
        List<Mean> meanList =
                Mean.dao.find("select * from mean where pid = ?", getParaToInt("pid", 0));
        renderJson(new Result(0, "0k", meanList));
    }

    public void del() {
        final Mean mean = Mean.dao.findById(getParaToInt("id", 0));
        if (mean.getIssys() == 0) {
            renderJson(new Result(Result.ERROR_CODE, "系统菜单不允许删除！"));
            return;
        }
        boolean flag = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                if (!mean.delete()) {
                    renderJson(new Result(Result.ERROR_CODE, "删除失败！"));
                    return false;
                }
                if (mean.getLevel() == 2) {
                    Db.delete("delete from mean where pid = ?", mean.getId());
                }
                if (mean.getLevel() == 1) {
                    Db.delete(delsql, mean.getId());
                    Db.delete("delete from mean where pid = ?", mean.getId());
                }
                return true;
            }
        });
        if (flag) {
            renderJson(new Result(Result.OK_CODE, "删除成功！"));
        } else {
            renderJson(new Result(Result.ERROR_CODE, "删除失败！"));
        }
    }


    private static final String delsql = "DELETE a FROM mean a WHERE a.pid IN (SELECT id from (select id from mean  where pid = ? ) b)";

    public void add() {
        int id = getParaToInt("id", -1);
        String name = getPara("name", "");
        if (StringUtils.isEmpty(name)) {
            renderJson(new Result(Result.ERROR_CODE, "菜单名称不能为空！"));
            return;
        }
        String action = getPara("action", "");
        if (StringUtils.isEmpty(action)) {
            renderJson(new Result(Result.ERROR_CODE, "菜单URL不能为空！"));
            return;
        }
        int pid = getParaToInt("pid", -1);
        int level = 0;
        String pname = "";
        if (pid != 0) {
            Mean mean = Mean.dao.findById(pid);
            if (mean == null || mean.getId() <= 0) {
                renderJson(new Result(Result.ERROR_CODE, "非法父亲节点！"));

                return;
            }
            pname = mean.getName();
            level = mean.getLevel();
        }

        Mean mean = null;
        if (id < 0) {
            mean = new Mean()
                    .setLevel(level + 1);
        } else {
            mean = Mean.dao.findById(id);
        }
        mean.setName(name)
                .setAction(action)
                .setPid(pid)
                .setPname(pname);
        if (mean.getLevel() > 3) {
            renderJson(new Result(Result.ERROR_CODE, "新增失败！不允许超过三级菜单"));
            return;
        }
        if (id <= 0) {
            if (mean.save()) {
                renderJson(new Result(Result.OK_CODE, "新增成功！"));
            } else {
                renderJson(new Result(Result.ERROR_CODE, "新增失败！"));
            }
        } else {
            if (mean.setId(id).update()) {
                renderJson(new Result(Result.OK_CODE, "修改成功！"));
            } else {
                renderJson(new Result(Result.ERROR_CODE, "修改失败"));
            }
        }

    }
}
