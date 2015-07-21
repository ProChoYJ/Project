package com.woowahan.intern.internproject.menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.woowahan.intern.internproject.BaseFragment;
import com.woowahan.intern.internproject.PaymentSuccess;
import com.woowahan.intern.internproject.R;
import com.woowahan.intern.internproject.network.GsonRequest;
import com.woowahan.intern.internproject.review.Reviews;

import java.util.ArrayList;

/**
 * Created by user on 2015. 6. 7..
 */
public class MenuListFragment extends BaseFragment implements Response.Listener<Menus>, Response.ErrorListener {
    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<Child>> mChildList = null;
    private ArrayList<ArrayList<String>> mChildCountList = null;
    private ArrayList<String> mChildListContent = null;
    private MenuListExpandableAdapter mInfoListAdapter;
    private int businessId;
    private Menus mMenus;
    private menu mMenu;
    private Child mChild;
    private ArrayList<Child> mChildArrayList;
    private View layout;
    private TextView mTotalPayment;
    private int totalPayment;
    private Button callBtn;
    private Button paymentBtn;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Button dialogBtnN;
    Button dialogBtnP;
    private TextView msgView;

    private String URL = "http://intern_pro1/jsonC/storeMenuDetail?id=";


    private ExpandableListView mListView;
    private Toast toast;

    public static MenuListFragment newInstance() {
        return new MenuListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_business_menu, container, false);

        totalPayment=0;
        mListView = (ExpandableListView) layout.findViewById(R.id.menu_list);
        mTotalPayment = (TextView) layout.findViewById(R.id.total_payment);
        callBtn = (Button) layout.findViewById(R.id.call_btn);
        paymentBtn = (Button) layout.findViewById(R.id.payment_btn);
        builder = new AlertDialog.Builder(getActivity());

        mChildArrayList = new ArrayList<>();


        mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<Child>>();
        mChildListContent = new ArrayList<String>();
        mChildCountList = new ArrayList<ArrayList<String>>();


        mInfoListAdapter = new MenuListExpandableAdapter(getActivity(), mGroupList, mChildList);

        mListView.setAdapter(mInfoListAdapter);


        // Alert
        builder.setTitle("결제 하시겠습니까?")        // 제목 설정
                .setMessage("정말?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("결제", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){

                        Intent it = new Intent(getActivity(), PaymentSuccess.class);


                        startActivity(it);
                        totalPayment=0;
                        mTotalPayment.setText("0");


                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    // 취소 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();



        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {


                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                int tmp_count = 0;
                Log.d("childprice", "" + mChildList.get(groupPosition).get(childPosition).getPrice());
                totalPayment += mChildList.get(groupPosition).get(childPosition).getPrice();
                tmp_count = mChildList.get(groupPosition).get(childPosition).getCount() + 1;


                mChildList.get(groupPosition).get(childPosition).setCount(tmp_count);
                mTotalPayment.setText("" + totalPayment);
                tmp_count = 0;

                mInfoListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                int childPosition = ExpandableListView.getPackedPositionChild(id);
                int tmp_count = 0;

                if (childPosition > -1) {
                    tmp_count = mChildList.get(groupPosition).get(childPosition).getCount() - 1;
                    if (tmp_count < 0) {
                        tmp_count = 0;
                    } else {

                        totalPayment -= mChildList.get(groupPosition).get(childPosition).getPrice();
                        mTotalPayment.setText("" + totalPayment);
                        mChildList.get(groupPosition).get(childPosition).setCount(tmp_count);
                    }
                    Log.d("child", "group: " + groupPosition + ", child: " + childPosition);
                }

                mInfoListAdapter.notifyDataSetChanged();
                return true;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-4696-6189"));
                startActivity(intent);

            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalPayment > 0) {
                    dialog.show();

                    msgView = (TextView) dialog.findViewById(android.R.id.message);
                    dialogBtnN = (Button) dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    dialogBtnP = (Button) dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    msgView.setGravity(Gravity.CENTER_HORIZONTAL);
                    dialogBtnN.setTextSize(30);
                    dialogBtnP.setTextSize(30);
                    msgView.setTextSize(30);
                }
            }
        });



        return layout;
    }


    ///// onstart
    @Override
    public void onStart() {
        super.onStart();
        requestReviews();
    }


    ////////// business id getter setter

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }


    //////// GSON

    private void requestReviews() {
        Log.d("jsontest", "try request");
        GsonRequest reqeust = new GsonRequest(Request.Method.GET, URL + getBusinessId(), Menus.class, null, this, this);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(reqeust);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("jsontest", "fail");

        Log.e("jsontest", error.getCause().toString());


        toast = Toast.makeText(getActivity(), "데이터를 불러오지 못하였습니다. 다시 시도해 주세요",
                Toast.LENGTH_SHORT);


        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(20);

        toast.show();

//        progressDialog.dismiss();
    }

    @Override
    public void onResponse(Menus response) {

        mGroupList.clear();
        mChildList.clear();

        for (menu menu : response.getMenus()) {
            mGroupList.add(menu.getParent());
            mChildList.add(menu.getChilds());
        }

        mInfoListAdapter.notifyDataSetChanged();
    }
}
