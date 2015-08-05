
function getProjectList(data){
	
	if (data.result == "success") {
		
		var list=data.resData[0].list;
		var productList="<tr>";
		for(i=0;i<list.length;i++){
			if(i%4==0 && i>0){
				productList+="</tr><br><tr>";
			}
			productList+="<td align='center' ><div align='right' status="+list[i].projectNo+"><input type='button' class='btn btn-default btn-xs' value='수정'>&nbsp;<input type='button' class='btn btn-danger btn-xs productRemove' value='삭제' ></div><br>"+list[i].projectNm+"<br>";
			productList+="<a href='"+list[i].projectLink+"'><img width='200px' height='100px' alt='"+list[i].projectImg+"' src='img/"+list[i].projectImg+"'></a><br>";
			productList+=list[i].projectDesc+"</td>";
			
			
		}
		productList+="</tr>";
		
		$('#productTable').html(productList);
		
		$('.productRemove').click(function(){
			if(!confirm("정말 삭제하시겠습니까?")){
				return;
			}
			var projectNo=$(this).parent().attr("status");
			requestJsonData("api/admin/deleteProject.do",{projectNo:projectNo},requestJsonData("api/admin/getProjectList.do", {
      			
      		}, getProjectList));
		})
	}
	else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
	

}
$(document).ready(function() {

	
	
	requestJsonData("api/admin/getProjectList.do", {
		
	}, getProjectList);
	
	
	
	var files;
	var fileName='';
	$('#productModal input[name=projectImg]').on('change',function(event){
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
	$("#addProductButton").click(function() {
		
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
		alert(1)
		var myForm = new FormData();
	    myForm.append("uploadFile", files);
	    myForm.append("projectNm",projectNm);
	    myForm.append("projectDesc",projectDesc);
	    myForm.append("projectLink",projectLink);
	    
	    $.ajax({dataType : 'json',
	          url : "../api/admin/projectAdd.do",
	          data : myForm,
	          type : "POST",
	          enctype: 'multipart/form-data',
	          processData: false, 
	          contentType:false,
	          success : function(result) {
	           
	        	  
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