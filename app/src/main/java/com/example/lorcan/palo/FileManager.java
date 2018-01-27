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


class FileManager {

    void writeToFile(Context context, String filename, String data) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<String> readFromFile(Context context, String filename) {

        ArrayList<String> oldUserStatus = new ArrayList<>();
        File file = new File(context.getCacheDir(), filename);

        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while (bufferedReader.readLine() != null) {
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
