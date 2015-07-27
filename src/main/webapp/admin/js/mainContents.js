var thisComponent = "";
var thisValue= "";

var colorDev="#32dcfa";
var colorDes="#ccff00";

function memberCount(data){
	if(data.result=="success"){
		
		
		//개발자 : rgb(50,220,250) #32dcfa 
		//디자이너 : rgb(0,255,0) #00ff00
		//etc : yellow
		
		var numOfDeveloper = data.resData[0].developer;
		var numOfDesigner = data.resData[0].designer;
		var numOfEtc = data.resData[0].etc;
		
		var chartData=[];
		
		chartData.push({
			value : numOfDeveloper,
			color : colorDev,
			label : "개발자"
		})
		chartData.push({
			value : numOfDesigner,
			color : colorDes,
			label : "디자이너"
		})
		
		var myPieChart = new Chart(thisComponent).Pie(chartData);
		
	}else{
		
		
	}
	
}

$(document).ready(function() {
	
	alert("Asd");
	console.log("asd");
	
	var ctx = $("#asd").get(0).getContext("2d");
	
	thisComponent=ctx;

	
	requestJsonData("api/admin/memberCount.do", {}, memberCount);
	
	
	
});