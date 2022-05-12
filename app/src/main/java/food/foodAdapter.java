package food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom35.foody.R;

public class foodAdapter extends RecyclerView.Adapter<foodAdapter.FoodHolder>{

    private Context mContext;
    private List<food> mfoods;

    public foodAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<food> list){
        this.mfoods = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent,false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        food fd = mfoods.get(position);
        if(fd == null){
            return;
        }

        holder.imgFood.setImageResource(fd.getId());
        holder.txtName.setText(fd.getName());
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

        public FoodHolder(@NonNull View itemView) {

            super(itemView);

            imgFood = itemView.findViewById(R.id.img_food);
            txtName = itemView.findViewById(R.id.food_name);

        }
    }
}
