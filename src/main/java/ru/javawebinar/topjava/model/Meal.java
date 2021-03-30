package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Meal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final static AtomicInteger count = new AtomicInteger();

    private final int idMeal;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.idMeal = count.incrementAndGet();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public int getIdMeal() {
        return idMeal;
    }

    public String getDateTimeJspFormat(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return getDateTime().format(formatter);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }


}
