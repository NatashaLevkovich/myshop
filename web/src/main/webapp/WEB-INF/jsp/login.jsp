<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="main">
    <c:set var="reg" scope="request" value="${registration}"/>
    <c:out value="${reg}"/>
    <h2>Добро пожаловать!</h2>
    <form id="login-form" action="${pageContext.request.contextPath}/login" method="post">
        <p>E-mail</p>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <p>Invalid username and password.</p>
            </div>
        </c:if>
        <br/>
        <input type="text" id="email" name="email" required="required"/>
        <p>Пароль</p>
        <input type="password" id="password" name="password" required/><br/>
        <button form="login-form">Войти</button>
    </form>
</div>