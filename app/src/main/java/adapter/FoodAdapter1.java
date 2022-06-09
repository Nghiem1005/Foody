package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import hcmute.nhom35.foody.MainActivity1;
import hcmute.nhom35.foody.R;
import hcmute.nhom35.foody.RestaurantActivity;
import models.CTCuaHang;
import models.MonAn;

public class FoodAdapter1 extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CTCuaHang> foodList;

    public FoodAdapter1(Context context, int layout, List<CTCuaHang> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        System.out.println("Owr dau" + foodList);
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    private class ViewHolder{
        TextView txtName, txtInfo;
        ImageView imgFood;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        MonAnDAO monAnDAO = new MonAnDAO(new database(context));
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_item);
            holder.txtInfo = (TextView) view.findViewById(R.id.txt_info_item);
            holder.imgFood = (ImageView) view.findViewById(R.id.img_food_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        CTCuaHang fd = foodList.get(i);

        MonAn mon = monAnDAO.getMonAnByIdMonAn(fd.getIdMon());

        holder.txtName.setText(mon.getName());
        holder.txtInfo.setText(fd.getDescription());
        //holder.imgFood.setImageResource(mon.getId());
        if(fd.getImgage() != null){
            byte[] img = fd.getImgage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.imgFood.setImageBitmap(bitmap);
        }

        System.out.println("Owr dau" + holder.txtName.getText().toString());
        return view;
    }
}
