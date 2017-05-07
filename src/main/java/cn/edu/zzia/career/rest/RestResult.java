package cn.edu.zzia.career.rest;
/**
 * 
 * @author zj
 * @date 2016年12月18日 下午1:11:45
 * @desc RestResult.java	json数据返回结果封装类
 */
public class RestResult {
	
	//成功码
	public static final Integer SUCCESS = 10000;
	//失败码
	public static final Integer FAIL = 10500;
	
	private Integer code;
	
	private String info;
	
	private Object response;

	public Integer getCode() {
		return code;
	}

	public RestResult setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public RestResult setInfo(String info) {
		this.info = info;
		return this;
	}

	public Object getResponse() {
		return response;
	}

	public RestResult setResponse(Object response) {
		this.response = response;
		return this;
	}
	
	public RestResult(){};
	
	public RestResult(Integer code, String info){
		this.code = code;
		this.info = info;
	}
	
	public static RestResult error(Integer code, String info){
		return new RestResult(code, info);
	}
	
	public static RestResult error(String info){
		return error(FAIL,info);
	}
	
	public static RestResult success(){
		return new RestResult(SUCCESS, "success");
	}
	
}
