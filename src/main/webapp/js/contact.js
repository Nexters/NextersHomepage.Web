function sendEmail() {
	
	$.ajax({

		url : "contact.do",
		type : "POST",
		data : {
			userName : $("#name").val()
			,userEmail : $("#email").val()
			,userComment : $("#comment").val()
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