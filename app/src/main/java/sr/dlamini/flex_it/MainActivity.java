package sr.dlamini.flex_it;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button btnMakeBooking;
    User user;
    Cursor c;
    ListView lstBooking;
    Button btnRegister;

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DatabaseHelper(this);
        lstBooking = findViewById(R.id.lstBookings);
        lstBooking.setDivider(null);
        btnMakeBooking = findViewById(R.id.btnMakeBooking);
        btnMakeBooking.setOnClickListener(view -> {
            Intent book = new Intent(MainActivity.this, FacilityActivity.class);
            book.putExtra("user", user);
            startActivity(book);
            finish();
        });

        user = (User) getIntent().getSerializableExtra("user");
        ((TextView) findViewById(R.id.txtGreet))
                .setText("Hello " + user.getFirstName() + " " + user.getLastName() + "\n" +
                        user.getEmail());
        if ("admin".equals(user.getFirstName()))
            c = helper.getData("Booking");
        else
            c = helper.getData("Booking", "select * from booking where email = '" + user.getEmail() + "'");

        ArrayList<Booking> bookings = new ArrayList<>();
        while (c.moveToNext()) {
            bookings.add(new Booking(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4), c.getString(5)));
        }
        if (bookings.size() == 0)
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
        lstBooking.setAdapter(new BookingAdapter(this, bookings));
    }
}