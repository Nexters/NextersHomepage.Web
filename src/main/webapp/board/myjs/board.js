$(document).ready(function(){
	getBoardList();
	$.ajax({
			          type: 'post'
			        , url: 'http://localhost:8080/Store/GetList'
							, data:{
									id:"asd"
							}
			        , success: function(data) {
			        		console.log(data);
			          }
			  });
});

function getBoardList(){


}
