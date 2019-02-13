<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="i18n"/>


<div class="main-catalog">

    <div class="left-menu">
        <h3><a href="${pageContext.request.contextPath}/catalog"><fmt:message bundle="${i18n}" key="header.catalog"/></a></h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/catalog?cat=boys">Одежда для мальчиков</a></li>
            <li><a href="${pageContext.request.contextPath}/catalog?cat=girls">Одежда для девочек</a></li>
            <li><a href="${pageContext.request.contextPath}/catalog?cat=newborn">Одежда для новороденных</a>
            </li>
            <%--<li><a href="${pageContext.request.contextPath}/shop?page=catalog&cat=underwear">Нижнее белье</a></li>--%>
        </ul>
    </div>


    <div class="main-header">
        <p class="sort">Сортировать по:</p>
        <ul class="sort-ul">
            <li><a href="#">цене</a></li>
            <li><a href="#">популярности</a></li>
            <li><a href="#">названию</a></li>
        </ul>
        <p class="limit">Показывать по:</p>
        <ul class="limit-ul">
            <li><a href="#">10</a></li>
            <li><a href="#">30</a></li>
            <li><a href="#">50</a></li>
        </ul>
        <span class="line-main"></span>
    </div>

    <div class="product">

        <c:forEach var="product" items="${products}">

            <div class="product-box-s">
                <img src="${product.image}">
                <h3><a href="${pageContext.request.contextPath}/product?id=${product.id}">${product.name}</a>
                </h3>
                <p class="price"><fmt:formatNumber type="number" pattern="####.##"
                                                   value="${product.price -(product.price*product.discount)}"/> р.</p>
                <c:if test="${product.discount != 0}">
                    <p class="old-price">${product.price} р.</p>
                </c:if>
                <a class="buy"
                   href="${pageContext.request.contextPath}/product?id=${product.id}"><span>Купить</span></a>
            </div>
        </c:forEach>

    </div>

    <div class="pagination">
        <ul>
            <li><a href="#1">1</a></li>
            <li><a href="#2">2</a></li>
            <li><a href="#3">3</a></li>
        </ul>
    </div>

</div>
<!-- END of main -->

