package com.timemoneywaste.flames;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "Flames";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_boy_NAME = "boyname";
    public static final String COLUMN_girl_NAME = "girlname";
    public static final String COLUMN_Result_NAME = "result";
    public static final String Dummy_Count = "dummycount";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


//    creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Flames " +
                        "(id integer primary key,dummycount integer , boyname text,girlname text,result text)"
        );
    }
//upgrading data to table in db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Flames");
        onCreate(db);
    }
//inserting data into the table in db
    public boolean insertContact (String bname, String gname, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("boyname", bname);
        contentValues.put("girlname", gname);
        contentValues.put("result", result);
        int count=numberOfRows()+1;
        contentValues.put("dummycount", count);



        db.insert("Flames", null, contentValues);
        return true;
    }

//    cursor used to getting the data from database. it will hold the record then it will show to us based on our requirement
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Flames where id="+id+"", null );
        return res;
    }

//    It return the no of rows in the olumns

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }
//updating the data in table

    public boolean updateContact (Integer id, String bname, String gname, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("boyname", bname);
        contentValues.put("girlname", gname);
        contentValues.put("result", result);

        db.update("Flames", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
//delete data from table in db
    public void delete_all () {
        SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("delete from Flames");

    }

//    Deleting the particular entry and also updating the unqiues value again from 1 to last row. for example i m deleting 5 th row ok. affter that whatever data after 5th it will be consider as 5th data

    public void delete_particular (String delete_id) {

        //Array list you know, refer ijava word file prepared for java interview
        //if delete id contain any commea, hope it will delete

        List<String> arrayList_str=new ArrayList<>(Arrays.asList(delete_id.split(",")));
        List<Integer> arrayList_int=new ArrayList<>();

        for(String dummy : arrayList_str) {
              arrayList_int.add(Integer.parseInt(dummy));
        }


//        String builder is just holding the data, like bundle(collection of data that is)
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < arrayList_int.size(); i++) {

            stringBuilder.append(arrayList_int.get(i)); // adding the data into string builder

            if ((i + 1) !=arrayList_int.size()) {
                stringBuilder.append(",");
                           }

        }

        SQLiteDatabase db = this.getWritableDatabase();


//For getting row count in database
//        Cursor res =  db.rawQuery( "select * from Flames", null );
//        Total_Count=res.getCount();


        int count_before_delete=numberOfRows();
        db.execSQL("delete from Flames where id in("+stringBuilder+")");
        int count_after_delete=numberOfRows();


//Below code helps to show the data inrecycler view after deleteing particular data
//        Also below deletion happe twice, if you know the logic you will understand, you kept 2 unique identifier
        Cursor res =  db.rawQuery( "select * from Flames LIMIT 500", null );
        res.moveToFirst();

        int table_count=1;
        while(res.isAfterLast() == false) {

            int x =res.getInt(res.getColumnIndex(COLUMN_ID));
            if(x!=table_count) {
                db.execSQL("update Flames set id=" + table_count + " where dummycount= " + x); //here only we are updating the unique value after deletion of particular value
            }
            table_count=table_count+1;
            res.moveToNext();
        }
        res.close();

        res =  db.rawQuery( "select * from Flames LIMIT 500", null );
        res.moveToFirst();
        table_count=1;
        while(res.isAfterLast() == false) {

            int x =res.getInt(res.getColumnIndex(COLUMN_ID));
            int xx=res.getInt(res.getColumnIndex(Dummy_Count));
            if(x!=xx) {
                db.execSQL("update Flames set dummycount=" + table_count + " where id= " + x);
            }
            table_count=table_count+1;
            res.moveToNext();
        }
        res.close();



    }

//Showing all data

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Flames LIMIT 500", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

//Getting all data into single list

            String x=res.getString(res.getColumnIndex(COLUMN_ID));
            String xx=res.getString(res.getColumnIndex(COLUMN_boy_NAME));
            String xxx=res.getString(res.getColumnIndex(COLUMN_girl_NAME));
            String xxxx=res.getString(res.getColumnIndex(COLUMN_Result_NAME));


            String full_row= x+". "+xx+ "  +  " +xxx+ " = " +xxxx;

            array_list.add(full_row);

//            array_list.add(res.getString(res.getColumnIndex(COLUMN_boy_NAME)));


            res.moveToNext();
        }
        return array_list;
    }
}