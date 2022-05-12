package hcmute.nhom35.foody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import DAO.UserAddressDAO;
import DAO.UserDAO;
import adapter.UserAddressAdapter;
import database.database;
import models.User;
import models.UserAddress;

public class ProfileActivity extends AppCompatActivity {

    int REQUEST_CODE_AVATAR = 123;

    UserAddressAdapter addressAdapter;
    ListView lvAddress;
    List<UserAddress> arrayListAddress;
    UserDAO userDAO = new UserDAO(new database(this));

    UserAddressDAO userAddressDAO = new UserAddressDAO(new database(this));

    EditText eFullname, eUsername, eBirthday, ePhone, eEmail, eChangeOldPass, eChangeNewPass, eChangeConfirmPass;

    TextView txtChangeAva;

    ImageView imvImaAva, btnAddAddress;

    LinearLayout btnHome;

    Button btnSaveProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();

        actionBar.hide();

        eFullname = (EditText) findViewById(R.id.eFullname);
        eUsername = (EditText) findViewById(R.id.eUsername);
        eBirthday = (EditText) findViewById(R.id.eBirthday);
        eEmail = (EditText) findViewById(R.id.eEmail);
        ePhone = (EditText) findViewById(R.id.ePhone);
        eChangeOldPass = (EditText) findViewById(R.id.eChangeOldPass);
        eChangeNewPass = (EditText) findViewById(R.id.eChangeNewPass);
        eChangeConfirmPass = (EditText) findViewById(R.id.eChangeCofirmPass);

        txtChangeAva = (TextView) findViewById(R.id.txtChangeAva);

        imvImaAva = (ImageView) findViewById(R.id.imvImgAva);

        Intent intent = getIntent();

        int idUser = intent.getIntExtra("idUser", 1);

        User user = userDAO.getUserById(idUser);

        eFullname.setText(user.getFullName());
        eUsername.setText(user.getUserName());
        eBirthday.setText(user.getBirthday());
        ePhone.setText(user.getPhone());
        eEmail.setText(user.getEmail());

        txtChangeAva.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_AVATAR);
        });

        lvAddress = findViewById(R.id.lvAddress);
        arrayListAddress = userAddressDAO.getUserAddressById(idUser);
        int countAddress = arrayListAddress.size();
        addressAdapter = new UserAddressAdapter(this, R.layout.address_item, arrayListAddress);
        lvAddress.setAdapter(addressAdapter);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnAddAddress = (ImageView) findViewById(R.id.btnAddAddress);

        btnSaveProfile.setOnClickListener(view -> {
            if(eChangeNewPass.getText().toString().equals("")){
                System.out.println(0);
                userDAO.updateUser(new User(idUser, eUsername.getText().toString(), user.getPassword(), eFullname.getText().toString(), ePhone.getText().toString(), eBirthday.getText().toString(), eEmail.getText().toString(), user.getRole()));
                System.out.println(1);
                Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
            } else {
                if (eChangeOldPass.getText().toString().equals(user.getPassword()) && eChangeNewPass.getText().toString().equals(eChangeConfirmPass.getText().toString()) ){
                    userDAO.updateUser(new User(idUser, eUsername.getText().toString(), eChangeNewPass.getText().toString(), eFullname.getText().toString(), ePhone.getText().toString(), eBirthday.getText().toString(), eEmail.getText().toString(), user.getRole()));
                    Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
                    System.out.println(2);
                } else {
                    System.out.println(3);
                    Toast.makeText(this, "Thông tin vừa nhập không chính xác", Toast.LENGTH_SHORT).show();
                }
            }

            for (int i = 0; i < countAddress; i++){
                userAddressDAO.updateUserAddress(arrayListAddress.get(i));
            }

            if(arrayListAddress.size() > countAddress){
                for (int i = 0; i < arrayListAddress.size(); i++){
                    if (i >= countAddress){
                        userAddressDAO.insert(arrayListAddress.get(i));
                    }
                }
            }

            /*Intent intentMain = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intentMain);*/
        });

        btnAddAddress.setOnClickListener(view -> {
            arrayListAddress.add(new UserAddress(1, null, idUser));
            addressAdapter = new UserAddressAdapter(this, R.layout.address_item, arrayListAddress);
            lvAddress.setAdapter(addressAdapter);
        });

        btnHome = findViewById(R.id.homeBtn);

        btnHome.setOnClickListener(view -> {
            Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
            intent1.putExtra("idUser", idUser);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_AVATAR);
        } else {
            Toast.makeText(this, "Bạn không cho phép truy cập vô thư viện ảnh", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_AVATAR && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imvImaAva.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
