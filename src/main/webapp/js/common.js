function requestJsonData(requestUrl, requestParam, successFunction) {
	$.ajax({
		url : serverUrl + requestUrl,
		type : "POST",
		async: true,
		data : requestParam,
		dataType : "json",
		/*beforeSend:function(){
	        $('.wrap-loading').removeClass('display-none');
	    },
	    complete:function(){
	        $('.wrap-loading').addClass('display-none');
	    },*/
		success : successFunction,
		fail : function() {
			alert("인터넷 연결 상태를 확인해주세요.");
		}
	});
}