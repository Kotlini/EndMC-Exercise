package fr.kotlini.exercise.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@UtilityClass
public class RandomUtils
{
    private final List<String> grades = Arrays.asList("barron", "champion", "noob", "dev", "bg", "nerd", "kid");

    public String randomGrade() {
        return grades.get(new Random().nextInt(grades.size()));
    }

    public int randomAge() {
        return new Random().nextInt(114);
    }

    public int randomMoney() {
        return new Random().nextInt(15000);
    }
}
