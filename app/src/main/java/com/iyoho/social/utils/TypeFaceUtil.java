package com.iyoho.social.utils;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFaceUtil {
    private static final String FONTS_ArialRegular = "fonts/Arial-Regular.ttf";
    private static final String FONTS_ArialBold = "fonts/Arial-Bold.ttf";
    private static final String FONTS_ArialRegularItalic = "fonts/Arial-Italic.ttf";
    private static final String FONTS_ArialBoldItalic = "fonts/Arial-Bold-Italic.ttf";
    private static final String FONTS_RockSalt = "fonts/RockSalt.ttf";
    private static Typeface ArialRegular;
    private static Typeface ArialBold;
    private static Typeface ArialRegularItalic;
    private static Typeface ArialBoldItalic;
    private static Typeface RockSalt;

    public static Typeface getTypeFaceArialRegular(Context context) {
        if (ArialRegular == null) {
            try{
                ArialRegular = Typeface.createFromAsset(context.getAssets(), FONTS_ArialRegular);
            }catch (Exception e){
                ArialRegular=null;
            }

        }
        return ArialRegular;
    }

    public static Typeface getTypeFaceArialBold(Context context) {
        if (ArialBold == null) {
            try{
                ArialBold = Typeface.createFromAsset(context.getAssets(), FONTS_ArialBold);
            }catch (Exception e){
                ArialBold = null;
            }

        }
        return ArialBold;
    }

    public static Typeface getTypeFaceArialRegularItalic(Context context) {
        if (ArialRegularItalic == null) {
            try{
                ArialRegularItalic = Typeface.createFromAsset(context.getAssets(), FONTS_ArialRegularItalic);
            }catch(Exception e){
                ArialRegularItalic=null;
            }

        }
        return ArialRegularItalic;
    }

    public static Typeface getTypeFaceArialBoldItalic(Context context) {
        if (ArialBoldItalic == null) {
            try{
                ArialBoldItalic = Typeface.createFromAsset(context.getAssets(), FONTS_ArialBoldItalic);
            }catch (Exception e){
                ArialBoldItalic = null;
            }

        }
        return ArialBoldItalic;
    }

    public static Typeface getTypeFaceRockSalt(Context context) {
        if (RockSalt == null) {
            try{
                RockSalt = Typeface.createFromAsset(context.getAssets(), FONTS_RockSalt);
            }catch (Exception e){
                RockSalt = null;
            }
        }
        return RockSalt;
    }
}
