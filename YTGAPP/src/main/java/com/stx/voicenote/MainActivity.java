package com.stx.voicenote;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtestproject.testapp.R;

import cn.myzchh.YTGuide.util.BaseActivity;
import cn.myzchh.YTGuide.util.localUser;
import cn.myzchh.YTGuide.util.sqlliteDB.DBManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RelativeLayout layout_login;
    private TextView text_TitleName_blur;
    private RelativeLayout layout_text_username;
    private EditText text_username;
    private RelativeLayout layout_text_password;
    private EditText text_password;
    private ImageView btn_login;
    private ImageView btn_login_shadow;
    private TextView text_regist;
    private TextView text_logining;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_login=(RelativeLayout)findViewById(R.id.layout_login);
        text_TitleName_blur=(TextView)findViewById(R.id.text_TitleName_blur);
        layout_text_username=(RelativeLayout)findViewById(R.id.layout_text_username);
        layout_text_password=(RelativeLayout)findViewById(R.id.layout_text_password);
        text_username=(EditText)findViewById(R.id.text_username);
        text_password=(EditText)findViewById(R.id.text_password);
        btn_login=(ImageView)findViewById(R.id.btn_login);
        btn_login_shadow=(ImageView)findViewById(R.id.btn_login_shadow);
        text_regist=(TextView)findViewById(R.id.btn_regist);
        text_logining=(TextView)findViewById(R.id.text_logining);



        Intent intent = getIntent();
        String uname;String upsd;
        uname=intent.getStringExtra("username");
        upsd=intent.getStringExtra("password");
        text_username.setText(uname);
        text_password.setText(upsd);

        DBManager mgr=new DBManager(this);
        mgr.getLocalUser(this);
        String strLocalUser=localUser.getUSERNAME();
        //showMessageByToast(strLocalUser);
        text_username.setText(strLocalUser);

        if (!localUser.getUUID().equals("")) {
            intentInviteActivity();
        }else{
            startAnmi();
        }

        btn_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("sd", event.getAction() + "");
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Animation aIn1 = new AlphaAnimation(1f, 0f);
                    aIn1.setDuration(200);
                    aIn1.setFillAfter(true);
                    btn_login.startAnimation(aIn1);
                    Animation aIn = new AlphaAnimation(0f, 1f);
                    aIn.setDuration(200);
                    aIn.setFillAfter(true);
                    btn_login_shadow.startAnimation(aIn);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Animation aIn1 = new AlphaAnimation(0f, 1f);
                    aIn1.setDuration(200);
                    aIn1.setFillAfter(true);
                    btn_login.startAnimation(aIn1);
                    Animation aIn = new AlphaAnimation(1f, 0f);
                    aIn.setDuration(200);
                    aIn.setFillAfter(true);
                    btn_login_shadow.startAnimation(aIn);
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                btn_login.setClickable(false);
                text_username.setEnabled(false);
                text_password.setEnabled(false);
                text_regist.setEnabled(false);
                Animation aIn = new AlphaAnimation(1f, 0f);
                aIn.setDuration(200);
                aIn.setFillAfter(true);
                btn_login_shadow.startAnimation(aIn);
                btn_login.startAnimation(aIn);

                text_logining.setVisibility(View.VISIBLE);
                Animation aIn1 = new AlphaAnimation(0f, 1f);
                aIn1.setDuration(200);
                aIn1.setFillAfter(true);
                text_logining.startAnimation(aIn1);

                aIn1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        doLogin();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        text_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                localUser.setUSERNAME(text_username.getText().toString());
                startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version > 5) {
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
            }
        });

        text_password.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_password.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                    text_password.setHint("����");
                }
            }
        });
    }

    public void startAnmi(){
        //��ʼ��������һ�¶���
        layout_login.setVisibility(View.VISIBLE);
        Animation aIn0 = new AlphaAnimation(1f, 0f);
        aIn0.setDuration(1);
        aIn0.setFillAfter(true);
        btn_login_shadow.startAnimation(aIn0);
        //��˹ģ������ʾ
        layout_login.setVisibility(View.VISIBLE);
        Animation aIn = new AlphaAnimation(0f, 1f);
        aIn.setDuration(800);
        aIn.setStartOffset(500);
        aIn.setFillAfter(true);
        layout_login.startAnimation(aIn);
        //��ͷ�����ƶ�
        Animation aIn1 = new TranslateAnimation(0, 0, 0, dip2px(this,-100));
        aIn1.setDuration(500);
        aIn1.setStartOffset(1300);
        aIn1.setFillAfter(true);
        text_TitleName_blur.startAnimation(aIn1);
        //�����ʺſ򶯻�
        layout_login.setVisibility(View.VISIBLE);
        Animation aIn2 = new AlphaAnimation(0f, 1f);
        aIn2.setDuration(400);
        aIn2.setStartOffset(1800);
        aIn2.setFillAfter(true);
        text_username.startAnimation(aIn2);
        Animation aIn3 = new TranslateAnimation(0, 0, dip2px(this,50), 0);
        aIn3.setDuration(400);
        aIn3.setStartOffset(1800);
        aIn3.setFillAfter(true);
        layout_text_username.startAnimation(aIn3);
        //��������򶯻�
        layout_login.setVisibility(View.VISIBLE);
        Animation aIn4 = new AlphaAnimation(0f, 1f);
        aIn4.setDuration(400);
        aIn4.setStartOffset(1920);
        aIn4.setFillAfter(true);
        text_password.startAnimation(aIn4);
        Animation aIn5 = new TranslateAnimation(0, 0, dip2px(this,50), 0);
        aIn5.setDuration(400);
        aIn5.setStartOffset(1920);
        aIn5.setFillAfter(true);
        layout_text_password.startAnimation(aIn5);
        //��ʾע�ᰴť
        Animation aIn6 = new AlphaAnimation(0f, 1f);
        aIn6.setDuration(500);
        aIn6.setStartOffset(2320);
        aIn6.setFillAfter(true);
        text_regist.startAnimation(aIn6);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void showMessageByToast(String Msg) {
        Toast.makeText(MainActivity.this, Msg, Toast.LENGTH_LONG).show();
    }

    public void doLogin(){
        if (text_username.getText().toString().equals("")) {
            doErrorLogin();
            text_password.setHint("��¼ʧ�ܣ������û���������");
        } else {
            if (text_password.getText().toString().equals("") || (text_password.getText().toString().length() < 6)) {
                if(!localUser.getUUID().equals("")){
                    intentInviteActivity();
                }else {
                    doErrorLogin();
                    text_password.setHint("���벻��Ϊ��");
                }
            } else {
                //��ʼ��¼
                new AsyncTask<String, Void, Object>() {
                    @Override
                    protected Object doInBackground(String... params) {
                        String url = "http://sanjin6035.vicp.cc/VoiceRecord/user/login";
                        String result = "";
                        try {
                            //��������
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost post = new HttpPost(url);
                            //���ò�������html���ύ
                            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                            BasicNameValuePair param = new BasicNameValuePair("uusername", text_username.getText().toString());
                            BasicNameValuePair param2 = new BasicNameValuePair("upassword", text_password.getText().toString());
                            paramList.add(param);
                            paramList.add(param2);

                            post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                            //����HttpPost���󣬲�����HttpResponse����
                            HttpResponse httpResponse = httpClient.execute(post);
                            // �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
                            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                //��ȡ���ؽ��
                                result = EntityUtils.toString(httpResponse.getEntity());
                                //showMessageByToast(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //showMessageByToast("����!");
                        }
                        return result;
                    }

                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        //showMessageByToast(result+"");
                        try {
                            JSONTokener jsonParser = new JSONTokener(result + "");
                            JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                            //showMessageByToast("getResult:" + result);
                            if (loginmsg.getBoolean("status")) {
                                JSONObject loginUserJSON = loginmsg.getJSONObject("user");
                                DBManager mgr=new DBManager(MainActivity.this);
                                String strUsername=loginUserJSON.getString("uusername");
                                String strUuid=loginUserJSON.getString("uuid");
                                mgr.setLocalUser(strUsername,strUuid);
                                localUser.setUSERNAME(strUsername);
                                localUser.setUUID(strUuid);
                                if (loginmsg.getInt("uactivate")==1) {
                                    intentInviteActivity();
                                }else{
                                    showMessageByToast("�����û���δ�����ǰ��������м��");
                                    doErrorLogin();
                                }
                            } else {
                                //showMessageByToast("Error1001,Username & Password is not true");
                                doErrorLogin();
                                text_password.setHint("��������޷���¼");
                            }

                        } catch (Exception e) {
                            //showMessageByToast("Error0000,Exception @JSON");
                            e.printStackTrace();
                            doErrorLogin();
                            text_password.setHint("��������޷���¼");
                        }
                        //
                        //showMessageByToast("" + result);
                    }
                }.execute();
            }
        }
    }

    public void doErrorLogin(){
        btn_login.setEnabled(true);
        btn_login.setClickable(true);
        text_username.setEnabled(true);
        text_password.setEnabled(true);
        text_regist.setEnabled(true);

        text_password.setHintTextColor(getResources().getColor(R.color.color_error));
        text_password.setText("");

        Animation aIn1 = new AlphaAnimation(1f, 0f);
        aIn1.setDuration(200);
        aIn1.setFillAfter(true);
        text_logining.startAnimation(aIn1);

        Animation aIn = new AlphaAnimation(0f, 1f);
        aIn.setDuration(200);
        aIn.setFillAfter(true);
        btn_login.startAnimation(aIn);
    }

    public void intentInviteActivity(){
        Intent intent = new Intent(MainActivity.this, InviteActivity.class);
        localUser.setUSERNAME(text_username.getText().toString());
        startActivity(intent);
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version > 5) {
            overridePendingTransition(R.anim.fade, R.anim.hold);
        }
        finish();
    }
}
