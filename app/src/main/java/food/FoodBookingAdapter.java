package food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hcmute.nhom35.foody.R;

public class FoodBookingAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<food> foodList;

    public FoodBookingAdapter(Context context, int layout, List<food> foodList) {
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
        FoodBookingAdapter.ViewHolder holder;

        if(view == null){
            holder = new FoodBookingAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_item_booking);
            holder.txtInfo = (TextView) view.findViewById(R.id.txt_info_item_booking);
            holder.txtPrice = (TextView) view.findViewById(R.id.item_food_price_booking);
            holder.txtQuantity = (TextView) view.findViewById(R.id.item_food_quantity_booking);
            holder.imgFood = (ImageView) view.findViewById(R.id.img_food_item_booking);
            view.setTag(holder);
        } else {
            holder = (FoodBookingAdapter.ViewHolder) view.getTag();
        }

        food fd = foodList.get(i);

        holder.txtName.setText(fd.getName());
        holder.txtInfo.setText(fd.getInfo());
        holder.txtPrice.setText(String.valueOf(fd.getPrice()));
        holder.txtQuantity.setText(String.valueOf(fd.getQuantity()));
        holder.imgFood.setImageResource(fd.getId());

        if(i == foodList.size() - 1){
            view.setBackground(null);
        }
        return view;
    }
}
