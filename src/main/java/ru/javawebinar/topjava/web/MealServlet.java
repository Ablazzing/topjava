package ru.javawebinar.topjava.web;
import org.slf4j.Logger;
import ru.javawebinar.topjava.data.MealData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealData mealData;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealData = new MealData();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action!=null) {
            switch (action) {
                //Изменение приема пищи по id
                case "edit_meal": {
                    log.debug("edit_meal");
                    int idMealTo = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("meal", mealData.findById(idMealTo));
                    request.setAttribute("type","Edit");
                    request.getRequestDispatcher("/add_meal.jsp").forward(request, response);
                    break;
                }
                //Добавление приема пищи по id
                case "add_meal":
                    log.debug("add_meal");
                    request.setAttribute("type","Add");
                    request.getRequestDispatcher("/add_meal.jsp").forward(request, response);
                    break;
            }
        }else{
            //Создаем список приемов пищи с признаком превышения потребления калорий
            log.debug("redirect to meals");
            List<MealTo> mealsTo = MealsUtil.filteredByCycles((List<Meal>) mealData.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.LIMIT_CALORIES);
            request.setAttribute("meals", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        //Удаление приема пищи
        if (action.equals("delete_meal")){
            log.debug("delete_meal");
            int idMealTo = Integer.parseInt(request.getParameter("id"));
            mealData.deleteById(idMealTo);
        }else {
            log.debug(request.getParameter("id"));
            String mealDescription = request.getParameter("description");
            int mealCalories = Integer.parseInt(request.getParameter("calories"));
            LocalDateTime parsedDate = LocalDateTime.parse(request.getParameter("datetime"),MealsUtil.formatter);
            //Если изменение приема пищи, то удаляем прошлую запись
            if (request.getParameter("id")!=null && !request.getParameter("id").equals("")){
                mealData.deleteById(Integer.parseInt(request.getParameter("id")));
            }
            //Создаем новый прием пищи
                Meal meal = new Meal(parsedDate, mealDescription, mealCalories);
                mealData.save(meal);
            }
        //Идем обратно на список приемов пищи
        response.sendRedirect("meals");
    }
}

