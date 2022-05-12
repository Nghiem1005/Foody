package hcmute.nhom35.foody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import models.UserAddress;

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<UserAddress> addressList;

    public AddressAdapter(Context context, int layout, List<UserAddress> addressList) {
        this.context = context;
        this.layout = layout;
        this.addressList = addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
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
        TextView txtAddress;
        EditText editAddress;
        ImageView imgDel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtAddress = view.findViewById(R.id.txtNameAddress);
            holder.editAddress = view.findViewById(R.id.editDescriptionAddress);
            holder.imgDel = view.findViewById(R.id.imgDelAddress);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        UserAddress address = addressList.get(i);

       /* holder.txtAddress.setText(address.getName());
        holder.editAddress.setText(address.getDescription());*/

        return view;
    }
}
