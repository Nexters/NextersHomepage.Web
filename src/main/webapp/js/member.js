function getMemberList(kk) {
	$.ajax({
		url : "api/main/memberList.do",
		type : "POST",
		data : {
			gener : $(kk).attr("gener")
		},
		dataType : "json" ,
		success : function(data) {

			$("#status").html("result::"+data.result+"<br/>error Msg::"+data._error_msg);
			if(data.result=="success") {
				alert("멤버 성공");
				alert("JSON::"+JSON.stringify(data));
			} else {
				alert("로그인 실패\n사유 : "+data._error_msg);
			}
		}
	});
}