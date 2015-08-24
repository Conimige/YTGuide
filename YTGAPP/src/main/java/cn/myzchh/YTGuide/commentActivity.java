package cn.myzchh.YTGuide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.myzchh.YTGuide.util.BaseActivity;
import cn.myzchh.YTGuide.util.localUser;

public class commentActivity extends BaseActivity {

    private TextView text_name;
    private TextView text_info;
    private Button btn_comment;
    private EditText edit_comment;
    private ListView list_comment;
    private ImageView image_point;
    private ImageView btn_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        text_name=(TextView)findViewById(R.id.text_name);
        text_info=(TextView)findViewById(R.id.text_info);
        btn_comment=(Button)findViewById(R.id.btn_comment);
        edit_comment=(EditText)findViewById(R.id.edit_comment);
        list_comment=(ListView)findViewById(R.id.list_comment);
        image_point=(ImageView)findViewById(R.id.image_point);
        btn_nav=(ImageView)findViewById(R.id.btn_nav);

        Intent intent=getIntent();
        String pointName=intent.getStringExtra("name");
        text_name.setText(pointName);
        text_info.setText(localUser.getInfoByName(pointName));

        loadTitlePic(pointName);
        refreshComment();

        edit_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btn_comment.setText("����");
                return false;
            }
        });

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localUser.GoWhere(text_name.getText().toString());
                finish();
                overridePendingTransition(R.anim.fade, R.anim.back);
            }
        });

        btn_comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_comment.setBackgroundResource(R.color.button_normal);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_comment.setBackgroundResource(R.color.button_shadow);
                }
                return false;
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_comment.getText().toString().equals("")){
                    return;
                }
                btn_comment.setText("��������...");
                SharedPreferences sharedPreferences = getSharedPreferences("preferences", Activity.MODE_PRIVATE);
                localUser.setLocalUserName(sharedPreferences.getString("UserName", ""));
                localUser.setLocalUserUUID(sharedPreferences.getString("UserUUID", ""));
                if (localUser.getLocalUserName().equals("")){
                    Intent intent1=new Intent(commentActivity.this,loginActivity.class);
                    startActivity(intent1);
                }else{
                    //showMessageByToast("���۳ɹ����û�"+localUser.getLocalUserName()+"��������:"+edit_comment.getText().toString());
                    final String pointName=text_name.getText().toString();
                    final String comment_text=edit_comment.getText().toString();

                    edit_comment.setText("");
                    //��ʼ����
                    new AsyncTask<String, Void, Object>() {
                        @Override
                        protected Object doInBackground(String... params) {
                            String url = "http://sanjin6035.vicp.cc/MapNavi/comment/addComment";
                            String result = "";
                            try {
                                //��������
                                HttpClient httpClient = new DefaultHttpClient();
                                HttpPost post = new HttpPost(url);
                                //���ò�������html���ύ

                                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                                BasicNameValuePair param = new BasicNameValuePair("cusername", localUser.getLocalUserName());
                                BasicNameValuePair param2 = new BasicNameValuePair("pointId", localUser.getPointID(pointName)+"");
                                BasicNameValuePair param3 = new BasicNameValuePair("comment", comment_text);
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
                                System.out.println("setComment_failed!");
                            }
                            return result;
                        }

                        protected void onPostExecute(Object result) {
                            btn_comment.setText("����");
                            super.onPostExecute(result);
                            System.out.println("setComment_result:"+result);
                            try {
                                JSONTokener jsonParser = new JSONTokener(result + "");
                                JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                                //showMessageByToast("getResult:" + result);
                                if(loginmsg.getBoolean("status")){
                                    refreshComment();
                                }else{
                                    showMessageByToast("�޷�������������");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                showMessageByToast("��������Ӧʧ�ܡ�");
                            }
                        }
                    }.execute();
                }
            }
        });

    }

    private void loadTitlePic(String pointName) {
        switch (pointName) {
            case "�ڶ���ѧ¥":
                image_point.setBackgroundResource(R.drawable.p_erjiao);
                break;
            case "�ڶ�ʵ��¥":
                image_point.setBackgroundResource(R.drawable.p_ershi);
                break;
            case "��һ��ѧ¥":
                image_point.setBackgroundResource(R.drawable.p_yijiao);
                break;
            case "B1����¥":
                image_point.setBackgroundResource(R.drawable.p_b_sushe);
                break;
            case "B2����¥":
                image_point.setBackgroundResource(R.drawable.p_b_sushe);
                break;
            case "B��ʳ��":
                image_point.setBackgroundResource(R.drawable.p_b_shitang);
                break;
            case "A��ʳ��":
                image_point.setBackgroundResource(R.drawable.p_a_shitang);
                break;
            case "����":
                image_point.setBackgroundResource(R.drawable.p_wangqiuchang);
                break;
            case "��ͨͼ���":
                image_point.setBackgroundResource(R.drawable.p_tushuguan);
                break;
            case "˫�Ӻ�":
                image_point.setBackgroundResource(R.drawable.p_shuangzihu);
                break;
            case "������˾糡":
                image_point.setBackgroundResource(R.drawable.p_xialibaren);
                break;
            case "������ѧ¥":
                image_point.setBackgroundResource(R.drawable.p_sanjiao);
                break;
            case "���Ľ�ѧ¥":
                image_point.setBackgroundResource(R.drawable.p_sijiao);
                break;
            case "A������¥":
                image_point.setBackgroundResource(R.drawable.p_a_sushe);
                break;
            case "C������":
                image_point.setBackgroundResource(R.drawable.p_tianti);
                break;
            case "C��ʳ��":
                image_point.setBackgroundResource(R.drawable.p_c_shitang);
                break;
            case "��ͨ��Ӿ��":
                image_point.setBackgroundResource(R.drawable.p_youyong);
                break;
            case "����":
                image_point.setBackgroundResource(R.drawable.p_lanqiuchang);
                break;
            case "��ͨ����":
                image_point.setBackgroundResource(R.drawable.p_beimen);
                break;
            case "��������ҵ���н�":
                image_point.setBackgroundResource(R.drawable.p_binguo);
                break;
            case "��ͨ�ٳ�":
                image_point.setBackgroundResource(R.drawable.p_caochang);
                break;
            case "����ɽ������Ժ":
                image_point.setBackgroundResource(R.drawable.p_huaguoshan);
                break;
            case "������ѧ¥":
                image_point.setBackgroundResource(R.drawable.p_liujiao);
                break;
            case "��ͨ����":
                image_point.setBackgroundResource(R.drawable.p_nanmen);
                break;
            case "����¥":
                image_point.setBackgroundResource(R.drawable.p_xingzheng);
                break;
            case "��һʵ��¥":
                image_point.setBackgroundResource(R.drawable.p_yishi);
                break;
            default:
                image_point.setBackgroundResource(R.drawable.p_normal);
                break;
        }
    }

    private void refreshComment(){
        final String pointName=text_name.getText().toString();
        //ץȡ����
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://sanjin6035.vicp.cc/MapNavi/comment/showCommentByPointid";
                String result = "";
                try {
                    //��������
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //���ò�������html���ύ

                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("pointId", localUser.getPointID(pointName)+"");
                    paramList.add(param);

                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    //����HttpPost���󣬲�����HttpResponse����
                    HttpResponse httpResponse = httpClient.execute(post);
                    // �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //��ȡ���ؽ��
                        result = EntityUtils.toString(httpResponse.getEntity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("getComment_failed!");
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                System.out.println("getComment_result:"+result);
                try {
                    JSONTokener jsonParser = new JSONTokener(result + "");
                    JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                    if(loginmsg.getBoolean("status")){
                        List<Map<String, Object>> datas=null;
                        JSONArray blogInfo = loginmsg.getJSONArray("pointComment");
                        datas=getList(blogInfo.toString());
                        list_comment.setAdapter(new SimpleAdapter(commentActivity.this,datas,R.layout.item_comment,
                                new String[]{"cusername","comment"},new int[]{R.id.text_name,R.id.text_body}));
                    }else{
                        showMessageByToast("�޷�ץȡ��������");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    showMessageByToast("��������Ӧʧ�ܡ�");
                }
            }
        }.execute();
    }

    public Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);   @SuppressWarnings("unchecked")
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext())
            {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Map<String, Object>> getList(String jsonString) {
        ArrayList<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(getMap(jsonObject.toString()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void showMessageByToast(String Msg) {
        Toast.makeText(commentActivity.this, Msg, Toast.LENGTH_SHORT).show();
        System.out.println(">>"+Msg);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            overridePendingTransition(R.anim.fade, R.anim.back);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
