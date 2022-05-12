package category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import food.food;
import hcmute.nhom35.foody.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{
    private Context mContext;
    private List<Category> mCates;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list){
        this.mCates = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category cate = mCates.get(position);
        if(cate == null){
            return;
        }

        holder.imgCate.setImageResource(cate.getIdCate());
        holder.txtNameCate.setText(cate.getNameCate());
    }

    @Override
    public int getItemCount() {
        if(mCates != null){
            return mCates.size();
        }
        return 0;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        private ImageView imgCate;
        private TextView txtNameCate;


        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            imgCate = itemView.findViewById(R.id.img_cate);
            txtNameCate = itemView.findViewById(R.id.cate_name);
        }
    }
}
