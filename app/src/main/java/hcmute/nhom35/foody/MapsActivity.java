package hcmute.nhom35.foody;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.CTHoaDonDAO;
import DAO.CartDetailDAO;
import DAO.HoaDonDAO;
import DAO.UserAddressDAO;
import DAO.UserDAO;
import adapter.CartAdapter;
import database.database;
import food.food;
import food.FoodBookingAdapter;
import hcmute.nhom35.foody.databinding.ActivityMapsBinding;
import models.CTCuaHang;
import models.CTHoaDon;
import models.CartDetail;
import models.HoaDon;
import models.UserAddress;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    ListView listViewFood;
    ArrayList<food> arrayFood;
    CartAdapter adapter;

    TextView txtUsername, txtAddress, txtTotal;

    Button btnPayment;

    UserDAO userDAO = new UserDAO(new database(this));
    UserAddressDAO userAddressDAO = new UserAddressDAO(new database(this));
    CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(this));
    HoaDonDAO hoaDonDAO = new HoaDonDAO(new database(this));
    CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO(new database(this));
    CartDetailDAO cartDetailDAO = new CartDetailDAO(new database(this));

    int total = 0;

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

        Intent intent = getIntent();
        int idUser = intent.getIntExtra("idUser", -1);
        List<CartDetail> cartDetailList = (List<CartDetail>) intent.getSerializableExtra("idMon");

        txtUsername.setText(userDAO.getUserById(idUser).getUserName());
        txtAddress.setText(userAddressDAO.getUserAddressById(idUser).get(0).getDescriptions());

        listViewFood = (ListView) findViewById(R.id.list_food_booking);
        arrayFood = new ArrayList<>();

        adapter = new CartAdapter(this, R.layout.list_item_booking, cartDetailList);


        listViewFood.setAdapter(adapter);



        for(int i = 0 ; i <cartDetailList.size(); i++){
            String value = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice();
            String[] result = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().split(",");
            System.out.println("Góa" + "10,000".split(",")[1]);
            System.out.println("re" + result.length );
            int price = Integer.valueOf(ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getIdMon()).getPrice().substring(0, 2));
            total = total + (price * cartDetailList.get(i).getQuantity());
        }

        txtTotal.setText(String.valueOf(total) + ",000");


        UIUtils.setListViewHeightBasedOnItems(listViewFood);

        btnPayment = findViewById(R.id.btnPayment);

        int finalTotal = total;
        btnPayment.setOnClickListener(view -> {

            int totalHoaDon = hoaDonDAO.getAllHoaDon().size();

            hoaDonDAO.insert(new HoaDon(totalHoaDon + 1, idUser, String.valueOf(LocalDateTime.now()), total));

            for (int i=0; i<cartDetailList.size(); i++){
                ctHoaDonDAO.insert(new CTHoaDon(totalHoaDon + 1, cartDetailList.get(i).getIdMon(), cartDetailList.get(i).getIdCH(), cartDetailList.get(i).getQuantity()));
                cartDetailList.remove(i);
            }
            cartDetailDAO.clear();
            Toast.makeText(this, "THành công", Toast.LENGTH_SHORT).show();
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

    private void getData(){

        arrayFood.add(new food(R.drawable.a1, "Food 1", "Quá ngon", 100000, 12));
        arrayFood.add(new food(R.drawable.a2, "Food 2", "Sử dụng thực phẩm sạch", 1903244, 2));
        arrayFood.add(new food(R.drawable.a3, "Food 3", "Bổ sung dinh dưỡng cho buổi sáng", 239223, 1));
        arrayFood.add(new food(R.drawable.a4, "Food 4", "Tốt cho sức khỏe", 233293, 3));
        arrayFood.add(new food(R.drawable.a5, "Food 5", "Hương thơm nồng nàn",585534, 1));
        arrayFood.add(new food(R.drawable.a6, "Food 6", "Vị ngon thanh đạm",4839884, 2));
        arrayFood.add(new food(R.drawable.a7, "Food 7", "Một buổi sáng tuyệt vời",438483, 4));
        adapter.notifyDataSetChanged();
    }


}