package com.woowahan.intern.internproject.imformation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.woowahan.intern.internproject.BaseFragment;
import com.woowahan.intern.internproject.BusinessInfoActivity;
import com.woowahan.intern.internproject.R;
import com.woowahan.intern.internproject.business.Business;
import com.woowahan.intern.internproject.business.BusinessListAdapter;
import com.woowahan.intern.internproject.menu.MenuListExpandableAdapter;
import com.woowahan.intern.internproject.menu.Menus;
import com.woowahan.intern.internproject.menu.menu;
import com.woowahan.intern.internproject.network.GsonRequest;

import org.w3c.dom.Text;

/**
 * Created by user on 2015. 6. 7..
 */
public class BusinessInfoFragment extends BaseFragment implements Response.Listener<BusinessInfomation>, Response.ErrorListener {

    //    private ListView mInfoListView;
//    private BusinessInfoAdapter mInfoListAdapter;
    private int BusinessId;
    private String URL = "http://intern_pro1/jsonC/oneStoreData?id=";
    private TextView businessAdress;
    private TextView businessHour;
    private TextView businessOpenDate;
    private TextView businessPhone;
    private TextView businessOrderCount;
    private TextView businessComment;
    private TextView businessHourE;
    private Toast toast;


    public static BusinessInfoFragment newInstance() {
        return new BusinessInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_business_info, container, false);



        businessAdress = (TextView) layout.findViewById(R.id.business_info_address);
        businessHour = (TextView) layout.findViewById(R.id.business_info_hour);
        businessOpenDate = (TextView) layout.findViewById(R.id.business_info_open_date);
        businessPhone = (TextView) layout.findViewById(R.id.business_info_phone);
        businessOrderCount = (TextView) layout.findViewById(R.id.business_info_order_count);
        businessComment = (TextView) layout.findViewById(R.id.business_info_comment);
        businessHourE = (TextView) layout.findViewById(R.id.business_info_hourE);



        return layout;
    }


    private void updateInfos() {


    }

    ///// onstart
    @Override
    public void onStart() {
        super.onStart();
        requestReviews();
    }


    ////////// business id getter setter


    //////// GSON

    private void requestReviews() {
        Log.d("jsontest", "try request");
        GsonRequest reqeust = new GsonRequest(Request.Method.GET, URL + getBusinessId(), BusinessInfomation.class, null, this, this);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(reqeust);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("jsontest", "fail");

        Log.e("jsontest", error.getCause().toString());

        toast = Toast.makeText(getActivity(), "데이터를 불러오지 못하였습니다. 다시 시도해 주세요",
                Toast.LENGTH_SHORT);

        toast.show();

    }

    @Override
    public void onResponse(BusinessInfomation response) {
//        mInfoList.addAll(response.getReviewList());
//        reviewCount.setText("" + response.getReviewList().size());
//        reviewAvgScore.setText(response.getAvg_Grade());
        Log.d("jsontest", "" + response.getAddress());


        businessAdress.setText(response.getAddress());
        businessHour.setText(response.getBusinessHour());
        businessOpenDate.setText(response.getOpenDate());
        businessPhone.setText(response.getPhone());
        businessOrderCount.setText("" + response.getOrderCount());
        businessComment.setText(response.getComment());
        businessHourE.setText(response.getBusinessHourE());

//        mInfoListAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //////// businessid getter setter


    public int getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(int businessId) {
        BusinessId = businessId;
    }
}
