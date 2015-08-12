var boardNo="";

var boardList=function(data){
	
	if(data.result=="success"){
		
		var list=data.resData[0].list;
		
		if(list.length>=1){
			var str="";
			for(i=0;i<list.length;i++){
				if(i==0){
					str+="<li class='active'><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"</a></li>";
				}
				else{
					str+="<li><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"</a></li>";
				}
				
			}
			
			$('#boardListPage').html(str);
		}
		
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

var postInsert=function(data){
	if(data.result=="success"){
		alert("완료되었습니다!");
		$("#postAddModal").modal('hide');
		$("#postAddModal .form-control").val('');
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	
}
$(document).ready(function(){
	
	requestJsonData("api/admin/boardList.do", {
		
		
		
	}, boardList);
	
	
	$('#addBoardButton').click(function(){
		var boardName=$('input[name=boardNm]').val().trim();
		
		if(boardName==""){
			alert("한 글자 이상 입력하세요!");
		}
		else{
			requestJsonData("api/admin/boardAdd.do", {
				
				boardName:boardName
				
			}, boardAdd);
			
			
			
		}
	})
	$('#addPostButton').click(function(){
		var boardNo=$('#boardListPage').find('.active a').attr("boardNo");
		var postTitle=$('input[name=postTitle]').val().trim();
		if(postTitle==""){
			alert("제목을 입력하세요!");
			return;
		}
		var postContent=$('textarea[name=postContent]').val().trim();
		if(postContent==""){
			alert("내용을 입력하세요!");
			return;
		}
		postContent=postContent.split("\n").join("<br>");
		
		requestJsonData("api/admin/postInsert.do", {
			
			boardNo:boardNo,
			postTitle:postTitle,
			postContent:postContent
			
		}, postInsert);
		
	})
	
})

var boardAdd=function(data){
	if(data.result=="success"){
		alert("추가되었습니다");
		
		$("#boardAddModal").modal('hide');
		$("#boardAddModal .form-control").val('');
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

