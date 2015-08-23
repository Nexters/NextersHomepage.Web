var sendAuthEmail=function(data){
	if(data.result=="success"){
		alert('전송하였습니다!');
		
		requestJsonData('api/admin/getAssosiateMemberList.do',{},getAssosiateMemberList)
		
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
var getAssosiateMemberList=function(data){
	if(data.result=="success"){
		list=data.resData[0].list;
		str="";
		for(i=0;i<list.length;i++){
			str+="<tr>";
			str+="<td width='5%'><input type='checkbox' name='mailCheck'></td>\n";
			str+="<td>"+list[i].userNo+"</td>\n";
			str+="<td>"+list[i].userNm+"</td>\n";
			str+="<td>"+list[i].userId+"</td>\n";
			str+="<td><input type='button' class='btn btn-default mailSend' userNo="+list[i].userNo+" userId="+list[i].userId+" value='전송'></td>\n";
			str+="<td>"+list[i].mailYN+"</td>\n";
			str+="</tr>"
		}
		
		$('#authMailBody').html(str);
		
		$('.mailSend').click(function(){
			requestJsonDataNoWait("api/admin/sendAuthEmail.do",
					{userNo:$(this).attr('userNo'),
					 userId:$(this).attr('userId')
					},sendAuthEmail);
		})
		
		$('#mailAllCheck').click(function(){
		if($(this).prop("checked")){
			$('input[name=mailCheck]').prop("checked",true);
		}else{
			$('input[name=mailCheck]').prop("checked",false);
		}
		})
	}else {
		alert("오류가 발생했습니다.\n계속적으로 발생시 관리자께 해당 메시지를 캡쳐하여 보내주세요.\n오류 코드: " + data.resData[0].errorCd + "\n오류 메시지: " + data.resData[0].errorMsg);
	}
}
$(document).ready(function(){
	requestJsonData('api/admin/getAssosiateMemberList.do',{},getAssosiateMemberList)
	
	
})