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
<h2>入力</h2>
<p class="message">${message}</p>
<form action="${pageContext.request.contextPath}/RoomEditServlet" method="post" class="form">
	<div class="input-wrap">
		<label for="roomName">会議室名</label>
		<input type="text" name="roomName" value="<c:out value="${addRoom.name}" />" id="roomName" class="form_input" placeholder="20文字以内" required>
	</div>
	<div class="input-wrap ${title == '会議室編集' ? 'hidden' : ''}">
		<label for="roomFloor">会議室階数</label>
		<input type="number" name="roomFloor" value="${addRoom.id}" id="roomFloor" min="1" max="99" step="1" class="form_input" placeholder="1～99以内" ${title == '会議室編集' ? '' : 'required'}>
	</div>
 	<div class="button_row">
	    <input type="hidden" name="title" value="${title}">
	    <input type="hidden" name="roomId" value="${room.id}">
		<a href="${pageContext.request.contextPath}/RoomAdminServlet" class="button_submit">戻る</a>
	    <input type="submit" name="action" value="決定" class="button_submit">
    </div>
</form>
<%@include file="../common/footer.jsp"%>