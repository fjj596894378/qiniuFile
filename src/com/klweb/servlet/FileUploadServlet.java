/*******************************************************************
 * FileUploadServlet.java   2016年10月31日
 * Copyright 2015 by GNNT Company. All Rights Reserved.
 * Author: fujj 
 ******************************************************************/
package com.klweb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.klweb.www.uploadservice.UploadClient;

/**
 * @author fujj
 *
 */
public class FileUploadServlet extends HttpServlet{  
  
    private static final long serialVersionUID = 1L;  
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        // TODO Auto-generated method stub  
        doPost(req, resp);  
    }  
  
    @Override  
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
          
        //项目完整路径  
        String projectPath = req.getRealPath("");  
        String path = req.getContextPath();;  
        HttpSession session  = req.getSession();  
          
        boolean flag = ServletFileUpload.isMultipartContent(req);  
        if(!flag){  
            System.out.println("不是文件上传！");  
            //页面跳转  
        }  
          
        try {  
            //创建文件上传工厂  
            DiskFileItemFactory factory = new DiskFileItemFactory();  
            factory.setSizeThreshold(1024*1024*2);  
            factory.setRepository(new File(projectPath));  
              
            //使用文件上传工厂穿件文件上传对象，解析form表单，保存到List集合中  
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setFileSizeMax(1024*1024*100);  
              
            //解析form表单，保存到List集合中  
            List<?> list = upload.parseRequest(req);  
            Iterator<?> iterator = list.iterator();  
            while(iterator.hasNext()){  
                FileItem item = (FileItem)iterator.next();  
                if(item.isFormField()){  
                    System.out.println("===普通表单域===");  
                    String name = item.getFieldName();  
                    String value = item.getString();  
                    System.out.println(name+"###"+value);  
                }else{  
                    System.out.println("===文件上传单域===");  
                    String name = item.getFieldName();  
                    String fileName = item.getName();  
                    long FileSize = item.getSize();  
                    String fileType = item.getContentType();  
  
                    System.out.println(name+"#"+fileName+"#"+FileSize+"#"+fileType);  
                      
             
                    String prefix = "";
                    prefix = "/upload/"+System.currentTimeMillis();  
                  
                    String suffix =   fileName.substring(fileName.lastIndexOf("."), fileName.length()) ;    //后缀  
                    String filePath = prefix+suffix;                                        //图片相对路径  
                    String destPath = projectPath+filePath;                     //图片完整路径  
                      
                    System.out.println("前缀:"+prefix);  
                    System.out.println("原始图片的相对路径:"+filePath);  
                    System.out.println("原始图片的完整路径:"+destPath);  
                  
                    //保存图片  
                    OutputStream outputStream = new FileOutputStream(destPath);  
                    if(item.isInMemory()){  
                        outputStream.write(item.get());  
                        outputStream.close();  
                    }  
                      
                    //删除临时文件  
                    item.delete();  
                      
                    //设置session值传递  
                    session.setAttribute("filePath", filePath);  
                     
                    System.out.println("上传开始---------------------");
                    UploadClient uc = new UploadClient(destPath);
                    uc.upload();
            	    System.out.println("上传完成---------------------");
            	    session.setAttribute("retResult", uc.retResult);  
            	    
                }  
                resp.sendRedirect(path+"/index.jsp");  
            }  
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        }  
    }  
      
      
}  