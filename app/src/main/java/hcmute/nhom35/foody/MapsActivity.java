package hcmute.nhom35.foody;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CTHoaDonDAO;
import DAO.CartDetailDAO;
import DAO.CuaHangDAO;
import DAO.HoaDonDAO;
import DAO.UserAddressDAO;
import DAO.UserDAO;
import adapter.CartAdapter;
import database.database;
import hcmute.nhom35.foody.databinding.ActivityMapsBinding;
import models.CTCuaHang;
import models.CTHoaDon;
import models.CartDetail;
import models.CuaHang;
import models.HoaDon;
import models.UserAddress;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    ListView listViewFood;
    CartAdapter adapter;

    TextView txtUsername, txtAddress, txtTotal;

    Button btnPayment;

    UserDAO userDAO = new UserDAO(new database(this));
    UserAddressDAO userAddressDAO = new UserAddressDAO(new database(this));
    HoaDonDAO hoaDonDAO = new HoaDonDAO(new database(this));
    CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO(new database(this));

    ImageView btnHome, btncart, btnRegisterRes, btnBill, btnProfile;
    TextView totalCart;
    TextView btnPay;
    ListView listMon;
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(this));
    CartAdapter cartAdapter;

    int total = 0;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtAddress = findViewById(R.id.txtAddress);
        txtUsername = findViewById(R.id.txtUsername);
        txtTotal = findViewById(R.id.txtTotal);

        btnHome = findViewById(R.id.homeBtn);
        btncart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.profileBtn);
        btnRegisterRes = findViewById(R.id.btnRegisterRes);
        btnBill = findViewById(R.id.btnBill);

        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idUser", -1);
        List<CartDetail> cartDetailList = (List<CartDetail>) intent.getSerializableExtra("idMon");

        txtUsername.setText(userDAO.getUserById(idUser).getUserName());
        if(userAddressDAO.getUserAddressById(idUser).size() == 0){
            Toast.makeText(this, "Bạn chưa có địa chỉ nhận hàng. Vui long nhập địa chỉ nhận hàng." , Toast.LENGTH_SHORT).show();
            txtAddress.setText("Chưa có.");
        } else {
            txtAddress.setText(userAddressDAO.getUserAddressById(idUser).get(0).getDescriptions());
        }

        listViewFood = (ListView) findViewById(R.id.list_food_booking);

        adapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailList);

        listViewFood.setAdapter(adapter);

        for(int i = 0 ; i <cartDetailList.size(); i++){
            String value = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice();
            String[] result = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().split(",");
            int price = Integer.valueOf(ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().substring(0, 2));
            total = total + (price * cartDetailList.get(i).getQuantity());
        }

        txtTotal.setText(String.valueOf(total) + ",000");


        UIUtils.setListViewHeightBasedOnItems(listViewFood);

        btnPayment = findViewById(R.id.btnPayment);

        int finalTotal = total;
        btnPayment.setOnClickListener(view -> {

            int totalHoaDon = hoaDonDAO.getAllHoaDon().size();

            hoaDonDAO.insert(new HoaDon(totalHoaDon + 1, String.valueOf(LocalDate.now()), total, idUser));

            for (int i=0; i<cartDetailList.size(); i++){
                ctHoaDonDAO.insert(new CTHoaDon(totalHoaDon + 1, cartDetailList.get(i).getIdMon(), cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getQuantity()));
                cartDetailList.remove(i);
            }
            cartDetailDAO.clear();
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        });
        btnBill.setOnClickListener(view -> {
            Intent intent2 = new Intent(MapsActivity.this, BillActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });


        btncart.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_cart);
            listMon = dialog.findViewById(R.id.list_food_cart);
            totalCart = dialog.findViewById(R.id.totalCart);
            /*List<CartDetail> cartDetailList = cartDetailDAO.getAllCart();*/
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
                    Intent intent2 = new Intent(MapsActivity.this, MapsActivity.class);
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
            Intent intent3 = new Intent(MapsActivity.this, ProfileActivity.class);
            intent3.putExtra("idUser", idUser);
            startActivity(intent3);
        });

        btnHome.setOnClickListener(view -> {
            Intent intent2 = new Intent(MapsActivity.this, MainActivity.class);
            intent2.putExtra("idUser", idUser);
            startActivity(intent2);
        });

        btnRegisterRes.setOnClickListener(view -> {
            Intent intent2 = new Intent(MapsActivity.this, RegisterRestaurantActivity.class);
            intent2.putExtra("idUser", idUser);


            CuaHang cuaHang = cuaHangDAO.getCuaHangByUserId(idUser);
            if(cuaHang != null){
                intent.putExtra("idCH", cuaHang.getId());
            }
            startActivity(intent2);
        });

    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}