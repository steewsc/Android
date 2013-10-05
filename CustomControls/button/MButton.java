package com.exavggple.koopp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Steewsc on 5.10.13..
 */
public class MButton extends Button implements View.OnTouchListener {

    public MButton(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public MButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if( event.getAction() == event.ACTION_DOWN ){
            /**
             * Implement your Event tracker here
             */
            System.out.println("TOUCH => ACTION_DOWN on => " + v.getTag() );
        }
        return false;
    }
}
