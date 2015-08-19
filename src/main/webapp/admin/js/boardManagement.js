var boardNo="";
var postViewTitleFlag=false;
var postViewContentFlag=false;
var postViewFilesFlag=false;

function share(){
    var share = {
        method: 'stream.share',
        u: 'nh.maden.kr/admin/index.html',
        t:'test123123'
    };
 
    FB.ui(share, function(response) { console.log(response); });
}
var commentRemove=function(data){
	if(data.result=="success"){
		requestJsonData("api/admin/getCommentList.do",{postNo:$('#postViewModal').attr("postNo")},getCommentList);
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var getCommentList=function(data){
	if(data.result=="success"){
		var commentList=data.resData[0].commentList;
		str="";
		for(i=0;i<commentList.length;i++){
			str+="<li style='border-bottom:1px solid black;' commentNo="+commentList[i].commentNo+">"+commentList[i].userNm+"&nbsp;&nbsp;"+commentList[i].commentDate+"<br>"+commentList[i].postComment+"<span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='commentRemove'>x</span></li>"; 
		}
		$('#commentList').html(str);
		
		$('.commentRemove').click(function(){
			requestJsonData("api/admin/commentRemove.do",{commentNo:$(this).parent().attr('commentNo')},commentRemove);
			
		})
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var addPostComment=function(data){
	if(data.result=="success"){
		$('#postViewModal textarea[name=postComment]').val('');
		
		requestJsonData("api/admin/getCommentList.do",{postNo:$('#postViewModal').attr("postNo")},getCommentList);
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var addMarkerHTML=function(lat,lng,title){
	
	var sHTML = "<iframe src='http://localhost:8080/NextersHomepage/admin/service/mapFrame.html?lat="+lat+"&lng="+lng+"&title="+title+"' width='300px' height='300px' style='overflow:hidden;' frameborder='0' scrolling='no'></iframe>"
	oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
	oEditors.getById["ir2"].exec("PASTE_HTML", [sHTML]);
}

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
		
		$('#fileName').show();
		$('#fileModify').hide();
		$('#postViewModal input[name=postViewTitle]').val(data.resData[0].list.postTitle);
		postContent=data.resData[0].list.postContent.split("<br>").join("\n");
		$('#postViewModal textarea[name=postViewContent').html(postContent);
		$('#viewContentDiv').html(postContent);
		temp=data.resData[0].list.file;
		if(temp!=null){
			$('#fileName').html(temp.substring(temp.indexOf("/")+1,temp.length));
		}
		
		$('#fileName').attr('file',data.resData[0].list.file);
		
		oEditors.getById["ir2"].exec("SET_CONTENTS", [""]); 
		
		$('#modifyRemovePost').attr('postNo',data.resData[0].list.postNo);
		$('#postViewModal').attr('postNo',data.resData[0].list.postNo);
		$('#modifyPost').attr('postNo',data.resData[0].list.postNo);
		
		
		requestJsonData("api/admin/getCommentList.do",{postNo:$('#postViewModal').attr("postNo")},getCommentList);
		
	
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var boardDelete=function(data){
	if(data.result=="success"){
		alert("삭제되었습니다!");
		requestJsonData("api/admin/boardList.do", {
			
			
			
		}, boardList);
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
					str+="<li><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"<span>&nbsp;&nbsp;&nbsp;x</span></a></li>";
				}
				else{
					str+="<li><a href='#' boardNo= "+list[i].boardNo+">"+list[i].boardName+"<span>&nbsp;&nbsp;&nbsp;x</span></a></li>";
				}
				
			}
			
			$('#boardListPage').html(str);
		}
		
		$('#boardListPage li a').click(function(){
			boardNo=$(this).attr('boardNo');
			
			requestJsonData("api/admin/postList.do", {
				
				boardNo:boardNo
				
			}, postList);
			
			$('#boardListPage li').removeClass('active');
			$(this).parent().addClass('active');
		})
		
		$('#boardListPage li a span').click(function(){
			if(confirm('정말 삭제하시겠습니까?')){
				boardNoParam=$(this).parent().attr('boardNo');
				requestJsonData("api/admin/boardDelete.do", {
					
					boardNo:boardNoParam
					
				}, boardDelete);
				
				return false;
			}
			return false;
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
		
		requestJsonData("api/admin/postList.do", {
			
			boardNo:boardNo
			
		}, postList);
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	
}
$(document).ready(function(){
	$('#fileModify').hide();
	$('#fileName').click(function(){
		if($(this).html().trim()!=""){
			location.href="../api/admin/fileDownload.do?fileName="+$(this).attr('file');
		}
	})
	var files;
	var fileName='';
	$('#postAddModal input[name=postFiles],#postViewModal input[name=fileModify]').on('change',function(event){
		postViewFilesFlag=true;
		files=event.target.files[0];
		fileName=files.name;
		var comma=fileName.lastIndexOf('.');
		var format=fileName.substring(comma+1);
		
		
		
	})
	
	requestJsonData("api/admin/boardList.do", {
		
		
		
	}, boardList);
	
	$('#modifyPost').hide();
	
	$('#addBoardButton').click(function(){
		
		var boardName=$('input[name=boardNm]').val().trim();
		var boardDir=$('input[name=boardDir').val().trim();
		var regType = /^[A-Za-z0-9+]*$/; 
		if(boardName==""){
			alert("이름을 한 글자 이상 입력하세요!");
			return;
		}
		else if(boardDir==""){
			alert("폴더를 한 글자 이상 입력하세요!");
			return;
		}
		else if(!regType.test(boardDir)){
			alert("폴더 이름은 영어와 숫자만 입력하세요!");
			return;
		}
		else{
			requestJsonData("api/admin/boardAdd.do", {
				
				boardName:boardName,
				boardDir:boardDir
				
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
		var myForm = new FormData();
		if(files!=undefined){
			myForm.append("uploadFile", files);
		}
		
	    myForm.append("boardNo",boardNo);
	    myForm.append("userNo",loginUserNo);
	    myForm.append("postTitle",postTitle);
	    myForm.append("postContent",postContent);
	    requestJsonDataMultipart("api/admin/postInsert.do",myForm,postInsert);
		
		
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
		oEditors.getById["ir2"].exec("UPDATE_CONTENTS_FIELD", []);
		$('#modifyRemovePost').hide();
		$('#modifyPost').show();
		$('#fileName').hide();
		$('#fileModify').show();
		postViewTitleFlag=false;
		postViewContentFlag=false;
		postViewFilesFlag=false;
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
		$('#fileName').show();
		$('#fileModify').hide();
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
		
		else if(postViewTitleFlag==false && postViewContentFlag==false &&postViewFilesFlag==false){
			alert('수정되었습니다!');
			$("#postViewModal").modal('hide');
			$("#postViewModal .form-control").val('');
			return;
		}
		
		var myForm = new FormData();
		var boardNo=$('#boardListPage').find('.active a').attr("boardNo");
		if(files!=undefined){
			myForm.append("uploadFile", files);
		}
		
	    myForm.append("boardNo",boardNo);
	    myForm.append("postNo",postNo);
	   
		if(postViewTitleFlag==true){
			myForm.append("postTitle",postTitle);
		}
		if(postViewContentFlag=true){
			myForm.append("postContent",postContent);
		}
		
		
		requestJsonDataMultipart("api/admin/modifyPost.do", myForm, modifyPost);
		
		
	})
	
	$('#postViewModal input[name=postViewTitle]').keyup(function(){
		postViewTitleFlag=true;
	})
	
	$('#postCommentWrite').click(function(){
		var postComment=$('#postViewModal textarea[name=postComment]').val().trim();
		
		if(postComment==""){
			alert('내용을 입력하세요!');
			
			return;
		}
		
		requestJsonData("api/admin/addPostComment.do",{
			postNo:$('#postViewModal').attr('postNo'),
			userNo:loginUserNo,
			postComment:postComment
		},addPostComment);
	})
	
	
	
	
})

var boardAdd=function(data){
	if(data.result=="success"){
		alert("추가되었습니다");
		
		$("#boardAddModal").modal('hide');
		$("#boardAddModal .form-control").val('');
		
		requestJsonData("api/admin/boardList.do", {
			
			
			
		}, boardList);
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

