<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp"%>
<h1>会議室予約</h1>
<hr>
<%-- 動的にパスを取得するため、プロジェクト名が変わっても修正不要です --%>
<h2>ログイン</h2>
<p class="message">${message}</p>
<% if ("loggedout".equals(request.getParameter("message"))) { %>
    <p class="message">アクセス権限がありません。</p>
<% } %>
<%session.removeAttribute("message"); %>
<form action="${pageContext.request.contextPath}/LoginServlet"
	method="post" class="form">
	<div class="input-wrap">
		<label for="userId">利用者ID</label> <input type="text" id="userId"
			name="userId" class="form_input" required>
	</div>
	<div class="input-wrap">
		<label for="userPw">パスワード</label> <input type="password" id="userPw"
			name="userPw" class="form_input" required>
	</div>
	<input type="submit" value="ログイン"
		class="button_submit button_submit_large">
</form>
<%@include file="../common/footer.jsp"%>