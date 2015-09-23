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

    /**
     * Unzips the given zip archives to the specified directory.
     *
     * @param fileToUnzip zip file
     * @param targetDirectory output directory
     * @throws ZipException in case the file cannot be unzipped
     */
    public static void unzip(File fileToUnzip, File targetDirectory) throws ZipException {
        ZipFile zipFile = new ZipFile(fileToUnzip);
        zipFile.extractAll(targetDirectory.getAbsolutePath());
    }

    /**
     * Zips the given directory with all its files to the specified output file.
     *
     * @param directoryToZip directory to archive
     * @param zipFile result zip file
     */
    public static void zip(File directoryToZip, File zipFile) {
        throw new UnsupportedOperationException();
    }

}
