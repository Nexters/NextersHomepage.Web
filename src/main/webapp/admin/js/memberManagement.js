
function getMemberColumn(data) {
	if(data.result=="success") {
		var rsltData = data.resData[0].attrList;
		var memberInfoList="";
		memberInfoList+="<tr>";
		memberInfoList+="<th>번호</th>";
		memberInfoList+="<th>이름</th>";
		memberInfoList+="<th>아이디</th>";
		memberInfoList+="<th>HP</th>";
		for(i=0;i<rsltData.length;i++) {
			memberInfoList+="<th columnname='"+rsltData[i].attr+"'>"+rsltData[i].desc+"</th>";
		}
		memberInfoList+="<th>권한</th>";
		memberInfoList+="<th>상태</th>";
		memberInfoList+="<th>액션</th>";
		memberInfoList+="</tr>";
		$("#DataTable thead").html(memberInfoList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: "+data.resData[0].errorCd+"\n오류 메시지: "+data.resData[0].errorMsg);
	}
}

function getMemberList(data) {
	if(data.result=="success") {
		var memberInfoList="";
		for(i=0;i<data.resData[0].userList.length;i++){
			memberInfoList+="<tr> \n";
			memberInfoList+="<td>"+ data.resData[0].userList[i].userNo +"</td>\n";
			memberInfoList+="<td>"+ data.resData[0].userList[i].userNm +"</td>\n";
			memberInfoList+="<td>"+ data.resData[0].userList[i].userId +"</td>\n";
			memberInfoList+="<td>"+ data.resData[0].userList[i].userCellNum +"</td>\n";
			$("#DataTable thead th[columnname]").each(function() {
				var isNotInsert = true;
				for(var j=0; j<data.resData[0].userList[i].userAddInfo.length; j++) {
					if(data.resData[0].userList[i].userAddInfo[j].attr==$(this).attr("columnname")) {
						memberInfoList+="<td>"+ data.resData[0].userList[i].userAddInfo[j].value+"</td>";
						isNotInsert = false;
					}	
				}
				
				if(isNotInsert) {
					memberInfoList+="<td>&nbsp;</td>";
				}
			});
			memberInfoList+="<td>"+getUserRole(data.resData[0].userList[i].userRole)+"</td>\n";
			memberInfoList+="<td>"+getUserStatus(data.resData[0].userList[i].userStatus)+"</td>\n";
			memberInfoList+="<td><button>수정</button><button>삭제</button></td>\n";
			memberInfoList+="<tr> \n";
		}
		$("#memberListInfo").html(memberInfoList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: "+data.resData[0].errorCd+"\n오류 메시지: "+data.resData[0].errorMsg);
	}
}

function getUserRole(userRole) {
	if(userRole=="1"){
		return "<span class='label label-info'>Admin</span>";
	} else if (userRole=="2") {
		return "<span class='label label-primary'>User</span>";
	}
}

function getUserStatus(userStatus) {
	if(userStatus=="0"){
		return "<span class='label label-success'>정상</span>";
	} else if(userStatus=="1"){
		return "<span class='label label-default'>비활성화</span>";
	} else if (userStatus=="2") {
		return "<span class='label label-warning'>계정 만료</span>";
	} else if (userStatus=="3") {
		return "<span class='label label-warning'>비밀번호 만료</span>";
	} else if (userStatus=="4") {
		return "<span class='label label-danger'>계정 잠금</span>";
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
	$("#memberListPage li a,#showAll a").click(function() {
		requestJsonData("api/admin/memberList.do", {gener:$(this).attr("gener")}, getMemberList);
	});

}

$(document).ready(function() {
	requestJsonData("api/main/memberAttr.do", {}, getMemberColumn);
	requestJsonData("api/main/memberGenerList.do", {}, getGenerList);
});
