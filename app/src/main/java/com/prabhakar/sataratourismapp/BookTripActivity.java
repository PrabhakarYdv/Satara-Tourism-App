package com.prabhakar.sataratourismapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class BookTripActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, emailEditText;
    private Spinner touristPlaceSpinner;
    private TextView selectDateTextView, confirmButton;

    private ArrayList<String> touristPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trip);
        // Initialize Views
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        touristPlaceSpinner = findViewById(R.id.touristPlaceSpinner);
        selectDateTextView = findViewById(R.id.selectDateTextView);
        confirmButton = findViewById(R.id.confirmButton);

        // Tourist Places List (You can replace this with your actual data)
        touristPlaces = new ArrayList<>();
        touristPlaces.add("Ajinkyatara Fort");
        touristPlaces.add("Kaas Plateau");
        touristPlaces.add("Thoseghar Waterfall");
        touristPlaces.add("Sajjangad Fort");
        touristPlaces.add("Kaas Lake");

        // Populate Spinner with Tourist Places
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, touristPlaces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        touristPlaceSpinner.setAdapter(adapter);

        // Handle Date Picker
        selectDateTextView.setOnClickListener(v -> openDatePickerDialog());

        // Handle Confirm Booking Button
        confirmButton.setOnClickListener(v -> confirmBooking());
    }

    private void openDatePickerDialog() {
        // Get Current Date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Open Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
            // Set the selected date in the TextView
            selectDateTextView.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);
        datePickerDialog.show();
    }


    private void confirmBooking() {
        // Get the entered data
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String selectedPlace = touristPlaceSpinner.getSelectedItem().toString();
        String bookingDate = selectDateTextView.getText().toString();

        // Simple validation
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || bookingDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Inflate dialog layout
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_booking_success, null);
            final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create();

            // Set data in dialog
            TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
            TextView dialogDetails = dialogView.findViewById(R.id.dialogDetails);
            Button closeDialogButton = dialogView.findViewById(R.id.closeDialogButton);

            dialogTitle.setText("Booking Confirmed!");
            dialogDetails.setText(
                    "Name: " + name + "\n" +
                            "Phone: " + phone + "\n" +
                            "Email: " + email + "\n" +
                            "Place: " + selectedPlace + "\n" +
                            "Date: " + bookingDate
            );

            // Close button action
            closeDialogButton.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        }
    }
}
