
package hcmute.nhom35.foody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CuaHangDAO;
import DAO.MonAnDAO;
import adapter.DishAdapter;
import database.database;
import models.CTCuaHang;
import models.CuaHang;

public class RegisterRestaurantActivity extends AppCompatActivity {
    DishAdapter dishAdapter;
    Button btnSaveRestaurant;
    ListView lvDish;
    ImageView btnAddDish;

    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));

    int REQUEST_CODE_ADD = 123;

   List<CTCuaHang> arraylistDish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        ActionBar actionBar = getSupportActionBar();

        actionBar.hide();

        btnAddDish = (ImageView) findViewById(R.id.btnAddDish);

        btnAddDish.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterRestaurantActivity.this, AddDishActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        lvDish = findViewById(R.id.lvDish);

        btnSaveRestaurant = findViewById(R.id.btnSaveRestaurant);
        /*arraylistDish = ctCuaHangDAO.getAll();
        dishAdapter = new DishAdapter(this, R.layout.dish_item, arraylistDish);

        lvDish.setAdapter(dishAdapter);*/

        /*Intent intent = getIntent();
        System.out.println(intent.getIntExtra("i", 0));
        String name = intent.getStringExtra("name");
        String des = intent.getStringExtra("des");
        String price = intent.getStringExtra("price");
        byte[] img = intent.getByteArrayExtra("img");

        System.out.println("Đay la name" + name);

        if(name != null){
            System.out.println("name" + name);
            arraylistDish.add(new CTCuaHang(-1, -1, des, price, img));

            dishAdapter = new DishAdapter(this, R.layout.dish_item, arraylistDish);

            lvDish.setAdapter(dishAdapter);
        }*/



        btnSaveRestaurant.setOnClickListener(view -> {
            int totalCH = cuaHangDAO.getAllCuaHang().size();
            //ctCuaHangDAO.insert(totalCH + 1, );
            //cuaHangDAO.insert(totalCH + 1, );
            Intent intent1 = new Intent(RegisterRestaurantActivity.this, MapsActivity.class);
            startActivity(intent1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_ADD && requestCode == RESULT_OK && data!= null){
            Intent intent = getIntent();
            System.out.println(intent.getIntExtra("i", 0));
            String name = intent.getStringExtra("name");
            String des = intent.getStringExtra("des");
            String price = intent.getStringExtra("price");
            byte[] img = intent.getByteArrayExtra("img");

            System.out.println("Đay la name" + name);

            if(name != null){
                System.out.println("name" + name);
                arraylistDish.add(new CTCuaHang(-1, -1, des, price, img));

                dishAdapter = new DishAdapter(this, R.layout.dish_item, arraylistDish);

                lvDish.setAdapter(dishAdapter);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
