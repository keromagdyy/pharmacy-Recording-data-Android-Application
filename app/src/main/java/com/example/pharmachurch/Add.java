package com.example.pharmachurch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pharmachurch.data.*;
import com.example.pharmachurch.data.contantclass.*;

import java.util.ArrayList;

public class Add extends AppCompatActivity {

    Button btnAdd;
    EditText txtName,txtCode;
    ListView LvAdd;

    ArrayList<String> arrayList = new ArrayList();
    ArrayAdapter adapter;

    Class_Helper H = new Class_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAdd = findViewById(R.id.btnAdd);
        txtName = findViewById(R.id.txtName);
        LvAdd = findViewById(R.id.LvAdd);




        showData();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(txtName.getText().toString())) {
                    SQLiteDatabase db = H.getWritableDatabase();
                    ContentValues cv = new ContentValues();

                    String name = txtName.getText().toString();
                    cv.put(patienttable.COLUMN_PATIENT_NAME, name);
                    db.insert(patienttable.TABLE_NAME, null, cv);

                    txtName.setText("");


                    showData();
                    Toast.makeText(Add.this, "ضيفتلك اسم جديد", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Add.this, "ياسطا انت بتعمل ايه !\nعايز تضيف أسم مش موجود ؟\nدخل أسم الاول وبعدين دوس إضافة", Toast.LENGTH_SHORT).show();
            }
        });


        LvAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final int which_item = position;

                new AlertDialog.Builder(Add.this)
                        .setTitle(" خد بالك انت كدة هتمسح الأسم ده"+"\n"+"أمسحهولك ؟؟")
                        .setPositiveButton("أمسحهولى", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s =arrayList.get(position);
                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();

                                SQLiteDatabase db3 = H.getWritableDatabase();
                                db3.delete(patienttable.TABLE_NAME,patienttable.COLUMN_PATIENT_CODE + " = ?",new String[]{s.charAt(0)+""});
                                Toast.makeText(Add.this, "أتمسحت بازميلى .. أبقى خد بالك بعد كدة", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("متمسحهوليش", null)
                        .show();

                return false;
            }
        });

    }

    public void showData()
    {
        arrayList.clear();
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);

        LvAdd.setAdapter(adapter);
        SQLiteDatabase dbR = H.getReadableDatabase();
        Cursor res = dbR.rawQuery("select * from " + patienttable.TABLE_NAME,null);
        res.moveToFirst();
        while(res.isAfterLast() == false)
        {
            String t1 = res.getString(0);
            String t2 = res.getString(1);


            arrayList.add(t1+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+t2);
            adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
            LvAdd.setAdapter(adapter);


            res.moveToNext();
        }
    }
}
