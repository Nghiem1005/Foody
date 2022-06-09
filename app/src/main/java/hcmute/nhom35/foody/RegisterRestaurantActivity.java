
package hcmute.nhom35.foody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import DAO.MonAnDAO;
import adapter.CartAdapter;
import adapter.DishAdapter;
import database.database;
import models.CTCuaHang;
import models.CartDetail;
import models.CuaHang;
import models.MonAn;

public class RegisterRestaurantActivity extends AppCompatActivity {
    int REQUEST_CODE_FOLDER = 123;
    int REQUEST_CODE_AVA = 456;

    DishAdapter dishAdapter;
    Button btnSaveRestaurant;
    ListView lvDish;
    ImageView btnAddDish;
    ImageView imvImgDish, imvResAva;
    Bitmap bitmapRes, bitmapCTCH;

    TextView btnChangeAva;

    ImageView btnHome, btncart, btnProfile, btnBill;
    TextView totalCart;
    TextView btnPay;
    ListView listMon;
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    CartAdapter cartAdapter;

    EditText eResName, eResAddress, eResTimeOpen, eResTimeClose;
    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));

    int REQUEST_CODE_ADD = 456;

   List<CTCuaHang> arraylistDish;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        ActionBar actionBar = getSupportActionBar();

        actionBar.hide();

        eResName = findViewById(R.id.eResName);
        eResAddress = findViewById(R.id.eResAddress);
        eResTimeOpen = findViewById(R.id.eResTimeOpen);
        eResTimeClose = findViewById(R.id.eResTimeClose);

        btncart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.profileBtn);
        btnBill = findViewById(R.id.btnBill);
        btnHome = findViewById(R.id.homeBtn);

        lvDish = findViewById(R.id.lvDish);

        imvResAva = findViewById(R.id.imvResAva);

        btnAddDish = (ImageView) findViewById(R.id.btnAddDish);

        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idUser", 1);
        if (cuaHangDAO.getCuaHangByIdUser(idUser) == null){
            arraylistDish = new ArrayList<>();
        } else {
            arraylistDish= ctCuaHangDAO.getCuaHangByIdCuaHang(cuaHangDAO.getCuaHangByIdUser(idUser).getId());
        }

        int dishInResQuantity = arraylistDish.size();

        dishAdapter = new DishAdapter(this, R.layout.dish_item, arraylistDish);
        lvDish.setAdapter(dishAdapter);

        CuaHang cuaHang = new CuaHang();
        if(cuaHangDAO.getCuaHangByIdUser(idUser) != null){
            cuaHang = cuaHangDAO.getCuaHangByIdUser(idUser);
        }


        eResName.setText(cuaHang.getName());
        eResAddress.setText(cuaHang.getAddress());
        eResTimeOpen.setText(cuaHang.getTimeOpen());
        eResTimeClose.setText(cuaHang.getTimeClose());

        if(cuaHang.getImg() != null){
            byte[] img = cuaHang.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imvResAva.setImageBitmap(bitmap);
        }

        btnAddDish.setOnClickListener(view -> {

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add_dish);

            EditText eDishName = dialog.findViewById(R.id.eDishName);
            EditText eDishDes = dialog.findViewById(R.id.eDishDes);
            EditText eDishPrice = dialog.findViewById(R.id.eDishPrice);
            EditText eDishType = dialog.findViewById(R.id.eDishType);
            ImageButton ibtnFolder = dialog.findViewById(R.id.ibtnFolder);

            imvImgDish = dialog.findViewById(R.id.imvImgDish);

            AppCompatButton btnAddDishSub = dialog.findViewById(R.id.btnAddDishSub);

            ibtnFolder.setOnClickListener(view1 -> {

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
            });

            btnAddDishSub.setOnClickListener(view1 -> {
                byte[] img = null;
                if (bitmapCTCH != null){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmapCTCH.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    img = byteArrayOutputStream.toByteArray();

                    if(eDishName.getText().toString() != null) {
                        if (monAnDAO.getMonAnByMonAn(eDishName.getText().toString()) == null){
                            monAnDAO.insert(new MonAn(eDishName.getText().toString(), Integer.valueOf(eDishType.getText().toString())));
                        }
                        arraylistDish.add(new CTCuaHang(-1, monAnDAO.getMonAnByMonAn(eDishName.getText().toString()).getId(), eDishDes.getText().toString(), eDishPrice.getText().toString(), img));
                        dishAdapter = new DishAdapter(this, R.layout.dish_item, arraylistDish);
                        lvDish.setAdapter(dishAdapter);
                    }

                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Bạn chưa có hình ảnh cho món ăn. Yêu cầu bạn chọn hình ảnh cho món ăn", Toast.LENGTH_SHORT).show();
                }




            });
            dialog.show();

        });

        btnHome.setOnClickListener(view -> {
            Intent intent1 = new Intent(RegisterRestaurantActivity.this, MainActivity.class);
            intent1.putExtra("idUser", idUser);
            startActivity(intent1);
        });

        btnSaveRestaurant = findViewById(R.id.btnSaveRestaurant);

        btnSaveRestaurant.setOnClickListener(view -> {
            CuaHang cuaHang1 = cuaHangDAO.getCuaHangByUserId(idUser);
            int totalCH = cuaHangDAO.getAllCuaHang().size();

            byte[] img = null;

            if (bitmapRes != null){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmapRes.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                img = byteArrayOutputStream.toByteArray();
            }

            int idCH = 0;
            if (cuaHang1 == null){
                idCH = totalCH;
                cuaHangDAO.insert(new CuaHang(idCH, eResName.getText().toString(), eResAddress.getText().toString(), eResTimeOpen.getText().toString(), eResTimeClose.getText().toString(), idUser, img));
            } else {
                idCH = cuaHang1.getId();
                cuaHangDAO.update(new CuaHang(idCH, eResName.getText().toString(), eResAddress.getText().toString(), eResTimeOpen.getText().toString(), eResTimeClose.getText().toString(), idUser, img));
            }

            for (int i=dishInResQuantity; i < arraylistDish.size(); i++ ){
                CTCuaHang ctCuaHang = arraylistDish.get(i);
                ctCuaHangDAO.insert(idCH, ctCuaHang.getIdMon(), ctCuaHang.getDescription(), ctCuaHang.getPrice(), ctCuaHang.getImgage());
            }
            Toast.makeText(this, "Lưu thông tin cửa hàng thành công", Toast.LENGTH_SHORT).show();
        });

        btnChangeAva = findViewById(R.id.btnChangeAva);

        btnChangeAva.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_AVA);
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
                }
            totalCart.setText(String.valueOf(total) + ",000");


            btnPay = dialog.findViewById(R.id.btnPay);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(RegisterRestaurantActivity.this, MapsActivity.class);
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

        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(RegisterRestaurantActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });

        btnProfile.setOnClickListener(view -> {
            Intent intent3 = new Intent(RegisterRestaurantActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            if(requestCode == REQUEST_CODE_FOLDER){
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            } else {
                startActivityForResult(intent, REQUEST_CODE_AVA);
            }

        } else {
            Toast.makeText(this, "Bạn không cho phép truy cập vô thư viện ảnh", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmapCTCH = BitmapFactory.decodeStream(inputStream);
                imvImgDish.setImageBitmap(bitmapCTCH);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        } else {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmapRes = BitmapFactory.decodeStream(inputStream);
                imvResAva.setImageBitmap(bitmapRes);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
