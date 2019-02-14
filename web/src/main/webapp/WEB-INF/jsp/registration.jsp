<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="main">


    <%--@elvariable id="user" type="entities"--%>
    <s:form id="registration-form" action="${pageContext.request.contextPath}/registration/add" method="post" modelAttribute="user">
        <fieldset>
            <p style="color: red"><c:out value="${errors}"/></p>
            <p>E-mail:</p>
            <p style="color: red"><c:out value="${errorName}"/></p>
            <s:input type="email" name="email" path="email" required="true"/>
            <p>Пароль:</p>
            <s:input type="password" name="pass" path="password" required="true"/>
            <p>Повторите пароль: <span style="color: red"><c:out value="${error}"/></span></p>
            <input type="password" name="pass2" path="pass2" required/>
            <p>Имя:</p>
            <s:errors path="firstName" cssStyle="color: red"/>
            <s:input type="text" name="firstname" path="firstName" required="true"/>
            <p>Фамилия:</p>
            <s:input type="text" name="lastname" path="lastName"/>
            <p>Адрес:</p>
            <s:input type="text" name="address" path="address"/>
            <p>Телефон:</p>
            <s:input type="tel" name="phone" path="phoneNumber"/>
            <button type="submit" form="registration-form">Зарегистрироваться</button>
        </fieldset>
    </s:form>
</div>
