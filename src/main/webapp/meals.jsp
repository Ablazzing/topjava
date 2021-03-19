<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ru.javawebinar.topjava.model.MealTo"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="bootstrap.min.css">

<html lang="ru">
<head>
    <title>Molodyko</title>
</head>
<body>
    <h3 class="alert alert-primary"><a href="index.html">Home</a></h3>

    <table class="table table-striped">
        <thead>
            <tr>
              <th scope="col">Описание</th>
              <th scope="col">Дата приема пищи</th>
              <th scope="col">Количество калорий</th>
            </tr>
          </thead>
        <tbody>
            <c:forEach var="meal" items="${requestScope.meals}">
                 <tr scope="row">
                            <td><c:out value="${meal.getDescription()}"></c:out></td>
                            <td><c:out value="${meal.getDateTime().toString().replaceAll('T',' ')}"></c:out></td>
                            <td class="<c:out value="${meal.isExcess() ? 'text-danger' : 'text-success'}"></c:out>">
                                <c:out value="${meal.getCalories().toString()}"></c:out>
                            </td>
                 </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>