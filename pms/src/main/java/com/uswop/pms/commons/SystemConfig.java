package com.uswop.pms.commons;

/**
 * 系统初始配置参数类，此类中的参数属性在将在系统启动时由Spring注入
 * @author Phills
 *
 */
public class SystemConfig {
		
	//允许访问系统API的指定应用ID，此为第三方应用获取token时的提供验证参数之一
	private String appId;
	//允许访问系统API的安全加密公钥，此为第三方应用获取token时的提供的验证参数安全密钥的加密公钥
	private String appPublicKey;
	//系统生甩的API访问token的有效期
	private int tokenExpiresIn;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppPublicKey() {
		return appPublicKey;
	}
	public void setAppPublicKey(String appPublicKey) {
		this.appPublicKey = appPublicKey;
	}
	public int getTokenExpiresIn() {
		return tokenExpiresIn;
	}
	public void setTokenExpiresIn(int tokenExpiresIn) {
		this.tokenExpiresIn = tokenExpiresIn;
	}
	
	public String getApiKey(){
		return SecurityTools.SHA1(appId+appPublicKey);
	}
		
}
