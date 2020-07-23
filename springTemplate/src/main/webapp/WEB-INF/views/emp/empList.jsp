<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>empList</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
<!-- ajax -->
$(function(){
	
	$(".empId").on("click",function(){
		/* 클릭한 a 링크안의 값을 읽어서 넣어줘*/
		var empid = $(this).html();
		var url = "getEmp/"+empid;
		//$("#getEmpDiv").load(url)		//html 로 받기.
		/* $.getJSON("getEmpAjax",{employeeId:empid},function(result){
					//서버 url호출할놈	보낼데이터 		콜백함수
					//보낼때 {employeeId:empid} / "employeeId="+empid 둘다가능
					//콜백함수(결과값) -> 넘어온값 쓰겠다
			$(".row").find(".col").eq(0).html(result.employeeId);
			$(".row").find(".col").eq(1).html(result.firstName);
			$(".row").find(".col").eq(2).html(result.email);
			$(".row").find(".col").eq(3).html(result.hireDate);
		})	 */	
		$.ajax({
			url : "getEmpAjax",
			data : {employeeId:empid},
			method : 'get',
			dataType:'json'
			/* success : function(result){
				$(".row").find(".col").eq(0).html(result.employeeId);
				$(".row").find(".col").eq(1).html(result.firstName);
				$(".row").find(".col").eq(2).html(result.email);
				$(".row").find(".col").eq(3).html(result.hireDate);
			},
			error: function(){
				alert("error");
			},
			async: false,
			cache: false */
		}).done(function(result){
			/* 정상실행 */
			$(".row").find(".col").eq(0).html(result.employeeId);
			$(".row").find(".col").eq(1).html(result.firstName);
			$(".row").find(".col").eq(2).html(result.email);
			$(".row").find(".col").eq(3).html(result.hireDate);
		})
		  .fail(function(){
			  /* 실패 */
		  })
		  .always(function(){
			  /* 무조건실행 */
		  });
	})	
});
</script>
</head>
<body>
	<h3>사원목록현황</h3>
	<a href="report.do" target="_blank">pdf보기</a>
	<br>
	<!-- <iframe src="report2.do" style="width: 100%; height: 400px"></iframe> -->
	<div id="getEmpDiv">
		<div class="row">
			<div class="col">${emp.employeeId}</div>
			<div class="col">${emp.firstName}${emp.lastName }</div>
			<div class="col">${emp.email}</div>
			<div class="col">${emp.hireDate}</div>
		</div>
	</div>
	<br>

	<c:forEach items="${empList }" var="emp">
		<img src="download?name=${emp.profile}" style="width: 80px"	onerror="this.src='resources/zzzz.jpg'">
		<a href="#" class="empId">${emp.employeeId}</a> ${emp.lastName } ${emp.firstName } <br>
	</c:forEach>

</body>
</html>