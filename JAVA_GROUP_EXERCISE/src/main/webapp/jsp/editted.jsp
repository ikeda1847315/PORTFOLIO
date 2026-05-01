<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp"%>
<h1>会員情報編集</h1>
<hr>
<h2>編集完了</h2>
<table>
	<tbody>
		<tr>
			<th>利用者ID</th>
			<td>${user.id}</td>
		</tr>
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
		<tr class="${'1'.equals(adminFlag) ? '' : 'hidden' || meetingRoom.user.isAdmin == '0' ? 'hidden' : ''}">
			<th>会員区分</th>
			<td>${user.isAdmin == 1 ? '管理者' : '一般会員'}</td>
		</tr>
	</tbody>
</table>
<form action="${pageContext.request.contextPath}/UserEditServlet" method="post">
    <input type="hidden" name="transition" value="${transition}">
    <input type="submit" name="action" value="${'会員情報編集'.equals(transition) ? 'メニューへ' : '一覧へ'}" class="button_submit">
</form>
<%@include file="../common/footer.jsp"%>