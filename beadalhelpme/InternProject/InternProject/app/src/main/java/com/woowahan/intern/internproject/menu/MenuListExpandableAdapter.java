package com.woowahan.intern.internproject.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.woowahan.intern.internproject.R;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 7..
 */
public class MenuListExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<Child>> childList = null;
    private ArrayList<ArrayList<String>> childCount = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    private ProgressDialog progressDialog;

    public MenuListExpandableAdapter(Context c, ArrayList<String> groupList, ArrayList<ArrayList<Child>> childList) {
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
        this.childCount = childCount;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_menu_parent, parent, false);
            viewHolder.gindicator = (ImageView) v.findViewById(R.id.expandable_indicator);
            viewHolder.groupName = (TextView) v.findViewById(R.id.menu_parent_name);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            //viewHolder.iv_image.setBackgroundColor(Color.GREEN);
            viewHolder.gindicator.setImageResource(R.drawable.expandableup);
        }else{
            //viewHolder.iv_image.setBackgroundColor(Color.WHITE);
            viewHolder.gindicator.setImageResource(R.drawable.expandabledown);
        }

        viewHolder.groupName.setText(getGroup(groupPosition));

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public Child getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_menu_child, null);
            viewHolder.childName = (TextView) v.findViewById(R.id.menu_chile_name);
            viewHolder.childPrice = (TextView) v.findViewById(R.id.menu_chile_price);
            viewHolder.childCount = (TextView) v.findViewById(R.id.child_count);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

//        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));
        viewHolder.childName.setText(getChild(groupPosition, childPosition).getMenuName());
        viewHolder.childPrice.setText("" + getChild(groupPosition,childPosition).getPrice());
        viewHolder.childCount.setText("" + getChild(groupPosition,childPosition).getCount());

        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public ImageView gindicator;
        public TextView groupName;
        public TextView childName;
        public TextView childPrice;
        public TextView childCount;
    }
}
