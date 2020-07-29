<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
<head>
<title>JSTL sql:query Tag</title>
<script src="./scripts/jquery-3.2.1.min.js"></script>
<style>
.backTr {
	background-color: skyblue
}
</style>
<script>
      /* 페이지 로드 하고 나서 */
      $(function(){
    	  //td 클릭이벤트
    	  $("#tblEmp td").on("click",function(){
    		  var tr = $(this).parent();			//jquery 객체
    		  //tr.toggleClass("backTr");
    		  //console.log(tr.data("id"), tr.data("name"));
    		  //console.log($(this).html());
    		  //$("#tblRight").append(tr);			//이동
    		  //$("#tblRight").append(tr.clone());	//복사
    		  //tr.remove();						//지우기
    		  //console.log(tr.html());				//태그까지 다 보여줌
    		  //console.log(tr.text());				//안에 text만!
    		  						//tr.get(0) == tr[0]
    		  //console.log('script',tr.get(0).innerHTML);		//j 객체 -> d객체
    		  var tb = document.getElementById("tblEmp");		//Dom 객체
    		  tb.innerHtml="";
    		  //$(tb).empty();									//d 객체 -> j 객체
    	  });
    	  				//이벤트타입, 핸들러
    	  	$(".btnClass").on("click",function(){
    	  		/* document.getElementById();
    	  		document.getElementsByTagName(); */				
    		  	//var tr = $(this).closetst("tr");
    		  	//var tr = $(this).parent().parent()    		  	
 				tr.toggleClass("backTr")
 				//$(this).parent().parent().css("backgroundColor","cyan")
    	  		console.log($(this).parent().parent())
    	  })
      });
      	function makeData(){
      		let list = [];
      		
			//체크한 행만 전송할 데이터 만들기. 은행계좌가 입력된 경우만 포함함.
  			//$("[name='selId']:checked")
			$(".backTr").each(function(i, checkbox){
      			var tr = $(checkbox).parent().parent();
      			//자식tag 다 데려옴
				var td = $(tr).children();
				var obj = {};
				
				var employee_id = td.eq(1).text();
				var bankAcct    = td.eq(6).find("input").val();
				var salary		= td.eq(3).text();
				var com			= td.eq(4).text();
				var name		= td.eq(2).text();
				var now = new Date();
				var year = now.getFullYear();
			    var month = now.getMonth() + 1;
			    if((month + "").length < 2){
			        month = "0" + month;
			    }
			    
				//은행계좌가 없으면 skip
				if(bankAcct == '') {
					return;
				}
				console.log(year+'년'+month+'월');
				//객체에 담기
				obj["slipAmount"] = parseInt(salary)+parseInt(com);  	     // 급여 + 상여금
				obj["salDt"] = ""+year+month;               // 급여년월 (현재년월)
				obj["customer"] = employee_id+"_"+name;   // 사번_이름
				obj["bankAcct"] = bankAcct;      // 은행계좌
				
				//목록에 담기
				list.push(obj);
      		});
		
			//객체를 string으로 변환
      		console.log(JSON.stringify(list));

      	}
      </script>
</head>

<body>
	<!-- 데이터조회 시작 -->
	<sql:setDataSource var="snapshot" driver="oracle.jdbc.OracleDriver"
		url="jdbc:oracle:thin:@localhost:1521:xe" user="hr" password="hr" />

	<sql:query dataSource="${snapshot}" var="result">
            SELECT e.*, round(salary*commission_pct, 2) as commission, d.department_name 
              FROM Employees e, departments d 
             WHERE e.department_id =d.department_id 
               AND commission_pct>0
             ORDER BY first_name  
         </sql:query>
	<!-- 데이터조회 끝-->

	<button type="button" id="" onclick="makeData()">급여신청</button>
	<!-- 목록 시작  -->
	<table border="1" width="40%" id="tblEmp">
		<tr>
			<th><input type="checkbox" id="chkAll"></th>
			<th>Employee_id</th>
			<th>Name</th>
			<th>Salary</th>
			<th>Commission</th>
			<th>Deaprtment</th>
			<th>bank</th>
		</tr>

		<c:forEach var="row" items="${result.rows}" begin="0" end="9">
			<tr data-id="${row.employee_id}" data-name="${row.first_name}">
				<td align="center"><input type="checkbox" name="selId"></td>
				<td align="center"><c:out value="${row.employee_id}" /></td>
				<td><c:out value="${row.first_name} ${row.last_name}" /></td>
				<td align="right"><c:out value="${row.salary}" /></td>
				<td align="right"><c:out value="${row.commission}" /></td>
				<td align="center"><c:out value="${row.department_name}" /></td>
				<td><input type="button" class="btnClass" id="btnId" value="선택"></td>
			</tr>
		</c:forEach>
	</table>
	<!-- 목록 끝  -->
	<table border="1">
		<thead>
		
		</thead>
		<tbody id="tblRight">
		
		</tbody>
	</table>
</body>
</html>