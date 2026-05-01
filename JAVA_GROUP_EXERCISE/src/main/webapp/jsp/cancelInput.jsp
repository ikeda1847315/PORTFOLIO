<%@page import="jp.co.sys.bean.ReservationBean"%>
<%@page import="jp.co.sys.util.RoomList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="jp.co.sys.bean.MeetingRoom"%>
<%@ page import="jp.co.sys.bean.RoomBean"%>

<%@include file="../common/header.jsp"%>
<h1>会議室予約キャンセル</h1>
<h2>利用日</h2>
<form action="${pageContext.request.contextPath}/ChangeDateServlet"
	method="post">
	<input type="date" name="date" class="form_input_date" value="${meetingRoom.date}" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"> <input
		type="submit" value="日付変更" class="button_submit" >
	<input type="hidden" name="page" value="cancelInput.jsp">
</form>
<h2>キャンセル可能時間帯（<c:out value="${meetingRoom.user.name}さん" />）</h2>
<%
//meetingRoomをセッションから取得
MeetingRoom meetingRoom = (MeetingRoom) session.getAttribute("meetingRoom");
//部屋の一覧
RoomList rooms = meetingRoom.getRooms();
//始まりの時間
String[] period = meetingRoom.getPeriod();
//予約情報の一覧
ReservationBean[][] reservations = meetingRoom.getReservations();
%>
<table class="input_table">
	<tr>
		<th class="input_th">会議室 / 時間</th>
		<%--始まりの時間を要素分取り出して順に表示--%>
		<%
		for (int j = 0; j < period.length; j++) {
		%>
		<th><%=period[j]%></th>
		<%
		}
		%>
	</tr>
	<%--二重for文　i=会議室名の表示--%>
	<%
	for (int i = 0; i < rooms.size(); i++) {
		String room = rooms.get(i).getName();
		request.setAttribute("room", room);
	%>
	<tr>
		<th><c:out value="${room }" /></th>
		<%
		for (int j = 0; j < period.length; j++) {
		%>
		<td>
			<%--予約キャンセルできるデータに〇をつける--%> <%
 if (reservations[i][j] != null) {
 	String resUser = reservations[i][j].getUserId();
 	String logUser = meetingRoom.getUser().getId();
 	if (resUser.equals(logUser)) {
 		System.out.println(reservations[i][j].getUserId() + meetingRoom.getUser().getId());
 %>
			<form action="${pageContext.request.contextPath}/CancelCreateServlet"
				method="post">
				<input type="hidden" name="roomId" value="<%=rooms.get(i).getId()%>">
				<input type="submit" value="〇"
					class="button_submit button_submit_small button_submit_blue">
				<input type="hidden" name="time" value="<%=period[j]%>">
				<input type="hidden" name="researvationId" value="<%=reservations[i][j].getId()%>">
			</form> <%--配列の中身が×だったら×を直書き--%> <%
 } else {
 %>
			<button
				class="button_submit button_submit_small button_submit_blue ${'button_submit_impossible'}"
				${"disabled"}>×</button> <%
 }
 } else {
 %>
			<button
				class="button_submit button_submit_small button_submit_blue ${'button_submit_impossible'}"
				${"disabled"}>×</button> <%
 }
 %>

		</td>
		<%
		}
		%>
	</tr>
	<%
	}
	%>
</table>
<a href="${pageContext.request.contextPath}/jsp/menu.jsp"
	class="button_submit">戻る</a>

<%@include file="../common/footer.jsp"%>