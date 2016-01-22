package com.yoonhuh.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/** -----------------------------------------------------------------------------------------------
 *  [EditItemActivity] CLASS
 *  PROGRAMMER: Michael Yoon Huh
 *  DESCRIPTION: EditItemActivity is an activity class that is used for editing an existing item
 *  from the ListView in MainActivity.
 *  -----------------------------------------------------------------------------------------------
 */
public class EditItemActivity extends AppCompatActivity {

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

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