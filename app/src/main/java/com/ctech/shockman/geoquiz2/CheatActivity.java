package com.ctech.shockman.geoquiz2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.ctech.shockman.geoquiz2.answer_is_true";
    private static final String EXTRA_ANSWER_WAS_SHOWN = "com.ctech.shockman.qeoquiz2.answer_was_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private Button mGoBackButton;
    private static final int PLACEHOLDER = 0;


    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
    //debug
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_WAS_SHOWN, false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mGoBackButton = findViewById(R.id.go_back_button);


        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CheatActivity .this, MainActivity.class);
                startActivityForResult(intent, PLACEHOLDER);
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent toReturn = new Intent();
        toReturn.putExtra(EXTRA_ANSWER_WAS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, toReturn);
    }
}
