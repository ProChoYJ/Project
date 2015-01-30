<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/pro2.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
	<style type="text/css">
		body{
			padding-top:20px;
			padding-bottom:40px;
		}

		/* Custom container */
		.container-narrow{
			margin: 0 auto;
			max-width: 700px;
		}
		.container-narrow > hr{
			margin:30px 0;
		}

		/* Main marketing message and sign up button */
		.jumbotron{
			margin:60px 0;
			text-align: center;
		}
		.jumbotron h1{
			font-size:50px;
			line-height: 1;
		}

		/* Supporting marketing content */
		.marketing{
			margin: 60px 0;
		}
		.marketing p + h4 {
			margin-top: 28px;
		}
		.modal-footer{
			text-align: left;
		}
	</style>
</head>


<body>

<div class="container-narrow">
	<div class="masthead">
		<h3 class="muted">Map Project</h3>
	</div>
	<hr>
	<div id="viewSub" class="jumbotron">
		<h1>Map</h1>
		<div class="map_wrap">
			<div id="map"
				style="width: 100%; height: 350px; position: relative; overflow: hidden;"></div>

			<div id="menu_wrap" class="bg_white">
				<div class="option">
					<p>
					<form onsubmit="searchPlaces(); return false;">
						keyword : <input type="text" value="" id="keyword" size="15">
						<button type="submit">Search</button>
					</form>
					</p>
				</div>
				<hr>
				<ul id="placesList"></ul>
				<div id="pagination"></div>
			</div>
		</div>

		<input class="btn btn-success" type="button" value="Search" id="search_place"
			onclick="searchBar()" />
		<div id="sublist" class="btn-group" data-toggle="buttons-radio"></div>
		<input class="btn" type="button" id="mode" onclick="mode_open()" value="write"/>
		<div id="addsub">
			
			<form onsubmit="addSub_Article(); return false;" id="addsub_form">
				<h4>Content</h4> <textarea id="sub_title" cols="40" rows="10" onkeyup="contentCount(this.value)" maxlength="400"></textarea><br>
				<h6><span><span id="cont_count"></span>/400</span></h6>
				<button class="btn btn-success" type="submit">Save</button>
			</form>
		</div>
	</div>
	<hr>
	<div id="art_wrap" class="center_wrap">
		<table id="art_table" class="table table-striped">
			<thead>
				<tr>
					<th>no.</th>
					<th>title</th>
					<th>date</th>
					<th>del/upd</th>
				</tr>
			</thead>
			<tbody id="art_tbody">
			</tbody>
		</table>
		<br>
		<button id="art_write" class="btn" onclick="artTitleOpen()">write</button><br><br>
		<form id="art_wrtie_form" class="form-search">
			<label class="control-label">Title </label>
			<input class="input-large" type="text" id="title_text" placeholder="input Main article title"/>
			<input id="target" class="btn" type="button" value="go" />
		</form>
	</div>
	<hr>
	<div class="footer center_wrap">
		<p>&copy; myHome 2015 </p>
	</div>

</div>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
		<h3 id="myModalLabel">SubArticle</h3>
	</div>
	<div id="modal_img" class="modal-body">
		<img src="${pageContext.request.contextPath}/Pictures/san2.jpg">
	</div>
	<div class="modal-footer">
		<p>My Home!!!</p>
	</div>

</div>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript"
				src="//apis.daum.net/maps/maps3.js?apikey=3ab99514c869160e13b7693eb8cc55d559434edb&libraries=services"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/pro2.js"></script>



</body>
</html>
