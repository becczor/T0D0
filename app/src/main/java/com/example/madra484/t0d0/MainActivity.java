package com.example.madra484.t0d0;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ListActivity {


    private ArrayList<Todo> testData;
    private ArrayAdapter<Todo> adapter;
    //private int numberof = 0;
    final TodosDataSource database = new TodosDataSource(this);

    @Override
    protected void onListItemClick(ListView l, View v, final int position, final long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("TodoLog", "onListItemClick funkar " + position + ", " + id);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton("Radera",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface diN, int k)
            {


                Log.d("TodoLog", "Position = " + position + ", id = " + id);

                database.deleteTodo(testData.get(position));
                adapter.remove(testData.get(position));

            }
        });

        builder.setPositiveButton("Avbryt",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface diP, int k)
            {

            }
        });

        // ----- Kod för om man inte vill ha centrering i TextView --------
        //builder.setTitle("Radering");
        //builder.setMessage("Vill du radera den här uppgiften?");
        //-----------------------------------------------------------------
        AlertDialog dialog = builder.create();

        TextView messageText = new TextView(this); //Till centrering
        messageText.setText("\n  Vill du radera den här uppgiften? \n");
        messageText.setGravity(Gravity.CENTER);

        dialog.setView(messageText);
        dialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testData = new ArrayList<Todo>();

        //Open Database

        database.open();
        testData = database.getAllTodos();

        /*for(int i = 0; i < 5; i++)
        {
            Todo theTask = new Todo();
            theTask.setId(i);
            theTask.setTodo("Nummer " + i);

            testData.add(theTask);
            Log.d("TodoLog", "Lägger till task " + i);
            numberof++;
        }*/

        adapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1 , testData);
        setListAdapter(adapter);
        //Skapar knapp och kopplar till gränssnitt
        Button button = (Button)findViewById(R.id.add);
        final EditText textfield = (EditText)findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.add)
                {
                    adapter.add(database.createTodo(textfield.getText().toString()));
                    textfield.setText("");
            }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
