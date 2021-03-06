var thisComponent = "";
var thisValue= "";
var thisName = "출석부";

// 장부 리스트 화면
function getBooksList(data) {
	var columnSet = "";
	var listSet = "";
	columnSet += "<tr>";
	columnSet += "<th>장부번호</th>";
	columnSet += "<th>장부이름</th>";
	columnSet += "<th>해당 사용자 수</th>";
	columnSet += "<th>액션</th>";
	columnSet += "</tr>";
	$("#BookListTable thead").html(columnSet);
	
	if(data.result=="success") {
		$(data.resData[0].list).each(function(idx, listData) {
			listSet += "<tr>";
			listSet += "<th scope='row'>"+listData.bookNo+"</th>";
			listSet += "<td onclick='BooksDetail("+listData.bookNo+",this)' style='cursor:pointer;'>"+listData.bookNm+"</td>";
			listSet += "<td>"+listData.total+"</td>";
			listSet += "<td><a class='btn btn-default btn-sm' onclick='mngBooksUser(this);' role='button'>사용자 관리</a>&nbsp;<a class='btn btn-default btn-sm' href='#' role='button'>수정</a>&nbsp;<a class='btn btn-default btn-sm' href='#' role='button'>삭제</a></td>"
			listSet += "</tr>";
		});
		
		$("#BookListTable tbody").html(listSet);
	}
}

function mngBooksUser(target) {
	var tempData = $(target).parent().parent().find("td")[0];
	$("#booksName").html($(tempData).html());
	$("#BooksUserManagementDiv").fadeIn();
}

function BooksDetail(bookNo, itsme) {
	$("#BookListDiv").fadeOut(function(){
		$("#BookDetailDiv").fadeIn();
		$("#booksName").html($(itsme).html());
		$(".custom-table").css("width", '0px');
		requestJsonData("api/admin/getDateList.do", {
			bookNm : thisName
		}, getDateList);
	});
}

function returnList() {
	$("#BookDetailDiv").fadeOut(function(){
		$("#BookListDiv").fadeIn();
		requestJsonData("api/admin/getBooksList.do", {}, getBooksList);
	});
}

//출석 화면
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
		
		requestJsonData("api/admin/getDateList.do", {
			bookNm : thisName
		}, getDateList);

	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getDateList(data) {


	if (data.result == "success") {
		$("#attendenceTable").hide();
		var dateList = "";

		dateList = "<tr> <th>이름</th>"
		for (i = 0; i < data.resData[0].dateList.length; i++) {

			var date = data.resData[0].dateList[i].bookColumnNo.split('-');

			dateList += "<th>" + date[1] + "월" + date[2] + "일" + "<span info=\"" + data.resData[0].dateList[i].bookColumnNo + "\"class='glyphicon glyphicon-remove-circle deleteButton' aria-hidden='true'></span></th>";
		}

		dateList += "<th><span id='addDateButton' class='glyphicon glyphicon-plus' aria-hidden='true'></span> <div id='date'></div></th>";
		dateList += "</tr>";

		$("#dateList").html(dateList);

		$(".deleteButton").click(function() {

			requestJsonData("api/admin/removeDateColumn.do", {
				bookNm : thisName,
				columnNo : $(this).attr("info")
			}, removeDateColumn);

		})

		addDatePicker();
		
		requestJsonData("api/admin/userActivityList.do", {}, userActiviyList);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	
}

function insertBooksValue(){
	
}
function addDate(data) {

	if (data.result == "success") {

		$("#activityList").html('');
		
		requestJsonData("api/admin/getDateList.do", {
			bookNm : thisName
		}, getDateList);
		
		requestJsonData("api/admin/insertBooksValue.do", {
			bookNm : thisName,
			columnNo : thisComponent
		}, insertBooksValue);
		
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

	thisComponent = year+"-"+month+"-"+day;
	
	requestJsonData("api/admin/addDate.do", {

		year : year,
		month : month,
		day : day,
		bookNm : thisName

	}, addDate);

}

function userActiviyList(data) {

	if (data.result == "success") {

		var memberNameList = "";

		for (i = 0; i < data.resData[0].memberList.length; i++) {

			memberNameList += "<tr>";
			memberNameList += "<td>" + data.resData[0].memberList[i].userNm + "</td>";
			memberNameList += "</tr>";
		}
		
		$("#activityList").html(memberNameList)
		
		requestJsonData("api/admin/booksValueList.do", {}, booksValueList);
		
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function modifyBooksValue(data){
	
	if (data.result == "success") {

		switch(thisValue){
		
		case "출석":
			thisComponent.removeClass("btn-danger");
			thisComponent.addClass("btn-success");
			break;
			
		case "지각":
			thisComponent.removeClass("btn-success");
			thisComponent.addClass("btn-warning");
			break;
			
		case "결석":
			thisComponent.removeClass("btn-warning");
			thisComponent.addClass("btn-danger");
			break;
			
		}
		thisComponent.html(thisValue);
		
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}

	
}
function booksValueList(data){
	
	if (data.result == "success") {
		
		var numOfDate=($("#dateList").children().children().length-2);
		console.log(numOfDate);
		
		for(i=0;i<numOfDate;i++){
			var trNum=0;
			$("#activityList tr").each(function(){
				var value=data.resData[0].valueList[trNum+i*data.resData[0].valueList.length/numOfDate].value;
				var userNo=data.resData[0].valueList[trNum+i*data.resData[0].valueList.length/numOfDate].userNo;
				var bookcolumnno=data.resData[0].valueList[trNum+i*data.resData[0].valueList.length/numOfDate].bookColumnNo;
				for(var key in data.resData[0].valueList[trNum+i*data.resData[0].valueList.length/numOfDate]){
					console.log(key);
				}
				var userInfo="userNo='"+userNo+"' "+"bookcolumnno='"+bookcolumnno+"' ";
				if(value=="출석"){
					$(this).append("<td  style='text-align:center;'> <button "+userInfo+" class='btn btn-success btn-xs attendenceButton'>출석</button></td>");
				}else if(value=="지각"){
					$(this).append("<td  style='text-align:center;'> <button "+userInfo+" class='btn btn-warning btn-xs attendenceButton'>지각</button></td>");
				}else if(value=="결석"){
					$(this).append("<td  style='text-align:center;'> <button "+userInfo+" class='btn btn-danger btn-xs attendenceButton'>결석</button></td>");
				}else{
					$(this).append("<td  style='text-align:center;'> <button "+userInfo+" class='btn btn-success btn-xs attendenceButton'>출석</button></td>");
				}
				trNum++;
			})
		}
		$('.attendenceButton').click(function(){
			var status="";
			if($(this).html()=='결석'){
				status="출석";
			}else if($(this).html()=='출석'){
				status="지각";
			}else{
				status="결석";
			}
			
			thisComponent=$(this);
			thisValue=status;
			
			requestJsonData("api/admin/modifyBooksValue.do", {
				
				bookNm : thisName,
				value : status,
				userNo : $(this).attr("userNo"),
				columnNo : $(this).attr("bookcolumnno")
				
			}, modifyBooksValue);
		})
		
		$("#attendenceTable").show();
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}


//초기화
$(document).ready(function() {
	requestJsonData("api/admin/getBooksList.do", {}, getBooksList);
});