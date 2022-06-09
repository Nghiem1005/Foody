package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

import DAO.CTCuaHangDAO;
import DAO.MonAnDAO;
import database.database;
import hcmute.nhom35.foody.R;
import models.CTCuaHang;
import models.CTHoaDon;
import models.HoaDon;
import models.MonAn;

public class BillDetailAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CTHoaDon> list;

    public BillDetailAdapter(Context context, int layout, List<CTHoaDon> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView txtName, txtInfo;
        ImageView imgFood;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BillDetailAdapter.ViewHolder holder;
        MonAnDAO monAnDAO = new MonAnDAO(new database(context));
        CTCuaHangDAO ctCuaHangDAO = new CTCuaHangDAO(new database(context));
        if(view == null){
            holder = new BillDetailAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_item);
            holder.txtInfo = (TextView) view.findViewById(R.id.txt_info_item);
            holder.imgFood = (ImageView) view.findViewById(R.id.img_food_item);
            view.setTag(holder);
        } else {
            holder = (BillDetailAdapter.ViewHolder) view.getTag();
        }

        CTHoaDon fd = list.get(i);

        MonAn mon = monAnDAO.getMonAnByIdMonAn(fd.getIdMon());
        CTCuaHang ctCuaHang = ctCuaHangDAO.getCTCuaHangByIdCuaHangMon(fd.getResID(), fd.getIdMon());

        holder.txtName.setText(mon.getName());
        holder.txtInfo.setText(ctCuaHang.getDescription());
        if (ctCuaHang.getImgage() != null){
            byte[] img = ctCuaHang.getImgage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            holder.imgFood.setImageBitmap(bitmap);
        }
        return view;
    }
}
