
function onSubmit() {
	$.ajax({
		url : "login",
		type : "POST",
		data : {
			username : $("input[name=username]").val()
			, password : $("input[name=password]").val()
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

function onLogout() {
	$.ajax({
		url : "logout",
		type : "POST",
		dataType : "json" ,
		success : function(data) {
			$("#status").html("result::"+data.result);
			if(data.result=="logout"){
				alert("로그아웃 성공");
			}
		}
	});
}

function isLogin() {
	$.ajax({
		url : "api/loginUser.do",
		type : "POST",
		dataType : "json" ,
		success : function(data) {
			$("#status").html("result::"+JSON.stringify(data));
			if(data.result=="error"){
				alert("[오류]\n오류코드:"+data._error_cd+"\n오류메시지:"+data._error_msg);
			} else {
				alert(data.userData.userName+"님 환영합니다.");
			}
		}
	});
}