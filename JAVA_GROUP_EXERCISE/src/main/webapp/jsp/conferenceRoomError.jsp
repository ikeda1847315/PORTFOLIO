<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>${title}</h1>
<hr>
<h2>エラー</h2>
<p class="message">${message}</p>
<table>
	<tbody>
		<tr>
			<th>会議室名</th>
			<td><c:out value="${room.name}" /></td>
		</tr>
		<tr class="${title == '会議室編集' ? 'hidden' : ''}">
			<th>会議室階</th>
			<td><c:out value="${addRoom.id}" /></td>
		</tr>
	</tbody>
</table>
<form action="${pageContext.request.contextPath}/RoomAdminServlet"
	method="post">
	<input type="submit" name="action" value="一覧へ" class="button_submit">
</form>
<%@include file="../common/footer.jsp"%>