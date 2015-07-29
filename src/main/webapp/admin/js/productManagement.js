$(document).ready(function() {
	
	
	$("#addProductButton").click(function(){
		//console.log("hide");
		//$("#productModal").modal('show');
		$("#productModal").modal('hide');
		//$('body').removeClass('modal-open');
		$('.modal-backdrop').remove();
		//console.log("hide end");
	})
});