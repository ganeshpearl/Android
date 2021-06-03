package com.timemoneywaste.flames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    Button click;
    EditText boy_name, girl_name;
    ImageView image_view;
    TextView result, app_name,reset;
    RelativeLayout r_layout;
    DBHelper mydb;
    public int clickcount_text = 0;

    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DBHelper(this);

        boy_name = findViewById(R.id.boy_name);
        girl_name = findViewById(R.id.girl_name);
        click = findViewById(R.id.button_send);

        image_view = findViewById(R.id.imageView);
        result = findViewById(R.id.result);
        reset = findViewById(R.id.reset);
        r_layout = findViewById(R.id.rlayout);

        app_name = findViewById(R.id.app_name);


//Adding listener to do some operation in edit text like checking count of name,etc

        boy_name.addTextChangedListener(character_count);
        girl_name.addTextChangedListener(character_count);



// Adding listner to flames name, for getting count like how many time pressed

        app_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount_text = clickcount_text + 1;

                if (clickcount_text > 4) {
                    clickcount_text = 0;

                    int id_To_Search = 0;
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    Intent intent = new Intent(getApplicationContext(), display_Data.class);
                    intent.putExtras(dataBundle);

                    startActivity(intent);

                }
            }
        });

//Pull down for reset the values in edit text.

        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {  //resetting the text view when pull down

                boy_name.setText("");
                girl_name.setText("");
                result.setText("");
                reset.setText("");
                click.setEnabled(false);

                image_view.setImageResource(0);

                pullToRefresh.setRefreshing(false);
            }

        });

// For clicking the button, action will happen

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = getApplicationContext();
                flames_java fj = new flames_java();

                String name1 = boy_name.getText().toString();
                String name2 = girl_name.getText().toString();

                String Answer = fj.main_program(name1, name2);

// adding the data to the sqlite- inserting one row to sqlllite


                Bundle extras = new Bundle();
                extras.putInt("id", 0);

                int Value = extras.getInt("id");
                if (mydb.insertContact(name1, name2, Answer)) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

//For closing the keyboard after clicking button
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(r_layout.getWindowToken(), 0);

                reset.setText("*Swipe down for reset");

                if (name1.toLowerCase().equals("muthupandi") & name2.toLowerCase().equals("vinitha")) {
                    result.setText("Marriage");
                    image_view.setImageResource(R.drawable.pandi_vinitha);
                    Toast.makeText(context, "Marriage", Toast.LENGTH_SHORT).show();
                }else if (name1.toLowerCase().equals("viratkohli") & name2.toLowerCase().equals("anushkasharma")) {
                     result.setText("Marriage");
                     image_view.setImageResource(R.drawable.virat);
                     Toast.makeText(context, "Marriage", Toast.LENGTH_SHORT).show();
                } else if (Answer == "Friend") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.friends);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Love") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.love);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Affection") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.affection);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Marriage") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.marriage);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Enemyy") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.enemy);
//                            Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Sister") {
                    result.setText(Answer);
                    image_view.setImageResource(R.drawable.sister);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                } else if (Answer == "Kalla Kadhal") {
                    result.setText(Answer);
//                          image_view.setImageResource(R.drawable.love);
                    Toast.makeText(context, Answer, Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


//exception handling action for edit text

    private TextWatcher character_count = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //Trim for should not consider even empty space also
            //Button enabling action

            String name1 = boy_name.getText().toString().trim();
            String name2 = girl_name.getText().toString().trim();
            click.setEnabled(!name1.isEmpty() && !name2.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

            String name1 = boy_name.getText().toString();
            String name2 = girl_name.getText().toString();
            Context context = getApplicationContext();

            if (name1.contains(" ") || name2.contains(" ")) {

                Toast.makeText(context, "Sorry! Name should not contain blank!!", Toast.LENGTH_SHORT).show();

                click.setEnabled(false);

            }
            if (name1.length() > 15 || name2.length() > 15) {
                Toast.makeText(context, "Enter the correct name please", Toast.LENGTH_SHORT).show();
            }

        }
    };
//     for reset like once you do refresh then it will automatically setRefreshing


    //for menu, but nothing added here now

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
// for getting the menu button, pressed count and also calling second activity

            Intent intent = new Intent(getApplicationContext(), display_Data.class);
            startActivity(intent);

            return super.onOptionsItemSelected(item);
    }

//Press back again for exit . Double time press back for exit , that function below

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    } //Press back again for exit

}
