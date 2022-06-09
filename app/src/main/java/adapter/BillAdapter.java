package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DAO.CTHoaDonDAO;
import DAO.CuaHangDAO;
import DAO.HoaDonDAO;
import database.database;
import hcmute.nhom35.foody.R;
import models.CTHoaDon;
import models.CuaHang;
import models.HoaDon;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HoaDon> billList;




    public BillAdapter(Context context, int layout, List<HoaDon> billList) {
        this.context = context;
        this.layout = layout;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtRestaurant, txtAddress, txtBillNumber, txtBillDay;
        ListView listBillDetail;
        ImageView imgBill;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CuaHangDAO cuaHangDAO = new CuaHangDAO(new database(context));
        CTHoaDonDAO ctHoaDonDAO = new CTHoaDonDAO(new database(context));

        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtRestaurant = (TextView) view.findViewById(R.id.bill_restaurant_name);
            holder.txtAddress = (TextView) view.findViewById(R.id.bill_address_restaurant);
            holder.txtBillNumber = (TextView) view.findViewById(R.id.bill_number);
            holder.txtBillDay = (TextView) view.findViewById(R.id.bill_day);
            holder.listBillDetail = (ListView) view.findViewById(R.id.list_food_in_bill);
            holder.imgBill = (ImageView) view.findViewById(R.id.image_bill);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        HoaDon hd = billList.get(i);
        List<CTHoaDon> list = new ArrayList<>();

        System.out.println(hd);

        if(ctHoaDonDAO.getCTHDById(hd.getId()).size() > 0){
            list = ctHoaDonDAO.getCTHDById(hd.getId());
        }
        System.out.println(list.get(0).getResID());

        CuaHang cuaHang = cuaHangDAO.getCuaHangById(list.get(0).getResID());

        holder.txtBillDay.setText(hd.getDay());
        System.out.println(hd.getDay());
        holder.txtBillNumber.setText(String.valueOf(hd.getId()));
        holder.txtRestaurant.setText(cuaHang.getName());
        holder.txtAddress.setText(cuaHang.getAddress());
        if(cuaHang.getImg() != null){
            byte[] img = cuaHang.getImg();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0 , img.length);
            holder.imgBill.setImageBitmap(bitmap);
        }

        BillDetailAdapter adapter = new BillDetailAdapter(context, R.layout.list_item_food, list);
        holder.listBillDetail.setAdapter(adapter);


        return view;
    }
}
