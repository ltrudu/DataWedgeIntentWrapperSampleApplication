package com.symbol.datacapturereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.zebra.datawedgeprofileintents.DWProfileSetConfig;
import com.zebra.datawedgeprofileintents.DWRFIDReceiver;
import com.zebra.datawedgeprofileintents.DWScanReceiver;
import com.zebra.datawedgeprofileintents.DWScannerPluginDisable;
import com.zebra.datawedgeprofileintents.DWScannerPluginEnable;
import com.zebra.datawedgeprofileintents.DataWedgeConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RFIDActivity extends AppCompatActivity implements DWScanReceiver.onScannedData {

    private static String TAG = "DataCaptureReceiver";

    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";

    /**
     * Scanner data receiver
     */
    DWRFIDReceiver mRFIDReceiver;

    /*
        Handler and runnable to scroll down textview
     */
    private Handler mScrollDownHandler = null;
    private Runnable mScrollDownRunnable = null;

    private BroadcastReceiver mBroadcastReceiverImportCommand = null;
    private static final String COMMAND_IDENTIFIER_IMPORT =" 1234560";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);

        setTitle("Second Activity");

        et_results = (TextView)findViewById(R.id.et_resultsRFIDActivity);
        sv_results = (ScrollView)findViewById(R.id.sv_resultsRFIDActivity);


        Button btClose = (Button)findViewById(R.id.button_closeRFIDActivity);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RFIDActivity.this.finish();
            }
        });

        Button btOpenThirdActivity = (Button)findViewById(R.id.button_openThirdActivity);
        btOpenThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RFIDActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        Button btSetRFIDProfile = (Button)findViewById(R.id.button_setRFIDProfile);
        btSetRFIDProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DWProfileSetConfig profileSetConfig = new DWProfileSetConfig(RFIDActivity.this);

                profileSetConfig.execute(DataWedgeSettingsHolder.mSetConfigSettingsRfid, new DWProfileCommandBase.onProfileCommandResult() {
                    @Override
                    public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                        if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                        {
                            addLineToResults("Set config on profile: " + profileName + " succeeded.");

                        }
                        else
                        {
                            addLineToResults("Error setting params on profile: " + profileName + "\n" + resultInfo);
                        }
                    }

                    @Override
                    public void timeout(String profileName) {
                        addLineToResults("Timeout while trying to set params on profile: " + profileName);
                    }
                });
            }
        });

        /**
         * Initialize the scan receiver
         */
        mRFIDReceiver = new DWRFIDReceiver(this,
                DataWedgeSettingsHolder.mDemoIntentAction,
                DataWedgeSettingsHolder.mDemoIntentCategory,
                false,
                new DWRFIDReceiver.onRFIDData() {
                    @Override
                    public void rfidData(String source, String data, String typology) {

                    }
                }
        );
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
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
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

        mRFIDReceiver.startReceive();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        mRFIDReceiver.stopReceive();
        DWScannerPluginDisable dwplugindisable = new DWScannerPluginDisable(this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
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

        if(mBroadcastReceiverImportCommand != null) {
            unregisterReceiver(mBroadcastReceiverImportCommand);
            mBroadcastReceiverImportCommand = null;
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
                    RFIDActivity.this.runOnUiThread(new Runnable() {
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



    private void importProfileFromAssets(String progileFilenameWithoutDbExtension) {
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

    private void importProfileFromDownloadFolder(String progileFilenameWithoutDbExtension) {
            // Source : http://techdocs.zebra.com/datawedge/6-7/guide/settings/
            //Export your profile using
            //1. Open DataWedge
            //2. Open Hamburger Menu -> Settings (Paramètres)
            //3. Open "Export" list entry
            //4. Select profile to export
            //5. Retrieve exported file in folder "\sdcard\Android\data\com.symbol.datawedge\files"

            // Open the db as the input stream
            InputStream fis = null;
            FileOutputStream fos = null;
            File outputFile = null;
            File finalFile = null;

            String targetDir = "/storage/emulated/0/Download/";
            String temporaryFileName = progileFilenameWithoutDbExtension + ".tmp";
            String finalFileName = progileFilenameWithoutDbExtension + ".db";

            try {


                fis = getAssets().open(finalFileName);


                // create a File object for the parent directory
                File outputDirectory = new File(targetDir);

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

                    // Register a broadcast receiver
                    mBroadcastReceiverImportCommand = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent.getAction().equalsIgnoreCase(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2))
                            {
                                // L'intent reçu est bien l'intent cible
                                // On trace le résultat
                                String action = intent.getAction();
                                String command = intent.getStringExtra("COMMAND");
                                String commandidentifier = intent.getStringExtra("COMMAND_IDENTIFIER");
                                String result = intent.getStringExtra("RESULT");

                                if(commandidentifier == null)
                                    return;

                                if(commandidentifier.equalsIgnoreCase(COMMAND_IDENTIFIER_IMPORT) == false)
                                    return;

                                Bundle bundle = new Bundle();
                                String resultInfo = "";

                                if (intent.hasExtra("RESULT_LIST")) { // returns for COMPLETE_RESULT
                                    ArrayList<Bundle> result_list = (ArrayList)intent.getSerializableExtra("RESULT_LIST");
                                    for (Bundle bundleResult : result_list) {

                                        resultInfo +="\n\n";

                                        Set<String> keys = bundleResult.keySet();
                                        for (String key : keys) {
                                            String val = bundleResult.getString(key);
                                            if (val == null) {

                                                if (bundleResult.getStringArray(key) != null) {
                                                    val = "";
                                                    for (String s : bundleResult.getStringArray(key)) {
                                                        val += "" + s + "\n";
                                                    }
                                                }
                                            }

                                            resultInfo += key + ": " + val + "\n";
                                        }
                                    }
                                }
                                else if (intent.hasExtra("RESULT_INFO")) {
                                    bundle = intent.getBundleExtra("RESULT_INFO");
                                    if(bundle != null) {
                                        Set<String> keys = bundle.keySet();
                                        for (String key : keys) {
                                            String value = "";

                                            if (bundle.getString(key) != null) {
                                                value = bundle.getString(key);
                                            } else if (bundle.getStringArray(key) != null) {
                                                for (String innerString : bundle.getStringArray(key))
                                                    value += innerString + ";";
                                            }
                                            if (resultInfo.length() > 0 && value != null)
                                                resultInfo += "\n";
                                            if (value != null)
                                                resultInfo += key + ": " + value;
                                        }
                                    }
                                }

                                String text = "Action: " + action + "\n" +
                                        "Command: " + command + "\n" +
                                        "Result: " + result + "\n" +
                                        "Result Info: " + resultInfo + "\n" +
                                        "CID:" + commandidentifier;
                                RFIDActivity.this.addLineToResults(text);
                                Log.d(TAG, text);
                                RFIDActivity.this.unregisterReceiver(mBroadcastReceiverImportCommand);
                                mBroadcastReceiverImportCommand = null;
                            }
                        }
                    };

                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2);
                    intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
                    ContextCompat.registerReceiver(this, mBroadcastReceiverImportCommand, intentFilter, null, null, ContextCompat.RECEIVER_EXPORTED);

                    // Create the Intent to import the file
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(DataWedgeConstants.ACTION_RESULT_DATAWEDGE_FROM_6_2);
                    filter.addCategory(Intent.CATEGORY_DEFAULT);

                    Bundle bMain = new Bundle();
                    bMain.putString("FOLDER_PATH", targetDir);
                    ArrayList<String> fileNames = new ArrayList<>();
                    fileNames.add(finalFileName);
                    bMain.putStringArrayList("FILE_LIST", fileNames);

                    Intent i = new Intent();
                    i.setAction("com.symbol.datawedge.api.ACTION");
                    i.putExtra("com.symbol.datawedge.api.IMPORT_CONFIG", bMain);
                    i.putExtra("SEND_RESULT", "LAST_RESULT");
                    i.putExtra("COMMAND_IDENTIFIER", COMMAND_IDENTIFIER_IMPORT);

                    sendBroadcast(i);
                }
            }
        }
}
