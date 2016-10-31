/*******************************************************************
 * AccessKey.java   2016年10月31日
 * Copyright 2015 by GNNT Company. All Rights Reserved.
 * Author: fujj 
 ******************************************************************/
package top.klweb.staticmodel;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fujj
 *
 */
public class AccessKey {

	private  String accesskey ="8KUB84JV-edQ5iKcp67GQ_5SZj4Z4pjnyaQ5Yg4C";
	private  String secretkey ="H257celN7YE94npiQdz7DjSsavMKSLj8SkxS8Wif";
	private  String bucketname = "klweb";
	
	/**
	 * 获取服务实例
	 * @return
	 */
	public static AccessKey getInstance() {
		if (instance == null) {
			synchronized(AccessKey.class) {
				if (instance == null) {
					instance = new AccessKey();
				}
			}
		}
		
		return instance;
	}
	
	private volatile static AccessKey instance = null;
	
	private AccessKey() {
	 
		initConfigInfo();
	}
	
	/**
	 * 初始化配置信息
	 */
	private void initConfigInfo() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("sms.properties");    
		Properties smsPro = new Properties();    
		try {
			smsPro.load(inputStream);
			
			/* 从配置文件读取配置信息 */
			bucketname 		= smsPro.getProperty("BUCKETNAME");
			accesskey 		= smsPro.getProperty("ACCESSKEY");
			secretkey 		= smsPro.getProperty("SECRETKEY");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			smsPro.clear();
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * @return the secretkey
	 */
	public String getSecretkey() {
		return secretkey;
	}

	/**
	 * @param secretkey the secretkey to set
	 */
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	/**
	 * @return the bucketname
	 */
	public String getBucketname() {
		return bucketname;
	}

	/**
	 * @param bucketname the bucketname to set
	 */
	public void setBucketname(String bucketname) {
		this.bucketname = bucketname;
	}
	
	
}
