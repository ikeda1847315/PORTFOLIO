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
<h1>会員登録画面</h1>
<hr>
<h2>登録情報の確認</h2>
<table>
	<tbody>
		<tr>
			<th>パスワード</th>
			<td>${user.password}</td>
		</tr>
		<tr>
			<th>氏名</th>
			<td><c:out value="${user.name}" /></td>
		</tr>
		<tr>
			<th>住所</th>
			<td><c:out value="${user.address}" /></td>
		</tr>
		<tr>
			<th>会員区分</th>
			<td>${user.isAdmin == 1 ? '管理者' : '一般会員'}</td>
		</tr>
	</tbody>
</table>
<div class="button_row">
	<form action="${pageContext.request.contextPath}/RegistrationServlet"
		method="post">
		<input type="hidden" name="userId" value="${user.id}">
		<input type="hidden" name="userPw" value="${user.password}">
		<input type="hidden" name="userName" value="${user.name}">
		<input type="hidden" name="userAddress" value="${user.address}">
		<input type="hidden" name="userAdmin" value="${user.isAdmin == 1 ? 'on' : ''}">
		<input type="submit" name="action" value="戻る" class="button_submit">
		<input type="submit" name="action" value="登録" class="button_submit">
	</form>
</div>
<%@ include file="../common/footer.jsp"%>