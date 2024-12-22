package sr.dlamini.flex_it;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BookingAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Booking> bookings;

    public BookingAdapter(Context context, ArrayList<Booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @Override
    public int getCount() {
        return bookings.size();
    }

    @Override
    public Object getItem(int i) {
        return bookings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.view_booking_activity, null);
        TextView txtFacName = v.findViewById(R.id.txtItemName),
                txtDate = v.findViewById(R.id.txtItemDate),
                txtTime = v.findViewById(R.id.txtItemTime);
        Booking b = bookings.get(i);
        txtFacName.setText(bookings.get(i).getEmail());
        txtDate.setText(bookings.get(i).getDateIn());
        txtTime.setText("FROM: " + bookings.get(i).getTimeIn()
                + " - "+ bookings.get(i).getTimeOut());
        return v;
    }
}
