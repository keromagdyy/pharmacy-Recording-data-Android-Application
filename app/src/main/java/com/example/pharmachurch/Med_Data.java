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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmachurch.data.Class_Helper;
import com.example.pharmachurch.data.contantclass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Med_Data extends AppCompatActivity {

    Button btnDataMid;
    EditText txtMid;
    ListView LvAdd;
    TextView txtName,txtCode;

    ArrayList<String> arrayList = new ArrayList();
    ArrayAdapter adapter;

    Class_Helper H = new Class_Helper(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med__data);

        btnDataMid = findViewById(R.id.btnAddMid);
        txtMid = findViewById(R.id.txtMid);
        txtName = findViewById(R.id.txtName);
        txtCode = findViewById(R.id.txtCode);
        LvAdd = findViewById(R.id.LVMid);


        txtCode.setText(globalclass.code);
        txtName.setText(globalclass.Name);

        showData();



        LvAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final int which_item = position;

                new AlertDialog.Builder(Med_Data.this)
                        .setTitle(" خد بالك انت كدة هتمسح الأسم ده"+"\n"+"أمسحهولك ؟؟")
                        .setPositiveButton("أمسحهولى", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s =arrayList.get(position);
                                int sL = s.length()-11;
//                                Toast.makeText(Med_Data.this, s.substring(sL), Toast.LENGTH_SHORT).show();

                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();

                                SQLiteDatabase db3 = H.getWritableDatabase();
                                db3.delete(contantclass.medicinetable.TABLE_NAME, contantclass.medicinetable.COLUMN_MEDICINE_TIME + " = ?",new String[]{s.substring(sL)+""});

                                Toast.makeText(Med_Data.this, "أتمسحت بازميلى .. أبقى خد بالك بعد كدة", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("متمسحهوليش", null)
                        .show();

                return false;
            }
        });

        btnDataMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!"".equals(txtMid.getText().toString())) {
                    SQLiteDatabase db = H.getWritableDatabase();
                    ContentValues cv = new ContentValues();

                    String name = txtMid.getText().toString();
                    Date date = new Date();
                    SimpleDateFormat sDay = new SimpleDateFormat("E");
                    SimpleDateFormat sDate = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat sTime = new SimpleDateFormat("hh:mm:ss a");

                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_NAME, name);
                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_DAY, sDay.format(date));
                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_DATE, sDate.format(date));
                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_TIME, sTime.format(date));
                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_PATID_FOR,globalclass.code);
                    cv.put(contantclass.medicinetable.COLUMN_MEDICINE_PATNAME_FOR,globalclass.Name);
                    db.insert(contantclass.medicinetable.TABLE_NAME, null, cv);

                    txtMid.setText("");

                    showData();
                    Toast.makeText(Med_Data.this, "ضيفتلك اسم جديد", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Med_Data.this, "ياسطا انت بتعمل ايه !\nعايز تضيف أسم مش موجود ؟\nدخل أسم الاول وبعدين دوس إضافة", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showData() {
        arrayList.clear();
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);

        LvAdd.setAdapter(adapter);
        SQLiteDatabase dbR = H.getReadableDatabase();

        Cursor res = dbR.rawQuery("select * from " + contantclass.medicinetable.TABLE_NAME + " WHERE " + contantclass.medicinetable.COLUMN_MEDICINE_PATID_FOR + " = " + globalclass.code, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String t1 = res.getString(0);
            String t2 = res.getString(1);
            String t3 = res.getString(2);
            String t4 = res.getString(3);
            String t5 = res.getString(4);
            String t6 = res.getString(5);

            arrayList.add(t1 + "\n\t\t\t\t\t\t\t\t\t\t\t\t" + t2 + "\t\t\t" + t3 + "\t\t\t" + t4);
            adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
            LvAdd.setAdapter(adapter);


            res.moveToNext();
        }
    }
}
