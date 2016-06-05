package com.huhx0015.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** -----------------------------------------------------------------------------------------------
 *  [MainActivity] CLASS
 *  PROGRAMMER: Michael Yoon Huh
 *  DESCRIPTION: MainActivity is the main activity class that is used to display a simple to-do list
 *  using a ListView.
 *  -----------------------------------------------------------------------------------------------
 */

public class MainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ACTIVITY VARIABLES
    private final int REQUEST_CODE = 2016;

    // LISTVIEW VARIABLES
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    // onCreate(): The initial function that is called when the activity is run. onCreate() only runs
    // when the activity is first started.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    /** ACTIVITY METHODS _______________________________________________________________________ **/

    // onActivityResult(): This method is run when focus returns to this activity from another
    // activity that was started with startActivityForResult().
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String item = data.getExtras().getString("Item");
            int position = data.getExtras().getInt("Position");
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            //itemsAdapter.add(item);
            writeItems();
        }
    }

    // onCreateOptionsMenu(): Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    // onOptionsItemSelected(): Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long as you specify a parent activity
    // in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** LIST METHODS ___________________________________________________________________________ **/

    // addItem(): Adds a new item to the to-do ListView.
    public void addItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    // setupListViewListener(): Sets on item click and long item click listeners for the to-do
    // ListView.
    private void setupListViewListener() {

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                editIntent.putExtra("Item", items.get(position));
                editIntent.putExtra("Position", position);
                startActivityForResult(editIntent, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    /** FILE I/O METHODS _______________________________________________________________________ **/

    // readItems(): Reads the saved items from the text file and loads them into the ArrayList items.
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    // writeItems(): Saves the existing ArrayList items into a text file.
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}