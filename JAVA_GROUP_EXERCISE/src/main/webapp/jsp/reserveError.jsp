<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h1>会議室予約</h1>
<hr>
<h2>予約エラー</h2>
<p class="message">${errorReason}</p>
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
<a href="${pageContext.request.contextPath}/jsp/menu.jsp"class="button_submit">確認</a>
<%@include file="../common/footer.jsp"%>