package com.shivam.stickyheader;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private DatabaseHandler db;
    private EditText fname, fdate;
    private LinearLayout linearLayout;
    private Button add, show;

    private String name, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = new ProgressDialog(this);

        //Instantiate database handler
        db = new DatabaseHandler(this);

        linearLayout = (LinearLayout) findViewById(R.id.ll);

        fname = (EditText) findViewById(R.id.name);
        fdate = (EditText) findViewById(R.id.date);
        fdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String formattedMonth = "" + month;
                        String formattedDayOfMonth = "" + dayOfMonth;

                        if (month < 10) {
                            formattedMonth = "0" + month;
                        }
                        if (dayOfMonth < 10) {
                            formattedDayOfMonth = "0" + dayOfMonth;
                        }
                        fdate.setText(formattedDayOfMonth + "-" + formattedMonth + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUser();
            }
        });

        show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
            }
        });

    }


    private void getValues() {
        name = fname.getText().toString();
        date = fdate.getText().toString();
    }

    private void addUser() {
        getValues();

        db.addUsers(new User(name, date));
        Toast.makeText(this, "Added successfully...", Toast.LENGTH_SHORT).show();

        fname.setText(" ");
        fdate.setText(" ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
