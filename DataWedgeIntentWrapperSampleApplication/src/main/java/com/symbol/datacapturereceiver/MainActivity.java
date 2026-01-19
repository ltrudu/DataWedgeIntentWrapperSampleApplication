package com.symbol.datacapturereceiver;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNER_IDENTIFIER;
import com.zebra.datawedgeprofileintents.*;
import com.zebra.datawedgeprofileintents.SynchronousMethodsNT.DWSynchronousMethodsNT;
import com.zebra.datawedgeprofileintentshelpers.CreateProfileHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

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
    private Spinner sp_ScannerDevices = null;
    private List<DWEnumerateScanners.Scanner> mScannerList = null;
    private int mScannerIndex = 0;
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

        sp_ScannerDevices = (Spinner)findViewById(R.id.spinnerScannerDevices);
        sp_ScannerDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3) {
                if (mScannerIndex != position) {
                    final DWEnumerateScanners.Scanner selectedScanner = mScannerList.get(position);
                    DWProfileSetConfigSettings settings = new DWProfileSetConfigSettings() {{
                        mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
                        MainBundle.CONFIG_MODE = MB_E_CONFIG_MODE.UPDATE;
                        ScannerPlugin.scanner_selection_by_identifier = selectedScanner.mScannerIdentifier;
                    }};
                    DWProfileSetConfig dwProfileSetConfig = new DWProfileSetConfig(MainActivity.this);
                    final int fPosition = position;
                    dwProfileSetConfig.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
                        @Override
                        public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                            if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS)) {
                                addLineToResults("New scanner selected with success:" + selectedScanner.mName);
                                mScannerIndex = fPosition;
                            }
                            else
                            {
                                addLineToResults("Error while trying to switch to new scanner:" + selectedScanner.mName);
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.this.sp_ScannerDevices.setSelection(mScannerIndex);
                                    }
                                });
                            }
                        }

                        @Override
                        public void timeout(String profileName) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected (AdapterView < ? > arg0){
            }
        });

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
                setupProfileAsync();
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

        Button btSwitchSync = (Button) findViewById(R.id.button_switch_sync);
        btSwitchSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContinuousModeSwitch = !mContinuousModeSwitch;
                switchScannerParamsSync(mContinuousModeSwitch);
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

        Button btEasyCreateProfile = (Button) findViewById(R.id.button_create_easy);
        btEasyCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyCreateProfile();
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

        Button btOpenRFIDActivity = (Button)findViewById(R.id.button_openRFIDActivity);
        btOpenRFIDActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RFIDActivity.class);
                startActivity(intent);
            }
        });

        Button btOpenSwitchActivity = (Button)findViewById(R.id.button_maOpenSwitchProfilesActivity);
        btOpenSwitchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SwitchProfileActivity.class);
                startActivity(intent);
            }
        });

        Button btEnumerateScannersSync = (Button)findViewById(R.id.button_enumerateSync);
        btEnumerateScannersSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // In this setup we'll use the NT (new thread methods), if you are using this wrapper in a service
                // thats use threading, you may encounter ANR using the DWSynchronousMethods, the "ugly" implementation
                // of DWSynchronousMethodsNT prevent this.
                // When you create it, one constructor takes a long parameter that represents the thread sleep used to wait
                // for the command to finish.
                DWSynchronousMethodsNT dwSynchronousMethodsNT = new DWSynchronousMethodsNT(MainActivity.this);
                DWEnumerateScannersSettings dwEnumerateScannersSettings = new DWEnumerateScannersSettings();
                Pair<DWSynchronousMethods.EResults, List<DWEnumerateScanners.Scanner>> returnValue = dwSynchronousMethodsNT.enumerateScanners(dwEnumerateScannersSettings);
                if(returnValue != null && returnValue.first == DWSynchronousMethods.EResults.SUCCEEDED) {
                    List<DWEnumerateScanners.Scanner> scannerList = returnValue.second;
                    if(scannerList != null && scannerList.size() > 0)
                    {
                        addLineToResults("Enumeration of scanner succeeded:");
                        for(DWEnumerateScanners.Scanner scanner : scannerList)
                        {
                            addLineToResults("Scanner name: " + scanner.mName + "\nScanner identifier:" + scanner.mScannerIdentifier);
                        }
                        addLineToResults("End of scanner enumeration.");
                    }
                }
                else
                {
                    addLineToResults("Error while trying to enumerate scanners.");
                }
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
                // extends the interface DWScanReceiver.onRFIDData then pass directly the
                // "this" reference instead
                new DWScanReceiver.onScannedData() {
                    @Override
                    public void scannedData(String source, String data, String typology) {
                        // Source contains the source of the scan (scanner, camera, bluetoothscanner)
                        // data contains the data that has been scanned
                        // typology contains the typology of what has been scanned
                        addLineToResults("Typology: " + typology+ ", Data: " + data);
                    }
                },
                true
        );

        /*
        // Use this to check if the toJson and fromJson work correctly
        DWProfileSetConfigSettings testSettings = new DWProfileSetConfigSettings();
        String json = DWProfileSetConfigSettings.toJson(testSettings);
        DWProfileSetConfigSettings returnSettings = DWProfileSetConfigSettings.fromJson(json);
        String profilName = returnSettings.mProfileName;
        */
    }

    private void easyCreateProfile() {
        mProfileProcessingStartDate = new Date();
        addLineToResults("Creating a profile using CreateProfileHelper.");
        CreateProfileHelper.createProfile(MainActivity.this, DataWedgeSettingsHolder.mSetConfigSettingsScanner, new CreateProfileHelper.CreateProfileHelperCallback() {
            @Override
            public void onSuccess(String profileName) {
                addLineToResults("Easy creation of profile:" + profileName + " succeeded.");
                addTotalTimeToResults();
            }

            @Override
            public void onError(String profileName, String error, String errorMessage) {
                addLineToResults("Error while trying to create profile:" + profileName);
                addLineToResults("Error:" + error);
                addLineToResults("ErrorMessage:" + errorMessage);
            }

            @Override
            public void ondebugMessage(String profileName, String message) {
                addLineToResults("Debug:" + message);
            }
        });
    }

    private void enumerateScannerDevices() {
        addLineToResults("Enumerating scanners.");
        DWEnumerateScanners dwEnumerateScanners = new DWEnumerateScanners(this);
        DWEnumerateScannersSettings settings = new DWEnumerateScannersSettings()
        {{
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};
        dwEnumerateScanners.execute(settings, new DWEnumerateScanners.onEnumerateScannerResult() {
            @Override
            public void result(String profileName, List<DWEnumerateScanners.Scanner> scannerList) {
                List<String> friendlyNameList = new ArrayList<String>();
                mScannerList = new ArrayList<DWEnumerateScanners.Scanner>(scannerList);
                int spinnerIndex = 0;
                if ((scannerList != null) && (scannerList.size() != 0)) {
                    addLineToResults("Scanner enumeration succeeded, found " + scannerList.size() + " scanners.");
                    Iterator<DWEnumerateScanners.Scanner> it = scannerList.iterator();
                    while(it.hasNext()) {
                        DWEnumerateScanners.Scanner scanner = it.next();
                        friendlyNameList.add(scanner.mName);
                        ++spinnerIndex;
                    }
                }
                else {
                    addLineToResults("Failed to get the list of supported scanner devices! Please close and restart the application.");
                }

                // Add auto scanner selection
                DWEnumerateScanners.Scanner autoScanner = new DWEnumerateScanners.Scanner();
                autoScanner.mName = "AUTO";
                autoScanner.mScannerIdentifier = SC_E_SCANNER_IDENTIFIER.AUTO;
                friendlyNameList.add(0, autoScanner.mName);
                mScannerList.add(0, autoScanner);
                mScannerIndex = 0;

                final int fDefaultIndex = mScannerIndex;
                final List<String> fFriendlyNameList = friendlyNameList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, fFriendlyNameList);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_ScannerDevices.setAdapter(spinnerAdapter);
                        sp_ScannerDevices.setSelection(fDefaultIndex);
                        mScannerIndex = fDefaultIndex;
                    }
                });
            }

            @Override
            public void timeOut(String profileName) {
                addLineToResults("Timeout while trying to enumerate scanners");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanReceiver.startReceive();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
        setupScannerStatusChecker();
        // Enumerate scanners
        enumerateScannerDevices();
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
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwpluginenable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
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
            mProfileName = DataWedgeSettingsHolder.mDemoProfileName;
        }};

        dwplugindisable.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
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
    
    private void switchScannerParamsAsync(final boolean mode)
    {
        addLineToResults(mode ? "Switching to Restricted mode" : "Switching to normal mode");
        if(mProfileProcessingStartDate == null)
            mProfileProcessingStartDate = new Date();
        DWProfileSwitchBarcodeParams switchRestrictedMode = new DWProfileSwitchBarcodeParams(MainActivity.this);

        /**
         * The switch param class will only change the settings inside targetSettings
         * In our case we hold the settings we used for profile creation in the member mNormalSettingsForSwitchParams
         * And we hold the restricted settings in the member mRestrictedSettingsForSwitchParams
         */
        DWProfileSwitchBarcodeParamsSettings targetSettings;
        if(mode)
        {
            targetSettings = DataWedgeSettingsHolder.mRestrictedSettingsForSwitchParams;
        }
        else
        {
            targetSettings = DataWedgeSettingsHolder.mNormalSettingsForSwitchParams;
        }

        // You can now use two different modes to switch params
        // The first mode use a single configuration object and switch all the settings (revert unset parameters to default)
        switchRestrictedMode.execute(targetSettings, new DWProfileCommandBase.onProfileCommandResult() {
        // The second mode use two different configuration previous->target to switch only the difference between the members of each settings
        //switchRestrictedMode.execute(previousSettings, targetSettings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    addLineToResults("Params switched to " + (mode ? "restricted mode" : "normal mode") + " on profile: " + profileName + " succeeded");
                }
                else
                {
                    addLineToResults("Error switching params to " + (mode ? "restricted mode" : "normal mode") + " on profile: " + profileName + "\n" + resultInfo);
                }
                addTotalTimeToResults();
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while trying to switching params on profile: " + profileName);
            }
        });
    }

   private void switchScannerParamsSync(final boolean mode)
    {
        addLineToResults(mode ? "Switching to Restricted mode" : "Switching to normal mode");
        if(mProfileProcessingStartDate == null)
            mProfileProcessingStartDate = new Date();

        /**
         * The switch param class will only change the settings inside targetSettings
         * In our case we hold the settings we used for profile creation in the member mNormalSettingsForSwitchParams
         * And we hold the restricted settings in the member mRestrictedSettingsForSwitchParams
         */
        DWProfileSwitchBarcodeParamsSettings targetSettings;
        if(mode)
        {
            targetSettings = DataWedgeSettingsHolder.mRestrictedSettingsForSwitchParams;
        }
        else
        {
            targetSettings = DataWedgeSettingsHolder.mNormalSettingsForSwitchParams;
        }

        Pair<DWSynchronousMethods.EResults, String> returnValue = null;
        DWSynchronousMethodsNT dwSynchronousMethodsNT = new DWSynchronousMethodsNT(this);

        addLineToResults("Switching parameters");
        returnValue = dwSynchronousMethodsNT.switchBarcodeParams(targetSettings);
        if(returnValue.first == DWSynchronousMethods.EResults.FAILED)
        {
            addLineToResults("Failed to switch barcode params" + returnValue.second);
        }
        else
            addLineToResults("Barcode params switched successfully");
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
        DWProfileCreate profileCreate = new DWProfileCreate(this);

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

        profileSetConfig.execute(DataWedgeSettingsHolder.mSetConfigSettingsScanner, new DWProfileCommandBase.onProfileCommandResult() {
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

    private void setupProfileSync()
    {
        mProfileProcessingStartDate = new Date();
        Date totalTimeStart = mProfileProcessingStartDate;
        DWSynchronousMethodsNT dwSynchronousMethods = new DWSynchronousMethodsNT(this);
        Pair<DWSynchronousMethods.EResults,String> result = dwSynchronousMethods.profileExists();
        addLineToResults("Total time to check if profile exists:");
        addTotalTimeToResults();
        if(result.first == DWSynchronousMethods.EResults.SUCCEEDED)
        {
            // the profile exists
            // we delete it
            mProfileProcessingStartDate = new Date();
            dwSynchronousMethods.deleteProfile();
            addLineToResults("Total time to delete profile.");
            addTotalTimeToResults();
        }
        // we create and setup the profile
        mProfileProcessingStartDate = new Date();
        dwSynchronousMethods.setupDWProfile(DataWedgeSettingsHolder.mSetConfigSettingsScanner);
        addLineToResults("Total time to setup profile.");
        addTotalTimeToResults();
        addLineToResults("Total time to fully create and setup profile in synchronous mode:");
        mProfileProcessingStartDate = totalTimeStart;
        addTotalTimeToResults();
    }

    private void setupProfileSyncFast()
    {
        mProfileProcessingStartDate = new Date();
        Date totalTimeStart = mProfileProcessingStartDate;
        DWSynchronousMethodsNT dwSynchronousMethods = new DWSynchronousMethodsNT(this);
        mProfileProcessingStartDate = new Date();
        dwSynchronousMethods.deleteProfile();
        addLineToResults("Total time to delete profile.");
        addTotalTimeToResults();
        // we create and setup the profile
        mProfileProcessingStartDate = new Date();
        dwSynchronousMethods.setupDWProfile(DataWedgeSettingsHolder.mSetConfigSettingsScanner);
        addLineToResults("Total time to setup profile.");
        addTotalTimeToResults();
        addLineToResults("Total time to fully create and setup profile in synchronous mode:");
        mProfileProcessingStartDate = totalTimeStart;
        addTotalTimeToResults();
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
