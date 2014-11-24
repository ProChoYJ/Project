<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id ="testAir" class = "TestURLBean.URLBean" scope="request"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>

<script language="JavaScript">
<!--
function tvgoto(url, targetstr)
{
  if (url == 'backward')
    history.back(1);
  else if (url == 'forward')
    history.forward(1);
  else {
     if (targetstr == 'blank') {
       window.open(url, 'win1');
     } else {
       var frameobj;
       if (targetstr == '') targetstr = 'self';
       if ((frameobj = eval(targetstr)) != null)
         frameobj.location = url;
     }
  }
}

// -->
</script>
<%
	
%>

<body>
<br>
<table width="100%" id="main" border=0 cellpadding=0 cellspacing=0>

<tr>
<td width="7"></td>
<td valign="top">

<center>
<table width="100%">
<%if(testAir.getnScreen() == 1){%>
<tr>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[0] %>" name="tv" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>
<%}else if(testAir.getnScreen() == 2){%>
<tr>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[0] %>" name="tv" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[1] %>" name="tv2" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>
<%}else if(testAir.getnScreen() == 3){%>
<tr>
<td colspan ="2">
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[0] %>" name="tv" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>


<tr>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[1] %>" name="tv2" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[2] %>" name="tv3" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>

<%}else if(testAir.getnScreen() == 4){ %>
<tr>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[0] %>" name="tv1" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[1] %>" name="tv2" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>

<tr>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[2] %>" name="tv3" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
<td>
<center>
<iframe height="520" width="700" src="<%=testAir.getTvURL()[3] %>" name="tv4" frameborder="0" scrolling="no" align="center"></iframe>
</center>
</td>
</tr>
<%} %>

</table>
</center>


</td>
</tr>
</table>
</body>
</html>
