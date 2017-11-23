package com.example.lorcan.palo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FileManager {

    FileOutputStream outputStream;
    FileInputStream inputStream;
    BufferedReader bufferedReader;

    public void writeToFile(Context context, String filename, String data) {
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFromFile (Context context, String filename) {

        ArrayList<String> oldUserStatus = new ArrayList<>();
        File file = new File(context.getCacheDir(), filename);

        if (file.exists()) {
            try {
                inputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    line = bufferedReader.readLine();
                    oldUserStatus.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return oldUserStatus;
    }
}
