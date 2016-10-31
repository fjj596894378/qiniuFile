/*******************************************************************
 * UploadDemo.java   2016年10月31日
 * Copyright 2015 by GNNT Company. All Rights Reserved.
 * Author: fujj 
 ******************************************************************/
package com.klweb.www.uploadservice;

import java.io.IOException;

import com.google.gson.Gson;
import com.klweb.www.staticmodel.AccessKey;
import com.klweb.www.staticmodel.GsonModel;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;


/**
 * @author fujj
 *
 */
public class UploadClient {
	 //设置好账号的ACCESS_KEY和SECRET_KEY
	  String ACCESS_KEY = AccessKey.ACCESSKEY;
	  String SECRET_KEY = AccessKey.SECRETKEY;
	  //要上传的空间
	  String bucketname = "klweb";
	  //上传到七牛后保存的文件名
	  String key = "";//在后面生成
	  //上传文件的路径
	  String FilePath = "C:\\Users\\fujj\\Desktop\\七牛\\电影图片\\sidai.jpg";
	  public String retResult = "";
	  //密钥配置
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  //创建上传对象
	  UploadManager uploadManager = new UploadManager();

	  public UploadClient(){
		  
	  }
	  public UploadClient(String filePath){
		  this.FilePath = filePath;
	  }
	  //简单上传，使用默认策略，只需要设置上传的空间名就可以了
	  public String getUpToken(){
	      //return auth.uploadToken(bucketname,null,3600,new StringMap()
         //.put("callbackUrl","http://oeakswgv8.bkt.clouddn.com/callback")
          //.put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
		  return auth.uploadToken(bucketname);
	  }

	  public void upload() throws IOException{
	    try {
	      //调用put方法上传
	    	
	    	//重新生成文件名
	      key = System.currentTimeMillis() + FilePath.substring(FilePath.lastIndexOf("."), FilePath.length()) ;
	      Response res = uploadManager.put(FilePath, key, getUpToken());
	      
	      Gson gson = new Gson();
	      
	      GsonModel retMsg = gson.fromJson(res.bodyString(),GsonModel.class);
	      retMsg.setKey("http://oeakswgv8.bkt.clouddn.com/" + retMsg.getKey());
	      this.retResult = retMsg.getKey();
	      System.out.println(retMsg);
	      //打印返回的信息
	     // System.out.println(res.bodyString()); 
	      } catch (QiniuException e) {
	          Response r = e.response;
	          // 请求失败时打印的异常的信息
	          System.out.println(r.toString());
	          try {
	              //响应的文本信息
	            System.out.println(r.bodyString());
	          } catch (QiniuException e1) {
	              //ignore
	          }
	      } 
	   
	  }

	  public static void main(String args[]) throws IOException{ 
		  System.out.println("上传开始---------------------");
	    new UploadClient().upload();
	    System.out.println("上传完成---------------------");
	  }
}
