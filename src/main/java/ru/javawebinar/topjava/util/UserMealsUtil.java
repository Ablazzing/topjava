package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //Создаем мап для хранения потребления калорий по дням
        Map<LocalDate, Integer> mealsByDayMap = new HashMap<>();

        //Проходим по meals, заполняем мап по каждому дню
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            mealsByDayMap.put(date, mealsByDayMap.getOrDefault(date, 0) + meal.getCalories());
        }

        //Создаем список приемов пищи с атрибутом "превышен лимит калорий или нет", отфильтрованный по часам
        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();

        //Заполняем список фильтруя meals по часам, и создавая новые объекты UserMealWithExcess,
        //основываясь на потреблении калорий по дням из mealsByDayMap
        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            //Фильтруем по часам
            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                // Смотрим на количество калорий в дне
                mealWithExcessList.add(
                        new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mealsByDayMap.get(mealDate) > caloriesPerDay)
                );
            }
        }
        return mealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //Создаем мап для хранения потребления калорий по дням
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();

        //Проходим по meals, заполняем мап по каждому дню
        meals.stream().collect(Collectors.groupingBy(el -> el.getDateTime().toLocalDate()))
                .entrySet().forEach((entry) -> {
            Integer countCalories = entry.getValue().stream().map(e -> e.getCalories()).reduce((acc, e) -> acc + e).get();
            mapCaloriesPerDay.put(entry.getKey(), countCalories);
        });

        //Фильтруем meals по часам, и создаем новые объекты UserMealWithExcess
        return meals.stream().filter(e -> {
            LocalTime mealTime = e.getDateTime().toLocalTime();
            return TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime);
        }).map(e -> {
            LocalDate mealDate = e.getDateTime().toLocalDate();
            return new UserMealWithExcess(e.getDateTime(), e.getDescription(), e.getCalories(), mapCaloriesPerDay.get(mealDate) > caloriesPerDay);
        }).collect(Collectors.toList());


    }
}
