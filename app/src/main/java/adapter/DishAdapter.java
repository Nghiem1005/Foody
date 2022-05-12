
package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DAO.CuaHangDAO;
import DAO.MonAnDAO;
import database.database;
import hcmute.nhom35.foody.R;
import models.CTCuaHang;
import models.CTHoaDon;

public class DishAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CTCuaHang> dishList;

    public DishAdapter(Context context, int layout, List<CTCuaHang> dishList) {
        this.context = context;
        this.layout = layout;
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
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
        TextView txtName, txtPrice;
        ImageView imgAVT, imgEdit, imgDel;
        CheckBox cbShow;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txtDishName);
            holder.txtPrice = (TextView) view.findViewById(R.id.txtDishPrice);
            holder.imgAVT = (ImageView) view.findViewById(R.id.imgAVT);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imgEditDish);
            holder.imgDel = (ImageView) view.findViewById(R.id.imgDelDish);
            holder.cbShow = (CheckBox) view.findViewById(R.id.cbShowDish);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        CTCuaHang dish = dishList.get(i);

        MonAnDAO monAnDAO = new MonAnDAO(new database(context));

        byte[] img = dish.getImgage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);


        holder.imgAVT.setImageBitmap(bitmap);
        holder.txtName.setText(monAnDAO.getMonAnByIdMonAn(dish.getIdMon()).getName());
        holder.txtPrice.setText("Price : " + dish.getPrice() + " VND");

        return view;
    }
}

