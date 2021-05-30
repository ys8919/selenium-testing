package com.gxun.controller;

import com.gxun.services.WebLoadServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;

@Controller
@ResponseBody
public class WebLoadController implements Serializable {
    @Autowired
    private WebLoadServices webLoadServices;

    @RequestMapping("/webLoad/webLoadTest")
    public  String webLoadTest(@RequestBody HashMap<String, Object> u){
        return webLoadServices.webLoadTest(u);
    }
}
