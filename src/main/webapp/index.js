var express = require('express');
var app = express();

app.use(express.static(__dirname));

app.post("/asd.do",function (req,res) {
	//console.log(req.body);
	var asd=[
		zxc = { a:"123"},
		zqc = { a:"456"},
		zfc = { a:"789"}
	];
	console.log(req.data);
	//console.log("asd.do!!!");
	res.send(asd);
})

app.listen(7777,function(){
	console.log('asd');
});
