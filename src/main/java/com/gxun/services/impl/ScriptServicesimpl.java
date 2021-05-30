package com.gxun.services.impl;

import com.alibaba.fastjson.JSON;
import com.gxun.DAO.ScriptMapper;
import com.gxun.entity.Script;
import com.gxun.services.ScriptServices;
import com.gxun.testing.concurrent.PostRequestTest;
import com.gxun.testing.selenium.WebLoadTest;
import com.gxun.util.ConstantValueUtil;
import com.gxun.util.RandIdUtil;
import com.gxun.util.ResultUtil;
import com.gxun.util.TencentCOSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ScriptServicesimpl implements ScriptServices {
    @Autowired
    private ScriptMapper scriptMapper;
    @Override
    public String queryScriptList(HashMap<String, Object> script) {
        return null;
    }

    @Override
    public String selectOneScript(Script script) {
        return null;
    }

    @Override
    public String addScript(Script script) {
        HashMap<String,Object> msg=new HashMap<String,Object>();
        if(scriptMapper.addScript(script)>0){
            msg.put("msg","添加成功");
            msg.put("code",ConstantValueUtil.RESCODE_SUCCESS);
            msg.put("flag",true);
        }else
        {
            msg.put("msg","添加失败");
            msg.put("flag",false);
        }
        return JSON.toJSONString(msg);
    }

    @Override
    public String updateScript(Script script) {
        return null;
    }

    @Override
    public String deleteScript(Script script) {
        HashMap<String,Object> msg=new HashMap<String,Object>();
        if(scriptMapper.deleteScript(script)>0)
        {
            msg.put("msg","删除成功");
            msg.put("code",ConstantValueUtil.RESCODE_SUCCESS);
            msg.put("flag",true);

        }else
        {
            msg.put("msg","删除失败");
            msg.put("flag",false);
        }
        return JSON.toJSONString(msg);
    }

    @Override
    public String deleteAllScript() {
        HashMap<String,Object> msg=new HashMap<String,Object>();
        Script script = new Script();
        if(scriptMapper.deleteScript(script)>0)
        {
            msg.put("msg","删除成功");
            msg.put("code",ConstantValueUtil.RESCODE_SUCCESS);
            msg.put("flag",true);

        }else
        {
            msg.put("msg","删除失败");
            msg.put("flag",false);
        }
        return JSON.toJSONString(msg);
    }

    @Override
    public String startScript(HashMap<String, Object> script) {
        HashMap<String, Object> msg = new HashMap<>();
        msg.put("data", PostRequestTest.postRequestTestStart(script));
        msg.put("msg","测试成功");
        msg.put("code", ConstantValueUtil.RESCODE_SUCCESS);
        msg.put("flag",true);
        return JSON.toJSONString(msg);
    }

    @Override
    public Map<String,Object> selectScriptCount(HashMap<String, Object> script) {
        LinkedHashMap<String,Object> collectionList= scriptMapper.selectScriptCount(script);
        HashMap<String,Object> msg=new HashMap<String,Object>();
        //msg.put("msg","查询成功");
        //msg.put("flag",true);
        msg.put("data",collectionList);
        //msg.put("code", ConstantValueUtil.RESCODE_SUCCESS);
        return msg;
    }

    @Override
    public ArrayList<Long> selecResponseTime() {
        HashMap<String,Object> map=new HashMap<String,Object>();
        ArrayList<Long> list= scriptMapper.selecResponseTime(map);
        return list;
    }

}
