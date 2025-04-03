package com.example.countryquiz;

/*
 * AndroidX libraries for managing fragments and lifecycle-aware paging in ViewPager2.
 */
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Adapter for the ViewPager2 that supplies QuestionFragments for each quiz question
 * and a ResultFragment at the end of the quiz.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {

    private Quiz quiz;

    /**
     * Constructs the adapter with the given quiz and fragment manager.
     *
     * @param quiz     the Quiz object containing all questions
     * @param fm       the FragmentManager used to manage fragments
     * @param lifecycle the lifecycle of the activity hosting the adapter
     */
    public QuizPagerAdapter(Quiz quiz, FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
        this.quiz = quiz;
    }

    /**
     * Creates a new fragment based on the current position.
     * Returns a QuestionFragment for quiz questions, and a ResultFragment at the end.
     *
     * @param position the index of the page to create
     * @return the corresponding Fragment instance
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < quiz.getQuestions().size()) {
            return QuestionFragment.newInstance(position, quiz);
        } else {
            ResultFragment.setQuiz(quiz); // pass quiz only
            return ResultFragment.newInstance(); // no score passed
        }
    }

    /**
     * Returns the number of pages (questions + result screen).
     *
     * @return total number of fragments
     */
    @Override
    public int getItemCount() {
        return quiz.getQuestions().size() + 1; // +1 for the result screen
    }
}