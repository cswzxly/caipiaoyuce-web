<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/view/system/comm/comm.jsp" %>
<%
String tips=(String)request.getAttribute("tips");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误提示</title>
<link href="css/style.css" rel="stylesheet">
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><table width="419" height="226" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" background="images/error.jpg"><table width="388" height="194" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center">
            	<img src="${ctx}/resources/system/images/error_j.jpg" width="31" height="31">&nbsp;&nbsp;<br/>
              
                <%
					if (tips==null){
						out.println("<P>错误提示信息：非法操作!请稍后再试试!</P>");
					}else{
						out.println("<P>错误提示信息："+tips+"</P>");
				    }
				%>
              <input name="Submit" type="submit" class="btn_grey" value="返回" onClick="toLoginPage();">
              </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<center>
</center>
</body>
<script>
	function toLoginPage(){
		window.location.href="${ctx}/sysLogin.jsp";
	}
</script>
</html>
