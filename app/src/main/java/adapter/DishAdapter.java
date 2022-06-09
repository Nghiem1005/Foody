
package adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import DAO.CuaHangDAO;
import DAO.MonAnDAO;
import database.database;
import hcmute.nhom35.foody.R;
import models.CTCuaHang;
import models.CTHoaDon;
import models.MonAn;

public class DishAdapter extends BaseAdapter {
    int REQUEST_CODE_FOLDER = 123;

    private Context context;
    private int layout;
    private List<CTCuaHang> dishList;

    ImageView imvImgDish;

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
            //holder.imgEdit = (ImageView) view.findViewById(R.id.imgEditDish);
            holder.imgDel = (ImageView) view.findViewById(R.id.imgDelDish);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        CTCuaHang dish = dishList.get(i);

        MonAnDAO monAnDAO = new MonAnDAO(new database(context));

        byte[] img = dish.getImgage();
        Bitmap bitmap = null;
        if(img != null){
            bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        }

        holder.imgAVT.setImageBitmap(bitmap);
        holder.txtName.setText(monAnDAO.getMonAnByIdMonAn(dish.getIdMon()).getName());
        holder.txtPrice.setText("Price : " + dish.getPrice() + " VND");


        return view;
    }
}

