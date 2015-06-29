package cn.myzchh.YTGuide;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidtestproject.testapp.R;

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

import cn.myzchh.YTGuide.util.BaseActivity;

public class registActivity extends BaseActivity {

    private EditText textbox_user;
    private EditText textbox_password;
    private EditText textbox_password2;
    private EditText textbox_nickname;
    private Button btn_login;
    private ImageView button_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        textbox_user=(EditText)findViewById(R.id.textbox_user);
        textbox_password=(EditText)findViewById(R.id.textbox_password);
        textbox_password2=(EditText)findViewById(R.id.textbox_password2);
        textbox_nickname=(EditText)findViewById(R.id.textbox_nickname);
        btn_login=(Button)findViewById(R.id.btn_login);
        button_Back=(ImageView)findViewById(R.id.button_Back);

        button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMe();
            }
        });

        btn_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_login.setBackgroundResource(R.drawable.btn_regist);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_login.setBackgroundResource(R.drawable.btn_regist_down);
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String strUserName=textbox_user.getText().toString();
                final String strPassword=textbox_password.getText().toString();
                final String strNickName=textbox_nickname.getText().toString();
                if (strUserName.equals("")){
                    System.out.println("1");
                    showMessageByToast("��������ȷ���û���");
                    return;
                }
                if (strUserName.length()<=4){
                    System.out.println("2");
                    showMessageByToast("�û������������λ");
                    return;
                }
                if (strPassword.equals("")){
                    System.out.println("3");
                    showMessageByToast("��������ȷ������");
                    return;
                }
                if (strPassword.length()<=6){
                    System.out.println("4");
                    showMessageByToast("������������λ");
                    return;
                }
                if (!textbox_password2.getText().toString().equals(strPassword)){
                    System.out.println("5");
                    showMessageByToast("��ȷ���������������һ��");
                    return;
                }
                if (strNickName.equals("")){
                    System.out.println("6");
                    showMessageByToast("��������ȷ���ǳ�");
                    return;
                }
                //��ʼע��
                new AsyncTask<String, Void, Object>() {
                    @Override
                    protected Object doInBackground(String... params) {
                        String url = "http://sanjin6035.vicp.cc/MapNavi/user/register";
                        String result = "";
                        try {
                            //��������
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost post = new HttpPost(url);
                            //���ò�������html���ύ

                            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                            BasicNameValuePair param = new BasicNameValuePair("uusername", strUserName);
                            BasicNameValuePair param2 = new BasicNameValuePair("upassword", strPassword);
                            BasicNameValuePair param3 = new BasicNameValuePair("unick", strNickName);
                            paramList.add(param);
                            paramList.add(param2);
                            paramList.add(param3);

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
                            System.out.println("regist_failed!");
                        }
                        return result;
                    }

                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        System.out.println("regist_post_result:" + result);
                        try {
                            JSONTokener jsonParser = new JSONTokener(result + "");
                            JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                            //showMessageByToast("getResult:" + result);
                            if(loginmsg.getBoolean("status")){
                                showMessageByToast("ע��ɹ�����ӭ��," + strUserName);
                                Intent intent = new Intent(registActivity.this, loginActivity.class);
                                intent.putExtra("username",textbox_user.getText().toString());
                                intent.putExtra("password",textbox_password.getText().toString());
                                startActivity(intent);
                                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                                if (version > 5) {
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                }
                                finish();
                            }else{
                                showMessageByToast("ע��ʧ�ܣ���ע����û��������Ѿ���ע��");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            showMessageByToast("ע��ʧ�ܣ���ע����û��������Ѿ���ע��");
                        }
                    }
                }.execute();
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            closeMe();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closeMe() {
        Intent intent=new Intent(registActivity.this,loginActivity.class);
        startActivity(intent);
    }

    public void showMessageByToast(String Msg) {
        Toast.makeText(registActivity.this, Msg, Toast.LENGTH_SHORT).show();
        System.out.println(">>"+Msg);
    }
}
