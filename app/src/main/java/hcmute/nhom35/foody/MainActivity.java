package hcmute.nhom35.foody;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.NhomMonDAO;
import adapter.CartAdapter;
import adapter.CuaHangAdapter;
import adapter.FoodAdapter1;
import adapter.NhomMonAdapter;
import database.database;
import models.CTCuaHang;
import models.CartDetail;
import models.CuaHang;
import database.GetDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFood;

    private RecyclerView recyclerViewFood1;
    private CuaHangAdapter cuaHangAdapter;

    private RecyclerView recyclerViewCate;
    private NhomMonAdapter cateAdapterl;

    ListView listViewFood;
    ArrayList<CTCuaHang> arrayFood;
    CuaHangAdapter adapter;
    CartAdapter cartAdapter;

    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));
    NhomMonDAO nhomMonDAO = new NhomMonDAO(new database(this));
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));

    ImageView btncart, btnProfile, btnRegisterRes, btnBill;
    TextView btnPay;
    ListView listMon;

    TextView totalCart;

    EditText eSearch;

    Button btnHoaDon;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        int idCate = 0;

        btncart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.profileBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnBill = findViewById(R.id.btnBill);

        Intent intent = getIntent();

        idCate = intent.getIntExtra("position", 0);

        recyclerViewCate = findViewById(R.id.rcv_category);

        cateAdapterl = new NhomMonAdapter(this);

        LinearLayoutManager linearLayoutManagerCate = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewCate.setLayoutManager(linearLayoutManagerCate);

        cateAdapterl.setData(nhomMonDAO.getNhomMon());

        recyclerViewCate.setAdapter(cateAdapterl);

        listViewFood = (ListView) findViewById(R.id.list_food);
        arrayFood = new ArrayList<>();

        if(idCate == 0){
            adapter = new CuaHangAdapter(this, R.layout.list_item_food, cuaHangDAO.getAllCuaHang());
        } else {
            adapter = new CuaHangAdapter(this, R.layout.list_item_food, cuaHangDAO.getCuaHangByType(idCate));
        }

        listViewFood.setAdapter(adapter);

        UIUtils.setListViewHeightBasedOnItems(listViewFood);

        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                CuaHang cuaHang = (CuaHang) adapter.getItem(i);
                intent.putExtra("idCH", cuaHang.getId());
                startActivity(intent);
            }
        });

        Intent intent1 = getIntent();

        int idUser = intent1.getIntExtra("idUser", 1);

        btncart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);
            totalCart = dialog.findViewById(R.id.totalCart);
            List<CartDetail> cartDetailList = cartDetailDAO.getAllCart();
            cartAdapter = new CartAdapter(this, R.layout.cart_item, cartDetailList);

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
                    Intent intent2 = new Intent(MainActivity.this, MapsActivity.class);
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
            Intent intent3 = new Intent(MainActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });

        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(MainActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });

        eSearch = findViewById(R.id.eSearch);
        eSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter = new CuaHangAdapter(MainActivity.this, R.layout.list_item_food, cuaHangDAO.getCuaHangByNameMon(eSearch.getText().toString()));
                listViewFood.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public static int getHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}