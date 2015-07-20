function getMemberList(data) {
	if(data.result=="success") {
		var memberInfoList="";
		for(i=0;i<data.resData[0].userList.length;i++){
			memberInfoList+="<tr> \n";
			memberInfoList+="<th>"+ data.resData[0].userList[i].userNm +"\n";
			memberInfoList+="<th>"+ data.resData[0].userList[i].userCellNum +"\n";
			memberInfoList+="<th>"+ data.resData[0].userList[i].userAddInfo +"\n";
			memberInfoList+="<th>"+ data.resData[0].userList[i].userNo +"\n";
			memberInfoList+="<th>"+ data.resData[0].userList[i].userId +"\n";
			memberInfoList+="<tr> \n";
		}
		$("#memberListInfo").html(memberInfoList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: "+data.resData[0].errorCd+"\n오류 메시지: "+data.resData[0].errorMsg);
	}
}

function getGenerList(rsltData) {
	var data = rsltData.resData[0];

	var generList="";
	for(i=0;i<data.generList.length;i++){
		var num=Number(data.generList[i].gener);
		generList=generList+"<li><a href=\"#\"  gener=\""+data.generList[i].gener+"\">"+num+"</a></li>\n";
	}
	$("#memberListPage").html(generList);
	$("#memberListPage li a").click(function() {
		requestJsonData("api/main/memberList.do", {gener:$(this).attr("gener")}, getMemberList);
	});

}

$(document).ready(function() {
	requestJsonData("api/main/memberGenerList.do", {}, getGenerList);
});
