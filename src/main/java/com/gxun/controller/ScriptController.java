package com.gxun.controller;

import com.gxun.services.ScriptServices;
import com.gxun.services.WebScriptServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;

@Controller
@ResponseBody
public class ScriptController implements Serializable {
    @Autowired
    private ScriptServices scriptServices;

    @RequestMapping("/concurrentTest/start")
    public  String webLoadTest(@RequestBody HashMap<String, Object> u){
        return scriptServices.startScript(u);
    }

}
