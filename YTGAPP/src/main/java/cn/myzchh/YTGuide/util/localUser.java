package cn.myzchh.YTGuide.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import cn.myzchh.YTGuide.MapActivity;

/**
 * Created by chao on 2015/5/19.
 */

public class localUser{
    private static double gps_JD;
    private static double gps_WD;
    private static List<Map> local_path;
    private static String localUserName="";
    private static String localUserUUID="";
    private static double goWhereX=0;
    private static double goWhereY=0;


    public static void GoWhere(String pointName){
        if (local_path==null){
            getLocal_path();
        }
        for (int i=0;i<local_path.size();i++){
            Map m=local_path.get(i);
            if (m.get("name").equals(pointName)){
                goWhereX=(double)m.get("x");
                goWhereY=(double)m.get("y");
                break;
            }
        }
    }

    public static String getInfoByName(String pointName){
        String result="";
        if (local_path==null){
            getLocal_path();
        }
        for (int i=0;i<local_path.size();i++){
            Map m=local_path.get(i);
            if (m.get("name").equals(pointName)){
                result=(String)m.get("info");
                break;
            }
        }
        return result;
    }

    public static int getPointID(String pointName){
        if (local_path==null){
            getLocal_path();
        }
        int t=-1;
        for (int i=0;i<local_path.size();i++){
            Map m=local_path.get(i);
            if (m.get("name").equals(pointName)){
                t=i;
                break;
            }
        }
        return t;
    }

    public static String getLocalUserUUID() {
        new localUser().loadUserInfo();
        return localUserUUID;
    }

    public static void setLocalUserUUID(String localUserUUID) {
        localUser.localUserUUID = localUserUUID;
        new localUser().saveUserInfo();
    }

    private void saveUserInfo() {
//        SharedPreferences mySharedPreferences = MapActivity.getSharedPreferences("preferences", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mySharedPreferences.edit();
//        editor.putString("UserName", localUserName);
//        editor.putString("UserUUID", localUserUUID);
//        editor.commit();
    }

    private void loadUserInfo() {
//        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Activity.MODE_PRIVATE);
//        setLocalUserName(sharedPreferences.getString("UserName", ""));
//        setLocalUserUUID(sharedPreferences.getString("UserUUID", ""));
    }

    public static String getLocalUserName() {
        new localUser().loadUserInfo();
        return localUserName;
    }

    public static void setLocalUserName(String localUserName) {
        localUser.localUserName = localUserName;
        new localUser().saveUserInfo();
    }

    public static double getGoWhereX() {
        double d=goWhereX;
        goWhereX=0;
        return d;
    }

    public static void setGoWhereX(double goWhereX) {
        localUser.goWhereX = goWhereX;
    }

    public static double getGoWhereY() {
        double d=goWhereY;
        goWhereY=0;
        return d;
    }

    public static void setGoWhereY(double goWhereY) {
        localUser.goWhereY = goWhereY;
    }



    public static List<Map> getLocal_path() {
        if (local_path==null){
            local_path=new ArrayList<Map>();
            local_path.add(addNewPath("�ڶ���ѧ¥","�����ǵڶ���ѧ¥�����̶�¥�������ʵ��ѧ��ͨѧԺ˫��ϵ׿Խ�˲Ž����������ڵء�",30.0038520000,106.2465740000));
            local_path.add(addNewPath("�ڶ�ʵ��¥","�����ǵڶ�ʵ��¥����ʵ��ξ�������",30.0046610000,106.2466100000));
            local_path.add(addNewPath("��һʵ��¥","�����ǵ�һʵ��¥����ʵ��ξ�������",30.0050600000,106.2466460000));
            local_path.add(addNewPath("��һ��ѧ¥","�����ǵ�һ��ѧ¥",30.0050560000,106.2472970000));
            local_path.add(addNewPath("B1����¥","������B1����¥����ͨ�����ν����������õ����ᣬ��ʩ�걸�����������ϴ������������¹���ܡ�������ȫ������������Ŀյ������б�׼���߲�ڣ������嵽ÿ����λ��",30.0058740000,106.2473500000));
            local_path.add(addNewPath("B2����¥","������B2����¥����ͨ�����ν����������õ����ᣬ��ʩ�걸�����������ϴ������������¹���ܡ�������ȫ������������Ŀյ������б�׼���߲�ڣ������嵽ÿ����λ��",30.0065620000,106.2473640000));
            local_path.add(addNewPath("��ͨ����","��������ͨ���ţ��������Ǳ�������ҵ���н�",30.0072620000,106.2474090000));//6
            local_path.add(addNewPath("B��ʳ��","������B��ʳ�ã�Ҳ������ʳ��",30.0073280000,106.2466680000));
            local_path.add(addNewPath("A��ʳ��","������A��ʳ�ã�Ҳ������ʳ��",30.0075430000,106.2451490000));
            local_path.add(addNewPath("����","����������",30.0051528775,106.2459762949));
            local_path.add(addNewPath("��ͨͼ���","��������ͨͼ��ݣ�ͼ�����ѧԺ��������Ϣ���ģ���Ϊ��ѧ�Ϳ��з����ѧ���Ի�������ѧУ��Ϣ���������Ϣ������Ҫ���ء�ͼ��ݵĽ���ͷ�չӦ��ѧУ�Ľ���ͷ�չ����Ӧ����ˮƽ��ѧУ����ˮƽ����Ҫ��־��",30.0043950000,106.2454910000));//10
            local_path.add(addNewPath("˫�Ӻ�","������˫�Ӻ���˫�Ӻ�����ͨ�������㣬Ҳ���������ֵĺ�ȥ����",30.0033318775,106.2449892949));
            local_path.add(addNewPath("������˾糡","������������˾糡",30.0026068775,106.2450962949));
            local_path.add(addNewPath("������ѧ¥","�����ǵ�����ѧ¥",30.0022030241,106.2449881059));
            local_path.add(addNewPath("���߽�ѧ¥","�����ǵ��߽�ѧ¥",30.0037878775,106.2473462949));
            local_path.add(addNewPath("�����ѧ¥","�����ǵ����ѧ¥",30.0026300000,106.2467190000));
            local_path.add(addNewPath("���Ľ�ѧ¥","�����ǵ��Ľ�ѧ¥",30.0029620000,106.2467010000));
            local_path.add(addNewPath("������ѧ¥","�����ǵ�����ѧ¥",30.0033690000,106.2466740000));//17
            local_path.add(addNewPath("����¥","����������¥��ѧԺ�쵼�Լ���λ��ʦ�칫�ص�������",30.0021821176,106.2460180741));
            local_path.add(addNewPath("��ͨ����","��������ͨ���ţ�����������೬�С��������Լ��������г��������ϻ������챾����ɫС��̯��",30.0043520000,106.2474230000));
            local_path.add(addNewPath("��ͨ����","��������ͨ����",30.0020770000,106.2455090000));
            local_path.add(addNewPath("A������¥","������A������¥����ͨ�����ν����������õ����ᣬ��ʩ�걸�����������ϴ������������¹���ܡ�������ȫ������������Ŀյ������б�׼���߲�ڣ������嵽ÿ����λ��",30.0057750000,106.2455590000));
            local_path.add(addNewPath("����ɽ������Ժ","�����ǻ���ɽ������Ժ������ɽ����ͨ��������֮һ��Ҳ����Ϣ��Լ���ȥ֮�ء�",30.0043990000,106.2444090000));
            local_path.add(addNewPath("��ͨ�ٳ�","��������ͨ�ٳ���������صĿγ̴����������У�ͬʱ�����ﻹ��ٰ���ֻ����������",30.0062290000,106.2465740000));//23
            local_path.add(addNewPath("����","����������",30.0064630000,106.2443190000));
            local_path.add(addNewPath("C������","������C�����ţ�ӵ�С����ݡ�֮�ƣ�ͨ�����žͿ���ǰ����ͨ��У���Լ�C������¥��",30.0075740000,106.2461160000));
            local_path.add(addNewPath("C��ʳ��","������C��ʳ��",30.0087230000,106.2461430000));//26
            local_path.add(addNewPath("��ͨ��Ӿ��","��������ͨ��Ӿ��",30.0096220000,106.2464120000));
            local_path.add(addNewPath("C������¥","������C������¥��Զ��ѧԺ����¥���ڵأ���ͨ�����ν����������õ����ᣬ��ʩ�걸�����������ϴ������������¹���ܡ�������ȫ������������Ŀյ������б�׼���߲�ڣ������嵽ÿ����λ��",30.0085430000,106.2472030000));
            local_path.add(addNewPath("��������ҵ���н�","�����Ǳ�������ҵ���н֣���������Ծ��鹺���������֣������ǻ�ӵ��һ����ӰԺ����ʱ�������´�Ƭ��",30.0082230000,106.2465380000));
//            local_path.add(addNewPath("","",1,1));
//            local_path.add(addNewPath("","",1,1));
//            local_path.add(addNewPath("","",1,1));
//            local_path.add(addNewPath("","",1,1));
        }
        return local_path;
    }

    public static List<Map> searchPath(String keyWord){
        List<Map> m=new ArrayList<Map>();
        if (keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("˫��","").length()!=keyWord.length()){
            m.add(local_path.get(0));
        }
        if (keyWord.replace("��ʵ��","").length()!=keyWord.length()){
            m.add(local_path.get(1));
        }
        if (keyWord.replace("һʵ��","").length()!=keyWord.length()){
            m.add(local_path.get(2));
        }
        if (keyWord.replace("һ��","").length()!=keyWord.length()){
            m.add(local_path.get(3));
        }
        if (keyWord.replace("B1","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("B��","").length()!=keyWord.length()){
            m.add(local_path.get(4));
        }
        if (keyWord.replace("�ų�","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(4));
        }
        if (keyWord.replace("B2","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("B��","").length()!=keyWord.length()){
            m.add(local_path.get(5));
        }
        if (keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(6));
        }
        if (keyWord.replace("B��ʳ��","").length()!=keyWord.length() || keyWord.replace("��ʳ��","").length()!=keyWord.length()|| keyWord.replace("ʳ��","").length()!=keyWord.length()|| keyWord.replace("�Է�","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(7));
        }
        if (keyWord.replace("A��ʳ��","").length()!=keyWord.length() || keyWord.replace("��ʳ��","").length()!=keyWord.length()|| keyWord.replace("ʳ��","").length()!=keyWord.length()|| keyWord.replace("�Է�","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(8));
        }
        if (keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(9));
        }
        if (keyWord.replace("ͼ���","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(10));
        }
        if (keyWord.replace("˫�Ӻ�","").length()!=keyWord.length() || keyWord.replace("Լ��","").length()!=keyWord.length() || keyWord.replace("��Ϣ","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(11));
        }
        if (keyWord.replace("�������","").length()!=keyWord.length() || keyWord.replace("��Ժ","").length()!=keyWord.length() || keyWord.replace("�糡","").length()!=keyWord.length()){
            m.add(local_path.get(12));
        }
        if (keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(13));
        }
        if (keyWord.replace("�߽�","").length()!=keyWord.length() || keyWord.replace("ͣ����","").length()!=keyWord.length()){
            m.add(local_path.get(14));
        }
        if (keyWord.replace("���","").length()!=keyWord.length() || keyWord.replace("��ҵ","").length()!=keyWord.length()){
            m.add(local_path.get(15));
        }
        if (keyWord.replace("�Ľ�","").length()!=keyWord.length() || keyWord.replace("ѧ����֯","").length()!=keyWord.length() || keyWord.replace("�칫��","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()|| keyWord.replace("�Ƽ����ϻ�","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()|| keyWord.replace("��ί","").length()!=keyWord.length()){
            m.add(local_path.get(16));
        }
        if (keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("���ݽ���","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(17));
        }
        if (keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("�칫��","").length()!=keyWord.length() || keyWord.replace("����Ա","").length()!=keyWord.length() || keyWord.replace("Ժ��","").length()!=keyWord.length() || keyWord.replace("У��","").length()!=keyWord.length()){
            m.add(local_path.get(18));
        }

        if (keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("�Է�","").length()!=keyWord.length() || keyWord.replace("��ͨ","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(19));
        }
        if (keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(20));
        }
        if (keyWord.replace("A��","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("A1","").length()!=keyWord.length() || keyWord.replace("A2","").length()!=keyWord.length() || keyWord.replace("A3","").length()!=keyWord.length() || keyWord.replace("A4","").length()!=keyWord.length() || keyWord.replace("A5","").length()!=keyWord.length() || keyWord.replace("A6","").length()!=keyWord.length() || keyWord.replace("A7","").length()!=keyWord.length() || keyWord.replace("A8","").length()!=keyWord.length() || keyWord.replace("A9","").length()!=keyWord.length() || keyWord.replace("A10","").length()!=keyWord.length() || keyWord.replace("A11","").length()!=keyWord.length() || keyWord.replace("A12","").length()!=keyWord.length() || keyWord.replace("A13","").length()!=keyWord.length()){
            m.add(local_path.get(21));
        }
        if (keyWord.replace("����ɽ","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("Լ��","").length()!=keyWord.length() || keyWord.replace("��Ϣ","").length()!=keyWord.length()){
            m.add(local_path.get(22));
        }
        if (keyWord.replace("�ٳ�","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(23));
        }
        if (keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(24));
        }
        if (keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("����","").length()!=keyWord.length()|| keyWord.replace("C��","").length()!=keyWord.length()){
            m.add(local_path.get(25));
        }
        if (keyWord.replace("C��ʳ��","").length()!=keyWord.length()|| keyWord.replace("ʳ��","").length()!=keyWord.length() || keyWord.replace("�Է�","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()){
            m.add(local_path.get(26));
        }

        if (keyWord.replace("��Ӿ","").length()!=keyWord.length()){
            m.add(local_path.get(27));
        }
        if (keyWord.replace("C������","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length() || keyWord.replace("Զ��","").length()!=keyWord.length()){
            m.add(local_path.get(28));
        }
        if (keyWord.replace("������","").length()!=keyWord.length()|| keyWord.replace("��Ժ","").length()!=keyWord.length() || keyWord.replace("��Ӱ","").length()!=keyWord.length()|| keyWord.replace("����","").length()!=keyWord.length()|| keyWord.replace("�Է�","").length()!=keyWord.length()){
            m.add(local_path.get(29));
        }
        return m;
    }

    private static Map addNewPath(String pathName,String info,double x,double y){
        Map map=new HashMap();
        map.put("name",pathName);
        map.put("info",info);
        map.put("x",x);
        map.put("y",y);
        return map;
    }

    public static double degree(double x1, double y1, double x2, double y2) {
        double result = 0;
        double r = 0;
        double x;
        double y;
        x = x2 - x1;
        y = y2 - y1;
        if (y > 0) {
            r = Math.atan2(y, x) / Math.PI * 180;
        } else {
            r = Math.atan2(y, x) / Math.PI * 180 + 360;
        }
        if (r >= 360) {
            r -= 360;
        }
        if (r >= 90) {
            result = 450 - r;
        } else {
            result = 90 - r;
        }
        // System.out.println(r);
        if (result >= 360) {
            result -= 360;
        }
        return result;
    }

    public static double getGps_JD() {
        return gps_JD;
    }

    public static void setGps_JD(double gps_JD) {
        localUser.gps_JD = gps_JD;
    }

    public static double getGps_WD() {
        return gps_WD;
    }

    public static void setGps_WD(double gps_WD) {
        localUser.gps_WD = gps_WD;
    }
}