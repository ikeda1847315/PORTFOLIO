<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp"%>
<h1>会員情報編集</h1>
<hr>
<h2>編集確認</h2>

<table>
    <tr><th>パスワード</th><td>${user.password}</td></tr>
    <tr><th>氏名</th><td>${user.name}</td></tr>
    <tr><th>住所</th><td>${user.address}</td></tr>
    <tr class="${adminConfigClass}">
        <th>会員区分</th>
        <td>${user.isAdmin == 1 ? '管理者' : '一般会員'}</td>
    </tr>
</table>

<div class="button_row">
    <form action="${pageContext.request.contextPath}/UserEditServlet" method="post">
        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="userPw" value="${user.password}">
        <input type="hidden" name="userName" value="${user.name}">
        <input type="hidden" name="userAddress" value="${user.address}">
        <input type="hidden" name="userAdmin" value="${user.isAdmin == 1 ? 'on' : ''}">
        <input type="hidden" name="adminFlag" value="${adminFlag}">
        <input type="hidden" name="transition" value="${transition}">
        
        <input type="submit" name="action" value="戻る" class="button_submit">
        <input type="submit" name="action" value="登録" class="button_submit">
    </form>
</div>
<%@include file="../common/footer.jsp"%>