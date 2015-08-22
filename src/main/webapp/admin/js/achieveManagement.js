var achieveNmChanged=false;
var achieveImgChanged=false;
var achieveLinkChanged=false;
var achieveDescChanged=false;
var achieveMemberChanged=false;
var achieveMemberIndex=-1;

var achieveMemberList=function(data){
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
			
			$('.receiveUserList').find('.achieveMemberList').removeClass('achieveMemberList');
			$(this).addClass('achieveMemberList');
			
		},function(){
			$('.receiveUserList').find('.achieveMemberList').removeClass('achieveMemberList');
		})
		
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}

function deleteFunction(data){
	if(data.result=="success"){
		requestJsonData("api/main/getAchieveList.do", {
  			
  		}, getAchieveList)
	}
	else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
function getAchieveList(data){
	
	if (data.result == "success") {
		
		var list=data.resData[0].list;
		var achieveList="";
		for(i=0;i<list.length;i++){
			
			achieveList+="<div class='col-lg-3 achieveList' align='center'><div class='inner'><div style='margin:5px;' align='right' status="+list[i].achieveNo+"><input type='button' class='btn btn-default btn-xs achieveModify' value='수정' data-toggle='modal' href='#achieveModifyModal'>&nbsp;<input type='button' class='btn btn-danger btn-xs achieveRemove' value='삭제' ></div><span class='achieveNm' style='font-weight:bold;'>"+list[i].achieveNm+"</span><br>";
			achieveList+="<a class='achieveLink' href='"+list[i].achieveLink+"'><img class='achieveImg' width='200px' height='100px' style='margin-top:15px;' alt='"+list[i].achieveImg+"' src='img/achieve/"+list[i].achieveImg+"'></a><br>";
			achieveList+="<span class='achieveDesc'>"+list[i].achieveDesc+"</span></div></div>";
			
			
		}
		achieveList+="";
		
		$('#achieveTable').html(achieveList);
		
		$('.achieveRemove').click(function(){
			if(!confirm("정말 삭제하시겠습니까?")){
				return;
			}
			var achieveNo=$(this).parent().attr("status");
			var achieveImg=$(this).parent().parent().parent().find('.achieveImg').attr('alt');
			
			requestJsonData("api/admin/deleteAchieve.do",{achieveNo:achieveNo,achieveImg:achieveImg},deleteFunction)
		})
		
		
		
		
		$('.achieveModify').click(function(){
			achieveNmChanged=false;
			achieveImgChanged=false;
			achieveLinkChanged=false;
			achieveLinkChanged=false;
			var achieveNo=$(this).parent().attr("status");
			var achieveNm=$(this).parent().parent().parent().find('.achieveNm').html();
			var achieveLink=$(this).parent().parent().parent().find('.achieveLink').attr("href");
			var originAchieveImg=$(this).parent().parent().parent().find('.achieveImg').attr("alt");
			var achieveDesc=$(this).parent().parent().parent().find('.achieveDesc').html();
			achieveDesc=achieveDesc.split("<br>").join("");
			
			$('#achieveModifyModal input[name=achieveNo]').val(achieveNo);
			$('#achieveModifyModal input[name=achieveNm]').val(achieveNm);
			$('#achieveModifyModal input[name=originAchieveImg]').val(originAchieveImg);
			$('#achieveModifyModal input[name=achieveLink]').val(achieveLink);
			$('#achieveModifyModal textarea[name=achieveDesc]').val(achieveDesc);
			$('.receiveUserList').html('');
			$('#userListResultModify').html('');
			achieveMemberChanged=false;
			requestJsonData("api/main/achieveMemberList.do",{achieveNo:achieveNo},achieveMemberList)
		})
		
		
	}
	else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	

}
$(document).ready(function() {
	
	$(document).on('click','.achieveMemberList',function(){
		
		userNo=$(this).attr('userNo');
		userNm=$(this).attr('userNm');
		$('#userListResult,#userListResultModify').html(function(index,html){
			return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
		})
		$('.receiveUserList').html('');
		$('#achieveMember').val('');
		$('#achieveMemberModify').val('');
		$('#achieveMember').focus();
		achieveMemberChanged=true;
	})
	
	$(document).on('click','.userListRemove',function(){
		$(this).parent().remove();
		achieveMemberChanged=true;
	})
	$('#achieveMember').keyup(function(){
		if(event.keyCode==40){
			if(achieveMemberIndex<($('.receiveUserList li').length-1)){
				achieveMemberIndex++;
				$('.receiveUserList').find('.achieveMemberList').removeClass('achieveMemberList');
				$('.receiveUserList li').eq(achieveMemberIndex).addClass('achieveMemberList');
			}
			return;
		}else if(event.keyCode==38){
			if(achieveMemberIndex>=0){
				achieveMemberIndex--;
				$('.receiveUserList').find('.achieveMemberList').removeClass('achieveMemberList');
				$('.receiveUserList li').eq(achieveMemberIndex).addClass('achieveMemberList');
				return;
			}
		}else{
			achieveMemberIndex=-1;
		}
		
		if(event.keyCode==13){
			var target=$('.receiveUserList').find('.achieveMemberList');
			if(target.get(0)==undefined){
				return;
			}
			
			userNo=target.attr('userNo');
			userNm=target.attr('userNm');
			$('#userListResult').html(function(index,html){
				return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
			})
			$('.receiveUserList').html('');
			$('#achieveMember').val('');
			$('#achieveMember').focus();
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
	
	$('#achieveMemberModify').keyup(function(){
		if(event.keyCode==40){
			if(achieveMemberIndex<($('.receiveUserList li').length-1)){
				
				achieveMemberIndex++;
				$('.receiveUserList').eq(1).find('.achieveMemberList').removeClass('achieveMemberList');
				$('.receiveUserList:eq(1) li').eq(achieveMemberIndex).addClass('achieveMemberList');
			}
			return;
		}else if(event.keyCode==38){
			if(achieveMemberIndex>=0){
				achieveMemberIndex--;
				$('.receiveUserList').eq(1).find('.achieveMemberList').removeClass('achieveMemberList');
				$('.receiveUserList:eq(1) li').eq(achieveMemberIndex).addClass('achieveMemberList');
				return;
			}
		}else{
			achieveMemberIndex=-1;
		}
		
		if(event.keyCode==13){
			var target=$('.receiveUserList').eq(1).find('.achieveMemberList');
			if(target.get(0)==undefined){
				return;
			}
			
			userNo=target.attr('userNo');
			userNm=target.attr('userNm');
			$('#userListResultModify').html(function(index,html){
				return html+"<li style='list-style:none;' userNo="+userNo+" userNm="+userNm+">"+userNo+"&nbsp;&nbsp;"+userNm+" <span style='float:right;margin-right:5px;cursor:pointer;cursor:hand;' class='userListRemove'>x</span></li>";
			})
			$('.receiveUserList').eq(1).html('');
			$('#achieveMemberModify').val('');
			$('#achieveMemberModify').focus();
			achieveMemberChanged=true;
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
	$('.addAchieve').click(function(){
		achieveNmChanged=false;
		achieveImgChanged=false;
		achieveLinkChanged=false;
		achieveLinkChanged=false;
		$("#achieveModal .form-control").val('');
	})
	
	requestJsonData("api/main/getAchieveList.do", {
		
	}, getAchieveList);
	
	
	
	var files;
	var fileName='';
	$('#achieveModal input[name=achieveImg],#achieveModifyModal input[name=achieveImg]').on('change',function(event){
		achieveImgChanged=true;
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
	$('#achieveModifyModal input[name=achieveNm]').on('change',function(){
		
		achieveNmChanged=true;
	})
	$('#achieveModifyModal input[name=achieveLink]').on('change',function(){
		achieveLinkChanged=true;
	})
	$('#achieveModifyModal textarea[name=achieveDesc]').on('change',function(){
		achieveDescChanged=true;
	})
	$("#modifyAchieveButton").click(function() {
		var memberList=[];
		$('#userListResultModify li').each(function(){
			memberList.push({userNo:$(this).attr('userNo'),userNm:$(this).attr('userNm')})
		})
		
		var achieveNm=$('#achieveModifyModal input[name=achieveNm]').val().trim();
		if(achieveNm==''){
			alert('이름을 입력하세요!');
			return;
		}		
		
		
		
		
		
		var achieveLink=$('#achieveModifyModal input[name=achieveLink]').val().trim();
		if(achieveLink==''){
			alert('링크를 넣어주세요!');
			return;
		}
		var achieveDesc=$('#achieveModifyModal textarea[name=achieveDesc]').val().trim();
		if(achieveDesc==''){
			alert('설명을 입력하세요!');
			return;
		}
		
		var myForm = new FormData();
		var originAchieveImg=$('#achieveModifyModal input[name=originAchieveImg]').val();
		myForm.append("achieveNo",$('#achieveModifyModal input[name=achieveNo]').val());
		
		if(achieveImgChanged)
			myForm.append("uploadFile", files);
		if(achieveNmChanged)
			myForm.append("achieveNm",achieveNm);
		if(achieveDescChanged)
			myForm.append("achieveDesc",achieveDesc);
		if(achieveLinkChanged)
			myForm.append("achieveLink",achieveLink);
		if(achieveMemberChanged)
			myForm.append("memberList",JSON.stringify(memberList))
		if(!achieveLinkChanged && !achieveDescChanged && !achieveNmChanged && !achieveImgChanged &&!achieveMemberChanged){
			alert('수정되었습니다!');
			 $("#achieveModifyModal").modal('hide');
     		 $("#achieveModifyModal .form-control").val('');
			return;
		}
	    myForm.append("originAchieveImg",originAchieveImg);
	    
	    $.ajax({dataType : 'json',
	          url : "../api/admin/updateAchieve.do",
	          data : myForm,
	          type : "POST",
	          enctype: 'multipart/form-data',
	          processData: false, 
	          contentType:false,
	          success : function(result) {
	           
	        	  
	        	  alert('수정되었습니다!');
	        	  $("#achieveModifyModal").modal('hide');
	      		  $("#achieveModifyModal .form-control").val('');
	      		requestJsonData("api/main/getAchieveList.do", {
	      			
	      		}, getAchieveList);
	      		  
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
	$("#addAchieveButton").click(function() {
		var memberList=[];
		$('#userListResult li').each(function(){
			memberList.push({userNo:$(this).attr('userNo'),userNm:$(this).attr('userNm')})
		})
		
		var achieveNm=$('#achieveModal input[name=achieveNm]').val().trim();
		if(achieveNm==''){
			alert('이름을 입력하세요!');
			return;
		}		
		
		
		
		if(fileName==''){
			alert('파일을 넣어주세요!');
			return;
		}
		
		var achieveLink=$('#achieveModal input[name=achieveLink]').val().trim();
		if(achieveLink==''){
			alert('링크를 넣어주세요!');
			return;
		}
		var achieveDesc=$('#achieveModal textarea[name=achieveDesc]').val().trim();
		if(achieveDesc==''){
			alert('설명을 입력하세요!');
			return;
		}
		
		var myForm = new FormData();
	    myForm.append("uploadFile", files);
	    myForm.append("achieveNm",achieveNm);
	    myForm.append("achieveDesc",achieveDesc);
	    myForm.append("achieveLink",achieveLink);
	    myForm.append("memberList",JSON.stringify(memberList));
	    
	    $.ajax({dataType : 'json',
	          url : "../api/admin/achieveAdd.do",
	          data : myForm,
	          type : "POST",
	          enctype: 'multipart/form-data',
	          processData: false, 
	          contentType:false,
	          success : function(result) {
	           
	        	  
	        	  alert('등록되었습니다!');
	        	  $("#achieveModal").modal('hide');
	      		  $("#achieveModal .form-control").val('');
	      		requestJsonData("api/main/getAchieveList.do", {
	      			
	      		}, getAchieveList);
	      		  
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