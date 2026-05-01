<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jp.co.sys.bean.RoomBean"%>
<%@ page import="jp.co.sys.util.RoomList"%>
<%@ page import="jp.co.sys.dao.RoomDao"%>
<%@ page import="jp.co.sys.bean.MeetingRoom" %>	
<%
    MeetingRoom mr = (MeetingRoom) session.getAttribute("meetingRoom");

    // 未ログイン、または管理者ではない場合セッションを破棄してログイン画面へ
    if (mr == null || mr.getUser() == null || mr.getUser().getIsAdmin() == 0) {
		session.invalidate();
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?message=loggedout");
        return; 
    }
%>
<%@include file="../common/header.jsp"%>
<h1>会議室一覧</h1>
<hr>
<p class="message">${message}</p>
<table class="list_table">
	<thead>
		<tr>
			<th>ID</th>
			<th>会議室名</th>
		</tr>
	</thead>
	<tbody>
		<% 
			RoomList list = (RoomList)request.getAttribute("list");
			if (list != null) {
			for (RoomBean l : list) {
				String name = l.getName();
				request.setAttribute("name", name);
		%>
		<tr>
			<td><%= l.getId() %></td>
			<td><c:out value="${name }" /></td>
			<td class="list_td_small">
				<form action="<%= request.getContextPath() %>/RoomEditServlet"
					method="post">					
					<input type="hidden" name="roomId" value="<%= l.getId() %>" class="button_list">
					<input type="hidden" name="roomName" value="<%= l.getName() %>" class="button_list">
					<input type="submit" name="action" value="編集" class="button_list">
				</form>
			</td>
			<td class="list_td_small">
				<form action="<%= request.getContextPath() %>/RoomAdminServlet"
					method="post">
					<input type="hidden" name="roomId" value="<%= l.getId() %>" class="button_list">
					<input type="hidden" name="roomName" value="<%= l.getName() %>" class="button_list">
					<input type="submit" name="action" value="削除" class="button_list" onclick="return confirm('本当に削除してよろしいですか？');">
				</form>
			</td>
		</tr>
		<% }} %>
	</tbody>
</table>
<hr>
<div class="button_row">
	<a href="${pageContext.request.contextPath}/jsp/menu.jsp" class="button_submit">戻る</a>
	<form action="${pageContext.request.contextPath}/RoomEditServlet"
		method="post">
		<input type="submit" name="action" value="追加" class="button_submit">
	</form>
</div>
<%@include file="../common/footer.jsp"%>