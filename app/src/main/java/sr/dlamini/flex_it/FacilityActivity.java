package sr.dlamini.flex_it;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FacilityActivity extends AppCompatActivity {

    ListView lstFacilities;
    ArrayList<Facility> facilities;
    Cursor c;
    DatabaseHelper helper;
    boolean isDateSet, isTimeSet;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date date;
    User user;
    AlertDialog dialog;
    Button btnAddFacility, btnGoHome;
    final Calendar cc = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        helper = new DatabaseHelper(this);
        facilities = new ArrayList<>();
        lstFacilities = findViewById(R.id.lstFacilities);
        lstFacilities.setDivider(null);
        c = helper.getData("Facility");
        isDateSet = isTimeSet = false;
        user = (User) getIntent().getSerializableExtra("user");
        btnAddFacility = findViewById(R.id.btnAddFacility2);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnAddFacility.setOnClickListener(view -> {
            View diag_view = LayoutInflater.from(this).inflate(R.layout.activity_add_facility, null);
            AlertDialog builder = new AlertDialog.Builder(this).create();

            EditText txtAddFacilityName = diag_view.findViewById(R.id.txtAddFacilityName),
                    txtAddFacilifyCapacity = diag_view.findViewById(R.id.txtAddFacilityCapacity),
                    txtAddFacilityPrice = diag_view.findViewById(R.id.txtAddFacilityPrice);

            builder.setView(diag_view);
            builder.setCanceledOnTouchOutside(true);

            diag_view.findViewById(R.id.btnAddFacility).setOnClickListener(v3 -> {
                if (helper.addFacility(txtAddFacilityName.getText().toString(),
                        txtAddFacilifyCapacity.getText().toString(), txtAddFacilityPrice.getText().toString())) {
                    Toast.makeText(this, "Facility added successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Couldn't add facility", Toast.LENGTH_SHORT).show();
            });
            diag_view.findViewById(R.id.btnAddGoBack).setOnClickListener(v3 -> {
                builder.dismiss();
            });
            builder.show();
        });
        btnGoHome.setOnClickListener(view -> {
            Intent _main = new Intent(new Intent(FacilityActivity.this, MainActivity.class));
            _main.putExtra("user", user);
            startActivity(_main);
            FacilityActivity.this.finish();
        });

        while (c.moveToNext()) {
            facilities.add(new Facility(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3)));
        }

        lstFacilities.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                facilities));

        if ("admin".equals(user.getFirstName())) {
            btnAddFacility.setVisibility(View.VISIBLE);
            findViewById(R.id.txtFacilityDetails).setVisibility(View.VISIBLE);
            lstFacilities.setOnItemLongClickListener((adapterView, view, i, l) -> {
                boolean hasBookings = false;
                Cursor cur = helper.getData("Booking",
                        "select * from booking where FacilityName = '" + facilities.get(i).getFacilityName() + "'");
                while (cur.moveToNext()) hasBookings = true;
                if (hasBookings)
                    Toast.makeText(FacilityActivity.this,
                            "Facility has bookings and couldn't be deleted.", Toast.LENGTH_SHORT).show();
                else {

                   new AlertDialog.Builder(this)
                           .setTitle("Confirm delete?")
                           .setMessage("Are you sure you want to delete " + facilities.get(i).getFacilityName() + "?")
                           .setPositiveButton("Yes", (dialogInterface, i1) -> {
                               if (helper.delete("Facility", "FacilityName = '" + facilities.get(i).getFacilityName() + "'")) {
                                   Toast.makeText(FacilityActivity.this, "Facility was deleted successful", Toast.LENGTH_SHORT).show();
                               } else
                                   Toast.makeText(FacilityActivity.this, "Facility couldn't be deleted, please contact system administrator.", Toast.LENGTH_SHORT).show();
                           }).show();

                }
                return true;
            });
        }
        lstFacilities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View diag_view = LayoutInflater.from(FacilityActivity.this).inflate(R.layout.make_booking_diag, null);
                AlertDialog builder = new AlertDialog.Builder(FacilityActivity.this).create();

                ImageButton btnSetDate = diag_view.findViewById(R.id.btnSelectDate),
                        btnTimeIn = diag_view.findViewById(R.id.btnSelectTimein),
                        btnTimeOut = diag_view.findViewById(R.id.btnSelectTimeout);
                TextView txtTimeIn = diag_view.findViewById(R.id.txtTimeinDialog),
                        txtTimeout = diag_view.findViewById(R.id.txtTimeoutDialog),
                        txtDate = diag_view.findViewById(R.id.txtDateDialog);


                builder.setView(diag_view);
                builder.setCanceledOnTouchOutside(true);

                btnSetDate.setOnClickListener(view1 -> {

                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    c.set(Calendar.DAY_OF_MONTH, mDay);
                    DatePickerDialog dpd = new DatePickerDialog(FacilityActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.set(Calendar.MONTH, monthOfYear);
                                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    cal.set(Calendar.YEAR, year);
                                    if (cal.before(c)) {
                                        Toast.makeText(FacilityActivity.this,
                                                "Date selected cannot be before today's date.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    StringBuilder date = new StringBuilder();
                                    isDateSet = true;
                                    date.append((dayOfMonth < 10 ? "0" : "")).append(dayOfMonth)
                                            .append("-").append((monthOfYear + 1) < 10 ? "0" : "")
                                            .append((monthOfYear + 1)).append("-").append(year);
                                    txtDate.setText(date.toString());
                                }
                            }, mYear, mMonth, mDay);
                    dpd.getDatePicker().setMinDate(c.getTimeInMillis());
                    dpd.show();
                });

                btnTimeIn.setOnClickListener(view1 -> {
                    mHour = cc.get(Calendar.HOUR_OF_DAY);
                    mMinute = cc.get(Calendar.MINUTE);
                    mYear = cc.get(Calendar.YEAR);
                    mMonth = cc.get(Calendar.MONTH);
                    mDay = cc.get(Calendar.DAY_OF_MONTH);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(FacilityActivity.this,
                            (v2, hourOfDay, minute) -> {
                                cc.set(mYear, mMonth, mDay, hourOfDay, minute);
                                txtTimeIn.setText(new SimpleDateFormat("HH:mm").format(cc.getTime()));
                                isTimeSet = true;
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                });

                btnTimeOut.setOnClickListener(view1 -> {
                    mHour = cc.get(Calendar.HOUR_OF_DAY);
                    mMinute = cc.get(Calendar.MINUTE);
                    mYear = cc.get(Calendar.YEAR);
                    mMonth = cc.get(Calendar.MONTH);
                    mDay = cc.get(Calendar.DAY_OF_MONTH);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(FacilityActivity.this,
                            (v2, hourOfDay, minute) -> {
                                cc.set(mYear, mMonth, mDay, hourOfDay, minute);
                                txtTimeout.setText(new SimpleDateFormat("HH:mm").format(cc.getTime()));
                                isTimeSet = true;
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                });

                diag_view.findViewById(R.id.btnGoBack).setOnClickListener(v3 -> {
                    builder.dismiss();
                });

                diag_view.findViewById(R.id.btnPay).setOnClickListener(v3 -> {
                    if (helper.addBooking(user.getEmail(), facilities.get(i).getFacilityName(), txtDate.getText().toString(),
                            txtTimeIn.getText().toString(), txtTimeout.getText().toString())) {
                        Toast.makeText(FacilityActivity.this, "Booking made successfully.", Toast.LENGTH_SHORT).show();
                        Intent _main = new Intent(new Intent(FacilityActivity.this, MainActivity.class));
                        _main.putExtra("user", user);
                        startActivity(_main);
                        FacilityActivity.this.finish();
                    } else
                        Toast.makeText(FacilityActivity.this, "Couldn't make a booking for this facility.", Toast.LENGTH_SHORT).show();
                });

                builder.show();
            }
        });
    }
}
