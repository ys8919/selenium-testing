package com.gxun.util;
import com.gxun.vo.ReturnResult;
public class ResultUtil {
    public static ReturnResult success(Object object, Integer code, Integer count) {
        ReturnResult result = new ReturnResult();
        result.setCode(code);
        result.setMsg("成功");
        result.setCount(count);
        result.setData(object);
        return result;
    }

    public static ReturnResult success() {
        return success(null, 1000, 1);
    }

    public static ReturnResult error(Integer code, String msg) {
        ReturnResult result = new ReturnResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
