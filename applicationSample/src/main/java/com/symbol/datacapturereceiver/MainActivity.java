package com.symbol.datacapturereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zebra.datawedgeprofileenums.SC_E_AIM_TYPE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNINGMODE;
import com.zebra.datawedgeprofileintents.*;
import com.zebra.datawedgeprofileintents.SQLiteDAO.DWProfileDAOHelpers;
import com.zebra.datawedgeprofileintents.SQLiteDAO.DWProfileDAOPlugin_Input_Scanner;
import com.zebra.datawedgeprofileintents.SQLiteDAO.DWProfileDAOScanner_Params;
import com.zebra.datawedgeprofileintents.SQLiteDAO.DbManager;
import com.zebra.datawedgeprofileintents.SQLiteDAO.Plugin_Input_Scanner;
import com.zebra.datawedgeprofileintents.SQLiteDAO.Scanner_Params;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /**
     *  *************************************************************
     *  How to configure DataWedge to send intent to this application
     *  *************************************************************
     *
     * More info on DataWedge can be found here:
     *      http://techdocs.zebra.com/datawedge/5-0/guide/about/
     * More info on DataCapture Intents can be found here:
     *      http://techdocs.zebra.com/emdk-for-android/6-3/tutorial/tutdatacaptureintent/
     *
     * Setup1 (Automatic):
     * 0- Install the app on the device
     * 1- Copy the dwprofile_datacapture.db file that is provided in the asset folder in
     * device sdcard
     * 2- Open DataWedge
     * 3- Select Settings in the Menu
     * 4- Select Import Profile
     * 5- Select the previously imported file
     * 6- Quit DataWedge
     * 7- Run the application
     *
     * Setup2 (Manual):
     * 0- Install the app on the device
     * 1- Open DataWedge
     * 2- Menu -> New Profile
     * 3- Enter a name for the profile
     * 4- Select the newly created profile
     * 5- Select Applications -> Associated apps
     * 6- Menu -> New app/activity
     * 7- Select com.symbol.datacapturereceiver
     * 8- Select com.symbol.datacapturereceiver.MainActivity
     * 9- Go back
     * 10- Disable Keystroke output
     * 11- Enable Intent Output
     * 12- Select Intent Output -> Intent Action
     * 13- Enter (case sensitive): com.symbol.datacapturereceiver.RECVR
     * 14- Select Intent Output -> Intent Category
     * 15- Enter (case sensitive): android.intent.category.DEFAULT
     * 16- Select Intent Output -> Intent Delivery
     * 17- Select via StartActivity to handle the date inside the OnCreate Method and in onNewIntent
     *     If you select this option, don't forget to add com.android.launcher in the Associated apps if
     *     you want your app to be started from the launcher when a barcode is scanned, otherwise the scanner
     *     will only work when the datacapturereceiver app is running
     *     Configure android:launchMode="" in your AndroidManifest.xml application tag if you want
     *     specific behaviors.
     *     https://developer.android.com/guide/topics/manifest/activity-element.html
     *     http://androidsrc.net/android-activity-launch-mode-example/
     * 18- Select Broadcast Intent to handle the data inside a Broadcast Receiver
     * 19- Configure the Symbology : go to Barcode input -> Decoders
     * 20- Configure Aim (only the barcode in the center of the scanner target is read)
     *      Go to Barcode input -> Reader params -> Picklist -> Enabled
     */

    private static String TAG = "DataCaptureReceiver";
    private static boolean mStartInContinuousMode = false;
    private static boolean mOptmizeRefresh = true;
    private static boolean mDisplaySpecialChars = true;


    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";
    private boolean mContinuousModeSwitch = false;
    private Date mScanDate = null;
    private Date mProfileProcessingStartDate = null;

    /*
    Scanner status checker
     */
    DWStatusScanner mScannerStatusChecker = null;

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
        setContentView(R.layout.activity_main);

        et_results = (TextView)findViewById(R.id.et_results);
        sv_results = (ScrollView)findViewById(R.id.sv_results);

        Button btEnableDW = (Button) findViewById(R.id.button_enabledw);
        btEnableDW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  datawedgeEnableWithErrorChecking();  }
        });

        Button btDisableDW = (Button) findViewById(R.id.button_disabledw);
        btDisableDW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { datawedgeDisableWithErrorChecking(); }
        });

        Button btStart = (Button) findViewById(R.id.button_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mScanDate  = new Date(); startScanWithErrorChecking(); }
        });

        Button btStop = (Button) findViewById(R.id.button_stop);
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopScanWithErrorChecking();
            }
        });

        Button btToggle = (Button) findViewById(R.id.button_toggle);
        btToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScanWithErrorChecking();
            }
        });

        Button btEnable = (Button) findViewById(R.id.button_enable);
        btEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginEnableWithErrorChecking();
            }
        });

        Button btDisable = (Button) findViewById(R.id.button_disable);
        btDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginDisableWithErrorChecking();
            }
        });

        Button btCreate = (Button) findViewById(R.id.button_create);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileProcessingStartDate = new Date();
                //setProfileConfigAsync();
                setupProfileAsync();
            }
        });

        Button btImport = (Button) findViewById(R.id.button_import);
        btImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"database profile auto creation" import mode (filebased)
                //importProfile("dwprofile_com.symbol.datacapturereceiver.db");

                // Let's use DAO classes to import a modified DW Profile
                updateThenImportAssetDBProfile("MC32_dwprofile_com.symbol.datacapturereceiver.db", "dwprofile_com.symbol.datacapturereceiver.db");
            }
        });

        Button btDelete = (Button) findViewById(R.id.button_delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfileAsync();
            }
        });

        Button btSwitch = (Button) findViewById(R.id.button_switch);
        btSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContinuousModeSwitch = !mContinuousModeSwitch;
                switchScannerParamsAsync(mContinuousModeSwitch);
            }
        });

        Button btClear = (Button) findViewById(R.id.button_clear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResults = "";
                et_results.setText(mResults);
            }
        });

        Button btOpenActivity = (Button)findViewById(R.id.button_openNewActivity);
        btOpenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        /**
         * We initialize the settings class that will hold all the configurations
         * we are going to use in this application
         */
        DataWedgeSettingsHolder.initSettings(this);

        /**
         * Initialize the scan receiver
         */
        mScanReceiver = new DWScanReceiver(this,
                DataWedgeSettingsHolder.mDemoIntentAction,
                DataWedgeSettingsHolder.mDemoIntentCategory,
                false, // Displays special chars between brackets
                // You can inline the code here (like in this exampleà, or make the current activity
                // extends the interface DWScanReceiver.onScannedData then pass directly the
                // "this" reference instead
                new DWScanReceiver.onScannedData() {
                    @Override
                    public void scannedData(String source, String data, String typology) {
                        // Source contains the source of the scan (scanner, camera, bluetoothscanner)
                        // data contains the data that has been scanned
                        // typology contains the typology of what has been scanned
                        addLineToResults("Typology: " + typology+ ", Data: " + data);
                    }
                }
        );

        /*
        // Use this to check if the toJson and fromJson work correctly
        DWProfileSetConfigSettings testSettings = new DWProfileSetConfigSettings();
        String json = DWProfileSetConfigSettings.toJson(testSettings);
        DWProfileSetConfigSettings returnSettings = DWProfileSetConfigSettings.fromJson(json);
        String profilName = returnSettings.mProfileName;
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanReceiver.startReceive();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
        setupScannerStatusChecker();
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
        stopScannerStatusChecker();
        super.onPause();
    }

    private void updateThenImportAssetDBProfile(String profileFilename, String destinationFileName)
    {
        String destinationFolder = "/sdcard/";
        String dbpath = DWProfileDAOHelpers.copyDataBaseFromAssetsToFolder(this, profileFilename, destinationFolder, destinationFileName);
        DbManager.setConfig(this);
        DbManager.getsInstance().open(dbpath, 1);

        ArrayList<Plugin_Input_Scanner> oned_marginless_decode_effort_level_records = DWProfileDAOPlugin_Input_Scanner.loadAllRecords("param_id = ? AND scanner_type = ?", new String[] { "1d_marginless_decode_effort_level", "INTERNAL_IMAGER" }, null, null, null);
        Plugin_Input_Scanner oned_marginless_decode_effort_level = null;
        if(oned_marginless_decode_effort_level_records != null && oned_marginless_decode_effort_level_records.size() > 0)
        {
            oned_marginless_decode_effort_level = oned_marginless_decode_effort_level_records.get(0);
            oned_marginless_decode_effort_level.setparam_value("3");
            DWProfileDAOPlugin_Input_Scanner.updateRecord(oned_marginless_decode_effort_level);
        }
        else
        {
            oned_marginless_decode_effort_level = new Plugin_Input_Scanner();
            oned_marginless_decode_effort_level.setprofile_id(1);
            oned_marginless_decode_effort_level.setdevice_id("1");
            oned_marginless_decode_effort_level.setparam_id("1d_marginless_decode_effort_level");
            oned_marginless_decode_effort_level.setparam_value("3");
            oned_marginless_decode_effort_level.setscanner_type("INTERNAL_IMAGER");
            oned_marginless_decode_effort_level.setdisplay_name("1D Quiet Zone Level");
            oned_marginless_decode_effort_level.setvalue_name("Level 3");
            DWProfileDAOPlugin_Input_Scanner.insertRecord(oned_marginless_decode_effort_level);
        }

        ArrayList<Plugin_Input_Scanner> poor_quality_bcdecode_effort_level_records = DWProfileDAOPlugin_Input_Scanner.loadAllRecords("param_id = ? AND scanner_type = ?", new String[] { "poor_quality_bcdecode_effort_level", "INTERNAL_IMAGER" }, null, null, null);
        Plugin_Input_Scanner poor_quality_bcdecode_effort_level = null;
        if(poor_quality_bcdecode_effort_level_records != null && poor_quality_bcdecode_effort_level_records.size() > 0)
        {
            poor_quality_bcdecode_effort_level = poor_quality_bcdecode_effort_level_records.get(0);
            poor_quality_bcdecode_effort_level.setparam_value("3");
            poor_quality_bcdecode_effort_level.setvalue_name("Effort Level 3");
            DWProfileDAOPlugin_Input_Scanner.updateRecord(poor_quality_bcdecode_effort_level);
        }
        else
        {
            poor_quality_bcdecode_effort_level = new Plugin_Input_Scanner();
            poor_quality_bcdecode_effort_level.setprofile_id(1);
            poor_quality_bcdecode_effort_level.setdevice_id("1");
            poor_quality_bcdecode_effort_level.setparam_id("poor_quality_bcdecode_effort_level");
            poor_quality_bcdecode_effort_level.setparam_value("3");
            poor_quality_bcdecode_effort_level.setscanner_type("INTERNAL_IMAGER");
            poor_quality_bcdecode_effort_level.setdisplay_name("Poor Quality Decode Effort");
            poor_quality_bcdecode_effort_level.setvalue_name("Effort Level 3");
            DWProfileDAOPlugin_Input_Scanner.insertRecord(poor_quality_bcdecode_effort_level);
        }

        ArrayList<Plugin_Input_Scanner> upcean_security_level_records = DWProfileDAOPlugin_Input_Scanner.loadAllRecords("param_id = ? AND scanner_type = ?", new String[] { "upcean_security_level", "INTERNAL_IMAGER" }, null, null, null);
        Plugin_Input_Scanner upcean_security_level = null;
        if(upcean_security_level_records != null && upcean_security_level_records.size() > 0)
        {
            upcean_security_level = upcean_security_level_records.get(0);
            upcean_security_level.setparam_value("0");
            upcean_security_level.setvalue_name("Level 0");
            DWProfileDAOPlugin_Input_Scanner.updateRecord(upcean_security_level);
        }
        else
        {
            upcean_security_level = new Plugin_Input_Scanner();
            upcean_security_level.setprofile_id(1);
            upcean_security_level.setdevice_id("1");
            upcean_security_level.setparam_id("upcean_security_level");
            upcean_security_level.setparam_value("0");
            upcean_security_level.setscanner_type("INTERNAL_IMAGER");
            upcean_security_level.setdisplay_name("Security Level");
            upcean_security_level.setvalue_name("Level 0");
            DWProfileDAOPlugin_Input_Scanner.insertRecord(upcean_security_level);
        }

        ArrayList<Plugin_Input_Scanner> upcean_retry_count_records = DWProfileDAOPlugin_Input_Scanner.loadAllRecords("param_id = ? AND scanner_type = ?", new String[] { "upcean_retry_count", "INTERNAL_IMAGER" }, null, null, null);
        Plugin_Input_Scanner upcean_retry_count = null;
        if(upcean_retry_count_records != null && upcean_retry_count_records.size() > 0)
        {
            upcean_retry_count = upcean_retry_count_records.get(0);
            upcean_retry_count.setparam_value("2");
            DWProfileDAOPlugin_Input_Scanner.updateRecord(upcean_retry_count);
        }
        else
        {
            upcean_retry_count = new Plugin_Input_Scanner();
            upcean_retry_count.setprofile_id(1);
            upcean_retry_count.setdevice_id("1");
            upcean_retry_count.setparam_id("upcean_retry_count");
            upcean_retry_count.setparam_value("2");
            upcean_retry_count.setscanner_type("INTERNAL_IMAGER");
            upcean_retry_count.setdisplay_name("Retry Count");
            upcean_retry_count.setvalue_name("2");
            DWProfileDAOPlugin_Input_Scanner.insertRecord(upcean_retry_count);
        }


        DbManager.getsInstance().close();

        importProfile(destinationFileName, destinationFolder);
    }

    private void importProfile(String profileFilename, String folder)
    {
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
            String temporaryFileName = DWProfileDAOHelpers.removeExtension(profileFilename) + ".tmp";
            String finalFileName = DWProfileDAOHelpers.removeExtension(profileFilename) + ".db";

            if(folder == null || folder.isEmpty())
                fis = getAssets().open(finalFileName);
            else
            {
                File inputFile = new File(folder, profileFilename);
                fis = new FileInputStream(inputFile);
            }

            // create a File object for the parent directory
            File outputDirectory = new File(autoImportDir);

            // create a temporary File object for the output file
            outputFile = new File(outputDirectory,temporaryFileName);
            finalFile = new File(outputDirectory, finalFileName);

            // attach the OutputStream to the file object
            fos = new FileOutputStream(outputFile);

            // transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            int tot = 0;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
                tot+= length;
            }
            Log.d(TAG,tot+" bytes copied");

            //flush the buffers
            fos.flush();

            //release resources
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fos = null;
            //set permission to the file to read, write and exec.
            if(outputFile != null)
            {
                outputFile.setExecutable(true, false);
                outputFile.setReadable(true, false);
                outputFile.setWritable(true, false);
                //rename the file
                if(finalFile != null)
                    outputFile.renameTo(finalFile);
            }

        }
    }

    private void startScanWithErrorChecking()
    {
        addLineToResults("Start Software Scan");
        DWScannerStartScan dwstartscan = new DWScannerStartScan(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwstartscan.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Start Scan on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Starting Scanner on profile: " + profileName + "\n" + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to start Scanner on profile: " + profileName);
            }
        });
    }

    private void stopScanWithErrorChecking()
    {
        addLineToResults("Stop Software Scan");
        DWScannerStopScan dwstopscan = new DWScannerStopScan(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwstopscan.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Stop Scan on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Stoping Scanner on profile: " + profileName + "\n" + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to stop scanner on profile: " + profileName);
            }
        });
    }

    private void toggleScanWithErrorChecking()
    {
        addLineToResults("Toggle Software Scan");
        DWScannerToggleScan dwtogglescan = new DWScannerToggleScan(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwtogglescan.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Toggle Scan on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Toggle-ing Scanner on profile: " + profileName + "\n" + resultInfo);
                }
            }
            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to toggle scanner on profile: " + profileName);
            }
        });
    }

    private void pluginEnableWithErrorChecking()
    {
        addLineToResults("Enabling DataWedge Plugin");
        DWScannerPluginEnable dwpluginenable = new DWScannerPluginEnable(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = getPackageName();
        }};

        dwpluginenable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Enabling plugin on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Enabling plugin on profile: " + profileName + "\n" + resultInfo);
                }
            }
            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to enable plugin on profile: " + profileName);
            }
        });
    }
    
    private void pluginDisableWithErrorChecking()
    {
        addLineToResults("Disabling DataWedge Plugin");
        DWScannerPluginDisable dwplugindisable = new DWScannerPluginDisable(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = getPackageName();
        }};

        dwplugindisable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Disabling plugin on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Disabling plugin on profile: " + profileName + "\n" + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to disable plugin on profile: " + profileName);
            }
        });
    }

    private void datawedgeEnableWithErrorChecking()
    {
        addLineToResults("Enabling DataWedge Plugin");
        DWDataWedgeEnable dwdatawedgeenable = new DWDataWedgeEnable(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwdatawedgeenable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Enabling datawedge on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Enabling datawedge on profile: " + profileName + "\n" + resultInfo);
                }
            }
            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to enable datawedge on profile: " + profileName);
            }
        });
    }

    private void datawedgeDisableWithErrorChecking()
    {
        addLineToResults("Disabling DataWedge Plugin");
        DWDataWedgeDisable dwdatawedgedisable = new DWDataWedgeDisable(MainActivity.this);
        DWProfileBaseSettings settings = new DWProfileBaseSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwdatawedgedisable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    // We will never see this one.... only errors are reported, no success callback
                    addLineToResults("Disabling datawedge on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error Disabling datawedge on profile: " + profileName + "\n" + resultInfo);
                }
            }
            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to disable datawedge on profile: " + profileName);
            }
        });
    }
    
    private void switchScannerParamsAsync(final boolean continuousMode)
    {
        addLineToResults(continuousMode ? "Switching to Continuous mode" : "Switching to normal mode");
        if(mProfileProcessingStartDate == null)
            mProfileProcessingStartDate = new Date();
        DWProfileSwitchBarcodeParams switchContinuousMode = new DWProfileSwitchBarcodeParams(MainActivity.this);

        /**
         * The switch param class will only change the settings that differs between the targetSettings and the previousSettings
         * In our case we hold the settings we used for profile creation in the member mNormalSettingsForSwitchParams
         * And we hold the aggressive settings in the member mAggressiveSettingsForSwitchParams
         * We will pass both settings to the switchBarcodeParams to allow comparison of both settings
         * This will ensure that we don't pass the whole settings parameters to the Intent
         */
        DWProfileSwitchBarcodeParamsSettings targetSettings;
        DWProfileSwitchBarcodeParamsSettings previousSettings;
        if(continuousMode)
        {
            targetSettings = DataWedgeSettingsHolder.mAggressiveSettingsForSwitchParams;
            previousSettings = DataWedgeSettingsHolder.mNormalSettingsForSwitchParams;
        }
        else
        {
            targetSettings = DataWedgeSettingsHolder.mNormalSettingsForSwitchParams;
            previousSettings = DataWedgeSettingsHolder.mAggressiveSettingsForSwitchParams;
        }

        // You can now use two different modes to switch params
        // The first mode use a single configuration object and switch all the settings (revert unset parameters to default)
        switchContinuousMode.execute(targetSettings, new DWProfileCommandBase.onProfileCommandResult() {
        // The second mode use two different configuration previous->target to switch only the difference between the members of each settings
        //switchContinuousMode.execute(previousSettings, targetSettings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    addLineToResults("Params switched to " + (continuousMode ? "continuous mode" : "normal mode") + " on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error switching params to " + (continuousMode ? "continuous mode" : "normal mode") + " on profile: " + profileName + "\n" + resultInfo);
                }
                addTotalTimeToResults();
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to switching params on profile: " + profileName);
            }
        });
    }

    private void deleteProfileAsync()
    {
        //sendDataWedgeIntentWithExtra(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2, DataWedgeConstants.EXTRA_DELETE_PROFILE, mDemoProfileName);
        addLineToResults("Deleting profile " + DataWedgeSettingsHolder.mDemoProfileName);

        mProfileProcessingStartDate = new Date();
        /*
        The profile delete will delete a profile if it exists
         */
        DWProfileDelete deleteProfile = new DWProfileDelete(this);

        // Setup profile checker parameters
        DWProfileDeleteSettings profileDeleteSettings = new DWProfileDeleteSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
            mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS;
        }};

        deleteProfile.execute(profileDeleteSettings, new DWProfileDelete.onProfileCommandResult(){
                @Override
                public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                    if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                    {
                        addLineToResults("Profile: " + profileName + " delete succeeded");
                    }
                    else
                    {
                        addLineToResults("Error while trying to delete profile: " + profileName + "\n" + resultInfo);
                    }
                    addTotalTimeToResults();
                }


                @Override
                public void timeout(String profileName) {
                    addLineToResults("Timeout while trying to delete profile: " + profileName);
                }
            }
        );
    }

    private void createProfileAsync()
    {
        DWProfileCreate profileCreate = new DWProfileCreate(MainActivity.this);

        DWProfileCreateSettings profileCreateSettings = new DWProfileCreateSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
            mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS;

        }};

        profileCreate.execute(profileCreateSettings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    addLineToResults("Profile: " + profileName + " created with success.\nSetting config now.");
                    setProfileConfigAsync();
                }
                else
                {
                    addLineToResults("Error creating profile: " + profileName + "\n" + resultInfo);
                    addTotalTimeToResults();
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to create profile: " + profileName);
            }
        });
    }

    private void setupScannerStatusChecker()
    {
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

        mScannerStatusChecker = new DWStatusScanner(MainActivity.this, profileStatusSettings);
        mScannerStatusChecker.start();
    }

    private void stopScannerStatusChecker()
    {
        mScannerStatusChecker.stop();
    }

    private void setProfileConfigAsync()
    {
        DWProfileSetConfig profileSetConfig = new DWProfileSetConfig(MainActivity.this);

        profileSetConfig.execute(DataWedgeSettingsHolder.mSetConfigSettings, new DWProfileCommandBase.onProfileCommandResult() {
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
                addTotalTimeToResults();
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to set params on profile: " + profileName);
            }
        });
    }

    private void setupProfileAsync()
    {
        mProfileProcessingStartDate = new Date();
        /*
        The profile checker will check if the profile already exists
         */
        DWProfileChecker checker = new DWProfileChecker(this);

        // Setup profile checker parameters
        DWProfileCheckerSettings profileCheckerSettings = new DWProfileCheckerSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
            mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS;
        }};

        // Execute the checker with the given parameters
        checker.execute(profileCheckerSettings, new DWProfileChecker.onProfileExistResult() {
            @Override
            public void result(String profileName, boolean exists) {
                // empty error... means... I let you guess....
                    // exists == true means that the profile already... exists..
                    if(exists)
                    {
                        addLineToResults("Profile " + profileName + " found in DW profiles list.\n Deleting profile before creating a new one.");
                        // the profile exists, so we are going to delete it.
                        // here is a sample on how you could directly inline the code (without using methods) and have the
                        // whole workflow at the same place
                        DWProfileDelete dwProfileDelete = new DWProfileDelete(MainActivity.this);
                        DWProfileDeleteSettings dwProfileDeleteSettings = new DWProfileDeleteSettings()
                        {{
                           mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
                           mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS;
                        }};
                        dwProfileDelete.execute(dwProfileDeleteSettings, new DWProfileCommandBase.onProfileCommandResult() {
                            @Override
                            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                                {
                                    addLineToResults("Profile: " + profileName + " deleted with success.\nCreating new profile now.");
                                    createProfileAsync();
                                }
                                else
                                {
                                    addLineToResults("Error deleting profile: " + profileName + "\n" + resultInfo);
                                    addTotalTimeToResults();
                                }
                            }

                            @Override
                            public void timeout(String profileName) {
                                addLineToResults("Timeout while trying to delete profile: " + profileName);
                            }
                        });
                    }
                    else
                    {
                        addLineToResults("Profile " + profileName + " not found in DW profiles list. Creating profile.");
                        createProfileAsync();
                    }
            }

            @Override
            public void timeOut(String profileName) {
                addLineToResults("Timeout while trying to check if profile " + profileName + " exists.");

            }
        });
    }

    private void addTotalTimeToResults()
    {
        if(mProfileProcessingStartDate != null)
        {
            Date current = new Date();
            long timeDiff = current.getTime() - mProfileProcessingStartDate.getTime();
            addLineToResults("Total time: " + timeDiff + "ms");
            mProfileProcessingStartDate = null;
        }
    }

    private void addLineToResults(final String lineToAdd)
    {
        mResults += lineToAdd + "\n";
        updateAndScrollDownTextView();
    }

    private void updateAndScrollDownTextView()
    {
        if(mOptmizeRefresh)
        {
            if(mScrollDownRunnable == null)
            {
                mScrollDownRunnable = new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
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
            }
            else
            {
                // A new line has been added while we were waiting to scroll down
                // reset handler to repost it....
                mScrollDownHandler.removeCallbacks(mScrollDownRunnable);
            }
            mScrollDownHandler.postDelayed(mScrollDownRunnable, 300);
        }
        else
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    et_results.setText(mResults);
                    sv_results.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

    }
}
