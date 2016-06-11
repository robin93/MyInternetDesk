package com.example.robin.myinternetdesk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText inputURL,inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //displays the GUI defined by the main_activity.xml file
        setContentView(R.layout.activity_main);

        possibilities = (NumberPicker) findViewById(R.id.numberPicker2);
        webView = (WebView) findViewById(R.id.webView);


        possibilitiesString_list.add("CN by AM");
        String[] possibilitiesString_Array = new String[possibilitiesString_list.size()];


        urlString_List.add("http://cse.iitkgp.ac.in/~animeshm/course_cnt2016.html");
        urlString_Array = new String[urlString_List.size()];


        possibilitiesString_list.toArray(possibilitiesString_Array);
        urlString_List.toArray(urlString_Array);

        possibilities.setDisplayedValues(possibilitiesString_Array);
        possibilities.setMinValue(0);
        possibilities.setMaxValue(possibilitiesString_Array.length-1);

        inputName = (EditText) findViewById(R.id.editTextName);
        inputURL = (EditText) findViewById(R.id.editTextURL);

    }


    public void addPage(View v){
        String name = inputName.getText().toString();
        String url_without_http = inputURL.getText().toString();
        String url_with_http = "http://"+url_without_http;
        Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();

        possibilitiesString_list.add(name);
        urlString_List.add(url_with_http);
        possibilitiesString_Array = new String[possibilitiesString_list.size()];
        urlString_Array = new String[urlString_List.size()];
        possibilitiesString_list.toArray(possibilitiesString_Array);
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




}