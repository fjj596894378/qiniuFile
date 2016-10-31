/*******************************************************************
 * FileUploadServlet.java   2016��10��31��
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
          
        //��Ŀ����·��  
        String projectPath = req.getRealPath("");  
        String path = req.getContextPath();;  
        HttpSession session  = req.getSession();  
          
        boolean flag = ServletFileUpload.isMultipartContent(req);  
        if(!flag){  
            System.out.println("�����ļ��ϴ���");  
            //ҳ����ת  
        }  
          
        try {  
            //�����ļ��ϴ�����  
            DiskFileItemFactory factory = new DiskFileItemFactory();  
            factory.setSizeThreshold(1024*1024*2);  
            factory.setRepository(new File(projectPath));  
              
            //ʹ���ļ��ϴ����������ļ��ϴ����󣬽���form�������浽List������  
            ServletFileUpload upload = new ServletFileUpload(factory);  
            upload.setFileSizeMax(1024*1024*100);  
              
            //����form�������浽List������  
            List<?> list = upload.parseRequest(req);  
            Iterator<?> iterator = list.iterator();  
            while(iterator.hasNext()){  
                FileItem item = (FileItem)iterator.next();  
                if(item.isFormField()){  
                    System.out.println("===��ͨ����===");  
                    String name = item.getFieldName();  
                    String value = item.getString();  
                    System.out.println(name+"###"+value);  
                }else{  
                    System.out.println("===�ļ��ϴ�����===");  
                    String name = item.getFieldName();  
                    String fileName = item.getName();  
                    long FileSize = item.getSize();  
                    String fileType = item.getContentType();  
  
                    System.out.println(name+"#"+fileName+"#"+FileSize+"#"+fileType);  
                      
             
                    String prefix = "";
                    prefix = "/upload/"+System.currentTimeMillis();  
                  
                    String suffix =   fileName.substring(fileName.lastIndexOf("."), fileName.length()) ;    //��׺  
                    String filePath = prefix+suffix;                                        //ͼƬ���·��  
                    String destPath = projectPath+filePath;                     //ͼƬ����·��  
                      
                    System.out.println("ǰ׺:"+prefix);  
                    System.out.println("ԭʼͼƬ�����·��:"+filePath);  
                    System.out.println("ԭʼͼƬ������·��:"+destPath);  
                  
                    //����ͼƬ  
                    OutputStream outputStream = new FileOutputStream(destPath);  
                    if(item.isInMemory()){  
                        outputStream.write(item.get());  
                        outputStream.close();  
                    }  
                      
                    //ɾ����ʱ�ļ�  
                    item.delete();  
                      
                    //����sessionֵ����  
                    session.setAttribute("filePath", filePath);  
                     
                    System.out.println("�ϴ���ʼ---------------------");
                    UploadClient uc = new UploadClient(destPath);
                    uc.upload();
            	    System.out.println("�ϴ����---------------------");
            	    session.setAttribute("retResult", uc.retResult);  
            	    
                }  
                resp.sendRedirect(path+"/index.jsp");  
            }  
        } catch (FileUploadException e) {  
            e.printStackTrace();  
        }  
    }  
      
      
}  