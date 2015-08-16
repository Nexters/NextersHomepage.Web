var isNotOk = true;

// 장부 리스트 화면
function getBooksList(data) {
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
	}
}


function ManageDetatil(bookNo, me) {
	var tds = $(me).parent().parent().find("td");
	$("#BookListDiv").fadeOut(function(){
		$("#ManageDetailDiv").fadeIn();
		$("#manageName").html("<h2>"+$(tds[1]).html()+"</h2><h5>"+$(tds[0]).html()+"</h5>");
	});
}

function returnList() {
	$("#ManageDetailDiv").fadeOut(function(){
		$("#BookListDiv").fadeIn();
		requestJsonData("api/admin/getManageList.do", {}, getBooksList);
	});
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
	requestJsonData("api/admin/getManageList.do", {}, getBooksList);
});