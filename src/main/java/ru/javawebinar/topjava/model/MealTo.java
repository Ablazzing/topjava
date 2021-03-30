package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class MealTo {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final int idMeal;
//    private final AtomicBoolean excess;      // or Boolean[1],  filteredByAtomic
//    private final Boolean excess;            // filteredByReflection
//    private final Supplier<Boolean> excess;  // filteredByClosure
    private boolean excess;

    public MealTo(int idMeal, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.idMeal = idMeal;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public int getIdMeal() {
        return idMeal;
    }
//    public Boolean getExcess() {
//        return excess.get();
//    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeJspFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return getDateTime().format(formatter);
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    // for filteredBySetterRecursion
    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
