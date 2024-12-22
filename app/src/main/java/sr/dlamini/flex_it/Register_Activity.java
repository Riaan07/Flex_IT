package sr.dlamini.flex_it;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register_Activity extends AppCompatActivity {
    EditText firstNameEditText, lastNameEditText, usernameEditText, IdNumberEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        helper = new DatabaseHelper(this);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        IdNumberEditText = findViewById(R.id.idNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
//                String username = usernameEditText.getText().toString();
                String IdNumber = IdNumberEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    if (password.equals(confirmPassword)) {
                        if (helper.addUser(firstName, lastName, IdNumber, email, password)) {
                            User user = new User(0, firstName, lastName, IdNumber, email, password);
                            Intent main = new Intent(Register_Activity.this, MainActivity.class);
                            main.putExtra("user", user);
                            startActivity(main);
                            Toast.makeText(Register_Activity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Register_Activity.this, "Couldn't add user, please contact administrator.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(Register_Activity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
