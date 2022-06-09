package adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hcmute.nhom35.foody.MainActivity;
import hcmute.nhom35.foody.R;
import models.NhomMon;

public class NhomMonAdapter extends RecyclerView.Adapter<NhomMonAdapter.FoodHolder> {
    private Context mContext;
    private List<NhomMon> mfoods;

    int[] listCate = {R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6};

    public NhomMonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<NhomMon> list){
        this.mfoods = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NhomMonAdapter.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new NhomMonAdapter.FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        NhomMon fd = mfoods.get(position);
        if(fd == null){
            return;
        }


        holder.imgFood.setImageResource(listCate[position]);

        holder.txtName.setText(fd.getName());
        holder.cateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("position", fd.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mfoods != null){
            return mfoods.size();
        }
        return 0;
    }

    public class FoodHolder extends RecyclerView.ViewHolder{

        private ImageView imgFood;
        private TextView txtName;
        private LinearLayout cateItem;

        public FoodHolder(@NonNull View itemView) {

            super(itemView);

            imgFood = itemView.findViewById(R.id.img_cate);
            txtName = itemView.findViewById(R.id.cate_name);
            cateItem = itemView.findViewById(R.id.cateItem);

        }
    }
}
