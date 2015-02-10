<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>Login</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/fileAjax.css">
	<style type="text/css">
	</style>

</head>
<body>
 <!-- NAVBAR
    ================================================== -->
    <div class="navbar-wrapper ">
      <!-- Wrap the .navbar in .container to center it within the absolutely positioned parent. -->
      <div class="container">

        <div class="navbar navbar-inverse">
          <div class="navbar-inner">
            <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">My File</a>
            <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
            <div class="nav-collapse collapse">
              <ul class="nav">
                <li><a href="#my">${ID}</a></li>
                <li><a href="#LogOut" id="navLogOut">LogOut</a></li>
                <!-- Read about Bootstrap dropdowns at http://twbs.github.com/bootstrap/javascript.html#dropdowns -->
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">State <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="#" id="sharebtn">on</a></li>
                    <li class="divider"></li>
                    <li><a href="#" id="cancelbtn">off</a></li>
                    
                  </ul>
                </li>

              </ul>
             	 <form class="navbar-form pull-right" id="fileform" enctype="multipart/form-data" method="post">
              	<button type="button" class="btn" onclick="document.getElementById('file2').click();" >File</button>
		<input class="btn" type="file" size="20" id="file2" name="file" style="display:none;" />
					<input class="btn" type="button" id="filebtn" value="upload">
				</form>

            </div><!--/.nav-collapse -->

          </div><!-- /.navbar-inner -->
        </div><!-- /.navbar -->

      </div> <!-- /.container -->
    </div><!-- /.navbar-wrapper -->

    <div class="container containerbody">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h2>File list</h2>
        <p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>

        <table class="table table-hover" id="table">
	      	
			<tdoby id="tableBody">

			</tdoby>
	      </table>
      </div>


    </div> <!-- /container -->


<!-- logout -->
<form action="/pro1/logout" method="post" id="logOut" style="display:none;">
	<input type="submit" value="logout">
</form>

<!-- SUB
    ================================================== -->
	<div class="navbar-wrapper">
      <!-- Wrap the .navbar in .container to center it within the absolutely positioned parent. -->
      <div class="container">

        <div class="navbar navbar-inverse">
          <div class="navbar-inner">
            <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Shared File</a>
            <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
            <div class="nav-collapse collapse">
              <ul class="nav">
                <li><a href="#some" id="some"></a></li>
                <li><a href="#about" id="about">About</a></li>
                <!-- Read about Bootstrap dropdowns at http://twbs.github.com/bootstrap/javascript.html#dropdowns -->
              </ul>
              
          
              <div class="navbar-form pull-right">
              <input type="button" class="btn pull-right " id="cutoffbtn" value="cutoff">
     
              <input type="button" class="btn pull-right btncenter" id="receivebtn" value="receive">
          
              <input class="span2 pull-right btncenter" type="text" id="receiveId" placeholder="ID">
              
              
          	</div>
			
	     		
	    	</div><!--/.nav-collapse -->
	      </div><!-- /.navbar-inner -->
	    </div><!-- /.navbar -->

	  </div> <!-- /.container -->
	</div><!-- /.navbar-wrapper -->


    <div class="container containerbody">

      <!-- Main hero unit for a primary marketing message or call to action -->
      <div class="hero-unit">
        <h2>File list</h2>
        <p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
        <table class="table table-hover" id="rtable">
			<tdoby id="tableBody">
			</tdoby>
	      </table>
      </div>
  	</div>
  	<div class="footer">
        <p>&copy; AweSome 2015</p>
	</div>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/fileAjax.js"></script>
<script type="text/javascript">
</script>
</body>
</html>