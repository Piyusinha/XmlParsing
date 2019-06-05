package com.example.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadData downloadData=new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onCreate:download complete ");
    }

    private class  DownloadData extends AsyncTask<String,Void,String> //url,display progress,result we get back//

    {
        private static final String TAG = "DownloadData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: "+s);
            ParseApplicaions parseApplicaions=new ParseApplicaions();
            parseApplicaions.parse(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: "+strings[0]);
            String RssFeed=downloadxml(strings[0]);
            if(RssFeed==null)
            {
                Log.e(TAG, "doInBackground: DOWNLOADING ERROR" );
            }
            return RssFeed;
        }
      private  String downloadxml( String urlpath)
      {
          StringBuilder xmlResult= new StringBuilder();
          try{
              URL url=new URL(urlpath);
              HttpURLConnection connection=(HttpURLConnection) url.openConnection();
              int response =connection.getResponseCode();
              Log.d(TAG, "downloadxml: "+response);
//              InputStream inputStream=connection.getInputStream();
//              InputStreamReader streamreader=new InputStreamReader(inputStream);
//              BufferedReader reader=new BufferedReader(streamreader);
              BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

              int charread;

              char[] inputreader=new  char[1000];


              while(true)
              {
                  charread=reader.read(inputreader);
              if(charread>0)
              {
                  xmlResult.append(String.copyValueOf(inputreader,0,charread));
              }
              if(charread<0)
              {
                  break;
              }

          }
          reader.close();
              return  xmlResult.toString();
          }

          catch (MalformedURLException e)
          {
              Log.e(TAG, "downloadxml: INVALID URL"+e.getMessage() );
          }
          catch (IOException e)
          {
              Log.e(TAG, "downloadxml:IOEXCEPTION "+e.getMessage());
          }
          return  null;
      }
    }
}
