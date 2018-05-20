;layui.define(['element', 'jquery', 'layer'], function (exports) {
    var $ = layui.$,
        element = layui.element,
        layer = layui.layer,
        ajaxObj = {},
        comm = {};


    function ajax(url, type, data, success, error, complete) {
        var error2 = function (ret) {
            var msg = ret.responseText || ret.msg;
            if (ret.status != undefined && ret.status == 0) {
                msg = "网络异常";
            }
            if (!error) {
                comm.sayError(msg);
            } else {
                error(msg)
            }
        };
        $.ajax({
            url: url,
            type: type,
            data: data,
            success: function (ret) {
                if (ret.code == 0) {
                    success(ret)
                } else {
                    error2(ret)
                }
            },
            error: error2,
            complete: complete
        });
    }


    comm.get = function (url, data) {
        ajaxObj.url = url;
        ajaxObj.data = data;
        ajaxObj.method = "GET";
        return this;
    };


    comm.post = function (url, data) {
        ajaxObj.url = url;
        ajaxObj.data = data;
        ajaxObj.method = "POST";
        return this;
    };

    comm.success = function (success) {
        ajaxObj.success = success;
        return this;
    };

    comm.error = function (error) {
        ajaxObj.error = error;
        return this;
    };

    comm.complete = function (complete) {
        ajaxObj.complete = complete;
        return this;
    };

    comm.run = function () {
        ajax(ajaxObj.url, ajaxObj.method, ajaxObj.data, ajaxObj.success, ajaxObj.error, ajaxObj.complete);
    };

    comm.sayOk = function (msg) {
        layer.msg(msg, {icon: 6});
    };
    comm.sayError = function (msg) {
        layer.msg(msg, {icon: 5})
    };

    exports('comm', comm);
});