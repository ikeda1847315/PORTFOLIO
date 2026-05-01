<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jp.co.sys.bean.MeetingRoom" %>	
<%@ page import="jp.co.sys.bean.UserBean"%>
<%@ page import="jp.co.sys.util.UserList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>会員一覧</h1>
<hr>
<p class="message">${message}</p>
<table class="list_table">
	<thead>
		<tr>
			<th>ID</th>
			<th>氏名</th>
			<th>住所</th>
			<th>区分</th>
			<th>編集</th>
			<th>削除</th>
		</tr>
	</thead>
	<tbody>
		<% 
			UserList list = (UserList)request.getAttribute("list");
			if (list != null) {
			for (UserBean l : list) {
				String name = l.getName();
				String address = l.getAddress();
				request.setAttribute("tmpName", name);
				request.setAttribute("tmpAddress", address);
		%>
			<tr>
				<td class="list_td_small"><%= l.getId() %></td>
				<td><c:out value="${tmpName}" /></td>
				<td class="list_td_large"><c:out value="${tmpAddress}" /></td>
				<td class="list_td_small"><%= l.getIsAdmin() == 0 ? "一般" : "管理者" %></td>				
				<td class="list_td_small">
					<form action="<%= request.getContextPath() %>/UserEditServlet" method="post">
						<input type="hidden" name="userAddress" value="<c:out value="${tmpAddress}" />">
						<input type="hidden" name="userId" value="<%= l.getId() %>">
						<input type="hidden" name="userName" value="<c:out value="${tmpName}" />">
						<input type="hidden" name="userAdmin" value="<%= l.getIsAdmin() == 1 ? "on" : "" %>">
						<input type="submit" name="action" value="編集" class="button_list">
					</form>
				</td>
				<td class="list_td_small">
					<form action="<%= request.getContextPath() %>/AdminUserServlet" method="post">
						<input type="hidden" name="userId" value="<%= l.getId() %>">
						<input type="submit" name="action" value="削除" class="button_list <%= (l.getId()).equals(mr.getUser().getId()) ? "button_disabled" : "" %>" 
						onclick="return confirm('本当に削除してよろしいですか？');" <%= (l.getId()).equals(mr.getUser().getId()) ? "disabled" : "" %> >
					</form>
				</td>
			</tr>
		<% }} %>
	</tbody>
</table>
<div class="button_row">
	<a href="${pageContext.request.contextPath}/jsp/menu.jsp" class="button_submit">戻る</a>
	<a href="${pageContext.request.contextPath}/jsp/registrationInput.jsp" class="button_submit">会員追加</a>
</div>
<%@include file="../common/footer.jsp"%>