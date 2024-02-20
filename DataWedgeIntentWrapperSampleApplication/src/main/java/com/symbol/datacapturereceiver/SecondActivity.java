package com.symbol.datacapturereceiver;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zebra.datawedgeprofileintents.DWProfileBaseSettings;
import com.zebra.datawedgeprofileintents.DWProfileCommandBase;
import com.zebra.datawedgeprofileintents.DWScanReceiver;
import com.zebra.datawedgeprofileintents.DWScannerPluginDisable;
import com.zebra.datawedgeprofileintents.DWScannerPluginEnable;
import com.zebra.datawedgeprofileintents.DWStatusScanner;
import com.zebra.datawedgeprofileintents.DWStatusScannerCallback;
import com.zebra.datawedgeprofileintents.DWStatusScannerSettings;
import com.zebra.datawedgeprofileintents.DataWedgeConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity implements DWScanReceiver.onScannedData {

    private static String TAG = "DataCaptureReceiver";

    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";

    /**
     * Scanner data receiver
     */
    DWScanReceiver mScanReceiver;

    DWStatusScanner mStatusReceiver;

    /*
        Handler and runnable to scroll down textview
     */
    private Handler mScrollDownHandler = null;
    private Runnable mScrollDownRunnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle("Second Activity");

        et_results = (TextView)findViewById(R.id.et_resultsSecondActivity);
        sv_results = (ScrollView)findViewById(R.id.sv_resultsSecondActivity);


        Button btClose = (Button)findViewById(R.id.button_closeSecondActivity);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondActivity.this.finish();
            }
        });

        Button btOpenThirdActivity = (Button)findViewById(R.id.button_openThirdActivity);
        btOpenThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        Button btImport = (Button) findViewById(R.id.button_import);
        btImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"database profile auto creation" import mode (filebased)
                    importProfile("dwprofile_com.symbol.datacapturereceiver");
            }
        });

        /**
         * Initialize the scan receiver
         */
        mScanReceiver = new DWScanReceiver(this,
                DataWedgeSettingsHolder.mDemoIntentAction,
                DataWedgeSettingsHolder.mDemoIntentCategory,
                false,
                new DWScanReceiver.onScannedData() {
                    @Override
                    public void scannedData(String source, String data, String typology) {
                        addLineToResults("Source: " + source);
                        addLineToResults("Typology: " + typology+ ", Data: " + data);
                    }
                }
        );

        DWStatusScannerSettings profileStatusSettings = new DWStatusScannerSettings()
        {{
            mPackageName = getPackageName();
            mScannerCallback = new DWStatusScannerCallback() {
                @Override
                public void result(String status) {
                    switch(status)
                    {
                        case DataWedgeConstants.SCAN_STATUS_CONNECTED:
                            addLineToResults("Scanner is connected.");
                            break;
                        case DataWedgeConstants.SCAN_STATUS_DISABLED:
                            addLineToResults("Scanner is disabled.");
                            break;
                        case DataWedgeConstants.SCAN_STATUS_DISCONNECTED:
                            addLineToResults("Scanner is disconnected.");
                            break;
                        case DataWedgeConstants.SCAN_STATUS_SCANNING:
                            addLineToResults("Scanner is scanning.");
                            break;
                        case DataWedgeConstants.SCAN_STATUS_WAITING:
                            addLineToResults("Scanner is waiting.");
                            break;
                    }
                }
            };
        }};

        addLineToResults("Setting up scanner status checking on package : " + profileStatusSettings.mPackageName + ".");

        mStatusReceiver = new DWStatusScanner(this, profileStatusSettings);
    }

    @Override
    public void scannedData(String source, String data, String typology) {
        addLineToResults("Typology: " + typology+ ", Data: " + data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DWScannerPluginEnable dwpluginenable = new DWScannerPluginEnable(this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = getPackageName();
        }};

        dwpluginenable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                addLineToResults("DWPlugin enable result:" + result);
            }

            @Override
            public void timeout(String profileName) {

            }
        });

        mScanReceiver.startReceive();
        mStatusReceiver.start();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        mScanReceiver.stopReceive();
        mStatusReceiver.stop();
        DWScannerPluginDisable dwplugindisable = new DWScannerPluginDisable(this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = getPackageName();
        }};

        dwplugindisable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                addLineToResults("DWPlugin disable result:" + result);
            }
            @Override
            public void timeout(String profileName) {

            }
        });
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
                    SecondActivity.this.runOnUiThread(new Runnable() {
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
        if(mScrollDownHandler != null)
            mScrollDownHandler.postDelayed(mScrollDownRunnable, 300);
    }

    private void importProfile(String progileFilenameWithoutDbExtension) {
            // Source : http://techdocs.zebra.com/datawedge/6-7/guide/settings/
            //Export your profile using
            //1. Open DataWedge
            //2. Open Hamburger Menu -> Settings (Paramètres)
            //3. Open "Export" list entry
            //4. Select profile to export
            //5. Retrieve exportes file in folder "\sdcard\Android\data\com.symbol.datawedge\files"

            // Open the db as the input stream
            InputStream fis = null;
            FileOutputStream fos = null;
            File outputFile = null;
            File finalFile = null;

            try {

                String autoImportDir = "/enterprise/device/settings/datawedge/autoimport/";
                String temporaryFileName = progileFilenameWithoutDbExtension + ".tmp";
                String finalFileName = progileFilenameWithoutDbExtension + ".db";

                fis = getAssets().open(finalFileName);


                // create a File object for the parent directory
                File outputDirectory = new File(autoImportDir);

                // create a temporary File object for the output file
                outputFile = new File(outputDirectory, temporaryFileName);
                finalFile = new File(outputDirectory, finalFileName);

                // attach the OutputStream to the file object
                fos = new FileOutputStream(outputFile);

                // transfer bytes from the input file to the output file
                byte[] buffer = new byte[1024];
                int length;
                int tot = 0;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                    tot += length;
                }
                Log.d(TAG, tot + " bytes copied");

                //flush the buffers
                fos.flush();

                //release resources
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fos = null;
                //set permission to the file to read, write and exec.
                if (outputFile != null) {
                    outputFile.setExecutable(true, false);
                    outputFile.setReadable(true, false);
                    outputFile.setWritable(true, false);
                    //rename the file
                    if (finalFile != null)
                        outputFile.renameTo(finalFile);
                }

            }
        }
}
