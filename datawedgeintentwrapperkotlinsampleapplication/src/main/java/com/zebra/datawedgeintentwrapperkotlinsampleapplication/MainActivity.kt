package com.zebra.datawedgeintentwrapperkotlinsampleapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zebra.datawedgeintentwrapperkotlinsampleapplication.ui.theme.DataWedgeIntentWrapperSampleApplicationTheme
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

class MainActivity : ComponentActivity() {

    var mScanReceiver: DWScanReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataWedgeIntentWrapperSampleApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        DataWedgeSettingsHolder.initSettings(this)
        setupProfileAsync(this)
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
                pluginDisableWithErrorChecking(this)
                // Simulate web service call
                Thread.sleep(3000)
                pluginEnableWithErrorChecking(this)
            },
            true
        )
    }

    override fun onResume() {
        super.onResume()
        pluginEnableWithErrorChecking(this)
        mScanReceiver?.startReceive()
    }

    override fun onPause() {
        super.onPause()
        mScanReceiver?.stopReceive()
        pluginDisableWithErrorChecking(this)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DataWedgeIntentWrapperSampleApplicationTheme {
        Greeting("Android")
    }
}



private fun addLineToResults(lineToAdd: String) {
    println(lineToAdd)
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

