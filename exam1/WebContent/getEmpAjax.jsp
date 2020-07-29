<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>getEmpAjax.jsp</title>
<style>
.backTr {
	background-color: skyblue
}
</style>
<script src="./scripts/jquery-3.2.1.min.js"></script>
<script>
	$(function(){
		function makeTrTag(item){
			return $("<tr>")
				.addClass("tblTr")
				.append($("<td>").attr("align","center").html(item.employee_id))
				.append($("<td>").html(item.first_name))
				.append($("<td>").append($("<button>").addClass("btnClass").attr("type","button").html("선택")));
		}
		function makeTr (item){
			var tr = 
				'<tr class="tblTr">'+
				'<td align="center">'+ item.employee_id+'</td>'+
				'<td>'+ item.first_name +'</td>'+
				'<td><button type="button">선택</button></td>'
				'</tr>'
				
			return tr;
		}
		$.ajax({
			url:"empdata.json",
			dataType : "json"
		}).done(function(datas){
			$.each(datas, function(idx, item){
				//console.log(item.first_name);
				$("#tblBody").append(makeTrTag(item));
			})
		});
		$("#tblBody").on("click",".btnClass",function(){
	  		var tr = $(this).closest("tr")  		  	
			tr.toggleClass("backTr")
	  	})
	})
</script>
</head>
<body>
<table border="1">
	<thead>
		<tr><td>id</td><td>name</td><td>email</td></tr>
	</thead>
	<tbody id ="tblBody">
		<tr class="tblTr">
			<td align="center"></td>
			<td></td>
			<td><button type="button">선택</button></td>
		</tr>
	</tbody>
</table>
</body>
</html>