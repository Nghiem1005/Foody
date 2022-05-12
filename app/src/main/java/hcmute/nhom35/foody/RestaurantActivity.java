package hcmute.nhom35.foody;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.MonAnDAO;
import adapter.CartAdapter;
import adapter.FoodAdapter1;
import adapter.FoodAdapter2;
import database.database;
import food.food;
import food.FoodAdapterImgPrice;
import models.CTCuaHang;
import models.CartDetail;

public class RestaurantActivity extends Activity {

    public static final String SHARED_PREFS = "sharePrefs";

    private RecyclerView recyclerViewFood;
    private FoodAdapterImgPrice foodAdapterIP;

    FoodAdapter1 adapter;

    ListView listViewFood;
    ArrayList<CTCuaHang> arrayFood;

    CTCuaHangDAO ctCuaHangdao = new CTCuaHangDAO(new database(this));
    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));

    LinearLayout btnCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_restaurant);

        Intent intent = getIntent();

        int idCH = intent.getIntExtra("idCH", 1);

        btnCart = findViewById(R.id.btnCart);

        //recyclerViewFood = findViewById(R.id.rcv_food_restaurant);

        foodAdapterIP = new FoodAdapterImgPrice(this);

        LinearLayoutManager linearLayoutManagerFood = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        //recyclerViewFood.setLayoutManager(linearLayoutManagerFood);
        //foodAdapterIP.setData(getListFood());

        //recyclerViewFood.setAdapter(foodAdapterIP);

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
            DialogCart();
        });
    }

    public void DialogCart(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cart);
        ListView listMon = findViewById(R.id.list_food_cart);



        /*SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Set<String> set = map.keySet();
        for(String key : set){

        }*/
        CartAdapter adapter2 = new CartAdapter(this, R.layout.list_item_food, cartDetailDAO.getAllCart());
        System.out.println(cartDetailDAO.getAllCart().get(0).getIdMon());
        listMon.setAdapter(adapter2);
        dialog.show();
    }

}
