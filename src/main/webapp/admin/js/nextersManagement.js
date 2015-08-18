var isNotOk = true;
var mngNo;
var nowTarget;
var nowValue;
// 장부 리스트 화면
function getManageList(data) {
	var columnSet = "";
	var listSet = "";
	columnSet += "<tr>";
	columnSet += "<th>번호</th>";
	columnSet += "<th>날짜</th>";
	columnSet += "<th>이름</th>";
	columnSet += "<th>인원</th>";
	columnSet += "<th>비고</th>";
	columnSet += "<th>액션</th>";
	columnSet += "</tr>";
	$("#BookListTable thead").html(columnSet);
	
	if(data.result=="success") {
		$(data.resData[0].list).each(function(idx, listData) {
			var ex = listData.mngremark;
			if(ex.length>14) {
				ex = ex.substr(0,15)+"...";
			}
			listSet += "<tr>";
			listSet += "<th scope='row'>"+listData.mngno+"</th>";
			listSet += "<td>"+listData.mngdate+"</td>";
			listSet += "<td>"+listData.mngtitle+"</td>";
			listSet += "<td>"+listData.mngcnt+"</td>";
			listSet += "<td><a href='javascript: void(0)' data-toggle='popover' title='비고' data-content='"+listData.mngremark+"'>"+ex+"</a></td>";
			listSet += "<td><button type='button' class='btn btn-default btn-xs'  onclick='ManageDetatil("+listData.mngno+",this)'>보기</button>&nbsp;<button type='button' class='btn btn-default btn-xs'>사용자</button>&nbsp;<button type='button' class='btn btn-danger btn-xs'>삭제</button></td>";
			listSet += "</tr>";
		});
		
		$("#BookListTable tbody").html(listSet);
		$("[data-toggle='popover']").popover();
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}


function ManageDetatil(bookNo, me) {
	var tds = $(me).parent().parent().find("td");
	$("#BookListDiv").fadeOut(function(){
		$("#ManageDetailDiv").fadeIn();
		$("#manageName").html("<h2>"+$(tds[1]).html()+"</h2><h5>"+$(tds[0]).html()+"</h5>");
		mngNo=bookNo;
		requestJsonData("api/admin/getAttendanceListByMngNo.do", {mngno:bookNo}, getAttendanceList);
		requestJsonData("api/admin/getBooksList.do", {mngno:bookNo}, getBooksList);
	});
}


function returnList() {
	$("#ManageDetailDiv").fadeOut(function(){
		$("#BookListDiv").fadeIn();
		requestJsonData("api/admin/getManageList.do", {}, getBooksList);
	});
}

function getBooksList(data){
	if(data.result=="success") {
		var startPosition = $("#mamng > div > div > div > table > thead > tr > th")[$("#mamng > div > div > div > table > thead > tr > th").size()-2];
		var booksColList = data.resData[0].list;
		var rsltTheadHtml;
		$(booksColList).each(function(idx, listData) {
			rsltTheadHtml += "<th>"+listData.title+"<br><small>"+commify(listData.amount)+"</small></th>";
			$(listData.val).each(function(ia, data) {
				var booksData = data.attval;
				var rsltBooks;
				if(booksData>=0){
					rsltBooks = commify(booksData);
				} else {
					var rsltMsg;
					if(booksData==-1) {
						rsltMsg = "불참";
					} else if(booksData==-3) {
						rsltMsg = "면제";
					}else if(booksData==-2) {
						rsltMsg = "n/a";
					}
					rsltBooks=rsltMsg;
				}
				
				$("tr[userNo="+data.userno+"] > td:last").after("<td books=true>"+rsltBooks+"</td>");
				$("td[books=true]").click(function() {
					var nowVal = GetNumString($(this).html());
					console.log("nowVal="+nowVal);
					console.log("nowval>=0::"+(nowVal>=0));
					var rsltModifyHtml;
					rsltModifyHtml="<div class='btn-group btn-group-xs' role='group' aria-label='selectBooks'>";
					rsltModifyHtml+="<button type='button' class='btn btn-default ";
					if(nowVal>=0) {
						rsltModifyHtml+="active";
					}
					rsltModifyHtml+="'>참석</button>";
					rsltModifyHtml+="<button type='button' class='btn btn-default ";
					if(nowVal=='불참') {
						rsltModifyHtml+="active";
					}
					rsltModifyHtml+="'>불참</button>";
					rsltModifyHtml+="<button type='button' class='btn btn-default ";
					if(nowVal=='면제') {
						rsltModifyHtml+="active";
					}
					rsltModifyHtml+="'>면제</button></div>";
					if(nowVal>=0) {
						rsltModifyHtml +="<input type='email' class='form-control input-sm' value='"+nowVal+"' id='booksVal'>";
					}
					$(this).html(rsltModifyHtml);

				});
			});
		});
		
		$(startPosition).after(rsltTheadHtml);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function GetNumString(s) {
    var rtn = parseFloat(s.replace(/,/gi, ""));
    console.log(rtn);
    if (isNaN(rtn)) {
        return s;
    }
    else {
        return rtn;
    }
}

function commify(n) {
    var reg = /(^[+-]?\d+)(\d{3})/;
    n += '';
    while (reg.test(n))
        n = n.replace(reg, '$1' + ',' + '$2');
    return n;
}

function getAttendanceList(data){
	$("#attendanceList").html("");
	$("#mamng > div > div > div > table > tbody").html("");
	if(data.result=="success") {
		var attendList = data.resData[0].list;
		var rsltHtml;
		var rsltTheadHtml;
		rsltTheadHtml += "<tr>";
		rsltTheadHtml += "<th class='text-center'>이름(번호)</th>";
		rsltTheadHtml += "<th class='text-center'>출석</th>";
		rsltTheadHtml += "<th><span id='addDateButton' class='glyphicon glyphicon-plus' style='cursor:pointer' aria-hidden='true'></span></th>";
		rsltTheadHtml += "</tr>";
		
		$(attendList).each(function(idx, listData) {
			var attendRslt = listData.eleId;
			rsltHtml +="<tr userNo='"+listData.userNo+"'>";
			rsltHtml += "<td>"+listData.userNm+"("+listData.userNo+")";
			if(attendRslt=="attend"){
				rsltHtml += "<td  style='text-align:center;'> <button class='btn btn-success btn-xs attendenceButton'>출석</button></td>";
			}else if(attendRslt=="late"){
				rsltHtml += "<td  style='text-align:center;'> <button class='btn btn-info btn-xs attendenceButton'>지각</button></td>";
			}else if(attendRslt=="absence"){
				rsltHtml += "<td  style='text-align:center;'> <button class='btn btn-warning btn-xs attendenceButton'>결석</button></td>";
			}else if(attendRslt=="nonotice"){
				rsltHtml += "<td  style='text-align:center;'> <button class='btn btn-danger btn-xs attendenceButton'>무단</button></td>";
			}else{
				rsltHtml += "<td  style='text-align:center;'> <button class='btn btn-xs attendenceButton'>n/a</button></td>";
			}
			rsltHtml += "</tr>";
		});
		
		$("#mamng > div > div > div > table > thead").html(rsltTheadHtml)
		$("#mamng > div > div > div > table > tbody").html(rsltHtml);
		$("#addDateButton").click(function () {
			$("#addMoney").modal("show");
		});
		$(".attendenceButton").click(function(){
			var status="";
			if($(this).html()=='n/a'){
				status="attend";
			}else if($(this).html()=='출석'){
				status="late";
			}else if($(this).html()=='지각'){
				status="absence";
			} else if($(this).html()=='결석') {
				status="nonotice";
			} else if($(this).html()=='무단') {
				status="attend";
			}
			nowTarget = $(this);
			nowValue=status;
			requestJsonData("api/admin/insertAttendance.do", {
				mngno : mngNo,
				value : status,
				userno : $(this).parent().parent().attr("userNo"),
			}, modifyAttendance);
		})
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function modifyAttendance(data){
	if (data.result == "success") {
		var rsltValue;
		switch(nowValue){
		
		case "attend":
			nowTarget.removeClass("btn-danger");
			nowTarget.addClass("btn-success");
			rsltValue="출석";
			break;
			
		case "late":
			nowTarget.removeClass("btn-success");
			nowTarget.addClass("btn-info");
			rsltValue="지각";
			break;
			
		case "absence":
			nowTarget.removeClass("btn-info");
			nowTarget.addClass("btn-warning");
			rsltValue="결석";
			break;
		case "nonotice":
			nowTarget.removeClass("btn-warning");
			nowTarget.addClass("btn-danger");
			rsltValue="무단";
			break;
		}
		nowTarget.html(rsltValue);
	} else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function checkDate(data) {
	var isCnt = data.resData[0].cnt;
	if(isCnt>0){
		$("#dateDiv").removeClass("has-success");
		$("#dateDiv").addClass("has-error");
		isNotOk=true;
	} else {
		$("#dateDiv").removeClass("has-error");
		$("#dateDiv").addClass("has-success");
		isNotOk=false;
	}
}

function submitMng() {
	var mngDate = $("input[name=mngDate").val();
	var mngName = $("input[name=mngName").val();
	var mngRemark = $("#mngRemark").val();
	if(mngDate==""){
		alert("날짜를 선택해주세요.");
		return;
	} else if(isNotOk){
		alert("해당 날짜는 이미 등록되어 있습니다.");
		return;
	} else if(mngName=="") {
		alert("이름을 입력해주세요.");
		return;
	}
	requestJsonDataNoLoading("api/admin/insertManager.do", {date:mngDate,title:mngName,remark:mngRemark}, insertManager);
}

function submitMny() {
	var mnyName = $("input[name=mnyName").val();
	var mnyAmount = $("input[name=mnyAmount").val();
	
	requestJsonDataNoLoading("api/admin/insertBooks.do", {mngno:mngNo,title:mnyName,amount:mnyAmount}, insertBooks);
}

function insertManager(data){
	if(data.result=="success"){
		var rslt = data.resData[0].result;
		if(rslt>0) {
			requestJsonData("api/admin/getManageList.do", {}, getBooksList);
			$("#addMng").modal("hide");
			$("#dateDiv").removeClass("has-error");
			$("#dateDiv").removeClass("has-success");
			$("input[name=mngDate").val("");
			$("input[name=mngName").val("");
			$("#mngRemark").val("");
			isNotOk=true;
		} else {
			alert("등록 실패했습니다.");
		}
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function insertBooks(data){
	if(data.result=="success"){
		var rslt = data.resData[0].rslt;
		if(rslt>0) {
			requestJsonData("api/admin/getAttendanceListByMngNo.do", {mngno:mngNo}, getAttendanceList);
			requestJsonData("api/admin/getBooksList.do", {mngno:mngNo}, getBooksList);
			$("#addMoney").modal("hide");
		} else {
			alert("등록 실패했습니다.");
		}
		$("input[name=mnyAmount").val("");
		$("input[name=mnyName").val("");
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

//초기화
$(document).ready(function() {
	$("input[name=mngDate]").datepicker({
		dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
		dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
		monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		changeMonth: true, 
        changeYear: true,
        nextText: '다음 달',
        prevText: '이전 달' ,
		dateFormat : "ymmdd",
		onSelect : function() {
			requestJsonDataNoLoading("api/admin/getManageCnt.do", {date:$("input[name=mngDate]").val()}, checkDate);
		}
	});
	requestJsonData("api/admin/getManageList.do", {}, getManageList);
});