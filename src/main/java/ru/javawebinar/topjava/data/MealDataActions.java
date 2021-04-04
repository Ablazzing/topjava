package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealDataActions {
    void save(Meal meal);

    List<Meal> getAll();

    void deleteById(int idMeal);

    Meal findById(int idMeal);
}
