package com.jicg.front;

import com.jfinal.core.Controller;
import com.jicg.common.model.WebsiteData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本 jicg 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * <p>
 * IndexController
 */
public class IndexController extends Controller {
    public void index() {
        List<WebsiteData> websiteDatas = WebsiteData.dao.find("select * from website_data where type = 1");
        Map<String, WebsiteData> dataMap = new HashMap<String, WebsiteData>();
        for (WebsiteData websiteData : websiteDatas) {
            dataMap.put(websiteData.getName(), websiteData);
        }
        setAttr("data", dataMap);
        render("index.html");
    }
}



