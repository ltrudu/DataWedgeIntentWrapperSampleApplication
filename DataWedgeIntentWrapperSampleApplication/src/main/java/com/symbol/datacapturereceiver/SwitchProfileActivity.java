package com.symbol.datacapturereceiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zebra.datawedgeprofileenums.INT_E_DELIVERY;
import com.zebra.datawedgeprofileenums.MB_E_CONFIG_MODE;
import com.zebra.datawedgeprofileenums.SC_E_SCANNER_IDENTIFIER;
import com.zebra.datawedgeprofileintents.DWProfileCommandBase;
import com.zebra.datawedgeprofileintents.DWProfileCreate;
import com.zebra.datawedgeprofileintents.DWProfileCreateSettings;
import com.zebra.datawedgeprofileintents.DWProfileSetConfigSettings;
import com.zebra.datawedgeprofileintents.DWScanReceiver;
import com.zebra.datawedgeprofileintents.DWStatusScanner;
import com.zebra.datawedgeprofileintents.DWStatusScannerCallback;
import com.zebra.datawedgeprofileintents.DWStatusScannerSettings;
import com.zebra.datawedgeprofileintents.DWStatusWorkflow;
import com.zebra.datawedgeprofileintents.DWStatusWorkflowCallback;
import com.zebra.datawedgeprofileintents.DWStatusWorkflowSettings;
import com.zebra.datawedgeprofileintents.DWSwitchToProfile;
import com.zebra.datawedgeprofileintents.DWSwitchToProfileSettings;
import com.zebra.datawedgeprofileintents.DataWedgeConstants;
import com.zebra.datawedgeprofileintentshelpers.CreateProfileHelper;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class SwitchProfileActivity extends AppCompatActivity implements DWScanReceiver.onScannedData {

    private static String TAG = "DataCaptureReceiver";

    private static String SCANNER_PROFILE_NAME = "scanner_profile";
    private static String WORKFLOW_PROFILE_NAME = "workflow_profile";

    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";

    /**
     * Scanner data receiver
     */
    DWScanReceiver mScanReceiver;

    DWStatusScanner mStatusReceiver;

    DWStatusWorkflow mWorkflowStatusReceiver;

    /*
        Handler and runnable to scroll down textview
     */
    private Handler mScrollDownHandler = null;
    private Runnable mScrollDownRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchprofile);

        setTitle("Switch Profile Activity");

        et_results = (TextView)findViewById(R.id.et_resultsSwitchProfileActivity);
        sv_results = (ScrollView)findViewById(R.id.sv_resultsSwitchProfileActivity);


        Button btClose = (Button)findViewById(R.id.button_closeSwitchActivity);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchProfileActivity.this.finish();
            }
        });

        Button btOpenThirdActivity = (Button)findViewById(R.id.button_openThirdActivity);
        btOpenThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SwitchProfileActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
        
        Button createScannerProfile = findViewById(R.id.button_create_scanner_profile);
        createScannerProfile.setOnClickListener(view -> {
                createScannerProfile();
        });
        
        Button createWorkflowProfile = findViewById(R.id.button_create_workflow);
        createWorkflowProfile.setOnClickListener(view -> {
            createWorkflowProfile();
        });

        Button switchToScanner = findViewById(R.id.button_switch_scanner);
        switchToScanner.setOnClickListener( view -> {
            switchToScanner();
        });

        Button switchtoWorkflow = findViewById(R.id.button_switch_workflow);
        switchtoWorkflow.setOnClickListener(view -> {
            switchtoWorkflow();
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

        DWStatusWorkflowSettings statusWorkflowSettings = new DWStatusWorkflowSettings(){{
            mPackageName = getPackageName();
            mWorkflowCallback = new DWStatusWorkflowCallback() {
                @Override
                public void result(String status) {
                    switch(status)
                    {
                        case DataWedgeConstants.WORKFLOW_STATUS_ENABLED:
                            addLineToResults("Workflow is enabled");
                            break;
                        case DataWedgeConstants.WORKFLOW_STATUS_DISABLED:
                            addLineToResults("Workflow is disabled");
                            break;
                        case DataWedgeConstants.WORKFLOW_STATUS_READY:
                            addLineToResults("Workflow is ready");
                            break;
                        case DataWedgeConstants.WORKFLOW_STATUS_CAPTURING_STARTED:
                            addLineToResults("Workflow capture has started");
                            break;
                        case DataWedgeConstants.WORKFLOW_STATUS_CAPTURING_STOPPED:
                            addLineToResults("Workflow capture has stopped");
                            break;
                        case DataWedgeConstants.WORKFLOW_STATUS_SESSION_STARTED:
                            addLineToResults("Workflow is started, waiting for scan trigger");
                            break;
                        default:
                            addLineToResults("Workflow status:" + status);
                    }

                }
            };
        }};

        mWorkflowStatusReceiver = new DWStatusWorkflow(this, statusWorkflowSettings);

    }

    private void switchtoWorkflow() {
        addLineToResults("Switching to scanner profile.");

        DWSwitchToProfileSettings settings = new DWSwitchToProfileSettings(){{
            mProfileName = WORKFLOW_PROFILE_NAME;
        }};

        DWSwitchToProfile dwSwitchToProfile = new DWSwitchToProfile(this);
        dwSwitchToProfile.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    addLineToResults("Profile switched with success: " + profileName);
                    addLineToResults("Waiting for Workflow to be ready.");
                }
                else
                {
                    addLineToResults("Error switching profile: " + profileName + "\nError: " + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Time out switching to profile: " + profileName);
            }
        });
    }

    private void switchToScanner() {
        addLineToResults("Switching to scanner profile.");
        DWSwitchToProfileSettings settings = new DWSwitchToProfileSettings(){{
            mProfileName = SCANNER_PROFILE_NAME;
        }};

        DWSwitchToProfile dwSwitchToProfile = new DWSwitchToProfile(this);
        dwSwitchToProfile.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    addLineToResults("Profile switched with success.");
                }
                else
                {
                    addLineToResults("Error switching profile: " + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Time out switching to profile: " + profileName);
            }
        });
    }

    private void createScannerProfile() {
        DWProfileSetConfigSettings settings = new DWProfileSetConfigSettings(){{
            mProfileName = SCANNER_PROFILE_NAME;
            mTimeOutMS = 5000;
            /* !!!!!!!!!! We remove the association with an app to make the profile switchable !!!!!!!!!!!!!!!!!*/
            /* The trick is to set a profile without application package name and activity list */
            //MainBundle.APP_LIST = new HashMap<>();
            //MainBundle.APP_LIST.put(getPackageName(), null);
            MainBundle.CONFIG_MODE = MB_E_CONFIG_MODE.CREATE_IF_NOT_EXIST;
            IntentPlugin.use_component = true; /* !!!!! Prevents the applicaton to fill the package name and app list to make the profile switchable !!!!!! */
            IntentPlugin.intent_action = DataWedgeSettingsHolder.mDemoIntentAction;
            IntentPlugin.intent_category = DataWedgeSettingsHolder.mDemoIntentCategory;
            IntentPlugin.intent_output_enabled = true;
            IntentPlugin.intent_delivery = INT_E_DELIVERY.BROADCAST;
            KeystrokePlugin.keystroke_output_enabled = false;
            RFIDPlugin.rfid_input_enabled = false;
            ScannerPlugin.scanner_selection_by_identifier = SC_E_SCANNER_IDENTIFIER.INTERNAL_IMAGER;
            ScannerPlugin.scanner_input_enabled = true;
            ScannerPlugin.Decoders.decoder_aztec = true;
            ScannerPlugin.Decoders.decoder_micropdf = true;
            ScannerPlugin.Decoders.decoder_qrcode = true;
            ScannerPlugin.Decoders.decoder_ean8 = false;
            ScannerPlugin.Decoders.decoder_ean13 = false;
            ScannerPlugin.Decoders.decoder_code128 = true;
        }};

        CreateProfileHelper.createProfile(this, settings, new CreateProfileHelper.CreateProfileHelperCallback() {
            @Override
            public void onSuccess(String profileName) {
                addLineToResults("Success creating profile: " + profileName);
            }

            @Override
            public void onError(String profileName, String error, String errorMessage) {
                addLineToResults("Error on creating profile: " + profileName);
            }

            @Override
            public void ondebugMessage(String profileName, String message) {
                addLineToResults(message);
            }
        });
    }

    @Override
    public void scannedData(String source, String data, String typology) {
        addLineToResults("Typology: " + typology+ ", Data: " + data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanReceiver.startReceive();
        mStatusReceiver.start();
        mWorkflowStatusReceiver.start();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        mScanReceiver.stopReceive();
        mStatusReceiver.stop();
        mWorkflowStatusReceiver.stop();

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
                    SwitchProfileActivity.this.runOnUiThread(new Runnable() {
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

    private void createWorkflowProfile() {
        // Create profile with DWWrapper
        DWProfileCreateSettings settings = new DWProfileCreateSettings(){{
           mProfileName = WORKFLOW_PROFILE_NAME;
        }};
        DWProfileCreate create = new DWProfileCreate(this);
        create.execute(settings, new DWProfileCommandBase.onProfileCommandResult() {
            @Override
            public void result(String profileName, String action, String command, String result, String resultInfo, String commandidentifier) {
                if(result.equalsIgnoreCase(DataWedgeConstants.COMMAND_RESULT_SUCCESS))
                {
                    Bundle bMain = new Bundle();

                    Bundle bConfigWorkflow = new Bundle();
                    ArrayList<Bundle> bundlePluginConfig = new ArrayList<>();

                    /*###### Configurations for Workflow Input [Start] ######*/
                    bConfigWorkflow.putString("PLUGIN_NAME", "WORKFLOW");
                    bConfigWorkflow.putString("RESET_CONFIG", "true"); //Reset existing configurations of barcode input plugin

                    bConfigWorkflow.putString("workflow_input_enabled", "true");
                    bConfigWorkflow.putString("selected_workflow_name", "free_form_capture");
                    bConfigWorkflow.putString("workflow_input_source", "2"); //input source 1- imager, 2- camera

                    /*###### Configurations for Workflow Input [Finish] ######*/

                    Bundle paramList = new Bundle();
                    paramList.putString("workflow_name","free_form_capture");
                    paramList.putString("workflow_input_source","2");

                    Bundle paramSetContainerDecoderModule = new Bundle();
                    paramSetContainerDecoderModule.putString("module","BarcodeTrackerModule");
                    Bundle moduleContainerDecoderModule = new Bundle();
                    moduleContainerDecoderModule.putString("session_timeout", "16000");
                    moduleContainerDecoderModule.putString("illumination", "off");
                    moduleContainerDecoderModule.putString("decode_and_highlight_barcodes", "1"); //1-off, 2-highlight, 3- decode and highlight
                    paramSetContainerDecoderModule.putBundle("module_params",moduleContainerDecoderModule);

                    Bundle paramSetFeedbackModule = new Bundle();
                    paramSetFeedbackModule.putString("module","FeedbackModule");
                    Bundle moduleParamsFeedback = new Bundle();
                    moduleParamsFeedback.putString("decode_haptic_feedback", "false");
                    moduleParamsFeedback.putString("decode_audio_feedback_uri", "Electra");
                    moduleParamsFeedback.putString("volume_slider_type", "0");// 0- Ringer, 1- Music and Media, 2-Alarms, 3- Notification
                    moduleParamsFeedback.putString("decoding_led_feedback", "true");
                    paramSetFeedbackModule.putBundle("module_params",moduleParamsFeedback);

                    ArrayList<Bundle> paramSetList = new ArrayList<>();
                    paramSetList.add(paramSetContainerDecoderModule);
                    paramSetList.add(paramSetFeedbackModule);

                    paramList.putParcelableArrayList("workflow_params", paramSetList);

                    ArrayList<Bundle> workFlowList = new ArrayList<>();
                    workFlowList.add(paramList);

                    bConfigWorkflow.putParcelableArrayList("PARAM_LIST", workFlowList);
                    bundlePluginConfig.add(bConfigWorkflow);


                    /*###### Configurations for Intent Output [Start] ######*/
                    Bundle bConfigIntent = new Bundle();
                    Bundle bParamsIntent = new Bundle();
                    bConfigIntent.putString("PLUGIN_NAME", "INTENT");
                    bConfigIntent.putString("RESET_CONFIG", "true"); //Reset existing configurations of intent output plugin
                    bParamsIntent.putString("intent_output_enabled", "true"); //Enable intent output plugin
                    bParamsIntent.putString("intent_action", DataWedgeSettingsHolder.mDemoIntentAction); //Set the intent action
                    bParamsIntent.putString("intent_category", "android.intent.category.DEFAULT"); //Set a category for intent
                    bParamsIntent.putInt("intent_delivery", 2); // Set intent delivery mechanism, Use "0" for Start Activity, "1" for Start Service, "2" for Broadcast, "3" for start foreground service
                    bParamsIntent.putString("intent_use_content_provider", "true"); //Enable content provider
                    bConfigIntent.putBundle("PARAM_LIST", bParamsIntent);
                    bundlePluginConfig.add(bConfigIntent);
                    /*###### Configurations for Intent Output [Finish] ######*/

                    //Putting the INTENT and BARCODE plugin settings to the PLUGIN_CONFIG extra
                    bMain.putParcelableArrayList("PLUGIN_CONFIG", bundlePluginConfig);


                    /* !!!!!!!!!!!!!!!!!! We removed app association so the profile can be switched later !!!!!!!!!!!!!!!!!! */
                    /*###### Associate this application to the profile [Start] ######*/
                    /*
                    Bundle configApplicationList = new Bundle();
                    configApplicationList.putString("PACKAGE_NAME",getPackageName());
                    configApplicationList.putStringArray("ACTIVITY_LIST", new String[]{"*"});
                    bMain.putParcelableArray("APP_LIST", new Bundle[]{
                            configApplicationList
                    });
                    */

                    /* ###### Associate this application to the profile [Finish] ######*/

                    bMain.putString("PROFILE_NAME", WORKFLOW_PROFILE_NAME); //Specify the profile name
                    bMain.putString("PROFILE_ENABLED", "true"); //Enable the profile
                    bMain.putString("CONFIG_MODE", "CREATE_IF_NOT_EXIST");
                    bMain.putString("RESET_CONFIG", "true");

                    Intent iSetConfig = new Intent();

                    iSetConfig.setAction("com.symbol.datawedge.api.ACTION");
                    iSetConfig.setPackage("com.symbol.datawedge");
                    iSetConfig.putExtra("com.symbol.datawedge.api.SET_CONFIG", bMain);
                    iSetConfig.putExtra("SEND_RESULT", "COMPLETE_RESULT");
                    iSetConfig.putExtra("COMMAND_IDENTIFIER",
                            "CREATE_PROFILE");

                    SwitchProfileActivity.this.sendBroadcast(iSetConfig);
                }
                else
                {
                    addLineToResults("Error while creating workflow profile: " + resultInfo);
                }
            }

            @Override
            public void timeout(String profileName) {
                addLineToResults("Timeout while creating profile: " + WORKFLOW_PROFILE_NAME);
            }
        });
    }
}
