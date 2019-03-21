package com.ctech.shockman.geoquiz2;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;


    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{

            new Question(R.string.question_australia, true),
            new Question(R.string.question_a, true),
            new Question(R.string.question_b, false),
            new Question(R.string.question_c, false),
            new Question(R.string.question_d, true),
            new Question(R.string.question_e, true),

    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int mCheat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) has been called!");

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }


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

                mIsCheater = false;

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

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(MainActivity.this, CheatActivity.class);
                mCheat = (mCheat + 1);
                mIsCheater = true;
                if (mCheat == 3) {
                    mCheatButton.setEnabled(false);
                } else {
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                    Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
            }
        });

        updateQuestion();
    }//end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
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
        if (mCheat == 3) {
            messageResourceId = R.string.cheater_toast;
        }
        if (mIsCheater == true) {
            messageResourceId = R.string.judgement_toast;

        }  else {

            if (userPressedTrue == answerIsTrue) {
                messageResourceId = R.string.correct_toast;
            } else {
                messageResourceId = R.string.incorrect_toast;
            }
            //Toast.setGravity(25,25,50);
            Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT).show();
        }
    }
}

