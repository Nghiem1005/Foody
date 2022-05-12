package hcmute.nhom35.foody;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import DAO.UserDAO;
import database.database;
import models.User;

public class RegisterActivity extends AppCompatActivity {

    EditText eUserName, ePassword, eFullName, ePhone, eEmail;
    Button btnRegister;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        UserDAO userdao = new UserDAO(new database(this));

        eUserName = (EditText) findViewById(R.id.editUsername);
        ePassword = (EditText) findViewById(R.id.editPassword);
        eFullName = (EditText) findViewById(R.id.editFullname);
        ePhone = (EditText) findViewById(R.id.editPhone);
        eEmail = (EditText) findViewById(R.id.editEmail);

        btnRegister = (Button) findViewById(R.id.btnRegister);

        txtLogin = (TextView) findViewById(R.id.txtRegister);

        btnRegister.setOnClickListener(view -> {
            if(userdao.checkExistUser(eUserName.getText().toString())){
                Toast.makeText(this, "Tên đăng nhập đã tồn tài.", Toast.LENGTH_LONG).show();
            } else {
                userdao.insert(new User(eUserName.getText().toString(), ePassword.getText().toString(), eFullName.getText().toString(), ePhone.getText().toString(), null, eEmail.getText().toString(), "User"));
                Toast.makeText(this, "Đã thêm tài khoản.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity1.class);
                startActivity(intent);
            }
        });

        txtLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity1.class);
            startActivity(intent);
        });
    }
}