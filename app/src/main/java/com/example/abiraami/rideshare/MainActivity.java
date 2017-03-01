package com.example.abiraami.rideshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PopupWindow pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onLoginClick(View v)
    {
        String userName=((TextView)findViewById(R.id.uxEditUserName)).getText().toString();
        String password=((TextView)findViewById(R.id.uxEditPassword)).getText().toString();
        if(userName.equals("Dave")&&password.equals("Test123"))
        {
            Intent explicitIntent = new Intent(this, NavigationPage.class);
            startActivity(explicitIntent);
        }
        else
        {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.popup_window, null, false);

            Button closeButton = (Button)layout.findViewById(R.id.uxClose);

            closeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pw.dismiss();
                }
            });

            pw = new PopupWindow(layout,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true);

            pw.showAtLocation(this.findViewById(R.id.activity_main), Gravity.CENTER, 0, 0);

        }


    }
}
