
$(document).ready(function() {

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
		
		var projectNm=$('#productModal input[name=projectNm').val().trim();
		if(projectNm==''){
			alert('이름을 입력하세요!');
			return;
		}		
		var projectDesc=$('#productModal input[name=projectDesc').val().trim();
		if(projectDesc==''){
			alert('설명을 입력하세요!');
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
	           
	        	  alert('success');
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
		requestJsonData("api/admin/projectAdd.do", {

			grade : $("#memberListPage li.active a").attr("gener"),
			position : $("#productModal input[type='checkbox']").val(),
			userId : $("#productModal input[name=userId]").val(),
			userNm : $("#productModal input[name=userNm]").val(),
			userCellNum : $("#productModal input[name=userCellNum]").val()

		}, SmemberAdd);
	})
});