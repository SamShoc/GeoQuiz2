package com.ctech.shockman.geoquiz2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{

            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),

    };

    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) has been called!");

        setContentView(R.layout.activity_main);


        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }

        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1);
                }
                updateQuestion();
            }

        });

        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart has been called!");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume has been called!");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause has been called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop has been called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy has been called!");
    }

    // encapsulate the update question code so we don't have to copy and paste it:
    private void updateQuestion() {
        int questionResourceId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(questionResourceId);
    }

    // check whether the button clicked matches the answer in the resource
    private void checkAnswer(boolean userPressedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResourceId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResourceId = R.string.correct_toast;
        } else {
            messageResourceId = R.string.incorrect_toast;
        }

        // you can make fancier Toast here if you want (from previous challenge)
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
    }
}

