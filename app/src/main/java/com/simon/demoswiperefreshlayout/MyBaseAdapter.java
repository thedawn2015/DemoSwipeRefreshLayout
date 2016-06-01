package com.simon.demoswiperefreshlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xw on 2016/6/1.
 */
public class MyBaseAdapter extends BaseAdapter {

    private List<String> list = null;
    private Context context;

    public MyBaseAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void addItems(List<String> addList) {
        list.addAll(addList);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_adaper, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.bindViews(list.get(position));

        return convertView;
    }

    class ViewHolder {
        @BindView (R.id.item_btn_click)
        Button itemBtnClick;
        @BindView (R.id.item_tv_name)
        TextView itemTvName;

        private View itemView;

        ViewHolder(View itemView) {
            super();
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
