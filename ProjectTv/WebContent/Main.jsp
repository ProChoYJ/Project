<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "controller.BroadDBBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String filename = application.getRealPath("/WEB-INF");
	BroadDBBean.getInstance().setLog(filename + "/TvBroad.txt");
	BroadDBBean.getInstance().resetChannel();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div id = "wrapper">
		<div id = "header-wrapper">
			<div id="header" class="container">
				<center>
					<div id="logo">
						<h1><a href="Main.jsp"> &nbsp; OnAir TV</a></h1>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;실시간으로 티비를 볼 수 있는 웹사이트</p>
				
					</div>
				</center>
			</div>
			
<table width="100%">
<tr>
<td><center>

 <jsp:include page ="Menu.jsp" flush="false" />
</center></td>
</tr>

<tr>
<td width="100%"> 
<div id="page" class="container">

<a href="#" class="button">OnAir</a>
</div>
<jsp:include page ="Tv.jsp" flush="false" />

</td>
</tr>

<tr>
<td>
<div id="page" class="container">

<a href="#" class="button" >FaceBook</a>
</div>

<center>


<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/all.js#xfbml=1";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<div id="fb-root"></div>
<script>
  window.fbAsyncInit = function() {
    // init the FB JS SDK
    FB.init({
      appId      : 'YOUR_APP_ID',                        // App ID from the app dashboard
      status     : true,                                 // Check Facebook Login status
      xfbml      : true                                  // Look for social plugins on the page
    });

    // Additional initialization code such as adding Event Listeners goes here
  };

  // Load the SDK asynchronously
  (function(){
     // If we've already installed the SDK, we're done
     if (document.getElementById('facebook-jssdk')) {return;}

     // Get the first script element, which we'll use to find the parent node
     var firstScriptElement = document.getElementsByTagName('script')[0];

     // Create a new script element and set its id
     var facebookJS = document.createElement('script'); 
     facebookJS.id = 'facebook-jssdk';

     // Set the new script's source to the source of the Facebook JS SDK
     facebookJS.src = '//connect.facebook.net/en_US/all.js';

     // Insert the Facebook JS SDK into the DOM
     firstScriptElement.parentNode.insertBefore(facebookJS, firstScriptElement);
   }());
</script>




<div class="fb-comments" data-href="http://example.com/comments2" data-numposts="5" data-colorscheme="light"></div>

</center>
</td>
</tr>



</table>
	</div>
</div>
</body>
</html>