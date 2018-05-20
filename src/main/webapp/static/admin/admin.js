layui.extend({
    comm: 'lib/comm'
}).use(['element', 'comm', 'jquery', 'code', 'layer', 'form'], function () {
    var element = layui.element,
        $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        comm = layui.comm,
        extend = [
            "treeGrid"
        ],
        nav = "",
        side = "";
    layui.code();
    var index;
    $("#change-pwd-btn").click(function () {
        index = layer.open({
            type: 1,
            anim: 0,
            shadeClose: true,
            area: ['500px', '250px'],
            skin: 'layui-layer-demo',
            content: $('#show-change-pwd-view').html()
        });
    });

    form.verify({
        pass: [/(.+){6,12}$/, '密码必须6到12位'],
    });

    form.on('submit(changePwd)', function (data) {
        if (!data.field.password || data.field.password !== data.field.password2) {
            comm.sayError('输入的密码不一致！');
            return false;
        }
        comm.post(
            "admin/changePwd",
            {pwd: data.field.password}
        ).success(function (ret) {
            comm.sayOk('修改成功！');
        }).complete(function () {
            layer.close(index);
        }).run();

        return false;
    });

    element.on('nav(nav)', function (data) {
        var href = $(data.context).attr("lay-href") || "";
        if (!href) {
            return;
        }
        loadSide(href)
    });
    element.on('nav(side)', function (data) {
        var href = $(data.context).attr("lay-href") || "";
        if (!href) {
            return;
        }
        loadPage(href)
    });
    $("document").ready(function () {
        loadNav()
    });

    function loadNav() {
        $.get("admin/nav", function (data) {
            $("#app-nav").html(data)
            element.render({elem: "app-nav"});
            if (!nav) {
                loadSide("/website")
            }
        });
    }

    function loadSide(href) {
        if (nav == href) {
            return;
        }
        //加载层
        var index = layer.load(2, {shade: false});
        $.get("admin/side?s=" + href, function (data) {
            layer.close(index);
            $("#app-side").html(data);
            element.render({elem: "app-nav"})
            nav = href;
        });
    }

    function loadPage(href) {
        if (!href) {
            href = "/index"
        }
        if (side == href) {
            return;
        }
        var index = layer.load(2, {shade: false});
        $.get("admin/page?s=" + href, function (data) {
            layer.close(index);
            $("#app-body").html(data);
            side = href;
        });
    }


    layui.each(extend, function (index, item) {
        var mods = {};
        mods[item] = '{/}' + layui.cache.base + 'lib/extend/' + item;
        layui.extend(mods);
    });
});