package hcmute.nhom35.foody;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.HoaDonDAO;
import adapter.BillAdapter;
import adapter.CartAdapter;
import database.database;
import models.CartDetail;
import models.CuaHang;
import models.HoaDon;

public class BillActivity extends AppCompatActivity {

    ListView listBill;
    BillAdapter billAdapter;

    List<HoaDon> hoaDonList;

    HoaDonDAO hoaDonDAO = new HoaDonDAO(new database(this));

    ImageView btnHome, btncart, btnRegisterRes, btnProfile;
    TextView totalCart;
    TextView btnPay;
    ListView listMon;
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));
    CartAdapter cartAdapter;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idUser", 1);

        listBill =findViewById(R.id.list_bill);

        btncart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.profileBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnHome = findViewById(R.id.homeBtn);

        hoaDonList = new ArrayList<>();

        billAdapter = new BillAdapter(this, R.layout.item_bill, hoaDonList);

        listBill.setAdapter(billAdapter);


        if(hoaDonDAO.getAllHoaDonByIdUser(idUser) != null){
            hoaDonList = hoaDonDAO.getAllHoaDonByIdUser(idUser);
            billAdapter = new BillAdapter(this, R.layout.item_bill, hoaDonList);

            listBill.setAdapter(billAdapter);
        }

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
                    Intent intent2 = new Intent(BillActivity.this, MapsActivity.class);
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

        btnProfile.setOnClickListener(view -> {
            Intent intent3 = new Intent(BillActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        btnHome.setOnClickListener(view -> {
            Intent intent1 = new Intent(BillActivity.this, MainActivity.class);
            intent1.putExtra("idUser", idUser);
            startActivity(intent1);
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(BillActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });
    }
}