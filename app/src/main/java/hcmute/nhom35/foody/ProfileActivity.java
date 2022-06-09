package hcmute.nhom35.foody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.UserAddressDAO;
import DAO.UserDAO;
import adapter.CartAdapter;
import adapter.UserAddressAdapter;
import database.database;
import models.CartDetail;
import models.CuaHang;
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

    ImageView btnHome, btncart, btnRegisterRes, btnBill;
    TextView totalCart;
    TextView btnPay;
    ListView listMon;
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));
    CartAdapter cartAdapter;

    Bitmap bitmap;

    User user;

    Button btnSaveProfile;
    @RequiresApi(api = Build.VERSION_CODES.P)
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

        btncart = findViewById(R.id.btnCart);
        btnHome = findViewById(R.id.homeBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnBill = findViewById(R.id.btnBill);

        Intent intent = getIntent();

        int idUser = intent.getIntExtra("idUser", 1);

        user = userDAO.getUserById(idUser);

        eFullname.setText(user.getFullName());
        eUsername.setText(user.getUserName());
        eBirthday.setText(user.getBirthday());
        ePhone.setText(user.getPhone());
        eEmail.setText(user.getEmail());

        byte[] avatar;
        if(user.getImg() != null){
            avatar = user.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            imvImaAva.setImageBitmap(bitmap);
        }


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
                userDAO.updateUser(new User(idUser, eUsername.getText().toString(), user.getPassword(), eFullname.getText().toString(), ePhone.getText().toString(), eBirthday.getText().toString(), eEmail.getText().toString(), user.getRole(),user.getImg()));
                Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
            } else {
                if (eChangeOldPass.getText().toString().equals(user.getPassword()) && eChangeNewPass.getText().toString().equals(eChangeConfirmPass.getText().toString()) ){
                    userDAO.updateUser(new User(idUser, eUsername.getText().toString(), eChangeNewPass.getText().toString(), eFullname.getText().toString(), ePhone.getText().toString(), eBirthday.getText().toString(), eEmail.getText().toString(), user.getRole(), user.getImg()));
                    Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
                } else {
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
        });

        btnAddAddress.setOnClickListener(view -> {
            arrayListAddress.add(new UserAddress(1, null, idUser));
            addressAdapter = new UserAddressAdapter(this, R.layout.address_item, arrayListAddress);
            lvAddress.setAdapter(addressAdapter);
        });

        btnHome.setOnClickListener(view -> {
            Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
            intent1.putExtra("idUser", idUser);
            startActivity(intent1);
        });

        btncart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);
            totalCart = dialog.findViewById(R.id.totalCart);
            List<CartDetail> cartDetailList = cartDetailDAO.getAllCart();
            cartAdapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailList);
            //dialog.getWindow().setLayout(((getWidth(this) )), ((getHeight(this) )));

            dialog.getWindow().setGravity(Gravity.END);

            listMon.setAdapter(cartAdapter);
            int total = 0;
            for(int i=0; i<cartDetailDAO.getAllCart().size(); i++){
                String value = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice();
                String[] result = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().split(",");

                int price = Integer.valueOf(ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().substring(0, 2));
                total = total + (price * cartDetailList.get(i).getQuantity());
                //total = total + (cartDetailDAO.getAllCart().get(i).getQuantity() * ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice())
            }
            totalCart.setText(String.valueOf(total) + ",000");


            btnPay = dialog.findViewById(R.id.btnPay);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(ProfileActivity.this, MapsActivity.class);
                    intent2.putExtra("idMon", (Serializable) cartDetailDAO.getAllCart());
                    intent2.putExtra("idUser", idUser);
                    startActivity(intent2);
                }
            });
            ImageView btnExit  = dialog.findViewById(R.id.btnExit);
            btnExit.setOnClickListener(view1 -> {
                dialog.dismiss();
            });

            TextView btnDeleteAll = dialog.findViewById(R.id.btnDeleteAll);
            btnDeleteAll.setOnClickListener(view1 -> {
                cartDetailDAO.clear();
                dialog.dismiss();
            });

            dialog.show();
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(ProfileActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });

        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(ProfileActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
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
                bitmap = BitmapFactory.decodeStream(inputStream);
                imvImaAva.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                user.setImg(byteArrayOutputStream.toByteArray());
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
