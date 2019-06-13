package com.zebra.datawedgeprofileintents.SQLiteDAO;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DWProfileDAOHelpers {
    private static String TAG = "DWProDAOHelp";

    public static void copyDataBaseFromAssetsToFolder(Context context, String assetNamePath, String destinatinFolder)
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
            String temporaryFileName = removeExtension(assetNamePath) + ".tmp";
            String finalFileName = removeExtension(assetNamePath) + ".db";

            fis = context.getAssets().open(finalFileName);

            // create a File object for the parent directory
            File outputDirectory = new File(destinatinFolder);

            // create a temporary File object for the output file
            outputFile = new File(outputDirectory,temporaryFileName);
            if(outputFile.exists())
            {
                outputFile.delete();
            }
            finalFile = new File(outputDirectory, finalFileName);
            if(finalFile.exists())
            {
                finalFile.delete();
            }

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

    public static String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path upto the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

}
