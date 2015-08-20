$(document).ready(function(){
		
		
		
		var liIndex=0;
		var listLength;
		var word=/@([ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-z|A-Z|0-9]+)/ig;
		var tagName;
		$('#postComment').focus();
		$('#postComment').keypress(function(){
			
			if(event.keyCode==13 && $('.tagHover').length==1){
				
				var name=$('.tagHover').text();
				var userNo=$('.tagHover').children().val();
				$('#postComment').html(function(index,html){
						
					html=html.replace(tagName,"<a class='tag' href='/abc.do?userNo="+userNo+"'>"+name+"</a>");
						
					
					return html;
				})
				
				
				
				
				liIndex=0;
				tagName=null;
				$('#userTagList').html('');
				
				
				elem = document.getElementById('postComment');
				setEndOfContenteditable(elem)
				event.preventDefault();
				
			}
		})
		$('#postComment').keyup(function(){
			
			tagName=$(this).text().match(word);
			if(event.keyCode==40){
				
				if(listLength>liIndex){
					liIndex++;
				}
					
				
				if(liIndex!=0){
						
					$('#postComment li').removeClass('tagHover');
				}
				
				$('#postComment li').eq([liIndex]).addClass('tagHover');
								
			}
			else if(event.keyCode==38){
				
				if(liIndex>-1){
					liIndex--;
				}
				
				if(liIndex!=listLength-1){
					$('#postComment li').removeClass('tagHover');
				}
					
				$('#postComment li').eq([liIndex]).addClass('tagHover');
				
			}
			
			
			
			
			if(tagName!=null){
				
				
				
				
				 var insertTagName=tagName.toString().split("@").join("").split(",");
				 
				 insertTagName=insertTagName[insertTagName.length-1];
				 
				
				
				
				
				
				
				$.ajax({
					"url":"../userTag.do",
					"type":"POST",
					"data":"str="+insertTagName,
					"dataType":"json",
					"success":function(obj){
						var txt="";
						listLength=obj.resData[0].tagList.length;
						$.each(obj.resData[0].tagList,function(idx){
							if(idx==liIndex)
								txt+="<li class='tagList tagHover' >"+this.userNm+" <input type='hidden' value='"+this.userNo+"'/> </li>";
							else
								txt+="<li class='tagList' >"+this.userNm+" <input type='hidden' value='"+this.userNo+"'/> </li>";
							
						})
						$('#userTagList').html(txt);
						
						$('#userTagList li').hover(function(){
							if(liIndex!=-1)
								$('#userTagList li').removeClass('tagHover');
							$(this).addClass('tagHover');
						},function(){
							$(this).removeClass('tagHover');
						})
					
					},
					"error":function(request,status,error){
						alert("오류가 발생했습니다.\n재시도 또는 다시 접속해주세요.\n\n[오류정보]\ncode:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				       }
				})
			}
			
			
			
			
			
		})
		
		
		
		$(document).on("click", ".tagList", function(){
			var name=$(this).text();
			var userNo=$(this).children().val();
			$('#postComment').html(function(index,html){
				
				
				
				
				
				
				
				html=html.replace(tagName,"<a class='tag' href='/abc.do?userId="+userNo+"'>"+name+"</a>");
				
				
				
				return html;
			})
			
		
			
			
			liIndex=0;
			tagName=null;
			$('#userTagList').html('');
			
			
			elem = document.getElementById('postComment');
			setEndOfContenteditable(elem)
			
		});
		
		$(document).click(function(){
			$('#userTagList').html('');
		})
		
		
	})
		

function setEndOfContenteditable(contentEditableElement)
{
    var range,selection;
    if(document.createRange)//Firefox, Chrome, Opera, Safari, IE 9+
    {
        range = document.createRange();//Create a range (a range is a like the selection but invisible)
        range.selectNodeContents(contentEditableElement);//Select the entire contents of the element with the range
        range.collapse(false);//collapse the range to the end point. false means collapse to end rather than the start
        selection = window.getSelection();//get the selection object (allows you to change selection)
        selection.removeAllRanges();//remove any selections already made
        selection.addRange(range);//make the range you have just created the visible selection
    }
    else if(document.selection)//IE 8 and lower
    { 
        range = document.body.createTextRange();//Create a range (a range is a like the selection but invisible)
        range.moveToElementText(contentEditableElement);//Select the entire contents of the element with the range
        range.collapse(false);//collapse the range to the end point. false means collapse to end rather than the start
        range.select();//Select the range (make it the visible selection
    }
}	

	
	