package com.epam.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mViewText;
    private double answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.editText);
        mViewText = (TextView) findViewById(R.id.textView);


        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = calculate(mEditText.getText().toString());
                mViewText.setText(new Double(answer).toString());
            }
        });
    }

    protected double calculate(final String expression) {
        Lemmer lemmer = new Lemmer(expression);
        Parser parser = new Parser(lemmer.getTokens());

        return parser.getAns();
    }


}
