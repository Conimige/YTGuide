package cn.myzchh.YTGuide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidtestproject.testapp.R;
import cn.myzchh.YTGuide.util.BaseActivity;


public class firstActivity extends BaseActivity {

    private ImageView imageBkg;
    private TextView text_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        imageBkg=(ImageView)findViewById(R.id.imageBkg);
        text_about=(TextView)findViewById(R.id.text_about);

        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(firstActivity.this,aboutActivity.class);
                startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version > 5) {
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
                finish();
            }
        });

        ScaleAnimation animation =new ScaleAnimation(1f, 1.1f, 1f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        imageBkg.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(firstActivity.this,MapActivity.class);
                startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version > 5) {
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
                finish();
            }
            public void onAnimationRepeat(Animation animation) {}
        });
    }

}
