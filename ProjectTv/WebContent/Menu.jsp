<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import = "controller.BroadDBBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script language="javascript">
function checkIt(){
	var onair = eval("document.onair");
	var ckbleng = document.getElementsByName("ckb");
	var count = onair.count.length;
	var countnum = 0,ckbnum = 0;
	for(var j=0; j<ckbleng.length; j++){
		if(ckbleng[j].checked == true)
			ckbnum += 1;
	}
	
	for(var i = 0; i<count; i++){
		if(onair.count[i].checked == true)
			countnum = onair.count[i].value;
		
	}
	
	if(ckbnum > 4){
		alert("채널은 최대 4개 까지 가능합니다.");
		return false;
	}
		
	if(ckbnum == 0){
		alert("채널을 선택하세요.");
		return false;
	}
	
	if(countnum == 0){
		alert("화면 수를 선택하세요.");
		return false;
	}
	
	if(countnum != ckbnum){
		alert("채널 수와 화면 수가 일치하지 않습니다. ");
		return false;
	}
	
	
	
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" rel="stylesheet" />
<link href="default.css" rel="stylesheet" type="text/css" media="all" />
<link href="fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="default_ie6.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<link href="css/jquery.selectbox.css" type="text/css" rel="stylesheet" />
<style>
.button {            
	margin: 5px;            
	text-decoration: none;            
	font: bold 1em 'Trebuchet MS',Arial, Helvetica; /*Change the em value to scale the button*/            
	display: inline-block;            
	text-align: center;            
	color: #fff;                        
	border: 1px solid #9c9c9c; /* Fallback style */            
	border: 1px solid rgba(0, 0, 0, 0.3);                                   
	text-shadow: 0 1px 0 rgba(0,0,0,0.4);                        
	box-shadow: 0 0 .05em rgba(0,0,0,0.4);            
	-moz-box-shadow: 0 0 .05em rgba(0,0,0,0.4);            
	-webkit-box-shadow: 0 0 .05em rgba(0,0,0,0.4);                    
}  
.button-black        {            
	background: #141414;            
	background: -webkit-gradient(linear, left top, left bottom, from(#656565), to(#141414) );            
	background: -moz-linear-gradient(-90deg, #656565, #141414);            
	filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr='#656565', EndColorStr='#141414');        
}                
.button-black:hover        {            
	background: #656565;            
	background: -webkit-gradient(linear, left top, left bottom, from(#141414), to(#656565) );            
	background: -moz-linear-gradient(-90deg, #141414, #656565);            
	filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr='#141414', EndColorStr='#656565');        
}                
.button-black:active        {            
	background: #141414;        
}


</style>

</head>
<body>
<form name ="onair" method="post" action="/ProjectTv/Testcon" onSubmit="return checkIt()">

		
		
		<div id = "menu" class = "container">
			<ul>
				<center>
				<li><a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';">
					<b><font color = "white">- 지상파 -</font></b></a><div style="display:none; background:none">
						<input type="checkbox" name = "ckb" value="http://vod.sbs.co.kr/onair/onair_index.jsp?Channel=SBS"  > SBS&nbsp;[
							<%=(String)BroadDBBean.getchannel().get("sbs") %>]
							<input type="checkbox" name = "ckb" value="http://k.kbs.co.kr/live/TV/11" > KBS1&nbsp;[
							<%=(String)BroadDBBean.getchannel().get("kbs1") %>]
							<input type="checkbox" name = "ckb" value="http://k.kbs.co.kr/live/TV/12" > KBS2&nbsp;[
							<%=(String)BroadDBBean.getchannel().get("kbs2") %>]
							<input type="checkbox" name = "ckb" value="http://member.imbc.com/Login/Login.aspx" > MBC&nbsp;[
							<%=(String)BroadDBBean.getchannel().get("mbc") %>]
							<input type="checkbox" name = "ckb" value="http://www.dogdrip.com//skin/board/tvchat/Channel/ebs.php" >EBS&nbsp;[
							<%=(String)BroadDBBean.getchannel().get("ebs") %>]
							<br> &nbsp;
						</div></li>
				<br>
				<li><a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';">
					<b><font color = "white">- 케이블 -</font></b></a><div style="display:none; background:none">
						<input type = "checkbox" name = "ckb" value = "http://www.dogdrip.com//skin/board/tvchat/Channel/ytn.php"/> YTN
							<input type = "checkbox" name = "ckb" value = "http://www.dogdrip.com//skin/board/tvchat/Channel/tvchosun.php"/> TV조선
							<input type = "checkbox" name = "ckb" value = "http://www.dogdrip.com//skin/board/tvchat/Channel/spotv.php"/> SPOTV
							<input type = "checkbox" name = "ckb" value = "http://www.dogdrip.com//skin/board/tvchat/Channel/spotv2.php"/> SPOTV2
							<input type = "checkbox" name = "ckb" value = "http://thisr.com/adSTREAM/obshd?session=362a66b7b8ebf66e9a4914623a430ef5" /> OBS
							<input type = "checkbox" name = "ckb" value = "http://www.dogdrip.com//skin/board/tvchat/Channel/Channela.php" /> 채널A
						</div></li>
				<br>
				<li><a onclick="this.nextSibling.style.display=(this.nextSibling.style.display=='none')?'block':'none';">
					<b><font color = "white">- 동시 화면 수 -</font></b></a><div style="display:none; background:none">
						<input type = "radio" name = "count" value ="1" /> 1개
						<input type = "radio" name = "count" value ="2"/> 2개
						<input type = "radio" name = "count" value ="3"/> 3개
						<input type = "radio" name = "count" value ="4"/> 4개
						</div></li>
				<br>
				<li><input class = "button button-black" type="submit" value="시청하기"></li>
				</center>
			</ul>
		</div>
		

</form>
</body>
</html>