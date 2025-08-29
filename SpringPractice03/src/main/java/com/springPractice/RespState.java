package com.springPractice;

public class RespState {
	/**
	 * 这里面去封装一些很常用的状态码
	 * 200 = 成功
	 * 400/500 = 出错
	 */
	private int code;
	private String msg;
	private String data;
	
	public RespState() {
		super();
	}
	public RespState(int code, String msg, String data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public static RespState build(int i) {
		// 静态方法
		return new RespState(i, "Ok", "Ok");
	}
	
}