package com.woowahan.intern.internproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.woowahan.intern.internproject.business.BusinessList;
import com.woowahan.intern.internproject.network.GsonRequest;
import com.woowahan.intern.internproject.review.Reviews;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2015. 6. 11..
 */
public class ReviewWriteActivity extends AppCompatActivity implements Response.Listener<RequestEcho>, Response.ErrorListener{

    private RatingBar reviewRatingBar;
    private TextView reviewGrade;
    private Button writeBtn;
    private Button writeCancel;
    private EditText reviewContent;
    private EditText reviewName;
    private TextView textCount;
    private int MAX_COUNT = 100;
    private int reviewId;
    private Toast toast;
    Map<String,String> params = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("리뷰 남기기");
        setContentView(R.layout.activity_review_write);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        writeBtn = (Button) findViewById(R.id.review_write_btn);
        reviewName = (EditText) findViewById(R.id.review_write_name);
        reviewRatingBar = (RatingBar) findViewById(R.id.review_grade_bar);
        reviewGrade = (TextView) findViewById(R.id.review_grade_textview);
        reviewContent = (EditText) findViewById(R.id.review_write_content);
        textCount = (TextView) findViewById(R.id.review_max_count);
        writeCancel = (Button) findViewById(R.id.review_write_cancel);

        Intent it = getIntent();
        reviewId = it.getExtras().getInt("Id");
//        Log.d("reviewId", "" + it.getExtras().getInt("Id"));
        reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                reviewGrade.setText("" + (int) rating);
            }
        });

        LayerDrawable stars = (LayerDrawable) reviewRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#C2185B"), PorterDuff.Mode.SRC_ATOP);


        ///// review name event
        reviewName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( s.length() != 0 && reviewContent.getText().length() != 0){

                }


            }
        });


        ///////////////////// review count evnet

        reviewContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = s.length();
                textCount.setText("" + count);
            }
        });

        /////// review write event
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reviewName.getText().toString().trim().length() > 0 && reviewContent.getText().toString().trim().length() > 0) {

                    params.put("reviewId", "" + reviewId);
                    params.put("reviewName",reviewName.getText().toString());
                    params.put("reviewContent", reviewContent.getText().toString());
                    params.put("reviewGrade", "" + reviewRatingBar.getRating());

                    Log.d("reviewId", "Id : " + params.get("reviewId"));

                    requestReviews();

                    toast = Toast.makeText(getApplicationContext(), "리뷰를 남겼습니다.",
                            Toast.LENGTH_SHORT);


                    LinearLayout linearLayout = (LinearLayout) toast.getView();
                    linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                    TextView messageTextView = (TextView) linearLayout.getChildAt(0);
                    messageTextView.setTextSize(20);

                    toast.show();

                    finish();




                }
            }
        });


        /////////// reveiw wirte cancel event
        writeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }

    ///// menu

    ////// font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    ///// menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ///////// back event
    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }

    ////// volley post request
    private void requestReviews(){
        Log.d("jsontest", "try request");
        GsonRequest reqeust = new GsonRequest(Request.Method.POST, "http://intern_pro1/jsonC/registerStoreReview", RequestEcho.class, getParam(), this, this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(reqeust);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.d("jsontest", "fail post");

        Log.e("jsontest", error.getCause().toString());
    }


    @Override
    public void onResponse(RequestEcho response) {


    }

    protected Map<String,String> getParam(){
        return params;
    }
}
