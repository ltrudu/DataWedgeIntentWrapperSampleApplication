package com.zebra.datawedgeintentwrapperkotlinsampleapplication

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.zebra.datawedgeprofileintents.DWProfileBaseSettings
import com.zebra.datawedgeprofileintents.DWProfileChecker
import com.zebra.datawedgeprofileintents.DWProfileChecker.onProfileExistResult
import com.zebra.datawedgeprofileintents.DWProfileCheckerSettings
import com.zebra.datawedgeprofileintents.DWProfileCommandBase.onProfileCommandResult
import com.zebra.datawedgeprofileintents.DWProfileCreate
import com.zebra.datawedgeprofileintents.DWProfileCreateSettings
import com.zebra.datawedgeprofileintents.DWProfileDelete
import com.zebra.datawedgeprofileintents.DWProfileDeleteSettings
import com.zebra.datawedgeprofileintents.DWProfileSetConfig
import com.zebra.datawedgeprofileintents.DWScanReceiver
import com.zebra.datawedgeprofileintents.DWScannerPluginDisable
import com.zebra.datawedgeprofileintents.DWScannerPluginEnable
import com.zebra.datawedgeprofileintents.DataWedgeConstants
import java.util.Date

class MainActivity : ComponentActivity() {

    var mScanReceiver: DWScanReceiver? = null

    var et_results: TextView? = null
    var sv_results: ScrollView? = null
    var mResults = ""

    /*
        Handler and runnable to scroll down textview
     */
    var mScrollDownHandler: Handler? = null
    var mScrollDownRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_results = findViewById(R.id.et_results)
        sv_results = findViewById(R.id.sv_results)

        mScrollDownHandler = Handler(Looper.getMainLooper())

        DataWedgeSettingsHolder.initSettings(this)

        findViewById<Button>(R.id.bt_createProfile).setOnClickListener(View.OnClickListener { view ->
            setupProfileAsync(this)
        })

        findViewById<Button>(R.id.bt_deleteProfile).setOnClickListener(View.OnClickListener { view ->
            deleteProfileAsync()
        })
        /**
         * Initialize the scan receiver
         */
        mScanReceiver = DWScanReceiver(
            this,
            DataWedgeSettingsHolder.mDemoIntentAction,
            DataWedgeSettingsHolder.mDemoIntentCategory,
            false,  // Displays special chars between brackets
            // You can inline the code here (like in this exampleà, or make the current activity
            // extends the interface DWScanReceiver.onScannedData then pass directly the
            // "this" reference instead
            { source, data, typology -> // Source contains the source of the scan (scanner, camera, bluetoothscanner)
                // data contains the data that has been scanned
                // typology contains the typology of what has been scanned
                addLineToResults("Typology: $typology, Data: $data")
                addLineToResults("Disabling plugin for 3s")
                pluginDisableWithErrorChecking(this)
                // Simulate web service call
                Thread.sleep(3000)
                addLineToResults("3s has passed")
                addLineToResults("Enabling plugin.")
                pluginEnableWithErrorChecking(this)
            },
            true
        )
    }

    override fun onResume() {
        super.onResume()
        mScanReceiver?.startReceive()
        if(mScrollDownHandler == null)
            mScrollDownHandler = Handler(Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        if (mScrollDownRunnable != null) {
            mScrollDownHandler!!.removeCallbacks(mScrollDownRunnable!!)
            mScrollDownRunnable = null
            mScrollDownHandler = null
        }
        mScanReceiver?.stopReceive()
    }
    private fun addLineToResults(lineToAdd: String) {
        println(lineToAdd)
        mResults += lineToAdd + "\n"
        updateAndScrollDownTextView()

    }


    private fun updateAndScrollDownTextView() {
        if (mScrollDownRunnable == null) {
            mScrollDownRunnable = Runnable {
                this.runOnUiThread(Runnable {
                    et_results?.setText(mResults)
                    sv_results?.post(Runnable { sv_results!!.fullScroll(ScrollView.FOCUS_DOWN) })
                })
            }
        } else {
            // A new line has been added while we were waiting to scroll down
            // reset handler to repost it....
            mScrollDownHandler?.removeCallbacks(mScrollDownRunnable!!)
        }
        if (mScrollDownHandler != null) mScrollDownHandler?.postDelayed(mScrollDownRunnable!!, 300)
    }

    private fun setupProfileAsync(context : Context) {
        /*
            The profile checker will check if the profile already exists
             */
        val checker = DWProfileChecker(context)

        // Setup profile checker parameters
        val profileCheckerSettings: DWProfileCheckerSettings = object : DWProfileCheckerSettings() {
            init {
                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
                mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS
            }
        }

        // Execute the checker with the given parameters
        checker.execute(profileCheckerSettings, object : onProfileExistResult {
            override fun result(profileName: String, exists: Boolean) {
                // empty error... means... I let you guess....
                // exists == true means that the profile already... exists..
                if (exists) {
                    addLineToResults("Profile $profileName found in DW profiles list.\n Deleting profile before creating a new one.")
                    // the profile exists, so we are going to delete it.
                    // here is a sample on how you could directly inline the code (without using methods) and have the
                    // whole workflow at the same place
                    val dwProfileDelete = DWProfileDelete(context)
                    val dwProfileDeleteSettings: DWProfileDeleteSettings =
                        object : DWProfileDeleteSettings() {
                            init {
                                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
                                mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS
                            }
                        }
                    dwProfileDelete.execute(dwProfileDeleteSettings, object : onProfileCommandResult {
                        override fun result(
                            profileName: String,
                            action: String,
                            command: String,
                            result: String,
                            resultInfo: String,
                            commandidentifier: String
                        ) {
                            if (result.equals(
                                    DataWedgeConstants.COMMAND_RESULT_SUCCESS,
                                    ignoreCase = true
                                )
                            ) {
                                addLineToResults("Profile: $profileName deleted with success.\nCreating new profile now.")
                                createProfileAsync(context)
                            } else {
                                addLineToResults("Error deleting profile: $profileName\n$resultInfo")
                            }
                        }

                        override fun timeout(profileName: String) {
                            addLineToResults("Timeout while trying to delete profile: $profileName")
                        }
                    })
                } else {
                    addLineToResults("Profile $profileName not found in DW profiles list. Creating profile.")
                    createProfileAsync(context)
                }
            }

            override fun timeOut(profileName: String) {
                addLineToResults("Timeout while trying to check if profile $profileName exists.")
            }
        })
    }

    private fun createProfileAsync(context:Context) {
        val profileCreate = DWProfileCreate(context)
        val profileCreateSettings: DWProfileCreateSettings = object : DWProfileCreateSettings() {
            init {
                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
                mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS
            }
        }
        profileCreate.execute(profileCreateSettings, object : onProfileCommandResult {
            override fun result(
                profileName: String,
                action: String,
                command: String,
                result: String,
                resultInfo: String,
                commandidentifier: String
            ) {
                if (result.equals(DataWedgeConstants.COMMAND_RESULT_SUCCESS, ignoreCase = true)) {
                    addLineToResults("Profile: $profileName created with success.\nSetting config now.")
                    setProfileConfigAsync(context)
                } else {
                    addLineToResults("Error creating profile: $profileName\n$resultInfo")
                }
            }

            override fun timeout(profileName: String) {
                addLineToResults("Timeout while trying to create profile: $profileName")
            }
        })
    }

    private fun setProfileConfigAsync(context: Context) {
        val profileSetConfig = DWProfileSetConfig(context)
        profileSetConfig.execute(DataWedgeSettingsHolder.mSetConfigSettings,
            object : onProfileCommandResult {
                override fun result(
                    profileName: String,
                    action: String,
                    command: String,
                    result: String,
                    resultInfo: String,
                    commandidentifier: String
                ) {
                    if (result.equals(DataWedgeConstants.COMMAND_RESULT_SUCCESS, ignoreCase = true)) {
                        addLineToResults("Set config on profile: $profileName succeeded.")
                    } else {
                        addLineToResults("Error setting params on profile: $profileName\n$resultInfo")
                    }
                }

                override fun timeout(profileName: String) {
                    addLineToResults("Timeout while trying to set params on profile: $profileName")
                }
            })
    }

    private fun deleteProfileAsync() {
        //sendDataWedgeIntentWithExtra(DataWedgeConstants.ACTION_DATAWEDGE_FROM_6_2, DataWedgeConstants.EXTRA_DELETE_PROFILE, mDemoProfileName);
        addLineToResults("Deleting profile " + DataWedgeSettingsHolder.mDemoProfileName)
        /*
        The profile delete will delete a profile if it exists
         */
        val deleteProfile = DWProfileDelete(this)

        // Setup profile checker parameters
        val profileDeleteSettings: DWProfileDeleteSettings = object : DWProfileDeleteSettings() {
            init {
                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
                mTimeOutMS = DataWedgeSettingsHolder.mDemoTimeOutMS
            }
        }
        deleteProfile.execute(profileDeleteSettings, object : onProfileCommandResult {
            override fun result(
                profileName: String,
                action: String,
                command: String,
                result: String,
                resultInfo: String,
                commandidentifier: String
            ) {
                if (result.equals(DataWedgeConstants.COMMAND_RESULT_SUCCESS, ignoreCase = true)) {
                    addLineToResults("Profile: $profileName delete succeeded")
                } else {
                    addLineToResults("Error while trying to delete profile: $profileName\n$resultInfo")
                }
            }

            override fun timeout(profileName: String) {
                addLineToResults("Timeout while trying to delete profile: $profileName")
            }
        }
        )
    }


    private fun pluginEnableWithErrorChecking(context:Context) {
        addLineToResults("Enabling DataWedge Plugin")
        val dwpluginenable = DWScannerPluginEnable(context)
        val settings: DWProfileBaseSettings = object : DWProfileBaseSettings() {
            init {
                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
            }
        }
        dwpluginenable.execute(settings, object : onProfileCommandResult {
            override fun result(
                profileName: String,
                action: String,
                command: String,
                result: String,
                resultInfo: String,
                commandidentifier: String
            ) {
                if (result.equals(DataWedgeConstants.COMMAND_RESULT_SUCCESS, ignoreCase = true)) {
                    addLineToResults("Enabling plugin on profile: $profileName succeeded")
                } else {
                    addLineToResults("Error Enabling plugin on profile: $profileName\n$resultInfo")
                }
            }

            override fun timeout(profileName: String) {
                addLineToResults("Timeout while trying to enable plugin on profile: $profileName")
            }
        })
    }

    private fun pluginDisableWithErrorChecking(context: Context) {
        addLineToResults("Disabling DataWedge Plugin")
        val dwplugindisable = DWScannerPluginDisable(context)
        val settings: DWProfileBaseSettings = object : DWProfileBaseSettings() {
            init {
                mProfileName = DataWedgeSettingsHolder.mDemoProfileName
            }
        }
        dwplugindisable.execute(settings, object : onProfileCommandResult {
            override fun result(
                profileName: String,
                action: String,
                command: String,
                result: String,
                resultInfo: String,
                commandidentifier: String
            ) {
                if (result.equals(DataWedgeConstants.COMMAND_RESULT_SUCCESS, ignoreCase = true)) {
                    addLineToResults("Disabling plugin on profile: $profileName succeeded")
                } else {
                    addLineToResults("Error Disabling plugin on profile: $profileName\n$resultInfo")
                }
            }

            override fun timeout(profileName: String) {
                addLineToResults("Timeout while trying to disable plugin on profile: $profileName")
            }
        })
    }
}

