<%@ page contentType="text/html; charset=UTF-8" pageEncoding= "UTF-8"%>
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
    <a class="btn text-uppercase font-weight-bold h4 btn-primary" href="meals?action=add_meal">Add meal</a>
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
                 <tr scope="row" class="<c:out value="${meal.isExcess() ? 'text-danger' : 'text-success'}"></c:out>">
                            <td><c:out value="${meal.getDescription()}"></c:out></td>
                            <td><c:out value="${meal.getDateTimeJspFormat()}"></c:out></td>
                            <td>
                                <c:out value="${meal.getCalories().toString()}"></c:out>
                            </td>
                            <td><a class="btn text-uppercase font-weight-bold btn-warning" href="meals?action=edit_meal&idMeal=${meal.getIdMeal()}">edit</a></td>
                            <td><a class="btn text-uppercase font-weight-bold btn-danger" href="meals?action=delete_meal&idMeal=${meal.getIdMeal()}">delete</a></td>

                 </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>