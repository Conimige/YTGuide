package com.stx.voicenote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.ClipboardManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.example.androidtestproject.testapp.R;

import cn.myzchh.YTGuide.util.BaseActivity;
import cn.myzchh.YTGuide.util.ChatAdapter;
import cn.myzchh.YTGuide.util.Message;
import cn.myzchh.YTGuide.util.MyAdapter;
import cn.myzchh.YTGuide.util.localUser;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class RoomActivity extends BaseActivity {

    // �����ʱ��
    Timer timer=new Timer();
    private MyAdapter sa;

    private ListView lvData;
    private TextView text_Name;
    private String getInviteName;
    private ImageView btn_rec;
    private ImageView btn_rec_shadow;
    private ImageView rec_status_bkg;
    private TextView text_status;
    private RelativeLayout newBox;
    private EditText edit_newBox;
    private HorizontalScrollView scroll_box;

    private BaseAdapter ba;

    private List<Message> msgList;
    private TextView btn_notTrue;
    private ImageView btn_publish;


    private String yuyinJSON="";
    private String[] yuyinChoose;
    private VoiceRecognitionClient client;
    private BaiduASRDigitalDialog dlgVoiceRecognize;

    private String old_msg_result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        dlgVoiceRecognize= createASRDialog();

        lvData=(ListView)findViewById(R.id.listView);
        ba=getAdapter();
        lvData.setAdapter(ba);
        text_Name=(TextView)findViewById(R.id.text_Name);
        btn_rec=(ImageView)findViewById(R.id.btn_rec);
        btn_rec_shadow=(ImageView)findViewById(R.id.btn_rec_shadow);
        text_status=(TextView)findViewById(R.id.text_status);
        rec_status_bkg=(ImageView)findViewById(R.id.rec_status_bkg);
        scroll_box=(HorizontalScrollView)findViewById(R.id.scroll_box);
        newBox=(RelativeLayout)findViewById(R.id.newBox);
        edit_newBox=(EditText)findViewById(R.id.edit_newBox);
        btn_notTrue=(TextView)findViewById(R.id.btn_notTrue);
        btn_publish=(ImageView)findViewById(R.id.btn_publish);

        Intent intent=getIntent();
        getInviteName=intent.getStringExtra("inviteName");
        text_Name.setText(localUser.getUSERNAME() + "��������");

        Animation aIn0 = new AlphaAnimation(1f, 0f);
        aIn0.setDuration(1);
        aIn0.setFillAfter(true);
        btn_rec_shadow.startAnimation(aIn0);
        rec_status_bkg.startAnimation(aIn0);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                loadMessage();
            }
        };

        timer.schedule(new TimerTask() {
            int i = 10;

            // TimerTask �Ǹ�������,ʵ�ֵ���Runable��
            @Override
            public void run() {
                android.os.Message message = new android.os.Message();
                message.what = i++;
                handler.sendMessage(message);
            }
        }, 100, 2000);


        btn_rec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Animation aIn0 = new AlphaAnimation(0f, 1f);
                    aIn0.setDuration(300);
                    aIn0.setFillAfter(true);
                    btn_rec_shadow.startAnimation(aIn0);
                    rec_status_bkg.startAnimation(aIn0);
                    text_status.startAnimation(aIn0);
                    startRec();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Animation aIn0 = new AlphaAnimation(1f, 0f);
                    aIn0.setDuration(300);
                    aIn0.setFillAfter(true);
                    btn_rec_shadow.startAnimation(aIn0);
                    rec_status_bkg.startAnimation(aIn0);
                    text_status.startAnimation(aIn0);
                    client.speakFinish();
                }
                return false;
            }
        });

        newBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation aIn0 = new AlphaAnimation(1f, 0f);
                aIn0.setDuration(300);
                aIn0.setFillAfter(true);
                newBox.startAnimation(aIn0);
                aIn0.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        newBox.setVisibility(View.GONE);
                        newBox.setEnabled(false);
                        newBox.setClickable(false);
                        btn_notTrue.setEnabled(false);
                        btn_notTrue.setClickable(false);
                        edit_newBox.setEnabled(false);
                        edit_newBox.setVisibility(View.GONE);
                        edit_newBox.setClickable(false);
                        btn_publish.setEnabled(false);
                        btn_publish.setClickable(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        btn_notTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RoomActivity.this).setTitle("").setItems(yuyinChoose,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        edit_newBox.setText(yuyinChoose[which]);
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_newBox.getText().toString().equals("")) {
                    showMessageByToast("�����ܷ��Ϳյ���Ϣ��");
                    return;
                }
                Message msg = new Message();
                msg.setType(ChatAdapter.VALUE_RIGHT);
                msg.setValue(edit_newBox.getText().toString());
                msgList.add(msg);
                ba.notifyDataSetChanged();
                lvData.setSelection(lvData.FOCUS_DOWN);//ˢ�µ��ײ�

                Animation aIn0 = new AlphaAnimation(1f, 0f);
                aIn0.setDuration(300);
                aIn0.setFillAfter(true);
                newBox.startAnimation(aIn0);
                aIn0.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        newBox.setVisibility(View.GONE);
                        newBox.setEnabled(false);
                        newBox.setClickable(false);
                        btn_notTrue.setEnabled(false);
                        btn_notTrue.setClickable(false);
                        edit_newBox.setEnabled(false);
                        edit_newBox.setVisibility(View.GONE);
                        edit_newBox.setClickable(false);
                        btn_publish.setEnabled(false);
                        btn_publish.setClickable(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                if (localUser.getROOMID().equals("")){
                    return;
                }
                //������Ϣ��������
                new AsyncTask<String, Void, Object>() {
                    @Override
                    protected Object doInBackground(String... params) {
                        String url = "http://sanjin6035.vicp.cc/VoiceRecord/voicerecord/addVoiceRecord";
                        String result = "";
                        try {
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost post = new HttpPost(url);

                            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                            BasicNameValuePair param = new BasicNameValuePair("vusername", localUser.getUSERNAME());
                            BasicNameValuePair param1 = new BasicNameValuePair("uuid", localUser.getUUID());
                            BasicNameValuePair param2 = new BasicNameValuePair("word", edit_newBox.getText().toString());
                            BasicNameValuePair param3 = new BasicNameValuePair("roomid", localUser.getROOMID());
                            paramList.add(param);
                            paramList.add(param1);
                            paramList.add(param2);
                            paramList.add(param3);
                            post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                            HttpResponse httpResponse = httpClient.execute(post);

                            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                result = EntityUtils.toString(httpResponse.getEntity());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return result;
                    }

                    protected void onPostExecute(Object result) {
                        super.onPostExecute(result);
                        try {
                            JSONTokener jsonParser = new JSONTokener(result + "");
                            JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                            if (loginmsg.getBoolean("status")) {
                                showMessageByToast("���ͳɹ�");
                            }
                        } catch (Exception e) {

                        }
                    }
                }.execute();
            }
        });
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //�����˵�

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                final View v=view;
                final EditText et = new EditText(RoomActivity.this);
                builder.setItems(getResources().getStringArray(R.array.MessageMenu), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int ItemIndex) {
                        TextView textBox=(TextView)v.findViewById(R.id.text_Body);
                        et.setText(textBox.getText().toString());
                        switch (ItemIndex) {
                            case 0:
                                ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                cmb.setText(textBox.getText().toString());
                                break;
                            case 1:
                                new AlertDialog.Builder(RoomActivity.this).setTitle("�༭����").setView(
                                        et).setPositiveButton("ȷ��", null)
                                        .setNegativeButton("ȡ��", null).show();
                                break;
                            case 2:
                                Intent intent=new Intent(Intent.ACTION_SEND);
                                intent.setType("image/*");
                                intent.putExtra(Intent.EXTRA_SUBJECT, "����");
                                intent.putExtra(Intent.EXTRA_TEXT, textBox.getText().toString());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(intent, getTitle()));
                                break;
                            default:
                                break;
                        }
                        arg0.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    public void startRec(){
        client=VoiceRecognitionClient.getInstance(RoomActivity.this);
        client.setTokenApis("x5vVhmsnoEN6OM1w6hbXgc6A", "3d3d824b4dc93c1edd465aa93bb9442a");

        VoiceRecognitionConfig config=new VoiceRecognitionConfig();
        config.setProp(VoiceRecognitionConfig.PROP_SEARCH);
        config.enableNLU();

        client.startVoiceRecognition(new VoiceRecognitionClient.VoiceClientStatusChangeListener() {
            @Override
            public void onClientStatusChange(int status, Object results) {
                switch (status){
                    case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START:
                        //��ʼ˵��
                        text_status.setText("��ʼ˵��");
                        break;
                    case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                        //¼����
                        text_status.setText("������...");
                        break;
                    case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                        //˵������
                        text_status.setText("�ɿ���ָ�Ա༭");
                        break;
                    case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                        //�ɹ�
                        //text_status.setText("FINISH");
                        List list=(List)results;
                        String str= Arrays.toString(null == list ? null : list.toArray());
                        //showMessageByToast(str);
                        str= str.substring(1,str.length()-1);
                        yuyinJSON=str;
                        newBox.setVisibility(View.VISIBLE);
                        newBox.setEnabled(true);
                        newBox.setClickable(true);
                        btn_notTrue.setEnabled(true);
                        btn_notTrue.setClickable(true);
                        edit_newBox.setEnabled(true);
                        edit_newBox.setClickable(true);
                        edit_newBox.setVisibility(View.VISIBLE);
                        btn_publish.setEnabled(true);
                        btn_publish.setClickable(true);
                        Animation aIn0 = new AlphaAnimation(0f, 1f);
                        aIn0.setDuration(300);
                        aIn0.setFillAfter(true);
                        newBox.startAnimation(aIn0);
                        String result="";
                        try {
                            JSONTokener jsonParser = new JSONTokener(str);
                            JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                            String str1=loginmsg.getString("json_res");
                            JSONTokener jsonParser1 = new JSONTokener(str1);
                            JSONObject loginmsg1 = (JSONObject) jsonParser1.nextValue();

                            result=loginmsg1.getString("raw_text");

//                            JSONTokener jsonParser = new JSONTokener(str);
//                            showMessageByToast("0");
//                            JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
//                            String str1=loginmsg.getString("json_res");
//                            JSONTokener jsonParser2 = new JSONTokener(str1);
//                            JSONObject json_res =loginmsg.getJSONObject("json_res");
//                            showMessageByToast("1");
//                            result=json_res.getString("raw_text");
//                            showMessageByToast("2"+result);
//                            JSONArray selectItem=json_res.getJSONArray("item");
//                            showMessageByToast("3");
//                            yuyinChoose=new String[selectItem.length()];
//                            showMessageByToast("4");
//                            JSONArray selectItem=loginmsg.getJSONArray("item");
//                            result=result+selectItem.toString();
//                            for(int i=0;i<selectItem.length();i++){
//                                yuyinChoose[i]=selectItem.getString(i);
//                            }
                            String itemStr=loginmsg.getString("item");
                            itemStr=itemStr.replace("[\"","");
                            itemStr=itemStr.replace("\"]", "");
                            yuyinChoose=itemStr.split("\",\"");
                        }catch (Exception e){}
                        edit_newBox.setText(result);

//                        Message msg = new Message();
//                        msg.setType(ChatAdapter.VALUE_RIGHT);
//                        msg.setValue(str);
//                        msgList.add(msg);
//                        ba.notifyDataSetChanged();
//                        lvData.setSelection(lvData.FOCUS_DOWN);//ˢ�µ��ײ�
                        //lvData.setAdapter(new ChatAdapter(RoomActivity.this,msgList));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNetworkStatusChange(int i, Object o) {

            }

            @Override
            public void onError(int i, int i1) {
                text_status.setText("ERROR");
            }
        },config);


    }



    private BaseAdapter getAdapter(){
        return new ChatAdapter(this, getMyData());
    }

    private List<Message> getMyData(){

        msgList = new ArrayList<Message>();
        Message msg;
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_LEFT);
//        msg.setValue("������һ���������ԣ��յ���ش�");
//        msgList.add(msg);
//
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("�յ�");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("����");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("����������ɽ����ɽ���ķ羰����");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_LEFT);
//        msg.setValue("�������Ҳ��������ߵ���ܴ󣬺ܳ�ʪ��");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("ɶʱ��ȥ�Ը��������ڴ�����������");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_LEFT);
//        msg.setValue("����~");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("֧�ֶ��û�Эͬ¼�����������Ҫ��½�ſ���ʹ�ã��û���¼֮����Կ�����½��������������û����û���¼�󣬿������������û�һ��¼��������΢�ŵ�����ͨ�������������������½�û����ܵ�һ��������ʾ�����ȷ���ȿ���һ��¼�� ");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("�Ǻ���");
//        msgList.add(msg);
//        msg = new Message();
//        msg.setType(ChatAdapter.VALUE_RIGHT);
//        msg.setValue("ok");
//        msgList.add(msg);

        return msgList;

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = new Intent(RoomActivity.this, InviteActivity.class);
            intent.putExtra("roomname",getInviteName);
            intent.putExtra("roomid","0");
            timer.cancel();
            timer=null;
            startActivity(intent);
            int version = Integer.valueOf(android.os.Build.VERSION.SDK);
            if (version > 5) {
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(dlgVoiceRecognize!=null)
            dlgVoiceRecognize.dismiss();
    }


    //��ð�ť�ĵ���¼�
    public void btnVoiceRecognize_Click(View view){
        if(dlgVoiceRecognize!=null)
            dlgVoiceRecognize.show();
        else
            Toast.makeText(this, "��ʼ������ʶ��ʧ�ܣ��޷��򿪡�", Toast.LENGTH_LONG).show();
    }

    private BaiduASRDigitalDialog createASRDialog(){
        Bundle params = new Bundle();
        //���ÿ��� API Key
        params.putString(BaiduASRDigitalDialog.PARAM_API_KEY,"x5vVhmsnoEN6OM1w6hbXgc6A");
        //���ÿ���ƽ̨ Secret Key
        params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,"3d3d824b4dc93c1edd465aa93bb9442a");
        //����ʶ���������������롢��ͼ�����֡�������ѡ��Ĭ��Ϊ���롣
        params.putInt(BaiduASRDigitalDialog.PARAM_PROP, VoiceRecognitionConfig.PROP_INPUT);
        //�����������ͣ�������ͨ�����������Ӣ�ģ���ѡ��Ĭ��Ϊ������ͨ��
        params.putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,VoiceRecognitionConfig.LANGUAGE_CHINESE);
        //�����Ҫ��������������·�����������Ϊ���벻֧��
        //params.putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE,true);
        //���öԻ������⣬��ѡ��BaiduASRDigitalDialog �ṩ�����������졢�̡���������ɫ��ÿ����
        //ɫ�ַ�����������ɫ������ 8 �����⣬�����߿��԰���ѡ��ȡֵ�ο� BaiduASRDigitalDialog ��
        //ǰ׺Ϊ THEME_�ĳ�����Ĭ��Ϊ����ɫ
        //params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME,BaiduASRDigitalDialog.THEME_RED_DEEPBG);
        BaiduASRDigitalDialog dlgVoiceRecognize = null;
        try{
            dlgVoiceRecognize = new BaiduASRDigitalDialog(this, params);
            dlgVoiceRecognize.setDialogRecognitionListener(new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle bundle) {
                    ProcessVoiceRecognizeResult(bundle);
                }
            });
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return dlgVoiceRecognize;
    }

    public void ProcessVoiceRecognizeResult(Bundle results){
        ArrayList<String> rs=null;
        if(results!=null)
            rs=results.getStringArrayList(DialogRecognitionListener.RESULTS_RECOGNITION);
        if(results==null || rs==null || rs.size()<=0){
            Toast.makeText(this,"û������ʶ������",Toast.LENGTH_LONG).show();
            return;
        }
        String s="";
        for(String i : rs){
            s+=i;
        }
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
//        if(s.contains("�˳�")) btnQuit_Click(null);
    }


    //�˳�
    public void btnQuit_Click(View view){
        //this.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public void showMessageByToast(String Msg){
        Toast.makeText(RoomActivity.this,Msg,Toast.LENGTH_SHORT).show();
    }

    public void loadMessage(){
        //��ȡ������Ϣ��¼
        if (localUser.getROOMID().equals("")){
            return;
        }
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://sanjin6035.vicp.cc/VoiceRecord/voicerecord/showAllVoiceRecord";
                String result = "";
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);

                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("roomid", localUser.getROOMID());
                    BasicNameValuePair param1 = new BasicNameValuePair("vuser", localUser.getUSERNAME());
                    paramList.add(param);
                    paramList.add(param1);
                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    HttpResponse httpResponse = httpClient.execute(post);

                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        result = EntityUtils.toString(httpResponse.getEntity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                if (!old_msg_result.equals(result+"")){
                    try {
                        JSONTokener jsonParser = new JSONTokener(result + "");
                        JSONObject loginmsg = (JSONObject) jsonParser.nextValue();
                        if (loginmsg.getBoolean("status")) {
                            List<Map<String, Object>> datas=null;
                            JSONArray msg=loginmsg.getJSONArray("VoiceRecords");
    //                        datas=getList(msg.toString());
    //                        sa=new MyAdapter(RoomActivity.this,datas,R.layout.invite_listitem,
    //                                new String[]{"uusername","uemail","btnico","status","uuid"},
    //                                new int[]{R.id.text_NickName,R.id.text_mail,R.id.btn_ico,R.id.text_status,R.id.text_UUID});
                            msgList = new ArrayList<Message>();
                            for (int i=0;i<msg.length();i++){
                                JSONObject jb=msg.getJSONObject(i);
                                Message msgr;
                                String NameStr=jb.getString("vusername");
                                msgr = new Message();
                                if (NameStr.equals(localUser.getUSERNAME())){
                                    msgr.setType(ChatAdapter.VALUE_RIGHT);
                                }else{
                                    msgr.setType(ChatAdapter.VALUE_LEFT);
                                }
                                msgr.setValue(jb.getString("word"));
                                msgr.setName(NameStr);

                                msgList.add(msgr);
                            }
                            ba=new ChatAdapter(RoomActivity.this, msgList);
                            lvData.setAdapter(ba);
                        }
                        old_msg_result=result+"";
                        lvData.setSelection(lvData.FOCUS_DOWN);//ˢ�µ��ײ�
                    } catch (Exception e) {

                    }
                }else{

                }
            }

        }.execute();
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
                Map<String, Object> m =getMap(jsonObject.toString());
                if (m!=null){
                    list.add(m);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Object> getMap(String jsonString) {
        JSONObject jsonObject;
        try {
            boolean Nullflag=false;
            String getName="";

            jsonObject = new JSONObject(jsonString);
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

}
