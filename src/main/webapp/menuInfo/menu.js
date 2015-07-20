var data;

var builddata = function() {
	var source = [];
	var items = [];
	// build hierarchical source.
	for (i = 0; i < data.length; i++) {
		var item = data[i];
		var label = item["text"];
		var parentid = item["parentid"];
		var id = item["id"];
		var url = item["url"];

		if (items[parentid]) {
			var item = {
				parentid : parentid,
				label : label,
				item : item,
				url : url
			};
			if (!items[parentid].items) {
				items[parentid].items = [];
			}
			items[parentid].items[items[parentid].items.length] = item;
			items[id] = item;
		} else {
			items[id] = {
				parentid : parentid,
				label : label,
				item : item,
				url : url
			};
			source[id] = items[id];
		}
	}
	return source;

}

var buildUL = function(parent, items) {
	$.each(items, function() {
		if (this.label) {
			// create LI element and append it to the parent element.
			if (this.label == "Sign In") {
				var li = $("<li><a data-toggle=\"modal\" href=\"#myModal\">" + this.label + "</a></li>");
			}else if(this.label == "="){
				var li = $("<li><a href=\"#\"><img src=\"image/menu_origin_resize.png\" ></a></li>");
			}
			else {
				
				var li = $("<li><a href='" + this.url + "'>" + this.label
						+ "</a></li>");
			}

			li.appendTo(parent);
			// if there are sub items, call the buildUL function.
			if (this.items && this.items.length > 0) {
				var ul = $("<ul></ul>");
				ul.appendTo(li);
				buildUL(ul, this.items);
			}
		}
	});
}

$(document).ready(function() {
	$.getJSON("menuInfo/menuInfo2.json", function(jsonData) {
		data = jsonData.menu;

		var ul = $("<ul class=\"nav navbar-header\"></ul>");
		var source = builddata();
		buildUL(ul, source);
		$("#jqxMenu").html(ul);
		$("#jqxMenu li").addClass("navbar-nav");
	});
});
