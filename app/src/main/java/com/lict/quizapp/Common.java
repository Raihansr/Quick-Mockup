package com.lict.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.snackbar.Snackbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class Common {
    public static DrawerLayout Root_Layout;
    private static Snackbar snackbar;

    /* Check Internet Connection: */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null){

                for (NetworkInfo anInfo : info){

                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {

                        /* Check if network connected but no internet */
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                            int     exitValue = ipProcess.waitFor();
                            return (exitValue == 0);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return false;
                    }
                }
            }

        }
        return false;
    }


    /* Custom SnackBar: */
    public static void Custom_SnackBar(final Context context, final View SnackBarLayout, final String SnackBarText){

        Typeface snackBarTf = Typeface.createFromAsset(context.getAssets(), "pf_regular.ttf");

        snackbar = Snackbar.make(SnackBarLayout, SnackBarText, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                })
                .setActionTextColor(Color.WHITE);

        Snackbar.SnackbarLayout s_layout = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView textView = s_layout.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(15);
        textView.setTypeface(snackBarTf);
        textView.setPadding(0,0,0,0);
        s_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_snackbar));

        snackbar.show();
    }


    /* Custom Toast: */
    public static void Custom_Toast(Context context, String message){

        Typeface toastTf = Typeface.createFromAsset(context.getAssets(), "pf_regular.ttf");

        Toast customToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        customToast.setGravity(Gravity.BOTTOM,0,10);
        View toastView = customToast.getView();

        TextView textView = toastView.findViewById(android.R.id.message);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        textView.setTypeface(toastTf);
        toastView.setPadding(15,15,15,15);
        toastView.setBackgroundResource(R.drawable.bg_toast);

        customToast.show();
    }


    /* Font*/
    public static void Set_Font(Context context, View[] view, String typeface){
        for(View font_View: view){
            if(font_View instanceof TextView){
                ((TextView) font_View).setTypeface(Typeface.createFromAsset(context.getAssets(), typeface));
            }
            if(font_View instanceof Button){
                ((Button) font_View).setTypeface(Typeface.createFromAsset(context.getAssets(), typeface));
            }
        }
    }


    /* Animation */
    public static void Bounce_Animation(Context context, View view, double amplitude, double frequency, long time){
        Animation Bounce_Animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(amplitude,frequency);
        Bounce_Animation.setInterpolator(interpolator);
        Bounce_Animation.setDuration(time);
        view.startAnimation(Bounce_Animation);
    }

    public static void Open_Animation(Context context, View view, long time){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.open);
        animation.setDuration(time);
        view.startAnimation(animation);
    }


    /* Stored Data */
    public static void Set_Stored_Data_Count(Context context, int n){
        context.getSharedPreferences("StoredDataCount", Context.MODE_PRIVATE).edit().putInt("Count_Key",n).apply();
    }

    public static int Get_Stored_Data_Count(Context context){
        return context.getSharedPreferences("StoredDataCount", Context.MODE_PRIVATE).getInt("Count_Key",0);
    }

    public static void Set_Is_Data_Modified(Context context, boolean modified){
        context.getSharedPreferences("IsDataModified", Context.MODE_PRIVATE).edit().putBoolean("Modify_Key",modified).apply();
    }

    public static boolean Get_Is_Data_Modified(Context context){
        return context.getSharedPreferences("IsDataModified", Context.MODE_PRIVATE).getBoolean("Modify_Key",true);
    }
}
