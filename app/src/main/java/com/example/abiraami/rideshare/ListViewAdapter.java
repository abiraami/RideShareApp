package com.example.abiraami.rideshare;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Abiraami on 12/1/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    Activity activity;
    Context context;
    TextView txtStartPt;
    TextView txtEndPt;
    TextView txtTravelDate;
    TextView txtSpot;
    ImageView imgProfile;
    RideDetails[] details;
    static int imgCounter=0;
    private int lastPosition = -1;
    public ListViewAdapter(Activity activity,RideDetails[] details,Context c)
    {
        super();
        this.activity=activity;
        this.details=details;
        this.context=c;
    }
    @Override
    public int getCount() {
        return details.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.column_row_list_view, null);


            txtStartPt = (TextView) convertView.findViewById(R.id.uxStartPt);
            txtEndPt = (TextView) convertView.findViewById(R.id.uxEndPt);
            txtTravelDate = (TextView) convertView.findViewById(R.id.uxTravelDate);
            txtSpot = (TextView) convertView.findViewById(R.id.uxSpot);
            imgProfile=(ImageView)convertView.findViewById(R.id.imgUser);
        }
        txtStartPt.setText(details[position].getStartPt());
        txtEndPt.setText(details[position].getEndPt());
        Date d=details[position].getTravelDate();
        String dateWithoutTime = d.toString().substring(0, 10);
        txtTravelDate.setText(dateWithoutTime);
        txtSpot.setText(Integer.toString(details[position].getNoSpot()));
        int profileSourceID;



            if(position<5) {
                String profile="profile"+position;
                profileSourceID = context.getResources().getIdentifier("com.example.abiraami.rideshare:drawable/" + profile, null, null);
                imgProfile.setImageResource(profileSourceID);
            }
            else if(position>=5)
            {

                //reset the imgCounter to 0 when ever the counter reaches 5
                if(imgCounter==5)
                    imgCounter=0;
                String profile="profile"+imgCounter;
                profileSourceID = context.getResources().getIdentifier("com.example.abiraami.rideshare:drawable/" + profile, null, null);
                imgProfile.setImageResource(profileSourceID);
                imgCounter++;
            }


        if(convertView != null) {
            /*Animation animation = new TranslateAnimation(0, 0, (position > lastPosition) ? 700 : -50, 10);
            animation.setDuration(600);
            convertView.startAnimation(animation);*/
            Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            /*Animation animation = AnimationUtils.loadAnimation(activity,  R.anim.push_up_in);
            convertView.startAnimation(animation);*/
        }
        lastPosition=position;
        return convertView;
    }
}
