<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="order">
    <p>Мои заказы</p>

    <c:forEach var="order" items="${orders}">
        <p>---------------------------------------------------------------------------</p>
        <p>Номер заказа: ${order.id}</p>
        <p>Дата заказа: <fmt:formatDate value="${order.orderDate.time}" pattern="dd/MM/yyyy"/></p>
        <p>Общая стоимость: ${order.totalPrice}</p>
        <table>
            <c:forEach var="dto" items="${productDto}">
                <c:if test="${dto.key == order.id}">

                        <tr>
                            <th>Название</th>
                            <th>Количество</th>
                            <th>Цена</th>
                            <th>Скидка</th>
                            <th>Итого</th>
                        </tr>
                    <c:forEach var="productDto" items="${dto.value}">
                        <tr>
                            <td><img src="${productDto.image}"> <br/> ${productDto.name}</td>
                            <td>${productDto.quantity}</td>
                            <td>${productDto.price}</td>
                            <td>${productDto.discount}</td>
                            <td><fmt:formatNumber type="number" pattern="###.##"
                                                  value="${((productDto.price -(productDto.price*productDto.discount)) * productDto.quantity)}"/>
                                р.
                            </td>
                        </tr>

                    </c:forEach>
                </c:if>

            </c:forEach>
        </table>
    </c:forEach>
</div>