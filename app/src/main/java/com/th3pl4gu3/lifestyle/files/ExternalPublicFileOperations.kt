package com.th3pl4gu3.lifestyle.files

import android.os.Environment
import com.th3pl4gu3.lifestyle.core.utils.*
import java.io.*


class ExternalPublicFileOperations(directory: String, directoryType: String) {

    companion object {
        /* Checks if external storage is available for read and write */
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /* Checks if external storage is available to at least read */
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }
    }

    // Get the directory for the user's public document directory.
    private val _file = File(Environment.getExternalStoragePublicDirectory(directoryType), directory)


    /**
     * Public functions that are accessible from the outside
     **/

    fun createFile(filename: String, content: String) {

        //Create the Public Directory
        _file.mkdir()

        //Create the file
        File("$_file$VALUE_SLASH_FRONT$filename").writeText(content)
    }

    fun readFile(filename: String): String {
        val json = StringBuilder()

        File("$_file$VALUE_SLASH_FRONT$filename").forEachLine { line ->
            json.append(line)
        }

        return json.toString()
    }


    /**
     * Private functions for Internal use ONLY
     **/
}