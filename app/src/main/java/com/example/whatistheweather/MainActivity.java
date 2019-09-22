package com.example.whatistheweather;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;


    public void getData(View view){
        String x=editText.getText().toString();
        getWeather obj=new getWeather();
        try{
            String encodeCityName= URLEncoder.encode(editText.getText().toString(),"UTF-8");
            String result=obj.execute("https://openweathermap.org/data/2.5/weather?q="+x+"&appid=b6907d289e10d714a6e88b30761fae22").get();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "hey fucker!!!stop messing with me!!!", Toast.LENGTH_SHORT).show();
        }
        InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);

    }

    public  class getWeather extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            try{
                URL url=new URL(urls[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream in =httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char)data;
                    result+=current;
                    data=reader.read();
                }
               return result;

            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "hey fucker!!!stop messing with me!!!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject=new JSONObject(s);
                String weatherInfo=jsonObject.getString("weather");
                JSONArray jsonArray=new JSONArray(weatherInfo);
                String x="";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    String main=object.getString("main");
                    String description=object.getString("description");

                    if(!main.equals("") && !description.equals("")){
                        x+=main +" : "+description+"\n";
                    }

                }
            if(!x.equals("")){
                textView.setText(x);
            }

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "hey fucker!!!stop messing with me!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        textView=(TextView)findViewById(R.id.textView);

    }
}
