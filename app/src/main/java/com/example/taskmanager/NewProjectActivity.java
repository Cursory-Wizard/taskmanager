package com.example.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProjectActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.taskmanager.REPLY";

    private EditText mEditProjectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        mEditProjectView = findViewById(R.id.edit_project);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditProjectView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String project = mEditProjectView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, project);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
