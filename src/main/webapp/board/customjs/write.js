$(document).ready(function(){

  var oEditors = [];
  nhn.husky.EZCreator.createInIFrame({
  	oAppRef: oEditors,
  	elPlaceHolder: "ir1",
  	sSkinURI: "./smartEditor/SmartEditor2Skin.html",
  	fCreator: "createSEditor2"
  });

  $("#submit").click(function(){
    oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
    console.log(document.getElementById("ir1").value);
    var date = new Date();
    var dateString = date.toString();
    var writeData={
      title:  $("input[type='title']").val(),
      writer: $("input[type='writer']").val(),
      contents: document.getElementById("ir1").value,
      password: '123'
    }
    boardWrite(writeData);
	});

})

//@Params //-> 쓰기
/* BoardInfo@RequestParam(value="writer")String strWriter
																,@RequestParam(value="title") String strTitle
																,@RequestParam(value="contents") String strContents
																,@RequestParam(value="insdate") String strInsdate
																,@RequestParam(value="password")
                                */
function boardWrite(writeData){
  console.log(writeData);
  $.ajax({
	    type:"POST",
	    url:"/api/board/insertBoardContents.do?"+"writer="+writeData.writer+"&title="+writeData.title+"&contents="+writeData.contents+"&insdate="+""+"&password="+123,
	    success:function(data){
	      //setBoardList(data);
	    	alert("글쓰기가 완료되었습니다");
        $(window).attr('location','/board/');
	    	//console.log(data);
	    	//data.resData[0]
	    },
	    error:function(xhr,status,error){
	      alert(error);
	    }
	  });
}
