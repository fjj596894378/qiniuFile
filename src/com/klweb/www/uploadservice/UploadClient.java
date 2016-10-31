/*******************************************************************
 * UploadDemo.java   2016��10��31��
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
	 //���ú��˺ŵ�ACCESS_KEY��SECRET_KEY
	  String ACCESS_KEY = AccessKey.ACCESSKEY;
	  String SECRET_KEY = AccessKey.SECRETKEY;
	  //Ҫ�ϴ��Ŀռ�
	  String bucketname = "klweb";
	  //�ϴ�����ţ�󱣴���ļ���
	  String key = "";//�ں�������
	  //�ϴ��ļ���·��
	  String FilePath = "C:\\Users\\fujj\\Desktop\\��ţ\\��ӰͼƬ\\sidai.jpg";
	  public String retResult = "";
	  //��Կ����
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  //�����ϴ�����
	  UploadManager uploadManager = new UploadManager();

	  public UploadClient(){
		  
	  }
	  public UploadClient(String filePath){
		  this.FilePath = filePath;
	  }
	  //���ϴ���ʹ��Ĭ�ϲ��ԣ�ֻ��Ҫ�����ϴ��Ŀռ����Ϳ�����
	  public String getUpToken(){
	      //return auth.uploadToken(bucketname,null,3600,new StringMap()
         //.put("callbackUrl","http://oeakswgv8.bkt.clouddn.com/callback")
          //.put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
		  return auth.uploadToken(bucketname);
	  }

	  public void upload() throws IOException{
	    try {
	      //����put�����ϴ�
	    	
	    	//���������ļ���
	      key = System.currentTimeMillis() + FilePath.substring(FilePath.lastIndexOf("."), FilePath.length()) ;
	      Response res = uploadManager.put(FilePath, key, getUpToken());
	      
	      Gson gson = new Gson();
	      
	      GsonModel retMsg = gson.fromJson(res.bodyString(),GsonModel.class);
	      retMsg.setKey("http://oeakswgv8.bkt.clouddn.com/" + retMsg.getKey());
	      this.retResult = retMsg.getKey();
	      System.out.println(retMsg);
	      //��ӡ���ص���Ϣ
	     // System.out.println(res.bodyString()); 
	      } catch (QiniuException e) {
	          Response r = e.response;
	          // ����ʧ��ʱ��ӡ���쳣����Ϣ
	          System.out.println(r.toString());
	          try {
	              //��Ӧ���ı���Ϣ
	            System.out.println(r.bodyString());
	          } catch (QiniuException e1) {
	              //ignore
	          }
	      } 
	   
	  }

	  public static void main(String args[]) throws IOException{ 
		  System.out.println("�ϴ���ʼ---------------------");
	    new UploadClient().upload();
	    System.out.println("�ϴ����---------------------");
	  }
}
