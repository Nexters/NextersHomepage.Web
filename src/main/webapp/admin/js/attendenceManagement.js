var thisComponent = "";
var thisName="출석부";
function addDate(date){
	
	if (date.result == "success") {
		
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function pickDate(thisCom){
	
	var dateObject = thisCom.datepicker('getDate');
	var year = dateObject.getYear()+1900;
	var month = dateObject.getMonth()+1;
	var day = dateObject.getDate();
	
	console.log(year+" "+month+" "+day);
	
	thisComponent=thisCom;
	requestJsonData("api/admin/addDate.do", {
		year: year,
		month: month,
		day : day,
		bookNm : thisName
	}, addDate);
	
	
}

function getUserActiviyList(data) {

	if (data.result == "success") {

		var memberInfoList = "";

		for (i = 0; i < data.resData[0].memberList.length; i++) {
			
			memberInfoList += "<tr>";
			memberInfoList += "<td>" + data.resData[0].memberList[i].userNm + "</td>";
			
			for(j=0; j<data.resData[0].dateList.length;j++){
				
				
			}
			
			memberInfoList += "</tr>";
		}
		
		$("#activityList").html(memberInfoList)
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

$(document).ready(function() {
	
	$(".table").css("width", '0px');
	
	$("#date").css('position', 'absolute');
	$("#date").datepicker({
		onSelect : function() {
			pickDate($(this));
		}
	});
	
	$("#addDateButton").click(function() {

		$("#date").show();
	})

	requestJsonData("api/admin/getDateList.do", {
		bookNm : thisName
	}, getDateList);
	requestJsonData("api/admin/userActivityList.do", {
		
	}, getUserActiviyList);
});