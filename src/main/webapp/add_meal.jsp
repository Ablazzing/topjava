<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding= "UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ru.javawebinar.topjava.model.Meal"%>
<%@page import="ru.javawebinar.topjava.util.MealsUtil"%>

<%
    String desc = "";
    String calories ="";
    String id ="";
    String datetimes="";

    if(request.getAttribute ("meal")!=null){
        Meal meal =(Meal) request.getAttribute ("meal");
        id =meal.getId().toString();
        calories =  meal.getCalories().toString();
        datetimes = MealsUtil.getDateTimeJspFormat(meal.getDateTime());
        desc = meal.getDescription();
    };

    request.setAttribute("id",id);
    request.setAttribute("desc",desc);
    request.setAttribute("calories",calories);
    request.setAttribute("datetimes",datetimes);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="https://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/4.0.0/flatly/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link type="text/css" href="css/bootstrap-datetimepicker.min.css" rel="stylesheet" />

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    <script src="js/moment.js" type="text/javascript"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>

    <script type="text/javascript">
        $(function () {
            $('#datetimepicker1').datetimepicker({
            format: 'yyyy-MM-DD HH:mm',
            locale: 'ru'
            });
        });
    </script>

    <title>${requestScope.type} meal</title>
</head>

<body>
    <div class="container ml-1 mt-1 mr-5">
        <h3 class="text-success">${requestScope.type} meal page</h3>
                    <form method="POST" action="meals?action=create&id=${requestScope.id}" name="frmAddMeal">
                        <div class="form-group">
                            <label for="description">Description :</label>
                            <input class="form-control" type="text" name="description" id="description" value="${requestScope.desc}"/>
                        </div>
                        <div class="form-group">
                            <label for="calories">Calories : </label>
                            <input class="form-control" type="text" name="calories" id="calories" value="${requestScope.calories}"/>
                        </div>
                        <div class="container m-0 mb-3 p-0 form-group">
                            <div class="row">
                                <div class="col">
                                    <label for="datetimepicker1">Datetime : </label>
                                    <input class="form-control" type="text" name="datetime" id="datetimepicker1" value="${requestScope.datetimes}"/>
                                </div>
                            </div>
                        </div>
                            <button type="submit" class="btn w-100 text-uppercase font-weight-bold h4 btn-primary">${requestScope.type} meal</button>
                    </form>
    </div>
</body>
</html>