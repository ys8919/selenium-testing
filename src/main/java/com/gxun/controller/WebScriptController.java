package com.gxun.controller;

import com.gxun.services.WebScriptServices;
import com.gxun.util.ConstantValueUtil;
import com.gxun.util.ResultUtil;
import com.gxun.vo.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;

@Controller
@ResponseBody
public class WebScriptController implements Serializable {
    @Autowired
    private WebScriptServices webScriptServices;
    @RequestMapping("/WebScript/select")
    public String select(HashMap<String, Object> webScript){
        return webScriptServices.queryWebScriptList(webScript);
    }
    @RequestMapping("/WebScript/start")
    public String start(){
        return webScriptServices.webScriptTest();
    }
}
