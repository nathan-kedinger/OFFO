package com.example.offo

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseError
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.StringBuilder

class Storage : AppCompatActivity() {

    private fun createOrGetFile (
        destination: File,
        fileName: String,
        folderName : String
    ) : File{

        var folder = File(destination, folderName)

        return File(folder, fileName)

    }

    private fun readOnFile(context: Context, file: File): String?{
        var result : String? = null
        if(file.exists()){
            var br : BufferedReader
            try {
                br = BufferedReader(FileReader(file))
                try{
                    var sb: java.lang.StringBuilder = StringBuilder()
                    var line = br.readLine()
                    while(line!=null){
                        sb.append(line)
                        sb.append("\n")
                        line = br.readLine()
                    }
                    var result = sb.toString()
                }
                finally {
                    br.close()
                }
            }
            catch (error: Throwable){
                Log.w(ContentValues.TAG, "erreur de lecture des valeurs", error)

            }
        }
        return result
    }

}