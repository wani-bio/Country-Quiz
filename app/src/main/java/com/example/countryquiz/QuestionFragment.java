package com.example.countryquiz;

/*
 * Android and Jetpack libraries for building a UI fragment with text,
 * radio button choices, and layout inflation.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment that displays a single quiz question with multiple choice answers.
 */
public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_INDEX = "index";
    private static Quiz quiz;

    /**
     * Factory method to create a new instance of this fragment.
     *
     * @param index         the question index in the quiz
     * @param quizInstance  the shared Quiz object
     * @return a new instance of QuestionFragment
     */
    public static QuestionFragment newInstance(int index, Quiz quizInstance) {
        quiz = quizInstance;
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    private int index;

    /**
     * Called to initialize fragment arguments.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt(ARG_QUESTION_INDEX);
    }

    /**
     * Inflates the view for this question and sets up the UI with the question and answer options.
     *
     * @param inflater           the LayoutInflater
     * @param container          the parent view group
     * @param savedInstanceState the saved instance state
     * @return the created view for this fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup optionsGroup = view.findViewById(R.id.optionsGroup);

        Question q = quiz.getQuestions().get(index);
        questionText.setText("Which continent is " + q.getCountry().getName() + " on?");

        // Set A–C labeled radio buttons
        for (int i = 0; i < 3; i++) {
            RadioButton btn = (RadioButton) optionsGroup.getChildAt(i);
            char label = (char) ('A' + i);
            btn.setText(label + ". " + q.getOptions().get(i));
        }

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = view.findViewById(checkedId);
            if (selected != null) {
                String answerWithLabel = selected.getText().toString();

                // Strip off "A. ", "B. ", or "C. "
                String answer = answerWithLabel.length() > 3 && answerWithLabel.charAt(1) == '.'
                        ? answerWithLabel.substring(3).trim()
                        : answerWithLabel.trim();

                q.setUserAnswer(answer);
            }
        });

        return view;
    }
}