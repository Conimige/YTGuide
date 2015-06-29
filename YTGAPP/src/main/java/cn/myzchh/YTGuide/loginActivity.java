package cn.myzchh.YTGuide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import cn.myzchh.YTGuide.util.localUser;

public class loginActivity extends BaseActivity {

    private EditText edit_user;
    private EditText edit_psd;
    private Button btn_login;
    private TextView btn_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_user = (EditText) findViewById(R.id.edit_user);
        edit_psd = (EditText) findViewById(R.id.edit_psd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_new = (TextView) findViewById(R.id.btn_new);

        Intent intentG=getIntent();
        String strUserName=intentG.getStringExtra("username");
        String strPassword=intentG.getStringExtra("password");

        if (strUserName!=null && strPassword!=null){
            edit_user.setText(strUserName);
            edit_psd.setText(strPassword);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                localUser.setLocalUserName(edit_user.getText().toString());
//                localUser.setLocalUserUUID(edit_psd.getText().toString());
//                showMessageByToast("�û�"+edit_user.getText().toString()+"��½�ɹ�");
                doLoginNow();
            }
        });

        btn_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_login.setBackgroundResource(R.drawable.btn_login);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_login.setBackgroundResource(R.drawable.btn_login_down);
                }
                return false;
            }
        });

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginActivity.this,registActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }//end onCreate

    private void doLoginNow() {
        final String strUserName=edit_user.getText().toString();
        final String strPassword=edit_psd.getText().toString();

        //��ʼ��¼
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://sanjin6035.vicp.cc/MapNavi/user/login";
                String result = "";
                try {
                    //��������
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //���ò�������html���ύ

                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("uusername", strUserName);
                    BasicNameValuePair param2 = new BasicNameValuePair("upassword", strPassword);
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
                    System.out.println("login_failed!");
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                System.out.println("login_post_result:"+result);
                try {
                    JSONTokener jsonParser = new JSONTokener(result + "");
                    JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                    //showMessageByToast("getResult:" + result);
                    if(loginmsg.getBoolean("status")){
                        String loginUUID=loginmsg.getString("uuid");
                        JSONObject jsonUserObj=loginmsg.getJSONObject("user");
                        String loginUserName=jsonUserObj.getString("unick");
                        showMessageByToast("��½�ɹ�����ӭ��," + strUserName);
                        localUser.setLocalUserName(edit_user.getText().toString());
                        localUser.setLocalUserUUID(edit_psd.getText().toString());

                        SharedPreferences mySharedPreferences = getSharedPreferences("preferences", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("UserName", loginUserName);
                        editor.putString("UserUUID", loginUUID);
                        editor.commit();

                        finish();
                    }else{
                        showMessageByToast("��¼ʧ�ܣ��޷���ȡ��������״̬");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    showMessageByToast("��¼ʧ�ܣ���������Ӧʧ�ܡ�");
                }
            }
        }.execute();
    }


    public void showMessageByToast(String Msg) {
        Toast.makeText(loginActivity.this, Msg, Toast.LENGTH_SHORT).show();
        System.out.println(">>"+Msg);
    }


}
