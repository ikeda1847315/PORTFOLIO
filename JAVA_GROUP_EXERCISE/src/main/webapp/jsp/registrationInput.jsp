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
<h1>会員登録入力</h1>
<hr>
<h2>入力</h2>
<p class="message">${message}</p>
<form action="${pageContext.request.contextPath}/RegistrationServlet" method="post" class="form">
	<div class="input-wrap">
		<label for="userPw">パスワード</label>
		<input type="text" name="userPw" value="${user.password}" id="userPw" class="form_input" placeholder="6～10文字以内の半角英数字のみ" required>
	</div>
	<div class="input-wrap">
		<label for="userName">氏名</label>
		<input type="text" name="userName" value="${user.name}" id="userName" class="form_input" placeholder="10文字以内" required>
	</div>
	<div class="input-wrap">
		<label for="userAddress">住所</label>
		<input type="text" name="userAddress" value="${user.address}" id="userAddress" class="form_input" placeholder="30文字以内" required>
	</div>
	<div class="input-wrap input-wrap_check">
		<label for="userAdmin">管理者</label>
		<input type="checkbox" name="userAdmin" id="userAdmin" class="form_check" ${checked}>
	</div>
 	<div class="button_row">
	    <a href="${pageContext.request.contextPath}/AdminUserServlet" class="button_submit">戻る</a>
	    <input type="submit" name="action" value="決定" class="button_submit">
    </div>
</form>
<%@include file="../common/footer.jsp"%>