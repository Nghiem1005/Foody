package adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.nhom35.foody.R;
import models.UserAddress;

public class UserAddressAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<UserAddress> addressList;

    public UserAddressAdapter(Context context, int layout, List<UserAddress> addressList) {
        this.context = context;
        this.layout = layout;
        this.addressList = addressList;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public UserAddress getItem(int i) {
        return addressList.get(i);
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

        holder.txtAddress.setText("Địa chỉ " + (i + 1));
        holder.editAddress.setText(addressList.get(i).getDescriptions());
        System.out.println(addressList.get(i).getDescriptions());

        holder.editAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addressList.get(i).setDescriptions(holder.editAddress.getText().toString());
            }
        });

        return view;
    }


}
