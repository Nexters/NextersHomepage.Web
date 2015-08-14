var boardNo="";
var postViewTitleFlag=false;
var postViewContentFlag=false;

var setPostViewContentFlag=function(value){
	postViewContentFlag=value;
	
}
var modifyPost=function(data){
	if(data.result=="success"){
		alert('수정되었습니다!');
		$("#postViewModal").modal('hide');
		$("#postViewModal .form-control").val('');
		
		requestJsonData("api/admin/postList.do", {
			
			boardNo:boardNo
			
		}, postList);
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	
}
var removePost=function(data){
	if(data.result=="success"){
		alert("삭제되었습니다!");
		$("#postViewModal").modal('hide');
		$("#postViewModal .form-control").val('');
		requestJsonData("api/admin/postList.do", {
			
			boardNo:boardNo
			
		}, postList);
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var postView=function(data){
	if(data.result=="success"){
		$('#postViewModal input[name=postViewTitle]').val(data.resData[0].list.postTitle);
		postContent=data.resData[0].list.postContent.split("<br>").join("\n");
		$('#postViewModal textarea[name=postViewContent').html(postContent);
		$('#viewContentDiv').html(postContent);
		
		oEditors.getById["ir2"].exec("SET_CONTENTS", [""]); 
		
		$('#modifyRemovePost').attr('postNo',data.resData[0].list.postNo);
		$('#modifyPost').attr('postNo',data.resData[0].list.postNo);
		
		
		
	
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var boardList=function(data){
	
	if(data.result=="success"){
		
		var list=data.resData[0].list;
		
		if(list.length>=1){
			var str="";
			for(i=0;i<list.length;i++){
				if(i==0){
					str+="<li><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"</a></li>";
				}
				else{
					str+="<li><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"</a></li>";
				}
				
			}
			
			$('#boardListPage').html(str);
		}
		
		$('#boardListPage li').click(function(){
			boardNo=$(this).find('a').attr('boardNo');
			
			requestJsonData("api/admin/postList.do", {
				
				boardNo:boardNo
				
			}, postList);
			
			$('#boardListPage li').removeClass('active');
			$(this).addClass('active');
		})
		
		
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

var postList=function(data){
	if(data.result=="success"){
		list=data.resData[0].list;
		if(list.length>=1){
			var str="";
			for(i=0;i<list.length;i++){
				str+="<tr class='postTr' postNo="+list[i].postNo+" data-toggle='modal' href='#postViewModal'>";
				str+="<td>"+list[i].postNo+"</td>";
				str+="<td>"+list[i].userName+"</td>";
				str+="<td>"+list[i].postTitle+"</td>";
				str+="<td>"+list[i].postDate+"</td>";
				str+="<td>"+list[i].postHits+"</td>";
				str+="</tr>";
			}
			$('#boardTable table tbody').html(str);
			
			$('.postTr').click(function(){
				
				$('#modifyRemovePost').show();
				$('#modifyPost').hide();
				$('#postViewModal input[name=postViewTitle]').attr('readonly','true');
				$('#postViewModal textarea[name=postViewContent]').attr('readonly','true');
				postViewTitleFlag=false;
				postViewContentFlag=false;
				postNo=$(this).attr("postNo");
				$('#viewContentDiv').show();
				$('iframe').hide();
				requestJsonData("api/admin/getPost.do", {
					
					postNo:postNo
					
				}, postView);
			})
		}
		else{
			$('#boardTable table tbody').html('');
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
	
	$('#modifyPost').hide();
	
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
	$('#postWriteButton').click(function(){
		var boardNo=$('#boardListPage').find('.active a').attr("boardNo");
		if(boardNo==undefined){
			alert("게시판을 선택해주세요!");
			return false;
		}
		oEditors.getById["ir1"].exec("SET_CONTENTS", [""]); 
		$('iframe').show();
		
	})
	$('#addPostButton').click(function(){
		var boardNo=$('#boardListPage').find('.active a').attr("boardNo");
		
		var postTitle=$('input[name=postTitle]').val().trim();
		if(postTitle==""){
			alert("제목을 입력하세요!");
			return;
		}
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
		var postContent=$('textarea[name=postContent]').val().trim();
		if(postContent==""){
			alert("내용을 입력하세요!");
			return;
		}
		postContent=postContent.split("\n").join("<br>");
		
		requestJsonData("api/admin/postInsert.do", {
			
			boardNo:boardNo,
			userNo:loginUserNo,
			postTitle:postTitle,
			postContent:postContent
			
		}, postInsert);
		
	})
	$('#removePostButton').click(function(){
		if(confirm('정말 삭제하시겠습니까?')){
			postNo=$(this).parent().attr('postNo');
			
			requestJsonData("api/admin/removePost.do", {
				
				postNo:postNo
				
			}, removePost);
		}
		
	})
	
	$('#modifyButton').click(function(){
		$('#modifyRemovePost').hide();
		$('#modifyPost').show();
		postViewTitleFlag=false;
		postViewContentFlag=false;
		$('#postViewModal input[name=postViewTitle]').removeAttr('readonly');
		$('#postViewModal textarea[name=postViewContent]').removeAttr('readonly');
		
		sHTML=$('#viewContentDiv').html();
		postViewContentConfirm=sHTML;
		oEditors.getById["ir2"].exec("PASTE_HTML", [sHTML]);
		$('#viewContentDiv').hide();
		$('iframe').show();
	})
	
	$('#resetButton').click(function(){
		$('#modifyRemovePost').show();
		$('#modifyPost').hide();
		$('#postViewModal input[name=postViewTitle]').attr('readonly','true');
		$('#postViewModal textarea[name=postViewContent]').attr('readonly','true');
		$('iframe').hide();
		$('#viewContentDiv').show();
	})
	
	$('#modifyPostButton').click(function(){
		postTitle=$('#postViewModal input[name=postViewTitle]').val();
		postNo=$(this).parent().attr('postNo');
		
		if(postTitle.trim()==""){
			alert('제목을 입력하세요!')
			return;
		}
		
		oEditors.getById["ir2"].exec("UPDATE_CONTENTS_FIELD", []);
	
		postContent=$('#postViewModal textarea[name=postViewContent]').val();
		
		
		if(postContent.trim()==""){
			alert('내용을 입력하세요!')
			return;
		}
		if(postViewTitleFlag==true && postViewContentFlag==true){
			data={postTitle:postTitle,postContent:postContent,postNo:postNo};
		}
		else if(postViewTitleFlag==false && postViewContentFlag==false){
			alert('수정되었습니다!');
			$("#postViewModal").modal('hide');
			$("#postViewModal .form-control").val('');
			return;
		}
		
		else if(postViewTitleFlag==true){
			data={postTitle:postTitle,postNo:postNo};
		}
		else if(postViewContentFlag=true){
			data={postContent:postContent,postNo:postNo};
		}
		
		
		requestJsonData("api/admin/modifyPost.do", data, modifyPost);
		
		
	})
	
	$('#postViewModal input[name=postViewTitle]').keyup(function(){
		postViewTitleFlag=true;
	})
	$('#se2_iframe').click(function(){
		alert(1)
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

