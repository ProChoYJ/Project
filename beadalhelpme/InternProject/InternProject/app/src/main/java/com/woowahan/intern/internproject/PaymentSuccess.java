package com.woowahan.intern.internproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.widget.Button;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2015. 6. 14..
 */
public class PaymentSuccess extends AppCompatActivity {

    private Button paymentConfirm;
    private Button paymentCancel;
    private Toast toast;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pament);
        setTitle("결제완료");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentConfirm = (Button) findViewById(R.id.payment_confirm);
        paymentCancel = (Button) findViewById(R.id.payment_cancel_btn);

        //
        paymentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //// cancel event
        paymentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toast = Toast.makeText(getApplicationContext(), "결제가 취소 되었습니다.",
                        Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    ///////// back event
    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
