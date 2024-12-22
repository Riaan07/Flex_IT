package sr.dlamini.flex_it;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        findViewById(R.id.signupLink).setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, Register_Activity.class));
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (isValid(email, password)) {
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                main.putExtra("user", getUser());
                startActivity(main);
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected User getUser() {
        if (!TextUtils.isEmpty(emailEditText.getText()) &&
                !TextUtils.isEmpty(emailEditText.getText()) &&
                Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            User user = null;
            Cursor c = helper.getData("User");
            while (c.moveToNext()) {
                if (c.getString(4).equals(emailEditText.getText().toString())) {
                    user = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
                    break;
                }
            }
            return user;
        } else Toast.makeText(this, "Please fill in values", Toast.LENGTH_SHORT).show();
        return null;
    }

    private boolean isValid(String email, String password) {
        User user = getUser();
        if (user != null)
            return email.equals(user.getEmail()) && password.equals(user.getPassword());
        else return false;
    }
}
