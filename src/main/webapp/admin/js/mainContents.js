var thisComponent = "";
var thisValue = "";

var colorDev = "#32dcfa";
var colorDes = "#ccff00";

function memberCount(data) {

	if (data.result == "success") {

		//개발자 : rgb(50,220,250) #32dcfa 
		//디자이너 : rgb(0,255,0) #00ff00
		//etc : yellow
		var ctx = $("#asd").get(0).getContext("2d");

		var numOfDeveloper = data.resData[0].developer;
		var numOfDesigner = data.resData[0].designer;
		var numOfEtc = data.resData[0].etc;

		var chartData = [];

		chartData.push({
			value : numOfDeveloper,
			color : colorDev,
			highlight : "rgba(50,220,250,0.8)",
			label : "개발자"
		})
		chartData.push({
			value : numOfDesigner,
			color : colorDes,
			highlight : "rgba(221,255,0,0.8)",
			label : "디자이너"
		})

		//		<span style='font-size: 7px; color: #32dfca'>개발자</span><br>
		//		<span style='font-size: 7px'>디자이너</span>

		var label = "<span style='font-size: 7px; color: #32dcfa;'>개발자</span><br> <span style='font-size: 7px; color: #ccff00; '>디자이너</span>";
		var myPieChart = new Chart(ctx).Pie(chartData);
		$("#labelArea").html(label);
	} else {

	}

}

function getMemberAttendenceCount(data) {

	if (data.result == "success") {
		
		
		//attend 출석
		//late 지각 
		//absence 결석
		
		var dataLabel=[];
		var attendCount=[];
		var lateCount=[];
		var absenceCount=[];
		
		for(i=0;i<data.resData[0].list.length;i++){
			
			var listData=data.resData[0].list[i];
			
			dataLabel.push(listData.date);
			
			/// attend late absence date
			
			
			//attend 
			attendCount.push(listData.attend);
			
			//absence
			lateCount.push(listData.late);
			//late
			absenceCount.push(listData.absence);
			
		}
		
		
		
		var ctx = $("#attendenceCountCanvas").get(0).getContext("2d");

		var Bardata = {

			//labels 에 날짜
			labels : dataLabel,

			//datasets의 label과 data값
			datasets : [ {
				label : "출석",
				fillColor : "rgba(92,184,92,0.5)",
				strokeColor : "rgba(92,184,92,0.8)",
				highlightFill : "rgba(92,184,92,0.75)",
				highlightStroke : "rgba(92,184,92,1)",
				data : attendCount
			}, {
				label : "지각",
				fillColor : "rgba(240,173,78,0.5)",
				strokeColor : "rgba(240,173,78,0.8)",
				highlightFill : "rgba(240,173,78,0.75)",
				highlightStroke : "rgba(240,173,78,1)",
				data : lateCount
			}, {
				label : "결석",
				fillColor : "rgba(201,48,44,0.5)",
				strokeColor : "rgba(201,48,44,0.8)",
				highlightFill : "rgba(201,48,44,0.75)",
				highlightStroke : "rgba(201,48,44,1)",
				data : absenceCount
			} ]
		};

		
		
//		Bardata.labels.push("mon");
//		Bardata.datasets[0].data.push(1);
		var myBarChart = new Chart(ctx).Bar(Bardata);

	} else {

	}

}
$(document).ready(function() {

	//alert("Asd");
	//console.log("asd");

	//getMemberAttendenceCount()

	requestJsonData("api/admin/memberCount.do", {}, memberCount);
	requestJsonData("api/admin/attendenceCountList.do", {}, getMemberAttendenceCount);

});