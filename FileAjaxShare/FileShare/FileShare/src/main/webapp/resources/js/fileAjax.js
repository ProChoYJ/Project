
	//fileadd
	console.log('ok');
	var sharebtn = $("#sharebtn");
	var cancelbtn = $("#cancelbtn");
	var form = $("#filebtn");
	var receivebtn = $("#receivebtn");
	var cutoffbtn = $("#cutoffbtn");
	var navLogOut = $("#navLogOut");

	function printList(data, name, del){
		var list = data.list;
		var body = "";
		var button = "<td><input type='button' class='btn' onclick='deleteFile(this)' value='delete'></td>";
		if(del){
			for(var i=0; i<list.length; i++){
				body += "<tr><td>" + (i+1) + "</td><td><a href='/pro1/download/"+ data.ID + "/" + list[i] + "/file/' >" + list[i] + 
				"</a></td>" +  button +"</tr>"; 
			}
		}else{
			for(var i=0; i<list.length; i++){
				body += "<tr><td>" + (i+1) + "</td><td><a href='/pro1/download/"+ data.ID + "/" + list[i] + "/file/' >" + list[i] + "</a></td></tr>"; 
			}
		}
		$(name).html(body);
	}

	getFileList();
	function getFileList(){

		$.ajax({
			url:'/pro1/login/list',
			data:JSON.stringify({"request":"get List"}),
			type:'post',
			dataType:'json',
			contentType:'application/json',
			success: function(data){
				// console.log(data);
				printList(data,"#table",true);
				// console.log(body);

			}
		});
	}

	function deleteFile(target){
		var filename = target.parentNode.parentNode.childNodes[1].textContent;
		// console.log(target.parentNode.parentNode.childNodes[1].textContent);

		$.ajax({
			url:'/pro1/login/delete',
			data:JSON.stringify({"filename":filename}),
			type:'post',
			dataType:'json',
			contentType:'application/json',
			success: function(data){
				printList(data,"#table",true);
			}
		});
	}

	form.click(function(){
		var formData = $("#file2")[0].files[0];
		var fd = new FormData();
		fd.append('file', $("#file2")[0].files[0])
		console.log(fd);

		$.ajax({
			url:'/pro1/login/fileadd',
			data:fd,
			type:'post',
			dataType:'json',
			contentType:false,
			processData:false,
			success: function(data){
				printList(data,"#table",true);
				// console.log(JSON.parse(data));
			}

		});
		console.log('test');

	});

	// shared 
	sharebtn.on('click',function(){
		$.ajax({
			url:'/pro1/login/share',
			data:JSON.stringify({"state":"O"}),
			type:'post',
			dataType:'json',
			contentType:'application/json',
			success:function(result){
				console.log('share : ' + result.state);
			}
		});
	});
	// shared cancel
	cancelbtn.on('click',function(){
		$.ajax({
			url:'/pro1/login/cancel',
			data:JSON.stringify({"state":"X"}),
			type:'post',
			dataType:'json',
			contentType:'application/json',
			success:function(result){
				console.log('share : ' + result.state);
			}
		});
	});

	// receive
	receivebtn.on('click',function(){
		var receiveid = document.getElementById('receiveId').value;

		$.ajax({
			url:'/pro1/login/receive',
			data:JSON.stringify({"ID":receiveid}),
			type:'post',
			dataType:'json',
			contentType:'application/json',
			success:function(data){
				$("#some").html(data.ID);
				printList(data,"#rtable",false);
			}
		});
	});

	navLogOut.on('click',function(){
		$("#logOut").submit();
	})

	// cutoff - cancel
	cutoffbtn.on('click',function(){
		$("#rtable").html("");
	});