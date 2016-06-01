package com.simon.demoswiperefreshlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xw on 2016/6/1.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> list = null;
    private Context context;

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adaper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindViews(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<String> addList) {
        int index = list.size();
        list.addAll(addList);
        notifyItemRangeInserted(index, index + addList.size());
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.item_tv_name)
        TextView itemTvName;
        @BindView (R.id.item_btn_click)
        TextView itemBtnClick;

        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final String nameStr) {
            itemTvName.setText(nameStr);
            itemBtnClick.setText(nameStr);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了 " + nameStr, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
