package com.gxun.services;

import com.gxun.entity.Script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 杨
 */
public interface ScriptServices {
    String queryScriptList(HashMap<String, Object> script);
    String selectOneScript(Script script);
    String addScript(Script script);
    String updateScript(Script script);
    String deleteScript(Script script);
    String deleteAllScript();
    String startScript(HashMap<String, Object> script);
    Map<String,Object> selectScriptCount(HashMap<String, Object> script);
    ArrayList<Long> selecResponseTime();

}
