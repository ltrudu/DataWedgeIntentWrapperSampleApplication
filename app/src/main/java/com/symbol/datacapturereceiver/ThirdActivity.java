package com.symbol.datacapturereceiver;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zebra.datawedgeprofileintents.DWScanReceiver;

public class ThirdActivity extends AppCompatActivity  implements DWScanReceiver.onScannedData {

    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";

    /**
     * Scanner data receiver
     */
    DWScanReceiver mScanReceiver;

    /*
        Handler and runnable to scroll down textview
     */
    private Handler mScrollDownHandler = null;
    private Runnable mScrollDownRunnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle("Third Activity");

        et_results = (TextView)findViewById(R.id.et_resultsThirdActivity);
        sv_results = (ScrollView)findViewById(R.id.sv_resultsThirdActivity);


        Button btClose = (Button)findViewById(R.id.button_closeThirdActivity);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThirdActivity.this.finish();
            }
        });

        /**
         * Initialize the scan receiver
         */
        mScanReceiver = new DWScanReceiver(this,
                DataWedgeSettingsHolder.mDemoIntentAction,
                DataWedgeSettingsHolder.mDemoIntentCategory,
                true,
                this
        );
    }

    @Override
    public void scannedData(String source, String data, String typology) {
        addLineToResults("Typology: " + typology+ ", Data: " + data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanReceiver.startReceive();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        mScanReceiver.stopReceive();
        if(mScrollDownRunnable != null)
        {
            mScrollDownHandler.removeCallbacks(mScrollDownRunnable);
            mScrollDownRunnable = null;
            mScrollDownHandler = null;
        }
        super.onPause();
    }

    private void addLineToResults(final String lineToAdd)
    {
        mResults += lineToAdd + "\n";
        updateAndScrollDownTextView();
    }

    private void updateAndScrollDownTextView() {
        if (mScrollDownRunnable == null) {
            mScrollDownRunnable = new Runnable() {
                @Override
                public void run() {
                    ThirdActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_results.setText(mResults);
                            sv_results.post(new Runnable() {
                                @Override
                                public void run() {
                                    sv_results.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                }
            };
        } else {
            // A new line has been added while we were waiting to scroll down
            // reset handler to repost it....
            mScrollDownHandler.removeCallbacks(mScrollDownRunnable);
        }
        mScrollDownHandler.postDelayed(mScrollDownRunnable, 300);
    }
}
