package com.gxun.util;

public class ConstantValueUtil {
	/**
	 * 数据请求返回码
	 */
	public static final int RESCODE_SUCCESS = 0;             //成功
	public static final int RESCODE_SUCCESS_MSG = 1001;          //成功(有返回信息)
	public static final int RESCODE_EXCEPTION = 1002;        //请求抛出异常
	public static final int RESCODE_NOLOGIN = 1003;             //未登陆状态
	public static final int RESCODE_NOEXIST = 1004;             //查询结果为空
	public static final int RESCODE_NOAUTH = 1005;          //无操作权限
	public static final int RESCODE_LOGINEXPIRE = 1006;             //登录过期
	public static final int RESCODE_INSERTERROR = 1007;             //插入失败
	/*用户权限*/
	public final static int ORDINARY_JCTION =1;
	/**
	 * 腾讯云存储桶
	 */
	public static final String ACCESSKEY = "AKIDz60d6HL9pELekdPeqmfHeuumLegScGxS";
	public static final String SECRETKEY = "M5ZonW0WlkW3EBrLfASVJqPnzvay4sWt";
	public static final String BUCKETNAME = "yx9819-1258077645";
	//public static final String APPID = "1300395088";
	public static final String REGIONID = "ap-guangzhou";
	public static final String FILEURL = "https://yx9819-1258077645.cos.ap-guangzhou.myqcloud.com/";
	public static final String FILEFOLDER = "product-promotion";

	/*
	 *分类信息
	 **/
	public static final int CLASSIFY1 =1;	//
	public static final int CLASSIFY2 =2;	//
	public static final int CLASSIFY3 =3;	//
	public static final int CLASSIFY4 =4;	//
	public static final int CLASSIFY5 =5;	//
	public static final int CLASSIFY6 =6;	//
	public static final int CLASSIFY7 =7;	//
}
