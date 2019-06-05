package com.example.myapplication;

import android.util.Log;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplicaions {
    private static final String TAG = "ParseApplicaions";
    private ArrayList<feedentry> applications;

    public ParseApplicaions() {
        this.applications=new ArrayList<>();
    }

    public ArrayList<feedentry> getApplications() {
        return applications;
    }
    public  boolean parse(String xmlData)
    {
        boolean status =true;
        feedentry currentrecord=null;
        boolean inEntry=false;
        String textvalue="";
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();//READ ABOUT XML PULL PARSER
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventtype=xpp.getEventType();
            while (eventtype!=XmlPullParser.END_DOCUMENT)
            {
               String tagname=xpp.getName();
                switch (eventtype)
                {   case XmlPullParser.START_TAG:
                     Log.d(TAG, "parse:starting tage"+tagname);
                        if("entry".equalsIgnoreCase(tagname))
                        {
                         inEntry=true;
                         currentrecord =new feedentry();
                        }
                       break;
                    case XmlPullParser.TEXT:
                       textvalue=xpp.getText();
                       break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: ending tag is"+tagname);
                        if(inEntry)
                        { if("entry".equalsIgnoreCase(tagname)) {
                            applications.add(currentrecord);
                            inEntry = false;
                        }
                        else if("name".equalsIgnoreCase(tagname))
                        {
                            currentrecord.setName(textvalue);
                        }
                        else if("artist".equalsIgnoreCase(tagname))
                        {
                            currentrecord.setArtist(textvalue);

                        }
                        else if("releaseDate".equalsIgnoreCase(tagname))
                        {
                            currentrecord.setReleasedate(textvalue);

                        }
                        else if("summary".equalsIgnoreCase(tagname))
                        {
                            currentrecord.setSummary(textvalue);

                        }
                        else if("image".equalsIgnoreCase(tagname))
                        {
                            currentrecord.setImageurl(textvalue);

                        }
                        break;

                        }
                        default:


                }
                eventtype=xpp.next();
            }
            for(feedentry app: applications)
            {
                Log.d(TAG, "******");
                Log.d(TAG, app.toString() );
            }
        }
        catch (Exception e)
        {
            status=false;
            e.printStackTrace();
        }
        return status;
    }
}
