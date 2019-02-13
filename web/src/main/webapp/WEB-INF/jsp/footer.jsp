<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="i18n"/>

<div class="footer">

    <div class="inf">
        <h4><spring:message code="footer.inf"/></h4>
        <ul>
            <li><a href="#"><spring:message code="footer.us"/></a></li>
            <li><a href="#"><spring:message code="footer.infdelivery"/></a></li>
            <li><a href="#"><spring:message code="footer.infprivate"/></a></li>
            <li><a href="#"><spring:message code="footer.pay"/></a></li>
        </ul>
    </div>

    <div class="inf2">
        <h4><a href="${pageContext.request.contextPath}/shop?page=catalog"><spring:message code="footer.catlog"/></a></h4>
        <ul>
            <li><a href="${pageContext.request.contextPath}/shop?page=catalog&cat=boys"><spring:message code="footer.cat2"/></a></li>
            <li><a href="${pageContext.request.contextPath}/shop?page=catalog&cat=girls"><spring:message code="footer.cat3"/></a></li>
            <li><a href="${pageContext.request.contextPath}/shop?page=catalog&cat=newborn"><spring:message code="footer.cat1"/></a></li>
        </ul>
    </div>

    <div class="inf3">
        <h4><spring:message code="footer.contacts"/></h4>
        <ul>
            <li><img src="${pageContext.request.contextPath}/assets/image/round_room_white_18dp.png"/> г. Минск, ул Сухаревкскаая, 123</li>
            <li><img src="${pageContext.request.contextPath}/assets/image/round_call_white_18dp.png"/> +375 44 788 89 89, +375 29 809 56 77</li>
            <li><img src="${pageContext.request.contextPath}/assets/image/round_schedule_white_18dp.png"/> Мы открыты: 9:00 - 21:00</li>
        </ul>
    </div>

</div>
<!-- END of footer -->