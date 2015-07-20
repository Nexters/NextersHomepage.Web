function getMemberList(kk) {
	$.ajax({
		url : serverUrl + "api/main/memberList.do",
		type : "POST",
		data : {
			gener : $(kk).attr("gener")
		},
		dataType : "json" ,
		success : function(data) {

			$("#status").html("result::"+data.result+"<br/>error Msg::"+data._error_msg);
			if(data.result=="success") {
				var memberInfoList="";
				for(i=0;i<data.resData[0].userList.length;i++){
					console.log(data.resData[0].userList[i].userNm);
					console.log(data.resData[0].userList[i].userCellNum);
					console.log(data.resData[0].userList[i].userAddInfo);
					console.log(data.resData[0].userList[i].userNo);
					console.log(data.resData[0].userList[i].userId);
					
					memberInfoList+="<tr> \n";
					memberInfoList+="<th>"+ data.resData[0].userList[i].userNm +"\n";
					memberInfoList+="<th>"+ data.resData[0].userList[i].userCellNum +"\n";
					memberInfoList+="<th>"+ data.resData[0].userList[i].userAddInfo +"\n";
					memberInfoList+="<th>"+ data.resData[0].userList[i].userNo +"\n";
					memberInfoList+="<th>"+ data.resData[0].userList[i].userId +"\n";
					memberInfoList+="<tr> \n";
					
					/*memberInfoList+=" \n";
					<tr>
						<th>회원 이름</th>
						<th>핸드폰 번호</th>
						<th>주소</th>
						<th>회원 번호</th>
						<th>회원 아이디</th>
					</tr>*/
				}
				$("#memberListInfo").html(memberInfoList);
			} else {
				alert("濡쒓렇???ㅽ뙣\n?ъ쑀 : "+data._error_msg);
			}
		}
	});
}

function getGenerList(kk) {
	$.ajax({
		url : serverUrl + "/api/main/memberGenerList.do",
		type : "POST",
		data : {
			
		},
		dataType : "json" ,
		success : function(data) {
			
			$("#status").html("result::"+data.result+"<br/>error Msg::"+data._error_msg);
			if(data.result=="success") {
				
				var generList="";
				for(i=0;i<data.resData[0].generList.length;i++){
					var num=Number(data.resData[0].generList[i].gener);
					generList=generList+"<li><a href=\"#\" onclick=\"getMemberList(this);\" gener=\""+data.resData[0].generList[i].gener+"\">"+num+"</a></li>\n";
					console.log(generList);
				}
				$("#memberListPage").html(generList);
			} else {
				alert("濡쒓렇???ㅽ뙣\n?ъ쑀 : "+data._error_msg);
			}
		}
	});
}

$(document).ready(function() {
	getGenerList();
	
});