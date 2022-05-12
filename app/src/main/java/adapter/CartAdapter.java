package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.MonAnDAO;
import database.database;
import food.food;
import hcmute.nhom35.foody.R;
import models.CTCuaHang;
import models.CartDetail;
import models.MonAn;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CartDetail> foodList;


    public CartAdapter(Context context, int layout, List<CartDetail> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }


    @Override
    public int getCount() {
        return foodList.size();
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
        TextView txtName, txtInfo, txtPrice, txtQuantity;
        ImageView imgFood;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(context));
        MonAnDAO monAnDAO = new MonAnDAO(new database(context));
        CartAdapter.ViewHolder holder;

        if(view == null){
            holder = new CartAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_item_booking);
            holder.txtInfo = (TextView) view.findViewById(R.id.txt_info_item_booking);
            holder.txtPrice = (TextView) view.findViewById(R.id.item_food_price_booking);
            holder.txtQuantity = (TextView) view.findViewById(R.id.item_food_quantity_booking);
            holder.imgFood = (ImageView) view.findViewById(R.id.img_food_item_booking);
            view.setTag(holder);
        } else {
            holder = (CartAdapter.ViewHolder) view.getTag();
        }

        CartDetail fd = foodList.get(i);
        CTCuaHang ctCuaHang = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(fd.getIdCH(),fd.getIdMon());
        MonAn monAn = monAnDAO.getMonAnByIdMonAn(fd.getIdMon());

        holder.txtName.setText(monAn.getName());
        holder.txtInfo.setText(ctCuaHang.getDescription());
        holder.txtPrice.setText(String.valueOf(ctCuaHang.getPrice()));
        holder.txtQuantity.setText(String.valueOf(fd.getQuantity()));
        //holder.imgFood.setImageResource(fd.getId());

        if(i == foodList.size() - 1){
            view.setBackground(null);
        }
        return view;
    }
}
