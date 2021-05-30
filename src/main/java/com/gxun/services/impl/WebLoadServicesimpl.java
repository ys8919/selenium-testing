package com.gxun.services.impl;

import com.alibaba.fastjson.JSON;
import com.gxun.services.WebLoadServices;
import com.gxun.testing.selenium.WebLoadTest;
import com.gxun.util.ConstantValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WebLoadServicesimpl implements WebLoadServices {
   /* @Autowired
    private WebLoadTest webLoadTest;*/

    @Override
    public String webLoadTest(HashMap<String, Object> u) {
        String url=u.get("url").toString();
        System.out.println(url);
        HashMap<String, Object> msg = new HashMap<>();
        msg.put("data",WebLoadTest.webLoadTestStart(url));
        msg.put("msg","测试成功");
        msg.put("code", ConstantValueUtil.RESCODE_SUCCESS);
        msg.put("flag",true);
        return JSON.toJSONString(msg);
    }
}
