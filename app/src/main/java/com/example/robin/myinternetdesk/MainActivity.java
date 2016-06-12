package com.example.robin.myinternetdesk;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.*;
import static android.graphics.Color.MAGENTA;

public class MainActivity extends AppCompatActivity {

    NumberPicker possibilities;
    WebView webView;
    private String[] urlString_Array;
    private String[] possibilitiesString_Array;
    List<String> possibilitiesString_list = new ArrayList<String>();
    List<String> urlString_List = new ArrayList<String>();
    EditText inputURL,inputName,inputNameDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //displays the GUI defined by the main_activity.xml file
        setContentView(R.layout.activity_main);

        possibilities = (NumberPicker) findViewById(R.id.numberPicker2);
        webView = (WebView) findViewById(R.id.webView);

        possibilitiesString_list = (List<String>) ReadArrayListFromSD(getApplicationContext(),"shortnames_list");

        if (possibilitiesString_list.isEmpty()){
            possibilitiesString_list.add("CN by AM");
        }

        possibilitiesString_Array = new String[possibilitiesString_list.size()];

        urlString_List = (List<String>) ReadArrayListFromSD(getApplicationContext(),"url_with_http_list");
        if (urlString_List.isEmpty()){
            urlString_List.add("http://cse.iitkgp.ac.in/~animeshm/course_cnt2016.html");
        }

        urlString_Array = new String[urlString_List.size()];


        possibilitiesString_list.toArray(possibilitiesString_Array);
        urlString_List.toArray(urlString_Array);

        possibilities.setDisplayedValues(possibilitiesString_Array);
        possibilities.setMinValue(0);
        possibilities.setMaxValue(possibilitiesString_Array.length-1);

        inputName = (EditText) findViewById(R.id.editTextName);
        inputName.setCursorVisible(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inputName.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                inputName.setSelection(inputName.getText().toString().length());
                inputName.requestFocus();
                inputName.requestFocusFromTouch();
                inputName.setCursorVisible(true);
                return false;
            }
        });
        inputURL = (EditText) findViewById(R.id.editTextURL);
        inputURL.setCursorVisible(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inputURL.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                inputURL.setSelection(inputURL.getText().toString().length());
                inputURL.requestFocus();
                inputURL.requestFocusFromTouch();
                inputURL.setCursorVisible(true);
                return false;
            }
        });
        inputNameDel = (EditText) findViewById(R.id.editTextDelPage);
        inputNameDel.setCursorVisible(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        inputNameDel.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                inputNameDel.setSelection(inputNameDel.getText().toString().length());
                inputNameDel.requestFocus();
                inputNameDel.requestFocusFromTouch();
                inputNameDel.setCursorVisible(true);
                return false;
            }
        });

    }


    public void addPage(View v){
        String name = inputName.getText().toString();
        String url_without_http = inputURL.getText().toString();
        String url_with_http = "http://"+url_without_http;

        inputName.getText().clear();
        inputURL.getText().clear();

        possibilitiesString_list.add(name);
        possibilitiesString_Array = new String[possibilitiesString_list.size()];
        possibilitiesString_list.toArray(possibilitiesString_Array);

        urlString_List.add(url_with_http);
        urlString_Array = new String[urlString_List.size()];
        urlString_List.toArray(urlString_Array);

        possibilities.setDisplayedValues(possibilitiesString_Array);
        possibilities.setMinValue(0);
        possibilities.setMaxValue(possibilitiesString_Array.length-1);
    }


    public void navigate(View v) {
        int choice = possibilities.getValue();
        webView.setWebViewClient(new WebViewClient());
        String url = urlString_Array[choice];
        webView.loadUrl(url);
    }


    public void delPage(View v){
        possibilities.setValue(0);

        String nameToDel = inputNameDel.getText().toString();


        if (possibilitiesString_list.size()>2) {
            int index = possibilitiesString_list.indexOf(nameToDel);
            possibilitiesString_list.remove(index);
            String[] possibilitiesString_Array2 = new String[possibilitiesString_list.size()];
            possibilitiesString_list.toArray(possibilitiesString_Array2);

            urlString_List.remove(index);
            urlString_Array = new String[urlString_List.size()];
            urlString_List.toArray(urlString_Array);

            possibilities.setDisplayedValues(possibilitiesString_Array2);
            possibilities.setMinValue(0);
            possibilities.setMaxValue(possibilitiesString_Array2.length - 1);
        }

        inputNameDel.getText().clear();

    }

    @Override
    protected void onStop() {
        super.onStop();

        String possibilitiesString_list_saved = "shortnames_list";
        String urlString_list_saved = "url_with_http_list";
        SaveArrayListToSD(getApplicationContext(),possibilitiesString_list_saved,possibilitiesString_list);
        SaveArrayListToSD(getApplicationContext(),urlString_list_saved,urlString_List);


    }
    //http://stackoverflow.com/questions/18827862/how-to-keep-data-stored-in-array-after-application-has-ended
    public static <E> void SaveArrayListToSD(Context mContext, String filename, List<String> list){
        try {
            FileOutputStream fos = mContext.openFileOutput(filename + ".dat", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object ReadArrayListFromSD(Context mContext,String filename){
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj= (Object) ois.readObject();
            fis.close();
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Object>();
        }
    }
}
