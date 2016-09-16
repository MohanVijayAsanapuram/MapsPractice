package com.spiders.maps.mapspractice.transitionanimations;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spiders.maps.mapspractice.R;

public class TransitionsActivity extends AppCompatActivity {

    private TextView tv;
    LinearLayout ll;
    ImageView img;
    RelativeLayout rl;
    FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitions);
        ll=(LinearLayout) findViewById(R.id.linearlayout);
        //rl=(RelativeLayout) findViewById(R.id.relativeLayout);
        fl=(FrameLayout) findViewById(R.id.frameLayout);
        tv=(TextView)ll.findViewById(R.id.txtmessage);
        img=(ImageView)ll.findViewById(R.id.zoomimageView);
        final Button btDownload=(Button)ll.findViewById(R.id.btdownload);
        final Button colorbutton=(Button)fl.findViewById(R.id.colorbutton);
        final Button rotatebutton=(Button)fl.findViewById(R.id.rotatebutton);
        btDownload.setOnClickListener(new View.OnClickListener() {

            boolean buttonhide;
            boolean visible;

            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(ll);
                //Transition transition=new Explode().setEpicenterCallback
                buttonhide=!buttonhide;
                Log.e("boolean val",""+buttonhide);
                EditText url=(EditText)ll.findViewById(R.id.txturl);
                url.setVisibility(buttonhide?View.GONE:View.VISIBLE);


                //Transition with fading
                visible=!visible;
                TransitionSet set = new TransitionSet().addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator());
                TransitionManager.beginDelayedTransition(ll, set);
                tv.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

            }
        });

        colorbutton.setOnClickListener(new View.OnClickListener() {
            boolean isColorsInverted,mColorsInverted;
            boolean isReturnAnimation,isRotated ;
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(fl);
                isColorsInverted=!isColorsInverted;
                mColorsInverted=!mColorsInverted;
                colorbutton.setTextColor(getResources().getColor(!isColorsInverted ? R.color.colorPrimary :R.color.colorAccent));
                colorbutton.setBackgroundDrawable(
                        new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent :
                                R.color.colorPrimary)));


                //This transition for Path(Curved) Motion
                TransitionManager.beginDelayedTransition(fl,new ChangeBounds().setDuration(1000));

                isReturnAnimation=!isReturnAnimation;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) colorbutton.getLayoutParams();
                params.gravity = isReturnAnimation ? (Gravity.LEFT | Gravity.TOP) :
                        (Gravity.BOTTOM | Gravity.RIGHT);
                colorbutton.setLayoutParams(params);
                isRotated=!isRotated;
                colorbutton.setRotation(isRotated ?135:0);
            }
        });

        rotatebutton.setOnClickListener(new View.OnClickListener() {
            boolean isSecondText;
            @Override
            public void onClick(View v) {


                String TEXT_1="Hi Hello Tap Me";
                String TEXT_2="Mohan Tap me ";
                isSecondText=!isSecondText;
                TransitionManager.beginDelayedTransition(fl);
                rotatebutton.setText(isSecondText ? TEXT_2 : TEXT_1);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            boolean expanded;

            @Override

            public void onClick(View v) {

                //This transition for zoom in or zoom out of a view.
                TransitionManager.beginDelayedTransition(ll, new TransitionSet()
                        .addTransition(new ChangeBounds())
                        .addTransition(new ChangeImageTransform()).setDuration(3000));
                expanded=!expanded;
                ViewGroup.LayoutParams params = img.getLayoutParams();
                params.height = expanded ? ViewGroup.LayoutParams.MATCH_PARENT :
                        ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = expanded ? ViewGroup.LayoutParams.MATCH_PARENT :
                        ViewGroup.LayoutParams.WRAP_CONTENT;
                img.setLayoutParams(params);
                img.setScaleType(expanded ? ImageView.ScaleType.CENTER_CROP :
                        ImageView.ScaleType.FIT_CENTER);



            }
        });
    }
}
