package cz.pikadorama.framework.util;

import android.util.Log;

import com.google.common.io.ByteStreams;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import cz.pikadorama.framework.Const;

/**
 * Created by Tomas on 14.9.2015.
 */
public class Archives {

    public static void unzip(File fileToUnzip, File targetDirectory) {
        try {
            ZipFile zipFile = new ZipFile(fileToUnzip);
            zipFile.extractAll(targetDirectory.getAbsolutePath());
        } catch (ZipException e) {
            Log.e(Const.TAG, e.getMessage(), e);
        }
//        try (ZipInputStream zipInputStream = new ZipInputStream(
//                new BufferedInputStream(new FileInputStream(zipFile)))) {
//            ZipEntry zipEntry;
//            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//                File outputFile = new File(targetDirectory, zipEntry.getName());
//                File outputFileParentDir = zipEntry.isDirectory() ? outputFile : outputFile.getParentFile();
//
//                if (!outputFileParentDir.isDirectory() && !outputFileParentDir.mkdirs()) {
//                    Log.e(Const.TAG, "Unable to create output directory: " + outputFileParentDir);
//                }
//
//                if (zipEntry.isDirectory()) {
//                    continue;
//                }
//
//                try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
//                    ByteStreams.copy(zipInputStream, outputStream);
//                    outputStream.flush();
//                }
//            }
//        } catch (IOException e) {
//            Log.e(Const.TAG, e.getMessage(), e);
//        }
    }

}
