var thisComponent = "";
var thisName = "출석부";

function addDatePicker() {

	$("#date").datepicker({
		onSelect : function() {
			pickDate($(this));
			$(this).hide();
		},
		onCreate : function() {
			$(this).draggable();
		}
	});

	$("#addDateButton").click(function() {

		$("#date").show();
	})

	$("#date").css('position', 'absolute');
	$("#date").hide();

}

function removeDateColumn(data) {

	if (data.result == "success") {

	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getDateList(data) {

	/*
	<th>7월 29일 <span class="glyphicon glyphicon-remove-circle"
		aria-hidden="true"></span>
	</th>
	<th><span class="glyphicon glyphicon-plus"
		aria-hidden="true"></span>
		<div id="date"></div></th>*/

	if (data.result == "success") {

		var dateList = "";

		for (i = 0; i < data.resData[0].dateList.length; i++) {

			var date = data.resData[0].dateList[i].bookColumnNo.split('-');

			dateList += "<th>" + date[1] + "월" + date[2] + "일" + "<span info=\"" + data.resData[0].dateList[i].bookColumnNo + "\"class='glyphicon glyphicon-remove-circle deleteButton' aria-hidden='true'></span></th>";
		}

		dateList += "<th><span id='addDateButton' class='glyphicon glyphicon-plus' aria-hidden='true'></span> <div id='date'></div></th>";

		$("#startName").after(dateList);

		$(".deleteButton").click(function() {
			
			requestJsonData("api/admin/removeDateColumn.do", {
				bookNm : thisName,
				columnNo : '2015-8-12'
			}, removeDateColumn);
			
		})
		addDatePicker();

	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}

}
function addDate(data) {

	if (data.result == "success") {

	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function pickDate(thisCom) {

	var dateObject = thisCom.datepicker('getDate');
	var year = dateObject.getYear() + 1900;
	var month = dateObject.getMonth() + 1;
	var day = dateObject.getDate();

	console.log(year + " " + month + " " + day);

	thisComponent = thisCom;
	requestJsonData("api/admin/addDate.do", {

		year : year,
		month : month,
		day : day,
		bookNm : thisName

	}, addDate);

}

function getUserActiviyList(data) {

	if (data.result == "success") {

		//alert(data.resData[0].memberList.length);
		var memberNameList = "";

		for (i = 0; i < data.resData[0].memberList.length; i++) {

			memberNameList += "<tr>";
			memberNameList += "<td>" + data.resData[0].memberList[i].userNm + "</td>";
			memberNameList += "</tr>";
		}

		$("#activityList").html(memberNameList)
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

$(document).ready(function() {

	$(".table").css("width", '0px');

	/*requestJsonData("api/admin/getDateList.do", {
		bookNm : thisName
	}, getDateList);*/

	requestJsonData("api/admin/getDateList.do", {
		bookNm : thisName
	}, getDateList);

	//requestJsonData("api/admin/userActivityList.do", {}, getUserActiviyList);

});