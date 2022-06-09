package hcmute.nhom35.foody;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import DAO.UserDAO;
import database.GetDatabase;
import database.database;
import models.User;

public class MainActivity1 extends AppCompatActivity {

    Button btnLogin;
    TextView txtRegister;
    EditText eUserName, ePass;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

        GetDatabase datas = new GetDatabase();
        datas.createDatabase(new database(this));
        //Chạy data cho lần đầu tiên
        datas.addData(new database(this));

        UserDAO userdao = new UserDAO(new database(this));

        ActionBar actionBar = getSupportActionBar();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        eUserName = (EditText) findViewById(R.id.editUsername);
        ePass = (EditText) findViewById(R.id.editPassword);

        btnLogin.setOnClickListener(view -> {

            User userC = userdao.getUser(eUserName.getText().toString(), ePass.getText().toString());
            if(userC == null){
                Toast.makeText(this, "User name hoặc password không chính xác!!!", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity1.this, MainActivity.class);
                intent.putExtra("idUser", userC.getId());
                startActivity(intent);
            }

        });

        txtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity1.this, RegisterActivity.class);
            startActivity(intent);
        });


        actionBar.hide();
    }
}
