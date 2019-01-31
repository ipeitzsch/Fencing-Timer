package com.example.irpei.fencingtimer;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BTHandler extends Handler {

    private TextView text;
    private RelativeLayout background;
    public BTHandler(TextView t, RelativeLayout l)
    {
        text = t;
        background = l;
    }

    @Override
    public void handleMessage(Message msg)
    {
        String s = msg.toString();
        switch(s.charAt(0))
        {
            case 'r':
                background.setBackgroundColor(Color.WHITE);
                String[] str = s.split(" ");
                text.setVisibility(View.VISIBLE);
                text.setTextColor(Color.RED);
                text.setText(str[1]);
                break;
            case 'y':
                text.setVisibility(View.INVISIBLE);
                background.setBackgroundColor(Color.YELLOW);
                break;
            case 'g':
                text.setVisibility(View.INVISIBLE);
                background.setBackgroundColor(Color.GREEN);
                break;
        }
    }
}
