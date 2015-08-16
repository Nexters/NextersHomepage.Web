var thisComponent = "";
var thisValue= "";
var thisName = "출석부";

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
			listSet += "<tr>";
			listSet += "<th scope='row'>"+listData.bookNo+"</th>";
			listSet += "<td onclick='BooksDetail("+listData.bookNo+",this)' style='cursor:pointer;'>"+listData.bookNm+"</td>";
			listSet += "<td>"+listData.total+"</td>";
			listSet += "<td><a class='btn btn-default btn-sm' onclick='mngBooksUser(this);' role='button'>사용자 관리</a>&nbsp;<a class='btn btn-default btn-sm' href='#' role='button'>수정</a>&nbsp;<a class='btn btn-default btn-sm' href='#' role='button'>삭제</a></td>"
			listSet += "<td>"+listData.total+"</td>";
			listSet += "<td>"+listData.total+"</td>";
			listSet += "</tr>";
		});
		
		$("#BookListTable tbody").html(listSet);
	}
}

//초기화
$(document).ready(function() {
	requestJsonData("api/admin/getBooksList.do", {}, getBooksList);
});