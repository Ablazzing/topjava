package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action!=null){
            log.debug(action);
        }


        //Создаем список приемов пищи с признаком превышения потребления калорий
        assert action != null;
        switch (action) {
            case "meals": {
                log.debug("redirect to meals");

                List<MealTo> mealsTo = MealsUtil.filteredByCycles(MealsUtil.MEALS, LocalTime.MIN, LocalTime.MAX, MealsUtil.LIMIT_CALORIES);
                request.setAttribute("meals", mealsTo);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

                break;
            }
            //Удаление пищи по id
            case "delete_meal": {
                log.debug("delete_meal");

                int idMealTo = Integer.parseInt(request.getParameter("idMeal"));
                MealsUtil.deleteById(idMealTo);
                List<MealTo> mealsTo = MealsUtil.filteredByCycles(MealsUtil.MEALS, LocalTime.MIN, LocalTime.MAX, MealsUtil.LIMIT_CALORIES);
                ;

                request.setAttribute("meals", mealsTo);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            }
            //Изменение приема пищи по id
            case "edit_meal": {
                log.debug("edit_meal");
                int idMealTo = Integer.parseInt(request.getParameter("idMeal"));
                request.setAttribute("meal", MealsUtil.findById(idMealTo));

                request.getRequestDispatcher("/edit_meal.jsp").forward(request, response);
                break;
            }
            //Добавление приема пищи по id
            case "add_meal":
                log.debug("add_meal");
                request.getRequestDispatcher("/add_meal.jsp").forward(request, response);
                break;
            default:
                log.debug("action not found");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        //Создание приема пищи
        switch (action) {
            case "create": {
                String mealDescription = request.getParameter("description");
                int mealCalories = Integer.parseInt(request.getParameter("calories"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(request.getParameter("datetime"), formatter);

                Meal meal = new Meal(parsedDate, mealDescription, mealCalories);
                MealsUtil.MEALS.add(meal);
                break;
            }
            //Изменение существуещего приема пищи
            case "edit": {
                int idMeal = Integer.parseInt(request.getParameter("idMeal"));
                String mealDescription = request.getParameter("description");
                int mealCalories = Integer.parseInt(request.getParameter("calories"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(request.getParameter("datetime"), formatter);
                Meal meal = new Meal(parsedDate, mealDescription, mealCalories);
                MealsUtil.changeMealById(idMeal, meal);
                break;
            }
        }

        //Обновление списка еды
        List <MealTo> mealsTo =  MealsUtil.filteredByCycles(MealsUtil.MEALS, LocalTime.MIN, LocalTime.MAX, MealsUtil.LIMIT_CALORIES);;
        request.setAttribute("meals",mealsTo);
        //Редирект на страницу списка еды
        request.getRequestDispatcher("/meals.jsp").forward(request,response);
    }
}

