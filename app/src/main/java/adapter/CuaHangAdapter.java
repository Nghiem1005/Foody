package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import DAO.MonAnDAO;
import database.database;
import hcmute.nhom35.foody.R;
import models.CTCuaHang;
import models.CuaHang;
import models.MonAn;

public class CuaHangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CuaHang> cuaHangList;

    public CuaHangAdapter(Context context, int layout, List<CuaHang> cuaHangList) {
        this.context = context;
        this.layout = layout;
        this.cuaHangList = cuaHangList;
    }

    @Override
    public int getCount() {
        return cuaHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return cuaHangList.get(i);
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
        CuaHangAdapter.ViewHolder holder;
        //MonAnDAO monAnDAO = new MonAnDAO(new database(context));
        if(view == null){
            holder = new CuaHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_item);
            holder.txtInfo = (TextView) view.findViewById(R.id.txt_info_item);
            holder.imgFood = (ImageView) view.findViewById(R.id.img_food_item);
            view.setTag(holder);
        } else {
            holder = (CuaHangAdapter.ViewHolder) view.getTag();
        }

        CuaHang fd = cuaHangList.get(i);

        //MonAn mon = monAnDAO.getMonAnByIdMonAn(fd.getIdMon());

        holder.txtName.setText(fd.getName());
        holder.txtInfo.setText(fd.getAddress());
        /* holder.imgFood.setImageResource(fd.getImgage());*/
        return view;
    }
}
