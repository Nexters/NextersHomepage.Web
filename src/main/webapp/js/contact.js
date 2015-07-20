function sendEmail() {
	$.ajax({

		url : "아직 안만들어 졌는데 주현이가 만들꺼임 주현이가 시작도 안함 ㅡㅡ",
		type : "POST",
		data : {
			username : $("#username").val()
			,useremail : $("#useremail").val()
			,usercomment : $("#usercomment").val()
		},
		dataType : "json" ,
		success : function(data) {
			$("#status").html("result::"+data.result+"<br/>error Msg::"+data._error_msg);
			
			if(data.result=="success") {
				$("#myModal").modal('hide');
				alert("로그인 성공");
			} else {
				alert("로그인 실패\n사유 : "+data._error_msg);
			}
		}
	});
}