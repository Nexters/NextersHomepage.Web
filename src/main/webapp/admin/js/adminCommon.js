function getPageContents(fileLocation) {
	$.ajax({
		url : fileLocation,
		success : function(data) {
			$("#page-wrapper").html(data);
		}
	});
}
