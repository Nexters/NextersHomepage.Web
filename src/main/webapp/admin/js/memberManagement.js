function memberModify(data) {
	if (data.result == "success") {

	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function SmemberAdd(data){
	
	if (data.result == "success") {
		
		alert("addmember");
		
		requestJsonData("api/admin/memberList.do", {
			gener : $("#memberListPage li.active a").attr("gener")
		}, getMemberList);
		
		$("#myModal").modal('hide');
		$("#myModal .form-control").val('');
				
	} else {
		
		$("#myModal").modal('hide');
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	
}
function putAddUser(location){
	
	location.html("<button href=\"#myModal\" data-toggle=\"modal\">회원 추가</button>");
	
	var generNum=$("#memberListPage li.active a").attr("gener");
	
	$("#generNum").html(generNum);
	alert("asd");
}


function deleteMember(data) {
	
	if (data.result == "success") {
		alert("삭제 성공");
		requestJsonData("api/admin/memberList.do", {
			gener : $("#memberListPage li.active a").attr("gener")
		}, getMemberList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getMemberColumn(data) {
	if (data.result == "success") {
		
		var rsltData = data.resData[0].attrList;
		var memberInfoList = "";
		memberInfoList += "<tr>";
		memberInfoList += "<th>번호</th>";
		memberInfoList += "<th>이름</th>";
		memberInfoList += "<th>아이디</th>";
		memberInfoList += "<th>HP</th>";
		for (i = 0; i < rsltData.length; i++) {
			memberInfoList += "<th columnname='" + rsltData[i].attr + "'>" + rsltData[i].desc + "</th>";
		}
		memberInfoList += "<th>권한</th>";
		memberInfoList += "<th>상태</th>";
		memberInfoList += "<th>활동</th>";
		memberInfoList += "<th>액션</th>";
		memberInfoList += "</tr>";
		$("#DataTable thead").html(memberInfoList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function getMemberList(data) {
	if (data.result == "success") {
		putAddUser($("#addUser"));
		var memberInfoList = "";
		for (i = 0; i < data.resData[0].userList.length; i++) {
			memberInfoList += "<tr id=\"" + data.resData[0].userList[i].userNo + "\"> \n";
			memberInfoList += "<td name=\"userNo\" info=\"" + data.resData[0].userList[i].userNo + "\">" + data.resData[0].userList[i].userNo + "</td>\n";
			memberInfoList += "<td name=\"userNm\" info=\"" + data.resData[0].userList[i].userNm + "\">" + data.resData[0].userList[i].userNm + "</td>\n";
			memberInfoList += "<td name=\"userId\" info=\"" + data.resData[0].userList[i].userId + "\">" + data.resData[0].userList[i].userId + "</td>\n";
			memberInfoList += "<td name=\"userCellNum\" info=\"" + data.resData[0].userList[i].userCellNum + "\">" + data.resData[0].userList[i].userCellNum + "</td>\n";
			$("#DataTable thead th[columnname]").each(function() {
				var isNotInsert = true;
				for (var j = 0; j < data.resData[0].userList[i].userAddInfo.length; j++) {
					if (data.resData[0].userList[i].userAddInfo[j].attr == $(this).attr("columnname")) {
						memberInfoList += "<td info=\"" + data.resData[0].userList[i].userAddInfo[j].value + "\">" + data.resData[0].userList[i].userAddInfo[j].value + "</td>";
						isNotInsert = false;
					}
				}

				if (isNotInsert) {
					memberInfoList += "<td>&nbsp;</td>";
				}
			});
			memberInfoList += "<td>" + getUserRole(data.resData[0].userList[i].userRole) + "</td>\n";
			memberInfoList += "<td>" + getUserStatus(data.resData[0].userList[i].userStatus) + "</td>\n";
			memberInfoList += "<td>" + "<button class='btn btn-default btn-sm'>"+data.resData[0].userList[i].activityyn + "</td>\n";
			memberInfoList += "<td><button status=\"modify\" class=\"infoChange\" userNo=\"" + data.resData[0].userList[i].userNo + "\">수정</button><button class=\"delete\" userNo=\""
					+ data.resData[0].userList[i].userNo + "\">삭제</button></td>\n";
			memberInfoList += "<tr> \n";
		}
		$("#memberListInfo").html(memberInfoList);

		$(".delete").click(function() {
			alert($(this).attr("userNo"));
			requestJsonData("api/admin/memberRemove.do", {
				userNo : $(this).attr("userNo")
			}, deleteMember);
		});

		$(".infoChange").click(function() {

			if ($(this).attr("status") == "modify") {

				$("#" + $(this).attr("userNo") + " td").each(function() {
					if ($(this).attr("info") != null && $(this).attr("name")!="userId") {

						var inputMemberListInfo = "<input size=\"13\" name=\"" + $(this).attr("name") + "\" type=\"text\" value=\"" + $(this).attr("info") + "\">";
						$(this).html(inputMemberListInfo);
					}
				})
				
				$(this).removeAttr("status")
				$(this).attr("status", "save");
				$(this).html("저장");
				
			} else {
				
				requestJsonData("api/admin/memberModify.do", {

					userNo : $("input[name=userNo]").val(),
					userNm : $("input[name=userNm]").val(),
					userCellNum : $("input[name=userCellNum]").val()
				}, memberModify);
				
				$("#" + $(this).attr("userNo") + " td").each(function() {
					if ($(this).attr("info") != null && $(this).attr("name")!="userId") {
						var inputMemberListInfo =   $("input[name="+$(this).attr("name")+"]").val();
						$(this).html(inputMemberListInfo);
					}
				})
				
				$(this).removeAttr("status")
				$(this).attr("status", "modify");
				$(this).html("수정");
				
			}
		});
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getUserRole(userRole) {
	if (userRole == "1") {
		return "<span class='label label-info'>Admin</span>";
	} else if (userRole == "2") {
		return "<span class='label label-primary'>User</span>";
	}
}

function getUserStatus(userStatus) {
	if (userStatus == "0") {
		return "<span class='label label-success'>정상</span>";
	} else if (userStatus == "1") {
		return "<span class='label label-default'>비활성화</span>";
	} else if (userStatus == "2") {
		return "<span class='label label-warning'>계정 만료</span>";
	} else if (userStatus == "3") {
		return "<span class='label label-warning'>비밀번호 만료</span>";
	} else if (userStatus == "4") {
		return "<span class='label label-danger'>계정 잠금</span>";
	}
}

function getGenerList(rsltData) {
	var data = rsltData.resData[0];
	var generList = "";
	for (i = 0; i < data.generList.length; i++) {
		var num = Number(data.generList[i].gener);
		generList = generList + "<li><a href=\"#\"  gener=\"" + data.generList[i].gener + "\">" + num + "</a></li>\n";
	}

	var addButton = "<li><a href=\"#\" id=\"addButton\" >+</a></li>\n";
	generList += addButton;
	$("#memberListPage").html(generList);
	
	$("#memberListPage li a,#showAll a").click(function() {
		if ($(this).attr("gener") == null) {

		} else {
			requestJsonData("api/admin/memberList.do", {
				gener : $(this).attr("gener")
			}, getMemberList);
		}
		$("#memberListPage li").removeClass("active");
		$(this).parent().addClass("active");
	});

	$("#addButton").click(function() {
		
		var count = $("#memberListPage").children().length;
		$("<li><a href=\"#\"  gener=\"" + "0"+count + "\">" + count + "</a></li>\n").insertBefore($(this).parent());
		$("#memberListPage li").removeClass("active");
		
		$("#memberListPage li a,#showAll a").click(function() {
			if ($(this).attr("gener") == null) {

			} else {
				requestJsonData("api/admin/memberList.do", {
					gener : $(this).attr("gener")
				}, getMemberList);
			}
			$("#memberListPage li").removeClass("active");
			$(this).parent().addClass("active");
		});
	});

}

$(document).ready(function() {
	requestJsonData("api/main/memberAttr.do", {}, getMemberColumn);
	requestJsonData("api/main/memberGenerList.do", {}, getGenerList);
	console.log("------------------");
	
	$("#SmemberAdd").click(function(){
		console.log("추가");
		
		console.log($("#memberListPage li.active a").attr("gener"));
		
		
		requestJsonData("api/admin/SmemberAdd.do", {
			
			grade : $("#memberListPage li.active a").attr("gener"),
			position : $("#myModal input[type='checkbox']").val(),
			userId : $("#myModal input[name=userId]").val(),
			userNm : $("#myModal input[name=userNm]").val(),
			userCellNum : $("#myModal input[name=userCellNum]").val()
			
		}, SmemberAdd);
	})
});
