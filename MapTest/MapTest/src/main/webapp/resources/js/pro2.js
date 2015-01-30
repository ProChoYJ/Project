
	
	firstConnect();
	var artList = {}
	var artLen = 0;
	var key;

	/////////////////////// map
	var mapContainer = document.getElementById('map'), // map 
				mapOption = {
					center : new daum.maps.LatLng(33.450701, 126.570667), // map center
					level : 3
				// map level
				};

	var map = new daum.maps.Map(mapContainer, mapOption); // create map

	var mode = false;
	
	$("#addsub_form").hide();
	$("#art_wrtie_form").hide();

	function mode_open(){
		if(!mode){
			mode = true;
		}else{
			mode = false;
			document.getElementById('addsub_form').reset();
		}
		console.log(mode);
		$("#addsub_form:not(:animated)").toggle("slow");
	}
	function artTitleOpen(){
		$("#art_wrtie_form:not(:animated)").toggle("slow");
	}
	// content count function
	function contentCount(value){
		var target = document.getElementById('cont_count');
		target.innerHTML = value.length;
			}
	///////////// table show
	function firstConnect(){
		$.ajax({
			url:'/mapTest/',
			type:'POST',
			data:JSON.stringify({"get":"ok"}),
			dataType:'json',
			contentType:'application/json',
			error:function(result){
				console.log("error status : " + result.statusText);
			},
			success:function(result){
				var tmp = "";
				artLen = result.length;
				var delbutton = "<input class='btn' type='button' onclick='del_art(this)' value='delete'/>";
				for(var index in result){
					tmp = "<tr><td>" + (parseInt(index) +1)+"</td><td>" + result[index].art_title +
					"</td><td>" + result[index].art_date + "</td><td>" + delbutton + "</td></tr>";
					// console.log(result[index].art_date);
					$("#art_table").append(tmp);
					// console.log(tmp);
				}

				$("table#art_table tbody tr").click(function(){
					key = this.childNodes[2].innerHTML;
					console.log(key);
					markSub(key);
				});	
			}

		});
	}
	// click event map mark sub article
	function markSub(key){
		$.ajax({
			url:'/mapTest/subArt',
			type:'POST',
			dataType:'json',
			data: JSON.stringify({ "key" : key }),
			contentType:'application/json',
			success: function(result){
				// console.log(parseFloat(result[0].sub_a));
				mode = false;
				$("#addsub_form").hide();
				mapContainer.innerHTML = ""; // init map
				var lastindex = result.length - 1;
				if(result[lastindex] === undefined){
					map = new daum.maps.Map(mapContainer, {
						center : new daum.maps.LatLng(33.450701, 126.570667), // map center
						level : 3
					});
					removeSuvListButton();
				}else{
					map = new daum.maps.Map(mapContainer, {
					center : new daum.maps.LatLng(parseFloat(result[lastindex].sub_a), parseFloat(result[lastindex].sub_b)),
					level : 3
					});

					createLine(result);
					finalsetMap(result);
					makeSubListButton(result);
				}
				daum.maps.event.addListener(map, 'click', function(event) {
						// click position add marker
						if(mode){
							addMarker(event.latLng);
						}
				});
			},
			error: function(error){
				console.log("Status markSub : " + error.statusText);
			}
		});
	}

	//////////////////// test
	// array
	var scriptTmp = [];
	function addTmp(data){
		//var stringTmp = JSON.stringify(data)
		scriptTmp = JSON.parse(data);
		console.log(scriptTmp[0]);
	}
	function send2(){
		alert('test');
	}
	// json object
	var obj = { 
			a: 33.450701,
			b: 126.570667		
	};
	// object -> json object sptring array
	var tmpj = JSON.stringify(obj);
	function send() {
		$.ajax({
			url:'/mapTest/',
			type:'POST',
			data: tmpj,
			dataType: 'text',
			contentType:"application/json",
			error : function(data){
				alert('no');
			},
			success : function(data){
				//var str = JSON.parse(data);
				addTmp(data);
				//alert(str);
			}
		});
		
	}
	// add article
	//////////////////////////////////////////////
	$("#target").on('click', function(event){
		var title = document.getElementById("title_text");
		var tmp = JSON.stringify({"title":title.value});
		var delbutton = "<input type='button' onclick='del_art(this)' value='delete'/>";
		title.value = "";
		$.ajax({
			url:'/mapTest/addArticle',
			type: 'post',
			data: tmp,
			dataType: 'json',
			contentType:"application/json",
			success: function(result){
				// alert('ok');
				console.log(result);
				$("#art_table tbody").append("<tr><td>" + (artLen+1) + "</td><td>" + result.title + "</td><td>" + result.date + "</td><td>" + delbutton + "</td></tr>");
				// $("#title_list").append("<p>"+result.title + "</p>");
				$("table#art_table tbody tr").on('click',function(){
					key = this.childNodes[2].innerHTML;
					console.log(key);
					markSub(key);
				});
			},
			error : function(error){
				console.log("status: " + error.statusText);
			}

		});
	});
	////// delete main article function
	function del_art(target){
		// test
		var del = {
			"date" : target.parentNode.parentNode.childNodes[2].innerHTML 
		}
		
		$.ajax({
			url:'/mapTest/delArticle',
			type:'POST',
			data:JSON.stringify(del),
			dataType:'json',
			contentType:'application/json',
			success:function(result){
				console.log(target.parentNode.parentNode.childNodes[2].innerHTML);
				$(target.parentNode.parentNode).remove();
			},
			error:function(error){
				console.log("status : " + error.statusText);
			}


		});
	}




// marker array
	var markers = [];
	////////search
	var ps = new daum.maps.services.Places();
	var infowindow2 = new daum.maps.InfoWindow({
		zIndex : 1
	});
	// searchPlaces();
	function searchPlaces() {

		var keyword = document.getElementById('keyword').value;

		if (!keyword.trim()) {
			alert('input keyword!');
			return false;
		}
		ps.keywordSearch(keyword, placesSearchCB);
	}

	function placesSearchCB(status, data, pagination) {
		if (status === daum.maps.services.Status.OK) {

			displayPlaces(data.places);

			displayPagination(pagination);

		} else if (status === daum.maps.services.Status.ZERO_RESULT) {

			alert('none');
			return;

		} else if (status === daum.maps.services.Status.ERROR) {

			alert('error');
			return;
		}
	}

	function getListItem(index, places) {

		var el = document.createElement('li'), itemStr = '<span class="markerbg marker_'
				+ (index + 1)
				+ '"></span>'
				+ '<div class="info">'
				+ '   <h5>' + places.title + '</h5>';

		if (places.newAddress) {
			itemStr += '    <span>' + places.newAddress + '</span>'
					+ '   <span class="jibun gray">'
					+ places.address + '</span>';
		} else {
			itemStr += '    <span>' + places.address + '</span>';
		}

		itemStr += '  <span class="tel">' + places.phone
				+ '</span>' + '</div>';

		el.innerHTML = itemStr;
		el.className = 'item';

		return el;
	}

	function displayPlaces(places) {

		var listEl = document.getElementById('placesList'), menuEl = document
				.getElementById('menu_wrap'), fragment = document
				.createDocumentFragment(), bounds = new daum.maps.LatLngBounds(), listStr = '';

		removeAllChildNods(listEl);

		removeMarker();

		for (var i = 0; i < places.length; i++) {

			var placePosition = new daum.maps.LatLng(
					places[i].latitude, places[i].longitude), marker = addMarker2(
					placePosition, i), itemEl = getListItem(i,
					places[i], marker);
			bounds.extend(placePosition);

			(function(marker, title) {
				daum.maps.event.addListener(marker, 'mouseover',
						function() {
							displayInfowindow(marker, title);
						});

				daum.maps.event.addListener(marker, 'mouseout',
						function() {
							infowindow2.close();
						});

				itemEl.onmouseover = function() {
					displayInfowindow(marker, title);
				};

				itemEl.onmouseout = function() {
					infowindow2.close();
				};
			})(marker, places[i].title);

			fragment.appendChild(itemEl);
		}

		listEl.appendChild(fragment);
		menuEl.scrollTop = 0;
		map.setBounds(bounds);
	}

	// 지도 위에 표시되고 있는 마커를 모두 제거합니다
	function removeMarker() {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = [];
	}

	// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
	function displayPagination(pagination) {
		var paginationEl = document.getElementById('pagination'), fragment = document
				.createDocumentFragment(), i;

		// 기존에 추가된 페이지번호를 삭제합니다
		while (paginationEl.hasChildNodes()) {
			paginationEl.removeChild(paginationEl.lastChild);
		}

		for (i = 1; i <= pagination.last; i++) {
			var el = document.createElement('a');
			el.href = "#";
			el.innerHTML = i;

			if (i === pagination.current) {
				el.className = 'on';
			} else {
				el.onclick = (function(i) {
					return function() {
						pagination.gotoPage(i);
					}
				})(i);
			}

			fragment.appendChild(el);
		}
		paginationEl.appendChild(fragment);
	}

	function displayInfowindow(marker, title) {
		var content = '<div style="padding:5px;z-index:1;">'
				+ title + '</div>';

		infowindow2.setContent(content);
		infowindow2.open(map, marker);
	}

	function removeAllChildNods(el) {
		while (el.hasChildNodes()) {
			el.removeChild(el.lastChild);
		}
	}

	function addMarker2(position, idx, title) {
		var imageSrc = 'http://i1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
		imageSize = new daum.maps.Size(36, 37), // 마커 이미지의 크기
		imgOptions = {
			spriteSize : new daum.maps.Size(36, 691), // 스프라이트 이미지의 크기
			spriteOrigin : new daum.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
			offset : new daum.maps.Point(13, 37)
		// 마커 좌표에 일치시킬 이미지 내에서의 좌표
		}, markerImage = new daum.maps.MarkerImage(imageSrc,
				imageSize, imgOptions), marker = new daum.maps.Marker(
				{
					position : position, // 마커의 위치
					image : markerImage
				});

		// marker.setMap(map); // 지도 위에 마커를 표출합니다
		// markers.push(marker); // 배열에 생성된 마커를 추가합니다

		return marker;
	}

	////////////////////////////////////////

	////////////////////////////// marker array
	var testMarkerArray = [];

	// tmp marker and infowindow
	var markerPosition = new daum.maps.LatLng(33.450701, 126.570667);

	var marker = new daum.maps.Marker({
		position : markerPosition,
		clickable : true
	});

	var iwContent, iwPosition = new daum.maps.LatLng(33.450701,
			126.570667), iwRemoveable = true;

	//marker small window
	var infowindow = new daum.maps.InfoWindow({
		position : iwPosition,
		content : iwContent,
		removable : iwRemoveable
	});
	infowindow.setContent('<div style="padding:5px;">Hello World!</div>');

	// add function
	var addPosition;
	function addMarker(position) {
		addPosition = position;
		console.log(position);
		console.log(position.getLng());
		console.log(position.getLat());
		// create marker
		marker.setPosition(position);
		//markerPosition = position;
		// show marker
		
		marker.setMap(map);
		// add array
		//markers.push(marker);
	}

	////////////////// multi marker setmap
	////////////////// sub show
	function finalsetMap(testMarkerArray) {
		// var linePath = [];
		for (var i = 0; i < testMarkerArray.length; i++) {
			var marker = new daum.maps.Marker({
				map : map, // 
				position : new daum.maps.LatLng(parseFloat(testMarkerArray[i].sub_a),parseFloat(testMarkerArray[i].sub_b)), // 
				title : testMarkerArray[i].sub_content,
				clickable : true
			// 
			});
			// linePath.push(marker.position);
			// closer
			(function(marker,title){
				daum.maps.event.addListener(marker, 'click',function(){
					veiwInfoWindow(marker, title);
					$("#myModal").modal('show',true);
				});
			})(marker,testMarkerArray[i].sub_content);
		}
		// createLine(linePath);
	}

	function veiwInfoWindow(marker, title){
		infowindow.setContent('<div style="padding:5px;">' + title +'</div>');
		console.log(title);
		infowindow.open(map, marker);
	}
	
	/////////////////////////////////////////////////////// 

	/////////////// create line
	
	function createLine(testMarkerArray) {
		var linepath = [], i;
		for(i=0; i<testMarkerArray.length ; i++){
			linepath.push(new daum.maps.LatLng(parseFloat(testMarkerArray[i].sub_a),parseFloat(testMarkerArray[i].sub_b)));
		}
		var polyline = new daum.maps.Polyline({
			path : linepath, // 
			strokeWeight : 5, // 
			strokeColor : '#FFAE00', //
			strokeOpacity : 0.7, // 
			strokeStyle : 'solid' //
		});

		polyline.setMap(map);
		computDistance(polyline,linepath[i-1]);
		// makeSubListButton();
	}
	/////////////////////////////////////////////////////////

	////////////// comput distance
	// parameter content
	function computDistance(polyline, linepath) {
		var distanceOverlay;
		var dots = {};
		var distance = Math.round(polyline.getLength());
		var contentTmp = '<div class="dotOverlay distanceInfo">total <scan class="number">'
				+ distance + '</span>m</div>';

		distanceOverlay = new daum.maps.CustomOverlay({
			map : map,
			content : contentTmp,
			position : linepath,
			xAnchor : 0,
			yAnchor : 0,
			zIndex : 3
		});

		distanceOverlay.setMap(map);
	}
	//////////////////////////////////////////////////////////
	// show marker function
	// move to mark function
	function makeSubListButton(testMarkerArray) {
		var subbutton = "";
		var sublist = document.getElementById('sublist');
		for(var i=0; i<testMarkerArray.length; i++){
			subbutton += "<input type='button' class='btn btn-primary'" + "data-a='"+ testMarkerArray[i].sub_a + "'" 
			+ "data-b='" +testMarkerArray[i].sub_b + "' " + "value='" + i + "' onclick='panTo(this)'/>";
			
		}
		sublist.innerHTML = subbutton;
	}
	function removeSuvListButton(){
		var sublist = document.getElementById('sublist');
		sublist.innerHTML = "";	
	}
	//// search bar show
	function searchBar() {
		if ($("#menu_wrap").css('display') === "block") {
			$("#menu_wrap").css('display', 'none');
		} else {
			$("#menu_wrap").css('display', 'block');

		}
	}
	function addSub_Article() {
		var position = marker.getPosition();
		var title = document.getElementById('sub_title');
		// var file = $("#img_file")[0].files[0];
		// console.log(file);
		var obj = {
			"sub_date" : key,
			"sub_a" : position.getLat(),
			"sub_b" : position.getLng(),
			"sub_content" : title.value,
			"sub_image" : "tmp"
		};
		// console.log(obj);
		$.ajax({
			url:'/mapTest/addSubArt',
			type:'POST',
			data:JSON.stringify(obj),
			dataType:'json',
			contentType:'application/json',
			success: function(result){
				markSub(key);
				title.value="";
			},
			error: function(error){
				console.log('Status addsubart : ' + error.statusText);
			}


		});

	}

	// move center
	function panTo(value) {
		map.panTo(new daum.maps.LatLng(parseFloat(value.dataset.a),parseFloat(value.dataset.b)));
	}
