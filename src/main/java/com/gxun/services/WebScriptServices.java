package com.gxun.services;

import com.gxun.entity.Script;
import com.gxun.entity.WebScript;

import java.util.ArrayList;
import java.util.HashMap;

public interface WebScriptServices {
    String webScriptTest();
    String queryWebScriptList(HashMap<String, Object> webScript);
    String selectOneWebScript(WebScript webScript);
    String addWebScript(WebScript webScript);
    String updateWebScript(WebScript webScript);
    String deleteWebScript(WebScript webScript);
}
