    //리얼에 포팅 시 ContextName이 존재하지 않습니다.

$(document).ready(function(){

  eventSetting();
  getTotalContentsNumber();


  $(".pageClick").click(function(){
		alert("clicked");
		console.log("ASdasd22");
	});

})

function getPageContents(fileLocation) {

	$.ajax({
	       url: fileLocation,
	       success: function(data) {
	          //this is the redirect
	          document.location.href=fileLocation;
	       }
    });

}

function eventSetting(){
	$("#boardPagination li a").click(function(){
		alert("clicked");
	});
}
//@Params //->전체 list 갯수 받아오기
//
//

function getTotalContentsNumber(){

  $.ajax({
    type:"POST",
    url:"/api/board/getBoardTotCnt.do",
    success:function(data){
      setPaginationNumber(data);
      //alert("asd");
    },
    error:function(xhr,status,error){
      alert(error);
    }
  });
}
function setPaginationNumber(data){

	var totalCount = data.resData[0].totCnt;

	var paginationHtml="";
	for(i=0;i<totalCount/10;i++){
		paginationHtml+='<li><a>'+(i+1)+'</a></li>\n';
	}
	console.log("zz");

	$("#boardPagination").html(paginationHtml);

	$("#boardPagination li a").click(function() {
		getBoardList($(this).html());
	});

	getBoardList(1);
}
//@Params //-> 각 pageNation에 해당하는 list 목록 가져오기
//  limit : 현재위치 (page nation 값)
//  offset : 10
function getBoardList(number){
//1 -> 1, 2 -> 10 , 3-> 20
  //console.log(myData);
	var data = JSON.stringify({
	      limit: 10,
	      offset: number,
	});
	 var offset=(number-1)*10;
  $.ajax({
    type:"POST",
    url:"/api/board/getNewBoardList.do?"+"limit="+10+"&offset="+offset,
    success:function(data){
      setBoardList(data);
    },
    error:function(xhr,status,error){
      alert(error);
    }
  });
}
function setBoardList(data){

	var boardList=data.resData[0].list;

	var boardListHtml="";
	for(i=0;i<boardList.length;i++){
		boardListHtml+='<tr>';
		boardListHtml+="<td>"+boardList[i].no+"</td>";
		boardListHtml+='<td><a boardNumber='+boardList[i].no+'>'+boardList[i].title+'</a></td>';
		boardListHtml+="<td>"+boardList[i].writer+"</td>";
		boardListHtml+="<td>"+boardList[i].insdate+"</td>";
		boardListHtml+="<td>"+boardList[i].view+"</td>";
		boardListHtml+="</tr>";
	}
	$("#NextersFreeBoard").html(boardListHtml);

	$("#NextersFreeBoard td a").click(function(){
		getBoardContent($(this).attr("boardNumber"));
	});

}

//@Params //->클릭한 게시물의 번호 입력
// BoardNumber : 게시물 number
//
function getBoardContent(boardNumber){
	$.ajax({
	    type:"POST",
	    url:"/api/board/getNewBoardContent.do?"+"no="+boardNumber,
	    success:function(data){
	      //setBoardList(data);
	    	alert(data.resData[0].contents);
	    	//console.log(data);
	    	//data.resData[0]
	    },
	    error:function(xhr,status,error){
	      alert(error);
	    }
	  });
}
