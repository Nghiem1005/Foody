package dish_sub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import food.food;
import hcmute.nhom35.foody.R;

public class dish_sub_adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<dish_sub> dish_subList;

    public dish_sub_adapter(Context context, int layout, List<dish_sub> dish_subList) {
        this.context = context;
        this.layout = layout;
        this.dish_subList = dish_subList;
    }

    @Override
    public int getCount() {
        return dish_subList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    private class ViewHolder{
        TextView txtName;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        dish_sub_adapter.ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.dish_sub_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        dish_sub fd = dish_subList.get(i);

        holder.txtName.setText(fd.getName());
        return view;
    }
}
