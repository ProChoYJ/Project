package com.woowahan.intern.internclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 29..
 */
public class SearchListAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SearchInfo> mItemList;
    private ViewHolder holder;


    public SearchListAdapter(Context mContext, ArrayList<SearchInfo> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.search_list_item, parent, false);
            final ViewHolder holder = new ViewHolder();

            holder.ip = (TextView) convertView.findViewById(R.id.search_item_ip);
            holder.port = (TextView) convertView.findViewById(R.id.search_item_port);
            holder.name = (TextView) convertView.findViewById(R.id.search_item_name);


            convertView.setTag(holder);

        }
        holder = (ViewHolder) convertView.getTag();
        SearchInfo item = mItemList.get(position);

        holder.ip.setText(item.getIp());
        holder.port.setText(item.getPort());
        holder.name.setText(item.getName());


        return convertView;
    }

    class ViewHolder{
        private TextView name;
        private TextView ip;
        private TextView port;
    }


}
