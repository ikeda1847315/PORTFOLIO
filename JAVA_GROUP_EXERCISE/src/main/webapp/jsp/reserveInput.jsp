<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jp.co.sys.bean.MeetingRoom"%>
<%@ page import="jp.co.sys.bean.RoomBean"%>
<%@ page import="jp.co.sys.bean.ReservationBean"%>
<%@ page import="jp.co.sys.util.RoomList"%>
<%@ page import="jp.co.sys.dao.UserDao"%>

<%
//=====サーブレットから受け取る=====
MeetingRoom meetingRoom=(MeetingRoom)
session.getAttribute("meetingRoom");
session.setAttribute("meetingRoom", meetingRoom);
String[] period = meetingRoom.getPeriod();
RoomList rooms = meetingRoom.getRooms();
ReservationBean[][] reservations = meetingRoom.getReservations();
%>

<%@include file="../common/header.jsp"%>
<h1>会議室予約</h1>
<h2>利用日</h2>
<form action="${pageContext.request.contextPath}/ChangeDateServlet"
	method="POST" class="input_table">

	<input type="date" name="date" class="form_input_date"
		value="${meetingRoom.date}"min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
		> <input type="submit" value="日付変更"
		class="button_submit"><br> <input type="hidden"
		name="page" value="reserveInput.jsp">
</form>
<h2>
	予約可能時間帯（<c:out value="${meetingRoom.user.name}さん" />）
</h2>

<%--後で復活タグ<h1>予約可能時間帯${meetingRoom.user.name}</h1>--%>

<table class="input_table">
	<thead>
		<tr>
			<th class="input_th">会議室/時間</th>
			<%--始まりの時間を要素分取り出して順に表示--%>
			<%
			for (int j = 0; j < period.length; j++) {
			%>
			<th><%=period[j]%></th>
			<%
			}
			%>
		</tr>
	</thead>
	<%--二重for文　i=会議室名の表示--%>
	<%
	for (int i = 0; i < rooms.size(); i++) {
		String room = rooms.get(i).getName();
		request.setAttribute("room", room);
	%>
	<tbody>
		<tr>
			<td><c:out value="${room }" /></td>
			<%--二重for文　j=時間の表示（今は〇、×表示をここで判定）--%>
			<%
			for (int j = 0; j < period.length; j++) {
			%>
			<td>
				<%--配列の中身が〇だったらボタンを作る--%> <%--MeetingRoom対応：if
																				(reservations[i][j]==null)だったら予約の空きあり=〇表示)
																				--%> <%
 if (reservations[i][j] == null) {
 %>
				<form
					action="${pageContext.request.contextPath}/reserveCreateServlet"
					method="post">
					<input type="hidden" name="roomId"
						value="<%=rooms.get(i).getId()%>">
					<button
						class="button_submit button_submit_small button_submit_blue">〇</button>
					<%--仕様書はsubmit送信ですが、〇×表示になったので
																							hiddenで送ってます--%>
					<input type="hidden" name="time" value="<%=period[j]%>">
				</form> <%--配列の中身が×だったら×を直書き--%> <%
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
	</tbody>
</table>

<a href="${pageContext.request.contextPath}/jsp/menu.jsp"
	class="button_submit">戻る</a>
<%@include file="../common/footer.jsp"%>