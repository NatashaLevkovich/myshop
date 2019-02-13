<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="product-table">
    <h1>Таблица товаров</h1>
    <table class="product-table" border="1">
        <tr>
            <th width="10">ID</th>
            <th>Название товара</th>
            <th>Цена</th>
            <th>Скидка</th>
            <th>Категория</th>
            <th>Подкатегория</th>
            <th>Изображение</th>
            <th>Производитель</th>
            <th>Состав</th>
            <th>Размеры</th>
            <th>Количество</th>
        </tr>

        <c:forEach var="product" items="${products}">
            <tr>
                <td width="10">${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.discount}</td>
                <td>${product.category}</td>
                <td>${product.subcategory}</td>
                <td>${product.image}</td>
                <td>${product.manufacturer}</td>
                <td>${product.material}</td>
                <td>
                    <table>
                        <c:forEach var="size" items="${product.sizeAndQuantity}">
                            <td>${size.key}</td>
                        </c:forEach>
                    </table>
                </td>

                <td>
                    <table>
                        <c:forEach var="size" items="${product.sizeAndQuantity}">
                            <td>${size.value}</td>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>


        <s:form id="redactorproduct" action="${pageContext.request.contextPath}/admin/redactor" method="post"
                modelAttribute="redproduct">

            <tr class="redactor">
                <td width="10"><s:input type="text" name="id" value="${redproduct.id}" path="id"/></td>
                <td><s:input type="text" name="name" value="${redproduct.name}" path="name"/></td>
                <td><s:input type="text" name="price" value="${redproduct.price}" path="price"/></td>
                <td><s:input type="text" name="discount" value="${redproduct.discount}" path="discount"/></td>
                <td><s:input type="text" name="category" value="${redproduct.category}" path="category"/></td>
                <td><s:input type="text" name="subcategory" value="${redproduct.subcategory}" path="subcategory"/></td>
                <td><s:input type="text" name="image" value="${redproduct.image}" path="image"/></td>
                <td><s:input type="text" name="manufacturer" value="${redproduct.manufacturer}" path="manufacturer"/></td>
                <td><s:input type="text" name="material" value="${redproduct.material}" path="material"/></td>
                <td><input type="text" name="size"/></td>
                <td><input type="text" name="quantity"/></td>
                <td>
                    <button form="redactorproduct">Редактировать/Добавить</button>
                </td>
            </tr>

        </s:form>
    </table>
</div>

<div class="order-table">
    <h1>Таблица заказов</h1>
    <table>
        <tr>
            <th>ID заказа</th>
            <th>Статус</th>
            <th>Дата заказа</th>
            <th>Сумма заказа</th>
            <th>Клиент</th>
            <th></th>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.status}</td>
                <td><fmt:formatDate value="${order.orderDate.time}" pattern="dd/MM/yyyy"/></td>
                <td>${order.totalPrice}</td>
                <td>${order.user}</td>
                <td><a href="${pageContext.request.contextPath}/admin/download?file=order${order.id}.doc">Скачать заказ</a></td>
            </tr>
        </c:forEach>

        <form id="redactororder" action="${pageContext.request.contextPath}/admin/redactororder"
              method="post">

            <tr class="redactororder">
                <td><p style="color: red"><c:out value="${errorOrder}"/></p>
                    <input type="text" name="id" required></td>
                <td>
                    <input type="radio" name="status" value="IS_FORMED" required/>IS_FORMED<br>
                    <input type="radio" name="status" value="DELIVERED" required/>DELIVERED<br>
                </td>
                <td>
                    <button form="redactororder">Редактировать</button>
                </td>
            </tr>

        </form>

    </table>

</div>

<div class="user-table">
    <h1>Таблица пользователей</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>E-mail</th>
            <th>Пароль</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Адресс</th>
            <th>Номер телефона</th>
            <th>Статус</th>
        </tr>

        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.password}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.address}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.status}</td>
            </tr>
        </c:forEach>

        <s:form id="setuser" action="${pageContext.request.contextPath}/admin/redactoruser" method="post" modelAttribute="reduser">
            <tr>
                <td><s:input type="text" name="id" value="${reduser.id}" path="id"/> </td>
                <td><p style="color: red"><c:out value="${errorName}"/></p>
                    <s:input type="text" name="email" value = "${reduser.email}" path="email"/></td>
                <td><s:input type="text" name="password" value = "${reduser.password}" path="password"/></td>
                <td><s:input type="text" name="firstname" value = "${reduser.firstName}" required="true" path="firstName" /></td>
                <td><s:input type="text" name="lastname" value = "${reduser.lastName}" path="lastName"/></td>
                <td><s:input type="text" name="address" value = "${reduser.address}" path="address"/></td>
                <td><s:input type="text" name="phone" value = "${reduser.phoneNumber}" path="phoneNumber"/></td>
                <td><s:input type="text" name="phone" value = "${reduser.status}" path="status"/>
                    <%--<s:radiobutton type="text" name="status" value = "ADMIN" path="status"/>ADMIN<br>--%>
                    <%--<s:radiobutton type="text" name="status" value = "USER" path="status"/>USER<br>--%>
                </td>
                <td>
                    <button form="setuser">Редактировать/Добавить</button>
                </td>
            </tr>
        </s:form>
    </table>
</div>