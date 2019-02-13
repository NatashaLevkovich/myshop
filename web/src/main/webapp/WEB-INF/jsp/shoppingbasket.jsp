<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main">

    <h2>Корзина</h2>

    <div class="item">
        <c:forEach var="productDto" items="${productDto}">

            <img src="${productDto.image}">
            <p>${productDto.name}</p>
            <p>Параметры:</p>
            <p>${productDto.manufacturer}</p>
            <p>${productDto.material}</p>
            <p>Количество: ${productDto.quantity}</p>
            <p>Цена: ${productDto.price}</p>
            <p>Скидка: ${productDto.discount}</p>
            <p>Итого: <fmt:formatNumber type="number" pattern="####.##"
                                 value="${((productDto.price -(productDto.price*productDto.discount)) * productDto.quantity)}"/> р.</p>
            <a href="${pageContext.request.contextPath}/shoppingbasket?action=del&id=${productDto.itemId}">Удалить</a>
            <p>------------------------------------------</p>
        </c:forEach>
    </div>

    <c:set var="order" value="${order}" scope="request"></c:set>
    <div class="order">
        <p>Сумма заказа: ${order.totalPrice}</p>
        <a href="${pageContext.request.contextPath}/orders?orderId=${order.id}">Оформить заказ</a>
    </div>

</div>
<!-- END of main -->
