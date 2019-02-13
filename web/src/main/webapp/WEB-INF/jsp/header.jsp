<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="i18n"/>
<c:url var="path" value="/?page=${sessionScope.pageName}"/>

<div class="header">

    <div class="header-top">

        <div class="logo"><a href="${pageContext.request.contextPath}">
            <span class="baby_sh">BABY SH</span>
            <img src="${pageContext.request.contextPath}/assets/image/logo.png">
            <span class="p">P</span></a>
        </div>

        <div class="search">
            <form id="search" action="${pageContext.request.contextPath}/catalog/search"><input type="search" name="search"/>">
            </form>
            <button form="search" class="button-search" src="${pageContext.request.contextPath}/assets/image/baseline_search_black_18dp.png" ><spring:message code="header.search"/></button>
        </div>


        <div>
            <sec:authorize access="not hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                <div class="si-in">
                    <p class="in"><a href="${pageContext.request.contextPath}/login"><spring:message code="header.signin"/></a>
                    </p>
                    <span class="line_h"></span>
                    <p class="reg"><a href="${pageContext.request.contextPath}/registration"><spring:message
                           code="header.reg"/></a></p>
                </div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                <div class="logout">
                    <p class="in_mail"><a href="${pageContext.request.contextPath}/orders">
                        <sec:authentication property="principal.username"/>
                    </a></p>
                    <span class="line_h1"></span>
                    <p class="out"><a href="${pageContext.request.contextPath}/logout"><spring:message code="header.logout"/></a>
                    </p>
                </div>
            </sec:authorize>

            <c:set value="${items}" var="itemscount" scope="session"/>

            <c:if test="${empty itemscount}">
                <c:set value="0" var="itemscount"/>
            </c:if>

            <div class="Sh_c">
                <p class="e_shop"><spring:message code="header.onshop"/></p>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                    <a href="${pageContext.request.contextPath}/shoppingbasket">
                        <span class="basket"><spring:message code="header.basket"/></span>
                        <img class="basket-img"
                             src="${pageContext.request.contextPath}/assets/image/baseline_shopping_basket_black_18dp.png"/>
                        <span class="ellipse"></span>
                        <span class="count_item">${itemscount}</span>
                    </a>
                </sec:authorize>

            </div>
        </div>
    </div>

    <div class="menu">

        <ul>
            <li class="select_menu"><a href="${pageContext.request.contextPath}"><spring:message code="header.main"/></a>
            </li>
            <li class="catalog"><a href="${pageContext.request.contextPath}/catalog"><spring:message code="header.catalog"/></a>
            </li>
            <div class="submenu">
                <ul class="select">
                    <li class="for-boys"><a href="${pageContext.request.contextPath}/catalog?cat=boys"><spring:message
                            code="header.cat2"/></a></li>
                    <li class="for-girls"><a href="${pageContext.request.contextPath}/catalog?cat=girls"><spring:message
                            code="header.cat3"/></a></li>
                    <li class="for-newborn"><a
                            href="${pageContext.request.contextPath}/catalog?cat=newborn"><spring:message code="header.cat1"/></a>
                    </li>
                    <%--<li><a href="#">Нижнее белье</a></li>--%>
                </ul>
            </div>
            </li>
            <li class="delivery"><a href="#"><spring:message code="header.delivery"/></a></li>
            <li class="pay"><a href="#"><spring:message code="header.pay"/></a></li>
            <li class="about-us"><a href="#"><spring:message code="header.us"/></a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="admin"><a href="${pageContext.request.contextPath}/admin">Страница администратора</a></li>
            </sec:authorize>
            <li class="ru"><a href="${path}&amp;locale=ru">RU</a></li>
            <li class="en"><a href="${path}&amp;locale=en">EN</a></li>
        </ul>


    </div>
</div>
<!-- END of header -->