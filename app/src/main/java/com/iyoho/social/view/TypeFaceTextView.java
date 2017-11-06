package com.iyoho.social.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.iyoho.social.R;
import com.iyoho.social.utils.TypeFaceUtil;

import static com.iyoho.social.utils.TypeFaceUtil.getTypeFaceArialRegular;


public class TypeFaceTextView extends AppCompatTextView {

    private int tvTypeFace;

    public TypeFaceTextView(Context context) {
        this(context, null);
    }

    public TypeFaceTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeFaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TypeFaceTextView);
        tvTypeFace = array.getInt(R.styleable.TypeFaceTextView_typeFace,0);
        array.recycle();


        if (tvTypeFace != 0) {
            setTypeFace(context);
        }
    }

    private void setTypeFace(Context context) {
        Typeface tf=null;
        switch (tvTypeFace) {
            case 1: {
                tf=TypeFaceUtil.getTypeFaceArialRegular(context);
                if(tf!=null){
                    this.setTypeface(tf);
                }
            }
            break;
            case 2: {
                tf=TypeFaceUtil.getTypeFaceArialBold(context);
                if(tf!=null){
                    this.setTypeface(tf);
                }
            }
            break;
            case 3: {
                tf=TypeFaceUtil.getTypeFaceArialRegularItalic(context);
                if(tf!=null){
                    this.setTypeface(tf);
                }
            }
            break;
            case 4: {
                tf=TypeFaceUtil.getTypeFaceArialBoldItalic(context);
                if(tf!=null){
                    this.setTypeface(tf);
                }
            }
            break;

        }
    }
}
