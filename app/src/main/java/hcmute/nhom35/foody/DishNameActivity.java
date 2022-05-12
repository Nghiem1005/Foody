
package hcmute.nhom35.foody;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import DAO.CTCuaHangDAO;
import DAO.CartDetailDAO;
import DAO.MonAnDAO;
import adapter.CartAdapter;
import category.CategoryAdapter;
import database.database;
import dish_sub.dish_sub;
import dish_sub.dish_sub_adapter;
/*import food.FoodAdapter1;*/
import food.food;
import food.foodAdapter;
import models.CTCuaHang;
import models.CartDetail;
import models.MonAn;

public class DishNameActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharePrefs";
    public static final String TEXT = "text";

    ListView listViewDish;
    ArrayList<dish_sub> arrayDish;
    dish_sub_adapter adapter;

    TextView txtDishName, txtDishDes, txtDishPrice;
    Button btnAddQuantity, btnSubQuantity, btnAddCart;

    EditText eQuantity;
    ListView listMon;

    CartAdapter cartAdapter;

    int Quantity = 0;

    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    MonAnDAO monAnDAO = new MonAnDAO(new database(this));
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));

    LinearLayout btncart;

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
            cartDetailDAO.insert(new CartDetail(idMon, idCH, Quantity));
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            //saveCart(String.valueOf(idMon), String.valueOf(idCH), String.valueOf(Quantity), idMon, idCH, Quantity);
        });
        Intent intent1 = getIntent();

        int idUser = intent1.getIntExtra("idUser", 1);
        btncart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);

            cartAdapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailDAO.getAllCart());

            listMon.setAdapter(cartAdapter);

            Button btnpay = dialog.findViewById(R.id.btnPay);

            btnpay.setOnClickListener(view1 -> {
                Intent intent3 = new Intent(DishNameActivity.this, MapsActivity.class);
                intent3.putExtra("idMon", (Serializable) cartDetailDAO.getAllCart());
                intent3.putExtra("idUser", idUser);
                System.out.println(1);
                startActivity(intent3);
            });
            dialog.show();
        });
        /*listViewDish = (ListView) findViewById(R.id.list_dish_sub);
        arrayDish = new ArrayList<>();

        adapter = new dish_sub_adapter(this, R.layout.dish_sub_item, arrayDish);


        listViewDish.setAdapter(adapter);

        getData();

        UIUtils.setListViewHeightBasedOnItems(listViewDish);*/
    }


}





