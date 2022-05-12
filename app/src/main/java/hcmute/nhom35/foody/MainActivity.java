package hcmute.nhom35.foody;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.NhomMonDAO;
import adapter.CartAdapter;
import adapter.CuaHangAdapter;
import adapter.FoodAdapter1;
import adapter.NhomMonAdapter;
import category.Category;
import category.CategoryAdapter;
import database.database;
import food.food;
import food.foodAdapter;
import models.CTCuaHang;
import models.CuaHang;
import database.GetDatabase;

public class MainActivity extends AppCompatActivity {

    // creating object of ViewPager


    // images array




    private RecyclerView recyclerViewFood;
    private foodAdapter foodAdapterl;

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

    LinearLayout btncart, btnProfile, btnRegisterRestaurant;
    Button btnPay;
    ListView listMon;

    Button btnHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        int idCate = 0;

        btncart = findViewById(R.id.btnCart);



        /*// Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);*/

        /*recyclerViewFood = findViewById(R.id.rcv_food);

        foodAdapterl = new foodAdapter(this);

        LinearLayoutManager linearLayoutManagerFood = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewFood.setLayoutManager(linearLayoutManagerFood);*/
        //foodAdapterl.setData(getListFood());

        //recyclerViewFood.setAdapter(foodAdapterl);

        //Category

        Intent intent = getIntent();

        idCate = intent.getIntExtra("position", 0);

        recyclerViewCate = findViewById(R.id.rcv_category);

        cateAdapterl = new NhomMonAdapter(this);

        LinearLayoutManager linearLayoutManagerCate = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewCate.setLayoutManager(linearLayoutManagerCate);

        cateAdapterl.setData(nhomMonDAO.getNhomMon());

        recyclerViewCate.setAdapter(cateAdapterl);



        /*//Food
        recyclerViewFood1 = findViewById(R.id.listFood);

        foodAdapterl1 = new foodAdapter(this);

        LinearLayoutManager linearLayoutManagerFood1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewFood1.setLayoutManager(linearLayoutManagerFood1);
        foodAdapterl1.setData(getListFood());

        recyclerViewFood1.setAdapter(foodAdapterl1);*/

        //Load 7 cua hang

        listViewFood = (ListView) findViewById(R.id.list_food);
        arrayFood = new ArrayList<>();

        if(idCate == 0){
            adapter = new CuaHangAdapter(this, R.layout.list_item_food, cuaHangDAO.get7CuaHang());
        } else {
            System.out.println(idCate);
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

            cartAdapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailDAO.getAllCart());

            listMon.setAdapter(cartAdapter);

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
            dialog.show();
        });
        btnProfile = findViewById(R.id.profileBtn);

        btnProfile.setOnClickListener(view -> {
            Intent intent3 = new Intent(MainActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        //btnHoaDon = findViewById(R.id.btnSupport);

        btnRegisterRestaurant = findViewById(R.id.btnRegisterRes);

        btnRegisterRestaurant.setOnClickListener(view -> {

        });


    }
    public void DialogCart(){
        /*Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cart);
        ListView listMon = findViewById(R.id.list_food_cart);



        *//*SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Set<String> set = map.keySet();
        for(String key : set){

        }*//*
        CartAdapter adapter2 = new CartAdapter(this, R.layout.list_item_food, cartDetailDAO.getAllCart());
        System.out.println(cartDetailDAO.getAllCart().get(0).getIdMon());
        listMon.setAdapter(adapter2);
        dialog.show();*/
    }

    private List<Category> getListCate(){
        List<Category> list = new ArrayList<Category>();
        list.add(new Category(R.drawable.b1, "Phở"));
        list.add(new Category(R.drawable.b2, "Bánh mì"));
        list.add(new Category(R.drawable.b3, "Pizza"));
        list.add(new Category(R.drawable.b4, "Sushi"));
        list.add(new Category(R.drawable.b5, "Chicken"));
        list.add(new Category(R.drawable.b6, "Coffe"));

        return list;
    }

}