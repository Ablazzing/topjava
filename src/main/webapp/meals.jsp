<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ru.javawebinar.topjava.model.MealTo"%>
<link rel="stylesheet" href="bootstrap.min.css">


<html lang="ru">
<head>
    <title>Molodyko</title>
</head>
<body>
<h3 class="alert alert-primary"><a href="index.html">Home</a></h3>

<%List<MealTo> mylist =(List) request.getAttribute("meals");%>

<table class="table table-striped">
<thead>
    <tr>
      <th scope="col">Описание</th>
      <th scope="col">Дата приема пищи</th>
      <th scope="col">Количество калорий</th>
    </tr>
  </thead>
<tbody>
    <% for (MealTo meal: mylist){%>
        <tr scope="row">
            <td><%=meal.getDescription()%></td>
            <td><%=meal.getDateTime().toString().replaceAll("T"," ")%></td>
            <td class=<%=meal.isExcess() ? "text-danger" : "text-success"%>>
                <%=meal.getCalories().toString()%>
            </td>
        </tr>
    <% } %>
</tbody>
</table>
</body>
</html>