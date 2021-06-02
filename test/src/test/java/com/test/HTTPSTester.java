package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Before;

import com.util.HttpClientUtil;

import net.sf.json.JSONObject;

/**
 * 现有系统的测试类
 *
 */
public class HTTPSTester {
	//服务地址前缀
	public String prefixUrl;
	//用户名
	public String username;
	//密码
	public String password;

	public String content;
	public String timestamp;
	public String templateid;

	Map<String,Object> baseMap = new HashMap<String, Object>();
	@Before
	public void init(){
		//公网地址需使用https访问，前缀地址为：https://zwsms.zfsg.gd.gov.cn
		//政务外网区地址需使用http访问，前缀地址为http://19.15.15.18:5880

		prefixUrl="https://zwsms.zfsg.gd.gov.cn";//根据实际请求调整

		username="999999";//根据实际参数修改
		password="123456";//根据实际参数修改
		timestamp = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
		templateid = "10002651";//根据实际添加的模板id修改
		baseMap.put("username", username);
	}

	/**
	 * 添加模板
	 */
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void templateAdd(){
		String url = prefixUrl.concat("/template/add");
		Map<String,Object> map = new HashMap<String, Object>(baseMap);
		content="【广东政数局】您本次登录短信管理系统的验证码为:${3}，请注意保管。";
		//content="";
		map.put("content", content);
		map.put("timestamp", timestamp);

		//拼接需校验的字符串
		StringBuffer sb = new StringBuffer();
		sb.append("timestamp=");
		sb.append(timestamp);
		sb.append("&");
		sb.append("username=");
		sb.append(username);
		sb.append("&");
		sb.append("password=");
		sb.append(password);

		System.out.println(sb.toString());
		String sign = DigestUtils.md5Hex(sb.toString());
		map.put("sign", sign);

		String json = JSONObject.fromObject(map).toString();
		String result = HttpClientUtil.pushHttps(url,json);
		System.out.println("url="+url+",info="+json+"result="+result);
		Map<String,String> resultMap = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		System.out.println(resultMap);
	}


	/**
	 * 发送短信
	 */
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void templateSend() {
		//支持一次发送多条
		String url = prefixUrl.concat("/template/send");
		Map<String,Object> map = new HashMap<String, Object>(baseMap);

		List<String> list = new ArrayList<String>();
		long base = 13429887006l;
		//控制发送条数i
		for(int i=0;i<1;i++){
			list.add(String.valueOf(base+i)+"|3号楼C会议室|123456");
		}

		map.put("timestamp", timestamp);
		map.put("templateid", templateid);
		map.put("content", list);

		//拼接需校验的字符串
		StringBuffer sb = new StringBuffer();
		sb.append("timestamp=");
		sb.append(timestamp);
		sb.append("&");
		sb.append("username=");
		sb.append(username);
		sb.append("&");
		sb.append("password=");
		sb.append(password);


		System.out.println(sb.toString());
		//对字符串进行md5,得到签名数据
		String sign = DigestUtils.md5Hex(sb.toString());
		map.put("sign", sign);


		String json = JSONObject.fromObject(map).toString();

		System.out.println(json);
		System.out.println("url="+url);
		String result = HttpClientUtil.pushHttps(url,json);
		System.out.println("url="+url+",info="+json+"result="+result);
		Map<String,String> resultMap = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		System.out.println(resultMap);
	}

	/**
	 * 获取模板状态
	 */
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void templateGet(){
		String url = prefixUrl.concat("/template/get/"+System.currentTimeMillis());
		Map<String,Object> map = new HashMap<String, Object>(baseMap);
		map.put("templateid", templateid);
		map.put("timestamp", timestamp);
		//拼接需校验的字符串
		StringBuffer sb = new StringBuffer();
		sb.append("timestamp=");
		sb.append(timestamp);
		sb.append("&");
		sb.append("username=");
		sb.append(username);
		sb.append("&");
		sb.append("password=");
		sb.append(password);
		System.out.println(sb.toString());

		//对字符串进行md5,得到签名数据
		String sign = DigestUtils.md5Hex(sb.toString());
		map.put("sign", sign);
		System.out.println("url="+url);
		String json = JSONObject.fromObject(map).toString();
		String result = HttpClientUtil.pushHttps(url,json);

		Map<String,String> resultMap = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		System.out.println(resultMap);
	}

	/**
	 * 获取状态报告
	 */
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void reportGet(){
		String url = prefixUrl.concat("/report/get/"+System.currentTimeMillis());
		Map<String,Object> map = new HashMap<String, Object>(baseMap);
		map.put("timestamp", timestamp);
		//拼接需校验的字符串
		StringBuffer sb = new StringBuffer();
		sb.append("timestamp=");
		sb.append(timestamp);
		sb.append("&");
		sb.append("username=");
		sb.append(username);
		sb.append("&");
		sb.append("password=");
		sb.append(password);
		System.out.println(sb.toString());

		//对字符串进行md5,得到签名数据
		String sign = DigestUtils.md5Hex(sb.toString());
		map.put("sign", sign);
		System.out.println("url="+url);
		String json = JSONObject.fromObject(map).toString();

		String result = HttpClientUtil.pushHttps(url,json);
		System.out.println("url="+url+",info="+json+"result="+result);
		Map<String,String> resultMap = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(result), Map.class);
		System.out.println(resultMap);
	}
}
