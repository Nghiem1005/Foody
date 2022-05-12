package food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.nhom35.foody.R;

public class FoodAdapterImgPrice extends RecyclerView.Adapter<FoodAdapterImgPrice.FoodImgPriceHolder> {

    private Context mContext;
    private List<food> mfoods;

    public FoodAdapterImgPrice(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<food> list){
        this.mfoods = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodImgPriceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_food_restaurant, parent,false);
        return new FoodImgPriceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodImgPriceHolder holder, int position) {
        food fd = mfoods.get(position);
        if(fd == null){
            return;
        }

        holder.imgFoodRes.setImageResource(fd.getId());
        holder.txtPriceRes.setText(String.valueOf(fd.getPrice()));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if(mfoods != null){
            return mfoods.size();
        }
        return 0;
    }

    public class FoodImgPriceHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoodRes;
        private TextView txtPriceRes;

        public FoodImgPriceHolder(@NonNull View itemView) {

            super(itemView);

            imgFoodRes = itemView.findViewById(R.id.img_food_restaurant);
            txtPriceRes = itemView.findViewById(R.id.food_price_restaurant);

        }
    }
}
