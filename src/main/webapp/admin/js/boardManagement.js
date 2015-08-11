$(document).ready(function(){
	
	$('#addBoardButton').click(function(){
		var boardName=$('input[name=boardNm]').val();
		
		if(boardName.trim()==""){
			alert("한 글자 이상 입력하세요!");
		}
		else{
			requestJsonData("api/admin/boardAdd.do", {
				
				boardName:boardName
				
			}, boardAdd);
			
			
			
		}
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