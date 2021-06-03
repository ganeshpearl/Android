package com.timemoneywaste.flames;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

public class display_Data extends AppCompatActivity {

    private DBHelper mydb;


    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private ArrayList<Result> result_data = new ArrayList<>();
    private MultiAdapter adapter;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data);


//coming back to home page when clicking the arrow in display dta page
        ActionBar actionBar = this.getSupportActionBar();

   // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        mydb = new DBHelper(this);

//for recycler view
        this.recyclerView = findViewById(R.id.recyclerView);

        //item for recycler view la iruka xml file ha display_data file ku kondu varadhuku
        getSupportActionBar().setTitle("HISTORY"); //Name in the top action bar
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, result_data);
        recyclerView.setAdapter(adapter);

        //help to get data from database and display in the recycler view
        createList();


    }

    //Getting data from database and show in the recycler view
    private void createList() {

        ArrayList array_list = mydb.getAllCotacts();

//      ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list); //idhula indha layout name constant..

        result_data = new ArrayList<>();
        for (int i = 0; i < array_list.size(); i++) {

            Result Result_class_obj = new Result();
            Result_class_obj.setName(array_list.get(i).toString());

//for example to show at least one selection
//            if (i == 0) {
//            Result_class_obj.setChecked(true);
//            }
            result_data.add(Result_class_obj);
        }
        adapter.setEmployees(result_data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.result_page, menu);
        menuItem = menu.findItem(R.id.clear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) { // Id .home is inbuild..for accessing the parent class. check in the manifest file.
            NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        switch (item.getItemId()) {
            case R.id.home1:
//                NavUtils.navigateUpFromSameTask(this);  //manifest la parent activity mention panu na ipdi pota podhum
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                super.onOptionsItemSelected(item);
                break;

            case R.id.clear:

                if (adapter.getSelected().size() > 0) {

                    StringBuilder id_for_delete = new StringBuilder();
                    StringBuilder gname_for_delete = new StringBuilder();
                    StringBuilder bname_for_delete = new StringBuilder();

                    String getting_only_girlname;
                    String getting_only_boyname;
                    String temp;
                    int Unique_id;


                    for (int i = 0; i < adapter.getSelected().size(); i++) {

                        temp = adapter.getSelected().get(i).getName();
                        temp = temp.replaceAll("\\s", "");
                        temp = temp.substring(0, temp.indexOf("."));
                        Unique_id = Integer.parseInt(temp.trim());

                        getting_only_boyname = adapter.getSelected().get(i).getName();
                        getting_only_boyname = getting_only_boyname.replaceAll("\\s", "");
                        getting_only_boyname = getting_only_boyname.substring(getting_only_boyname.indexOf(".") + 1, getting_only_boyname.indexOf("+"));

                        getting_only_girlname = adapter.getSelected().get(i).getName();
                        getting_only_girlname = getting_only_girlname.replaceAll("\\s", "");
                        getting_only_girlname = getting_only_girlname.substring(getting_only_girlname.indexOf("+") + 1, getting_only_girlname.indexOf("="));

                        id_for_delete.append(Unique_id);
                        gname_for_delete.append("'" + getting_only_boyname + "'");
                        bname_for_delete.append("'" + getting_only_girlname + "'");


                        if ((i + 1) != adapter.getSelected().size()) {
                            id_for_delete.append(",");
                            gname_for_delete.append(",");
                            bname_for_delete.append(",");
                        }

                    }

                    mydb.delete_particular(id_for_delete.toString().trim());
                    //For refreshing teh page
                    finish();
                    startActivity(getIntent());
                    showToast("Deleted successfully from the list");

                } else {
                    showToast("Select something from the list");
                }
                super.onOptionsItemSelected(item);

                break;
            case R.id.clearall:
                mydb.delete_all();
                finish();
                startActivity(getIntent());
                showToast("All values are cleared");
                super.onOptionsItemSelected(item);

                break;


        }
        return true;

    }


}