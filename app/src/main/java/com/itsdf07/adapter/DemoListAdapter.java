package com.itsdf07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsdf07.R;
import com.itsdf07.bean.ItemDemoCaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author itsdf07
 * @Time 2019/5/28/028
 */
public class DemoListAdapter extends BaseAdapter {
    private Context mContent;
    private List<ItemDemoCaseBean> mDatas = new ArrayList();

    public DemoListAdapter(Context context) {
        mContent = context;
    }

    public void setData(List<ItemDemoCaseBean> data) {
        if (null == mDatas) {
            mDatas = new ArrayList<>();
        }
        if (!mDatas.isEmpty()) {
            mDatas.clear();
        }
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mDatas ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return null == mDatas ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContent);
            convertView = inflater.inflate(R.layout.layout_item_demo, null, true);
            initItemView(convertView, viewHolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mItemIcon.setImageResource(mDatas.get(position).getIcon());
        viewHolder.mItemTitle.setText(mDatas.get(position).getTitle());
        viewHolder.mItemDesc.setText(mDatas.get(position).getDesc());
        return convertView;
    }

    private void initItemView(View convertView, ViewHolder viewHolder) {
        viewHolder.mItemIcon = (ImageView) convertView.findViewById(R.id.id_item_icon);
        viewHolder.mItemTitle = (TextView) convertView.findViewById(R.id.id_item_title);
        viewHolder.mItemDesc = (TextView) convertView.findViewById(R.id.id_item_desc);
    }

    class ViewHolder {
        public ImageView mItemIcon;
        public TextView mItemTitle;
        public TextView mItemDesc;
    }
}
