package com.gxun.services.impl;

import com.alibaba.fastjson.JSON;
import com.gxun.DAO.WebScriptMapper;
import com.gxun.entity.WebScript;
import com.gxun.services.WebScriptServices;
import com.gxun.testing.crossPlatform.crossPlatform;
import com.gxun.testing.selenium.WebLoadTest;
import com.gxun.util.ConstantValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WebScriptServicesimpl implements WebScriptServices {

    @Autowired
    private WebScriptMapper webScriptMapper;

    @Override
    public String webScriptTest() {
        HashMap<String,Object> msg=new HashMap<String,Object>();
        crossPlatform.crossPlatformStart();
        msg.put("msg","启动成功");
        msg.put("code",ConstantValueUtil.RESCODE_SUCCESS);
        msg.put("flag",true);
        return JSON.toJSONString(msg);

    }
    @Override
    public String queryWebScriptList(HashMap<String, Object> webScript) {


        HashMap<String,Object> msg=new HashMap<String,Object>();
        msg.put("msg","查询成功");
        msg.put("code",ConstantValueUtil.RESCODE_SUCCESS);
        msg.put("flag",true);
        msg.put("data",webScriptMapper.queryWebScriptList(webScript));
        return JSON.toJSONString(msg);
    }

    @Override
    public String selectOneWebScript(WebScript webScript) {
        return null;
    }

    @Override
    public String addWebScript(WebScript webScript) {
        HashMap<String,Object> msg=new HashMap<String,Object>();
        if(webScriptMapper.addWebScript(webScript)>0){
            msg.put("msg","添加成功");
            msg.put("code", ConstantValueUtil.RESCODE_SUCCESS);
            msg.put("flag",true);
        }else
        {
            msg.put("msg","添加失败");
            msg.put("flag",false);
        }
        return JSON.toJSONString(msg);
    }

    @Override
    public String updateWebScript(WebScript webScript) {
        return null;
    }

    @Override
    public String deleteWebScript(WebScript webScript) {
        return null;
    }
}
