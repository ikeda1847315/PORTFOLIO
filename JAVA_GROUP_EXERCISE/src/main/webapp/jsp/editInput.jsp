<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp"%>
<h1>会員情報編集</h1>
<hr>
<p class="message">${message}</p>

<form action="${pageContext.request.contextPath}/UserEditServlet" method="post" class="form">
    <div class="input-wrap">
        <label>パスワード</label>
        <input type="text" name="userPw" value="${user.password}" class="form_input" placeholder="6～10文字以内の半角英数字のみ" required>
    </div>
    <div class="input-wrap">
        <label>氏名</label>
        <input type="text" name="userName" value="${user.name}" class="form_input" placeholder="10文字以内" required>
    </div>
    <div class="input-wrap">
        <label>住所</label>
        <input type="text" name="userAddress" value="<c:out value='${user.address}'/>" class="form_input" placeholder="30文字以内" required>
    </div>
	<div class="input-wrap input-wrap_check ${adminConfigClass}">
	    <label>管理者</label>
	    <input type="checkbox" name="userAdmin" id="userAdmin" class="form_check" ${checked}>
	</div>
    <div class="button_row">
        <a href="${pageContext.request.contextPath}/${'編集'.equals(transition) ? 'AdminUserServlet' : 'jsp/menu.jsp' }" class="button_submit">戻る</a>
        <input type="hidden" name="userId" value="${user.id}">
        <input type="hidden" name="adminFlag" value="${adminFlag}">
        <input type="hidden" name="transition" value="${transition}">
        <input type="submit" name="action" value="決定" class="button_submit">
    </div>
</form>
<form action="${pageContext.request.contextPath}/AdminUserServlet" method="post" class="form ${unsubscribeClass}">
    <input type="hidden" name="userId" value="${user.id}">
    <input type="submit" name="action" value="退会する" class="button_submit button_unsubscribe" onclick="return confirm('本当に退会しますか？');">
</form>
<%@include file="../common/footer.jsp"%>