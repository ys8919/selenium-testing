package com.gxun.DAO;

import com.gxun.entity.Script;
import com.gxun.entity.WebScript;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 杨
 */
@Mapper
@Repository
public interface WebScriptMapper {
    ArrayList<WebScript> queryWebScriptList(HashMap<String, Object> webScript);
    Script selectOneWebScript(WebScript webScript);
    int addWebScript(WebScript webScript);
    int updateWebScript(WebScript webScript);
    int deleteWebScript(WebScript webScript);
}
