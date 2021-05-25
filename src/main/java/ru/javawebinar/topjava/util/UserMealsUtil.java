package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, Integer> tempDatesWithCalories = new TreeMap<>();

        for (UserMeal um : meals) {
            LocalDate date = um.getDateTime().toLocalDate();
            tempDatesWithCalories.merge(date, um.getCalories(), Integer::sum);
        }

        for (UserMeal um : meals) {
            LocalDate date = um.getDateTime().toLocalDate();
            LocalTime time = um.getDateTime().toLocalTime();
            if (time.compareTo(startTime) >= 0 && time.compareTo(endTime) < 0 && tempDatesWithCalories.get(date) > caloriesPerDay) {
                UserMealWithExcess umwe = new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), true);
                result.add(umwe);
            }
            if (time.isAfter(startTime) && time.isBefore(endTime) && tempDatesWithCalories.get(date) <= caloriesPerDay) {
                UserMealWithExcess umwe = new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), false);
                result.add(umwe);
            }
        }

        return result;


        // TODO return filtered list with excess. Implement by cycles
//        return null;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
