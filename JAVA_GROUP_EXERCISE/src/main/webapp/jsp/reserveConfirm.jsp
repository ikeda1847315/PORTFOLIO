<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h1>会議室予約</h1>
<hr>
<h2>予約確認</h2>
<table>
	<tbody>
		<tr>
			<th>予約日</th>
			<td>${reservation.date}</td>
		</tr>
		<tr>
			<th>会議室</th>
			<td><c:out value="${room.name}" /></td>
		</tr>
		<tr>
			<th>予約時刻</th>
			<td>${reservation.start}～${reservation.end}</td>
		</tr>
		<tr>
			<th>予約者</th>
			
			<td><c:out value="${meetingRoom.user.name}" /></td>
		</tr>
	</tbody>
</table>
<div class="button_row">
	<form action="${pageContext.request.contextPath}/jsp/reserveInput.jsp"
		method="post">
		<input type="submit" value="戻る" class="button_submit">
	</form>
	<form action="${pageContext.request.contextPath}/ReserveServlet"
		method="post">
		<input type="submit" value="決定" class="button_submit">
	</form>
</div>
<%@include file="../common/footer.jsp"%>