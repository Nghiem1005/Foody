
package hcmute.nhom35.foody;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.MonAnDAO;
import adapter.CartAdapter;
import database.database;
import models.CTCuaHang;
import models.CartDetail;
import models.CuaHang;
import models.MonAn;

public class DishNameActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharePrefs";
    public static final String TEXT = "text";

    ListView listViewDish;

    TextView txtDishName, txtDishDes, txtDishPrice;
    Button btnAddQuantity, btnSubQuantity, btnAddCart;

    EditText eQuantity;
    int Quantity = 0;

    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    ImageView btnHome, btncart, btnRegisterRes, btnProfile, btnBill;
    TextView totalCart;
    TextView btnPay;
    ListView listMon;
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));
    CartAdapter cartAdapter;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_name_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        txtDishName = (TextView) findViewById(R.id.txtNameDish);
        txtDishDes = (TextView) findViewById(R.id.txtDesDish);
        txtDishPrice = (TextView) findViewById(R.id.txtPriceDish);

        btnAddQuantity = (Button) findViewById(R.id.btnAddQuantity);
        btnSubQuantity = (Button) findViewById(R.id.btnSubQuantity);
        btnAddCart = (Button) findViewById(R.id.btnAddToCart);

        eQuantity = (EditText) findViewById(R.id.eQuantity);

        btncart = findViewById(R.id.btnCart);
        btnBill = findViewById(R.id.btnBill);
        btnProfile = findViewById(R.id.profileBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnHome = findViewById(R.id.homeBtn);

        Intent intent = getIntent();

        int idCH = intent.getIntExtra("idCH", 1);
        int idMon = intent.getIntExtra("idMon", 2);

        CTCuaHang ctCuaHang = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(idCH, idMon);

        MonAn monAn = monAnDAO.getMonAnByIdMonAn(idMon);

        txtDishName.setText(monAn.getName());
        txtDishDes.setText(ctCuaHang.getDescription());
        txtDishPrice.setText(ctCuaHang.getPrice());
        Quantity =  Integer.valueOf(eQuantity.getText().toString());

        btnSubQuantity.setOnClickListener(view -> {
            Quantity -= 1;
            if(Quantity >= 1){
                eQuantity.setText(String.valueOf(Quantity));
            } else {
                eQuantity.setText(String.valueOf(1));
            }

        });

        btnAddQuantity.setOnClickListener(view -> {
            Quantity += 1;
            eQuantity.setText(String.valueOf(Quantity));
        });

        btnAddCart.setOnClickListener(view -> {
            cartDetailDAO.insert(new CartDetail(idCH, idMon, Quantity));
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            //saveCart(String.valueOf(idMon), String.valueOf(idCH), String.valueOf(Quantity), idMon, idCH, Quantity);
        });
        Intent intent1 = getIntent();

        int idUser = intent1.getIntExtra("idUser", 1);
        btncart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);
            totalCart = dialog.findViewById(R.id.totalCart);
            List<CartDetail> cartDetailList = cartDetailDAO.getAllCart();
            cartAdapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailList);

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

            TextView btnpay = dialog.findViewById(R.id.btnPay);

            btnpay.setOnClickListener(view1 -> {
                Intent intent3 = new Intent(DishNameActivity.this, MapsActivity.class);
                intent3.putExtra("idMon", (Serializable) cartDetailDAO.getAllCart());
                intent3.putExtra("idUser", idUser);
                System.out.println(1);
                startActivity(intent3);
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

        btnProfile.setOnClickListener(view -> {
            Intent intent3 = new Intent(DishNameActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        btnHome.setOnClickListener(view -> {
            Intent intent2 = new Intent(DishNameActivity.this, MainActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(DishNameActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });

        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(DishNameActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });
    }


}





