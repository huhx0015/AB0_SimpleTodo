package com.yoonhuh.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Michael Yoon Huh on 1/16/2016.
 */
public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        final EditText editTextField = (EditText) findViewById(R.id.editText);
        String editItem = getIntent().getStringExtra("Item");
        editTextField.setText(editItem);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("Item", editTextField.getText().toString());
                data.putExtra("Position", getIntent().getIntExtra("Position", 0));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
