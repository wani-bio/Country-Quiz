package com.example.countryquiz;

/*
 * AndroidX and Android libraries for fragment management,
 * layout inflation, and UI components like TextView and LinearLayout.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment that displays the final quiz score and a detailed
 * breakdown of the user's answers compared to the correct answers.
 */
public class ResultFragment extends Fragment {

    private static Quiz quiz;

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @return a new ResultFragment instance
     */
    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    /**
     * Assigns the quiz instance to this fragment for use during result display.
     *
     * @param quizInstance the completed Quiz object
     */
    public static void setQuiz(Quiz quizInstance) {
        quiz = quizInstance;
    }

    /**
     * Inflates the result fragment layout and populates it with the final score
     * and individual question feedback (correct/incorrect).
     *
     * @param inflater the LayoutInflater to inflate views
     * @param container the parent view group
     * @param savedInstanceState the saved instance state
     * @return the fully populated result view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView resultText = view.findViewById(R.id.resultText);
        LinearLayout resultList = view.findViewById(R.id.resultList);

        int score = quiz != null ? quiz.getCurrentScore() : 0;
        resultText.setText(getString(R.string.quiz_score_summary, score));

        if (quiz != null) {
            for (Question q : quiz.getQuestions()) {
                String country = q.getCountry().getName();
                String userAnswer = q.getUserAnswer();
                String correct = q.getCorrectAnswer();
                boolean isCorrect = q.isCorrect();

                TextView resultItem = new TextView(getContext());
                resultItem.setTextSize(16);
                resultItem.setPadding(0, 8, 0, 8);

                String resultLine = isCorrect
                        ? getString(R.string.answer_correct, country, userAnswer)
                        : getString(R.string.answer_wrong, country, userAnswer, correct);

                resultItem.setText(resultLine);
                resultList.addView(resultItem);
            }
        }

        return view;
    }
}