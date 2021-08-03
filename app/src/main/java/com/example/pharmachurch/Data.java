package com.example.pharmachurch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pharmachurch.data.Class_Helper;
import com.example.pharmachurch.data.contantclass;

import java.util.ArrayList;

public class Data extends AppCompatActivity {

    EditText txtSearch;
    ListView LvSearch;
    Spinner spinner;

    ArrayList<String> arrayList = new ArrayList();
    ArrayAdapter adapter;

    Class_Helper H = new Class_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        txtSearch = findViewById(R.id.txtSearch);
        LvSearch = findViewById(R.id.LvSearch);
        spinner = findViewById(R.id.spinner);


        showData();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(spinner.getSelectedItem().equals("الكود"))
                    search("code");
                else
                    search("name");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        LvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Data.this, arrayList.get(position), Toast.LENGTH_SHORT).show();

                globalclass.code = String.valueOf(arrayList.get(position).charAt(0));
                globalclass.Name = arrayList.get(position).substring(22);
                Intent intent = new Intent(Data.this, Med_Data.class);
                startActivity(intent);
            }
        });
    }

    public void showData()
    {

        arrayList.clear();
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);

        LvSearch.setAdapter(adapter);
        SQLiteDatabase dbR = H.getReadableDatabase();

        Cursor res = dbR.rawQuery("select * from " + contantclass.patienttable.TABLE_NAME,null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            String t1 = res.getString(0);
            String t2 = res.getString(1);


            arrayList.add(t1+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+t2);
            adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
            LvSearch.setAdapter(adapter);


            res.moveToNext();
        }
    }

    public void search(String S)
    {
        String sTool;

        arrayList.clear();
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);

        LvSearch.setAdapter(adapter);
        SQLiteDatabase dbR = H.getReadableDatabase();
        if(S == "code")
            sTool = contantclass.patienttable.COLUMN_PATIENT_CODE;
        else
            sTool = contantclass.patienttable.COLUMN_PATIENT_NAME;

        Cursor res = dbR.rawQuery("select * from " + contantclass.patienttable.TABLE_NAME + " WHERE " + sTool + " like '%" + txtSearch.getText().toString() + "%'",null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            String t1 = res.getString(0);
            String t2 = res.getString(1);


            arrayList.add(t1+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+t2);
            adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
            LvSearch.setAdapter(adapter);


            res.moveToNext();
        }
    }

}
