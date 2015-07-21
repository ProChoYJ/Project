package com.woowahan.intern.internclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 29..
 */
public class AutoSearchServerDialogFragment extends DialogFragment {

    public interface OnThreadListener {
        void onStop();
    }

    public void setOnThreadListener(OnThreadListener listener) {
        mOnTreadListener = listener;
    }

    private OnThreadListener mOnTreadListener;

    private ListView mListView;
    private ArrayList<SearchInfo> mArrayList;
    private SearchListAdapter mSearchListAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



//        mOnTreadListener = (OnThreadListener) activity;
    }

    public static AutoSearchServerDialogFragment newInstance() {
        AutoSearchServerDialogFragment fragment = new AutoSearchServerDialogFragment();

//        Bundle bundle = new Bundle();
//        bundle.putInt("extra", 1);
//        fragment.setArguments(bundle);


        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auto_search_server_list_view, container, false);
        // TODO list view implement
//        int extra = getArguments().getInt("extra", -1);
        getDialog().setTitle("검색된 서버");


        mListView = (ListView) view.findViewById(R.id.search_listview);

        mArrayList = ((MainActivity) getActivity()).getSearchInfoList();
        mSearchListAdapter = ((MainActivity) getActivity()).getSearchListAdapter();

        mListView.setAdapter(mSearchListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent(getActivity(), TouchActivity.class);
                it.putExtra("ip", mArrayList.get(position).getPort());
                it.putExtra("port", mArrayList.get(position).getIp());
//                Log.d("input address", "ip: " + ipEditText.getText().toString() + " - port : " + portEditText.getText().toString());

                if (mOnTreadListener != null) {
                    mOnTreadListener.onStop();
                }

                startActivity(it);
            }
        });


        return view;
    }


    public void runSearchServer(String ack, String ip) {

    }
}
