package hcmute.nhom35.foody;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.MonAnDAO;
import adapter.CartAdapter;
import adapter.FoodAdapter1;
import database.database;
import models.CTCuaHang;
import models.CartDetail;
import models.CuaHang;

public class RestaurantActivity extends Activity {

    public static final String SHARED_PREFS = "sharePrefs";

    private RecyclerView recyclerViewFood;

    FoodAdapter1 adapter;
    CartAdapter cartAdapter;

    ListView listViewFood;
    ArrayList<CTCuaHang> arrayFood;
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));

    ImageView btnHome, btnProfile, btnRegisterRes, btnBill;
    TextView btnPay;
    ListView listMon;

    TextView totalCart;

    CTCuaHangDAO ctCuaHangdao = new CTCuaHangDAO(new database(this));
    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));

    ImageView btnCart;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_restaurant);

        Intent intent = getIntent();

        int idCH = intent.getIntExtra("idCH", 1);
        int idUser = intent.getIntExtra("idUser", 1);

        btnCart = findViewById(R.id.btnCart);
        btnBill = findViewById(R.id.btnBill);
        btnProfile = findViewById(R.id.profileBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnHome = findViewById(R.id.homeBtn);

        LinearLayoutManager linearLayoutManagerFood = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);


        //List mon an
        listViewFood = (ListView) findViewById(R.id.list_food_restaurant);
        arrayFood = new ArrayList<>();

        adapter = new FoodAdapter1(this, R.layout.list_item_food, ctCuaHangdao.getCuaHangByIdCuaHang(idCH));

        listViewFood.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        UIUtils.setListViewHeightBasedOnItems(listViewFood);

        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RestaurantActivity.this, DishNameActivity.class);
                CTCuaHang ctCuaHang = (CTCuaHang) adapter.getItem(i);
                intent.putExtra("idCH", ctCuaHang.getIdCH());
                intent.putExtra("idMon", ctCuaHang.getIdMon());
                startActivity(intent);
            }
        });

        btnCart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);
            totalCart = dialog.findViewById(R.id.totalCart);
            List<CartDetail> cartDetailList = cartDetailDAO.getAllCart();
            cartAdapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailDAO.getAllCart());
            dialog.getWindow().setLayout(((getWidth(this) / 100) * 90), LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setGravity(Gravity.END);
            dialog.show();
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
                    Intent intent2 = new Intent(RestaurantActivity.this, MapsActivity.class);
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
            Intent intent3 = new Intent(RestaurantActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        btnHome.setOnClickListener(view -> {
            Intent intent2 = new Intent(RestaurantActivity.this, MainActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(RestaurantActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });

        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(RestaurantActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });
    }
    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
