function onSubmit(data) {
		if(data.result=="success") {
			if(data.resData[0].userRoles<2) {
				location.href="Main.html";
			} else {
				alert("Admin 페이지에 접근 권한이 없습니다.")
				location.href="../index.html";
			}
		} else {
			alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: "+data.resData[0].errorCd+"\n오류 메시지: "+data.resData[0].errorMsg);
		}
}

function onLogout() {
	$.ajax({
		url : "logout",
		type : "POST",
		dataType : "json" ,
		success : function(data) {
			
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
			
			if(data.result=="error"){
				alert("[오류]\n오류코드:"+data._error_cd+"\n오류메시지:"+data._error_msg);
			} else {
				alert(data.userData.userName+"님 환영합니다.");
			}
		}
	});
}

$(document).ready(function() {
	$("#signin").click(function() {
		if($("input[name=username]").val()==""){
			$("input[name=username]").focus();
			alert("아이디를 입력해주세요.");
			return;
		} else if ($("input[name=password]").val()=="") {
			$("input[name=password]").focus();
			alert("비밀번호를 입력해주세요.");
			return;
		}
		
		requestJsonData("login", {
			username : $("input[name=username]").val()
			, password : $("input[name=password]").val()
		}, onSubmit);
	});
});