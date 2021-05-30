package com.gxun.DAO;

import com.gxun.entity.Script;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author 杨
 */
@Mapper
@Repository
public interface ScriptMapper {
    ArrayList<Script> queryScriptList(HashMap<String, Object> script);
    Script selectOneScript(Script script);
    LinkedHashMap<String,Object> selectScriptCount(HashMap<String, Object> script);
    ArrayList<Long> selecResponseTime(HashMap<String, Object> script);
    int addScript(Script script);
    int updateScript(Script script);
    int deleteScript(Script script);
}
