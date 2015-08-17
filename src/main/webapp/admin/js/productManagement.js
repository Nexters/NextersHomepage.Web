var projectNmChanged=false;
var projectImgChanged=false;
var projectLinkChanged=false;
var projectDescChanged=false;
var projectMemberChanged=false;
var projectMemberIndex=-1;

var projectMemberList=function(data){
	if(data.result=="success"){
		memberList=data.resData[0].memberList;
		str="";
		for(i=0;i<memberList.length;i++){
			
			str+="<li style='list-style:none;' userNo="+memberList[i].userNo+" userNm="+memberList[i].userNm+">"+memberList[i].userNo+"&nbsp;&nbsp;"+memberList[i].userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
			
		}
		$('#userListResultModify').html(str);
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var getMemberList=function(data){
	
	if(data.result=="success"){
		userList=data.resData[0].tagList;
		str="";
		for(i=0;i<userList.length;i++){
			str+="<li userNo="+userList[i].userNo+" userNm="+userList[i].userNm+" style='list-style:none;border-bottom:1px solid black;'>"+userList[i].userNo+"&nbsp;&nbsp;"+userList[i].userNm+"</li>";
		}
		
		$('.receiveUserList').html(str);
		
		$('.receiveUserList li').hover(function(){
			
			$('.receiveUserList').find('.projectMemberList').removeClass('projectMemberList');
			$(this).addClass('projectMemberList');
			
		},function(){
			$('.receiveUserList').find('.projectMemberList').removeClass('projectMemberList');
		})
		
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function deleteFunction(data){
	if(data.result=="success"){
		requestJsonData("api/admin/getProjectList.do", {
  			
  		}, getProjectList)
	}
	else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getProjectList(data){
	
	if (data.result == "success") {
		
		var list=data.resData[0].list;
		var productList="";
		for(i=0;i<list.length;i++){
			
			productList+="<div class='col-lg-3 productList' align='center'><div class='inner'><div style='margin:5px;' align='right' status="+list[i].projectNo+"><input type='button' class='btn btn-default btn-xs productModify' value='수정' data-toggle='modal' href='#productModifyModal'>&nbsp;<input type='button' class='btn btn-danger btn-xs productRemove' value='삭제' ></div><span class='projectNm' style='font-weight:bold;'>"+list[i].projectNm+"</span><br>";
			productList+="<a class='projectLink' href='"+list[i].projectLink+"'><img class='projectImg' width='200px' height='100px' style='margin-top:15px;' alt='"+list[i].projectImg+"' src='img/product/"+list[i].projectImg+"'></a><br>";
			productList+="<span class='projectDesc'>"+list[i].projectDesc+"</span></div></div>";
			
			
		}
		productList+="";
		
		$('#productTable').html(productList);
		
		$('.productRemove').click(function(){
			if(!confirm("정말 삭제하시겠습니까?")){
				return;
			}
			var projectNo=$(this).parent().attr("status");
			var projectImg=$(this).parent().parent().parent().find('.projectImg').attr('alt');
			
			requestJsonData("api/admin/deleteProject.do",{projectNo:projectNo,projectImg:projectImg},deleteFunction)
		})
		
		
		
		
		$('.productModify').click(function(){
			projectNmChanged=false;
			projectImgChanged=false;
			projectLinkChanged=false;
			projectLinkChanged=false;
			var projectNo=$(this).parent().attr("status");
			var projectNm=$(this).parent().parent().parent().find('.projectNm').html();
			var projectLink=$(this).parent().parent().parent().find('.projectLink').attr("href");
			var originProjectImg=$(this).parent().parent().parent().find('.projectImg').attr("alt");
			var projectDesc=$(this).parent().parent().parent().find('.projectDesc').html();
			projectDesc=projectDesc.split("<br>").join("");
			
			$('#productModifyModal input[name=projectNo]').val(projectNo);
			$('#productModifyModal input[name=projectNm]').val(projectNm);
			$('#productModifyModal input[name=originProjectImg]').val(originProjectImg);
			$('#productModifyModal input[name=projectLink]').val(projectLink);
			$('#productModifyModal textarea[name=projectDesc]').val(projectDesc);
			$('.receiveUserList').html('');
			$('#userListResultModify').html('');
			projectMemberChanged=false;
			requestJsonData("api/admin/projectMemberList.do",{projectNo:projectNo},projectMemberList)
		})
		
		
	}
	else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	

}
$(document).ready(function() {
	
	$(document).on('click','.projectMemberList',function(){
		
		userNo=$(this).attr('userNo');
		userNm=$(this).attr('userNm');
		$('#userListResult,#userListResultModify').html(function(index,html){
			return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
		})
		$('.receiveUserList').html('');
		$('#projectMember').val('');
		$('#projectMemberModify').val('');
		$('#projectMember').focus();
		projectMemberChanged=true;
	})
	
	$(document).on('click','.userListRemove',function(){
		$(this).parent().remove();
		projectMemberChanged=true;
	})
	$('#projectMember').keyup(function(){
		if(event.keyCode==40){
			if(projectMemberIndex<($('.receiveUserList li').length-1)){
				projectMemberIndex++;
				$('.receiveUserList').find('.projectMemberList').removeClass('projectMemberList');
				$('.receiveUserList li').eq(projectMemberIndex).addClass('projectMemberList');
			}
			return;
		}else if(event.keyCode==38){
			if(projectMemberIndex>=0){
				projectMemberIndex--;
				$('.receiveUserList').find('.projectMemberList').removeClass('projectMemberList');
				$('.receiveUserList li').eq(projectMemberIndex).addClass('projectMemberList');
				return;
			}
		}else{
			projectMemberIndex=-1;
		}
		
		if(event.keyCode==13){
			var target=$('.receiveUserList').find('.projectMemberList');
			if(target.get(0)==undefined){
				return;
			}
			
			userNo=target.attr('userNo');
			userNm=target.attr('userNm');
			$('#userListResult').html(function(index,html){
				return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
			})
			$('.receiveUserList').html('');
			$('#projectMember').val('');
			$('#projectMember').focus();
		}
		
		userName=$(this).val();
		if(userName.trim()!=""){
			requestJsonDataNoLoading("userTag.do", {
				str:userName
			}, getMemberList);
		}
		else{
			$('.receiveUserList').html('');
		}
		
		
	})
	
	$('#projectMemberModify').keyup(function(){
		if(event.keyCode==40){
			if(projectMemberIndex<($('.receiveUserList li').length-1)){
				
				projectMemberIndex++;
				$('.receiveUserList').eq(1).find('.projectMemberList').removeClass('projectMemberList');
				$('.receiveUserList:eq(1) li').eq(projectMemberIndex).addClass('projectMemberList');
			}
			return;
		}else if(event.keyCode==38){
			if(projectMemberIndex>=0){
				projectMemberIndex--;
				$('.receiveUserList').eq(1).find('.projectMemberList').removeClass('projectMemberList');
				$('.receiveUserList:eq(1) li').eq(projectMemberIndex).addClass('projectMemberList');
				return;
			}
		}else{
			projectMemberIndex=-1;
		}
		
		if(event.keyCode==13){
			var target=$('.receiveUserList').eq(1).find('.projectMemberList');
			if(target.get(0)==undefined){
				return;
			}
			
			userNo=target.attr('userNo');
			userNm=target.attr('userNm');
			$('#userListResultModify').html(function(index,html){
				return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
			})
			$('.receiveUserList').eq(1).html('');
			$('#projectMemberModify').val('');
			$('#projectMemberModify').focus();
			projectMemberChanged=true;
		}
		
		userName=$(this).val();
		
		if(userName.trim()!=""){
			requestJsonDataNoLoading("userTag.do", {
				str:userName
			}, getMemberList);
		}
		else{
			$('.receiveUserList').html('');
		}
		
		
	})
	$('.addProduct').click(function(){
		projectNmChanged=false;
		projectImgChanged=false;
		projectLinkChanged=false;
		projectLinkChanged=false;
		$("#productModal .form-control").val('');
	})
	
	requestJsonData("api/admin/getProjectList.do", {
		
	}, getProjectList);
	
	
	
	var files;
	var fileName='';
	$('#productModal input[name=projectImg],#productModifyModal input[name=projectImg]').on('change',function(event){
		projectImgChanged=true;
		files=event.target.files[0];
		fileName=files.name;
		var comma=fileName.lastIndexOf('.');
		var format=fileName.substring(comma+1);
		
		if(format!='jpg' && format!='jpeg' && format!='png' && format!='bmp' && format!='gif'){
			alert('이미지 파일을 넣어주세요!');
			$(this).val('');
			return;
		}
		
	})
	$('#productModifyModal input[name=projectNm]').on('change',function(){
		
		projectNmChanged=true;
	})
	$('#productModifyModal input[name=projectLink]').on('change',function(){
		projectLinkChanged=true;
	})
	$('#productModifyModal textarea[name=projectDesc]').on('change',function(){
		projectDescChanged=true;
	})
	$("#modifyProductButton").click(function() {
		var memberList=[];
		$('#userListResultModify li').each(function(){
			memberList.push({userNo:$(this).attr('userNo'),userNm:$(this).attr('userNm')})
		})
		
		var projectNm=$('#productModifyModal input[name=projectNm]').val().trim();
		if(projectNm==''){
			alert('이름을 입력하세요!');
			return;
		}		
		
		
		
		
		
		var projectLink=$('#productModifyModal input[name=projectLink]').val().trim();
		if(projectLink==''){
			alert('링크를 넣어주세요!');
			return;
		}
		var projectDesc=$('#productModifyModal textarea[name=projectDesc]').val().trim();
		if(projectDesc==''){
			alert('설명을 입력하세요!');
			return;
		}
		
		var myForm = new FormData();
		var originProjectImg=$('#productModifyModal input[name=originProjectImg]').val();
		myForm.append("projectNo",$('#productModifyModal input[name=projectNo]').val());
		
		if(projectImgChanged)
			myForm.append("uploadFile", files);
		if(projectNmChanged)
			myForm.append("projectNm",projectNm);
		if(projectDescChanged)
			myForm.append("projectDesc",projectDesc);
		if(projectLinkChanged)
			myForm.append("projectLink",projectLink);
		if(projectMemberChanged)
			myForm.append("memberList",JSON.stringify(memberList))
		if(!projectLinkChanged && !projectDescChanged && !projectNmChanged && !projectImgChanged &&!projectMemberChanged){
			alert('수정되었습니다!');
			 $("#productModifyModal").modal('hide');
     		 $("#productModifyModal .form-control").val('');
			return;
		}
	    myForm.append("originProjectImg",originProjectImg);
	    
	    $.ajax({dataType : 'json',
	          url : "../api/admin/updateProject.do",
	          data : myForm,
	          type : "POST",
	          enctype: 'multipart/form-data',
	          processData: false, 
	          contentType:false,
	          success : function(result) {
	           
	        	  
	        	  alert('수정되었습니다!');
	        	  $("#productModifyModal").modal('hide');
	      		  $("#productModifyModal .form-control").val('');
	      		requestJsonData("api/admin/getProjectList.do", {
	      			
	      		}, getProjectList);
	      		  
	          },
	          beforeSend:function(){
	  	        $('.wrap-loading').removeClass('display-none');
	  	      },
	  	      complete:function(){
		        $('.wrap-loading').addClass('display-none');
		      },
		      fail : function() {
					alert("인터넷 연결 상태를 확인해주세요.");
			  }
	      });
		
	})
	$("#addProductButton").click(function() {
		var memberList=[];
		$('#userListResult li').each(function(){
			memberList.push({userNo:$(this).attr('userNo'),userNm:$(this).attr('userNm')})
		})
		
		var projectNm=$('#productModal input[name=projectNm]').val().trim();
		if(projectNm==''){
			alert('이름을 입력하세요!');
			return;
		}		
		
		
		
		if(fileName==''){
			alert('파일을 넣어주세요!');
			return;
		}
		
		var projectLink=$('#productModal input[name=projectLink]').val().trim();
		if(projectLink==''){
			alert('링크를 넣어주세요!');
			return;
		}
		var projectDesc=$('#productModal textarea[name=projectDesc]').val().trim();
		if(projectDesc==''){
			alert('설명을 입력하세요!');
			return;
		}
		
		var myForm = new FormData();
	    myForm.append("uploadFile", files);
	    myForm.append("projectNm",projectNm);
	    myForm.append("projectDesc",projectDesc);
	    myForm.append("projectLink",projectLink);
	    myForm.append("memberList",JSON.stringify(memberList));
	    
	    $.ajax({dataType : 'json',
	          url : "../api/admin/projectAdd.do",
	          data : myForm,
	          type : "POST",
	          enctype: 'multipart/form-data',
	          processData: false, 
	          contentType:false,
	          success : function(result) {
	           
	        	  
	        	  alert('등록되었습니다!');
	        	  $("#productModal").modal('hide');
	      		  $("#productModal .form-control").val('');
	      		requestJsonData("api/admin/getProjectList.do", {
	      			
	      		}, getProjectList);
	      		  
	          },
	          beforeSend:function(){
	  	        $('.wrap-loading').removeClass('display-none');
	  	      },
	  	      complete:function(){
		        $('.wrap-loading').addClass('display-none');
		      },
		      fail : function() {
					alert("인터넷 연결 상태를 확인해주세요.");
			  }
	      });
		
	})
	
	
});