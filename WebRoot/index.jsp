<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <base href="<%=basePath%>">  
      
    <title>图片上传</title>  
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
    <meta http-equiv="description" content="This is my page">  
    <!-- 
    <link rel="stylesheet" type="text/css" href="styles.css"> 
    -->  
  </head>  
    
  <body>  
    <center>  
        <form action="<%=path %>/servlet/FileUploadServlet"  enctype="multipart/form-data"  method="post">  
            <table>  
                <tr>  
                    <td colspan="2" align="center">图片上传最大20M</td>  
                </tr>  
                <tr>  
                    <td>图片上传</td>  
                    <td><input type="file" name="picPath"></td>  
                </tr>  
                <tr>  
                    <td colspan="2" align="center">  
                        <input type="submit" value="提交">  
                        <input type="reset" value="重置">  
                    </td>  
                </tr>  
            </table>  
              
            <c:if test="${filePath!=null }">  
            <p>外链：${retResult}</p>  
           
                <p><img src="<%=path %>${filePath}"></p>  
            </c:if>  
        </form>  
    </center>  
  </body>  
</html>  