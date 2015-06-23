package com.stx.voicenote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtestproject.testapp.R;
import cn.myzchh.YTGuide.util.BaseActivity;

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

public class RegistActivity extends BaseActivity {

    private ImageView button_login;
    private ImageView button_login_shadow;
    private EditText textbox_user;
    private EditText textbox_password;
    private EditText textbox_password2;
    private EditText text_mail;
    private ImageView button_Back;
    private TextView text_registing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        button_login=(ImageView)findViewById(R.id.button_login);
        button_login_shadow=(ImageView)findViewById(R.id.button_login_shadow);
        textbox_user=(EditText)findViewById(R.id.textbox_user);
        textbox_password=(EditText)findViewById(R.id.textbox_password);
        textbox_password2=(EditText)findViewById(R.id.textbox_password2);
        text_mail=(EditText)findViewById(R.id.text_mail);
        button_Back=(ImageView)findViewById(R.id.button_Back);
        text_registing=(TextView)findViewById(R.id.text_registing);

        Animation aIn = new AlphaAnimation(1f, 0f);
        aIn.setDuration(1);
        aIn.setFillAfter(true);
        button_login_shadow.startAnimation(aIn);

        button_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.i("sd","ff");
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Animation aIn = new AlphaAnimation(0f, 1f);
                    aIn.setDuration(500);
                    aIn.setFillAfter(true);
                    button_login_shadow.startAnimation(aIn);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Animation aIn = new AlphaAnimation(1f, 0f);
                    aIn.setDuration(200);
                    aIn.setFillAfter(true);
                    button_login_shadow.startAnimation(aIn);
                }
                return false;
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //��һ���жϣ�ȷ�������Ƿ���ȷ
                if (textbox_password.getText().toString().equals(textbox_password2.getText().toString())) {
                    //�������жϣ���ֵУ��
                    if (!textbox_user.getText().toString().equals("")) {
                        if (!textbox_password.getText().toString().equals("")) {
                            if ((textbox_password.getText().toString().length() >= 6)) {
                                if (!text_mail.getText().toString().equals("")) {
                                    startRegist();
                                } else {
                                    text_mail.setHintTextColor(getResources().getColor(R.color.color_error));
                                    text_mail.setHint("���䲻��Ϊ��");
                                }
                            } else {
                                textbox_password.setText("");
                                textbox_password2.setText("");
                                textbox_password.setHintTextColor(getResources().getColor(R.color.color_error));
                                textbox_password.setHint("���벻��С����λ");
                            }
                        } else {
                            textbox_password.setHintTextColor(getResources().getColor(R.color.color_error));
                            textbox_password.setHint("���벻��Ϊ��");
                        }
                    } else {
                        textbox_user.setHintTextColor(getResources().getColor(R.color.color_error));
                        textbox_user.setHint("�û�������Ϊ��");
                    }
                } else {
                    textbox_password2.setText("");
                    textbox_password2.setHintTextColor(getResources().getColor(R.color.color_error));
                    textbox_password2.setHint("ȷ���������");
                }
            }
        });


        textbox_password2.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textbox_password2.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                    textbox_password2.setHint("ȷ������");
                }
            }
        });
        textbox_user.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textbox_user.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                    textbox_user.setHint("�û���");
                }
            }
        });
        textbox_password.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textbox_password.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                    textbox_password.setHint("����");
                }
            }
        });
        text_mail.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    text_mail.setHintTextColor(getResources().getColor(R.color.hint_foreground_material_light));
                    text_mail.setHint("����");
                }
            }
        });

        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void startRegist(){
        button_login.setEnabled(false);
        text_registing.setText("���ڽ���ע��...");

        Animation aIn = new AlphaAnimation(1f, 0f);
        aIn.setDuration(300);
        aIn.setFillAfter(true);
        button_login.startAnimation(aIn);
        button_login_shadow.startAnimation(aIn);

        Animation aIn1 = new AlphaAnimation(0f, 1f);
        aIn1.setDuration(300);
        aIn1.setFillAfter(true);
        text_registing.startAnimation(aIn1);

        //��ʼ��¼
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://sanjin6035.vicp.cc/VoiceRecord/user/register";
                String result = "";
                try {
                    //��������
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //���ò�������html���ύ

                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("uusername", textbox_user.getText().toString());
                    BasicNameValuePair param2 = new BasicNameValuePair("upassword", textbox_password.getText().toString());
                    BasicNameValuePair param5 = new BasicNameValuePair("uemail", text_mail.getText().toString());
                    paramList.add(param);
                    paramList.add(param2);
                    paramList.add(param5);

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
                    if(loginmsg.getBoolean("status")){
                        showMessageByToast("ע��ɹ�����ӭ��," + textbox_user.getText().toString());
                        Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                        intent.putExtra("username",textbox_user.getText().toString());
                        intent.putExtra("password",textbox_password.getText().toString());
                        startActivity(intent);
                        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                        if (version > 5) {
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                        }
                        finish();
                    }else{
                        doErrorLogin();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    doErrorLogin();
                }
            }
        }.execute();
    }

    public void doErrorLogin(){
        button_login.setEnabled(true);
        text_registing.setText("���ڽ���ע��...");

        Animation aIn = new AlphaAnimation(0f, 1f);
        aIn.setDuration(300);
        aIn.setFillAfter(true);
        button_login.startAnimation(aIn);
        //button_login_shadow.startAnimation(aIn);

        Animation aIn1 = new AlphaAnimation(1f, 0f);
        aIn1.setDuration(300);
        aIn1.setFillAfter(true);
        text_registing.startAnimation(aIn1);
    }
    public void showMessageByToast(String Msg) {
        Toast.makeText(RegistActivity.this, Msg, Toast.LENGTH_LONG).show();
    }
}
