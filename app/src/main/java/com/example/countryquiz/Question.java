package com.example.countryquiz;

/*
 * Java utility libraries for list manipulation and shuffling.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single quiz question, including the country being asked about,
 * a list of answer options, and the user's selected answer.
 */
public class Question {
    private Country country;
    private List<String> options;
    private String correctAnswer;
    private String userAnswer;

    /**
     * Constructs a Question for the given country, generating two random incorrect options
     * in addition to the correct continent, and shuffling them.
     *
     * @param country        the country being asked about
     * @param allContinents  a list of all possible continents (must include correct one)
     */
    public Question(Country country, List<String> allContinents) {
        this.country = country;
        this.correctAnswer = country.getContinent();

        // Generate 2 random wrong answers
        List<String> shuffled = new ArrayList<>(allContinents);
        shuffled.remove(correctAnswer);
        Collections.shuffle(shuffled);
        List<String> wrongAnswers = shuffled.subList(0, 2);

        options = new ArrayList<>();
        options.add(correctAnswer);
        options.addAll(wrongAnswers);
        Collections.shuffle(options); // Shuffle answer order
    }

    /**
     * Returns the country this question is about.
     *
     * @return the country object
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Returns the shuffled list of answer options (including the correct one).
     *
     * @return a list of continent options
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Returns the correct answer for this question.
     *
     * @return the correct continent
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the user's selected answer.
     *
     * @param answer the answer chosen by the user
     */
    public void setUserAnswer(String answer) {
        userAnswer = answer;
    }

    /**
     * Checks whether the user's answer is correct.
     *
     * @return true if correct, false otherwise
     */
    public boolean isCorrect() {
        return correctAnswer.equals(userAnswer);
    }

    /**
     * Gets the user's selected answer.
     *
     * @return the user's answer
     */
    public String getUserAnswer() {
        return userAnswer;
    }
}