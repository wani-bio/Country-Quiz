package com.example.countryquiz;

/*
 * Java utility classes for list manipulation and unique set handling.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a full quiz session containing a list of questions.
 * Handles quiz generation and scoring.
 */
public class Quiz {
    private final List<Question> questions;

    /**
     * Constructs a quiz by selecting 6 random countries from the given list
     * and generating one question for each.
     *
     * @param countryList the list of all available countries
     */

    public Quiz(List<Country> countryList) {
        // Shuffle and pick 6 unique countries
        Collections.shuffle(countryList);
        List<Country> selected = countryList.subList(0, 6);

        // Get all unique continents
        Set<String> continentSet = new HashSet<>();
        for (Country c : countryList) {
            continentSet.add(c.getContinent());
        }
        List<String> allContinents = new ArrayList<>(continentSet);

        questions = new ArrayList<>();
        for (Country c : selected) {
            questions.add(new Question(c, allContinents));
        }
    }

    /**
     * Returns the list of generated quiz questions.
     *
     * @return list of Question objects
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Calculates and returns the number of correctly answered questions.
     *
     * @return current quiz score
     */
    public int getCurrentScore() {
        int score = 0;
        for (Question q : questions) {
            if (q.isCorrect()) score++;
        }
        return score;
    }
}
