
$(document).ready(function() {

	$("#addProductButton").click(function() {
		requestJsonData("api/admin/projectAdd.do", {

			grade : $("#memberListPage li.active a").attr("gener"),
			position : $("#myModal input[type='checkbox']").val(),
			userId : $("#myModal input[name=userId]").val(),
			userNm : $("#myModal input[name=userNm]").val(),
			userCellNum : $("#myModal input[name=userCellNum]").val()

		}, SmemberAdd);
	})
});