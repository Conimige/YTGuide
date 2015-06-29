package cn.myzchh.YTGuide;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.hardware.SensorManager;
import android.view.animation.RotateAnimation;

import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.androidtestproject.testapp.R;

import java.io.IOException;

import cn.myzchh.YTGuide.util.BaseActivity;
import cn.myzchh.YTGuide.util.localUser;

public class MapActivity extends BaseActivity {

    //��ͼ
    public MapView mMapView = null;
    public BaiduMap baiduMap = null;
    // ��λ�������
    public LocationClient locationClient = null;
    // �Զ���ͼ��
    BitmapDescriptor mCurrentMarker = null;
    boolean isFirstLoc = true;// �Ƿ��״ζ�λ

    //ָ���봫����
    private SensorManager manager;
    private SensorListener listener = new SensorListener();

    private ImageView btn_rec;
    private ImageView btn_rec_shadow;
    private ImageView img_rec_bkg;
    private ImageView img_map;
    private Button btn_search;
    private EditText edit_search;


    /** ��ʼ������mark���� */
    // �ڶ���ѧ¥
    private LatLng point1 = new LatLng(30.0038520000,106.2465740000);
    private Marker maker1;
    // �ڶ�ʵ��¥
    private LatLng point2 = new LatLng(30.0046610000,106.2466100000);
    private Marker maker2;
    // ��һʵ��¥
    private LatLng point3 = new LatLng(30.0050600000,106.2466460000);
    private Marker maker3;
    // ��һ��ѧ¥
    private LatLng point4 = new LatLng(30.0050560000,106.2472970000);
    private Marker maker4;
    // B1����¥
    private LatLng point5 = new LatLng(30.0058740000,106.2473500000);
    private Marker maker5;
    // B2����¥
    private LatLng point6 = new LatLng(30.0065620000,106.2473640000);
    private Marker maker6;
    // ��ͨ����
    private LatLng point7 = new LatLng(30.0072620000,106.2474090000);
    private Marker maker7;
    // B��ʳ��
    private LatLng point8 = new LatLng(30.0073280000,106.2466680000);
    private Marker maker8;
    // A��ʳ��
    private LatLng point9 = new LatLng(30.0075430000,106.2451490000);
    private Marker maker9;
    // ����
    private LatLng point10 = new LatLng(30.0051528775,106.2459762949);
    private Marker maker10;
    // ��ͨͼ���
    private LatLng point11 = new LatLng(30.0043950000,106.2454910000);
    private Marker maker11;
    // ˫�Ӻ�
    private LatLng point12 = new LatLng(30.0033318775,106.2449892949);
    private Marker maker12;
    // ������˾糡
    private LatLng point13 = new LatLng(30.0026068775,106.2450962949);
    private Marker maker13;
    // ������ѧ¥
    private LatLng point14 = new LatLng(30.0022498775,106.2468822949);
    private Marker maker14;
    // ���߽�ѧ¥
    private LatLng point15 = new LatLng(30.0037878775,106.2473462949);
    private Marker maker15;
    // �����ѧ¥
    private LatLng point16 = new LatLng(30.0026300000,106.2467190000);
    private Marker maker16;
    // ���Ľ�ѧ¥
    private LatLng point17 = new LatLng(30.0029620000,106.2467010000);
    private Marker maker17;
    // ������ѧ¥\
    private LatLng point18 = new LatLng(30.0033690000,106.2466740000);
    private Marker maker18;
    // ����¥
    private LatLng point19 = new LatLng(30.0022500000,106.2449180000);
    private Marker maker19;
    // ��ͨ����
    private LatLng point20 = new LatLng(30.0043950000,106.2469690000);
    private Marker maker20;
    // ��ͨ����
    private LatLng point21 = new LatLng(30.0020770000,106.2455090000);
    private Marker maker21;
    // A������¥
    private LatLng point22 = new LatLng(30.0057750000,106.2455590000);
    private Marker maker22;
    //����ɽ������Ժ
    private LatLng point23 = new LatLng(30.0043990000,106.2444090000);
    private Marker maker23;

    //��ͨ�ٳ�
    private LatLng point24 = new LatLng(30.0062290000,106.2465740000);
    private Marker maker24;

    //����
    private LatLng point25 = new LatLng(30.0064630000,106.2443190000);
    private Marker maker25;

    //C������
    private LatLng point26 = new LatLng(30.0075740000,106.2461160000);
    private Marker maker26;

    //C��ʳ��
    private LatLng point27 = new LatLng(30.0087230000,106.2461430000);
    private Marker maker27;

    //��ͨ��Ӿ��
    private LatLng point28 = new LatLng(30.0096220000,106.2464120000);
    private Marker maker28;

    //C������¥
    private LatLng point29 = new LatLng(30.0085430000,106.2472030000);
    private Marker maker29;

    //��������ҵ���н�
    private LatLng point30 = new LatLng(30.0082230000,106.2465380000);
    private Marker maker30;

















    // ����Markerͼ��
    private BitmapDescriptor bitmap;
    private BitmapDescriptor ground;

    //�������
    boolean useDefaultIcon = false;
    private OverlayManager routeOverlay = null;

    private boolean ori_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
        // ע��÷���Ҫ��setContentView����֮ǰʵ��
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        //�󶨿ؼ�
        btn_rec=(ImageView)findViewById(R.id.btn_rec);
        btn_rec_shadow=(ImageView)findViewById(R.id.btn_rec_shadow);
        img_rec_bkg=(ImageView)findViewById(R.id.img_rec_bkg);
        img_map=(ImageView)findViewById(R.id.img_map);
        btn_search=(Button)findViewById(R.id.btn_search);
        edit_search=(EditText)findViewById(R.id.edit_search);

        //ָ�����ȡϵͳ����SENSOR_SERVICE)����һ��SensorManager ����
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        loadAnim();

        btn_rec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    Animation aIn = new AlphaAnimation(0f, 1f);
                    aIn.setDuration(300);
                    aIn.setFillAfter(true);
                    btn_rec_shadow.startAnimation(aIn);
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    Animation aIn = new AlphaAnimation(1f, 0f);
                    aIn.setDuration(300);
                    aIn.setFillAfter(true);
                    btn_rec_shadow.startAnimation(aIn);
                }
                return false;
            }
        });

        btn_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeOverlay!=null){
                    routeOverlay.removeFromMap();
                }
                img_rec_bkg.setVisibility(View.VISIBLE);
                ScaleAnimation animation =new ScaleAnimation(1f, 20f, 1f, 20f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);
                animation.setStartOffset(10);
                animation.setFillAfter(true);
                img_rec_bkg.setAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {}
                    public void onAnimationEnd(Animation animation) {
                        Intent intent=new Intent(MapActivity.this,recActivity.class);
                        startActivity(intent);
                        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                        if (version > 5) {
                            overridePendingTransition(R.anim.fade, R.anim.hold);
                        }
                    }
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        });

        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this,testActivity.class);
                startActivity(intent);
            }
        });

        // ��ȡ��ͼ�ؼ�����
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap=mMapView.getMap();
        //������λͼ��
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getApplicationContext()); // ʵ����LocationClient��
        locationClient.registerLocationListener(myListener); // ע���������
        this.setLocationOption();   //���ö�λ����
        locationClient.start(); // ��ʼ��λ
        locationClient.requestLocation();

        addMapMark();

        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }
            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                if (baiduMap.getMapStatus().zoom <= 17.0) {
                    // ����mark
                    baiduMap.clear();
                }
                if (baiduMap.getMapStatus().zoom >= 17.0) {
                    // ���¼���mark
                    initOverlay();
                }
            }
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

        });

        //ȥ�����
        int childCount = mMapView.getChildCount();
        View zoom = null;
        for(int i = 0;i<childCount;i++){
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                zoom = child;
                break;
            }
        }
        zoom.setVisibility(View.GONE);
        //ȥ���ٶ�logo
        mMapView.removeViewAt(1);
        BaiduMapOptions options = new BaiduMapOptions()
                .overlookingGesturesEnabled(false).rotateGesturesEnabled(false)
                        // .scaleControlEnabled(false) //�Ƿ���ʾ�����߿ؼ�
                .scrollGesturesEnabled(false).zoomGesturesEnabled(false)
                .zoomControlsEnabled(false).compassEnabled(false);
        mMapView = new MapView(MapActivity.this, options);

        ori_flag=false;

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mark) {
                //showMessageByToast("���롶"+mark.getTitle()+"����������");
                if (routeOverlay!=null){
                    routeOverlay.removeFromMap();
                }
                Intent intent=new Intent(MapActivity.this,commentActivity.class);
                intent.putExtra("name",mark.getTitle());
                startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version > 5) {
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
                return true;
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeOverlay!=null){
                    routeOverlay.removeFromMap();
                }
                Intent intent=new Intent(MapActivity.this,SearchResultActivity.class);
                intent.putExtra("searchStr",edit_search.getText().toString());
                startActivity(intent);
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version > 5) {
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
            }
        });

    }//end OnCreate




    private void walkToPoint(double x, double y){
        System.out.println("��λ����x="+x+";y="+y+";��ǰ���꣺x="+localUser.getGps_WD()+";y="+localUser.getGps_JD());
        if (x==0 || y==0){
            return;
        }
        try {
            //routeOverlay.removeFromMap();
            //Log.e(TAG, "��ǰ��γ��" + locLatitude + "," locLongitude + "��" + point + "��ľ���Ϊ��" getPointsDistance(point));
            // ������·����·�߹滮
            RoutePlanSearch search = RoutePlanSearch.newInstance();
            //��Ҫ�滮������
            PlanNode startPint = PlanNode.withLocation(new LatLng(localUser.getGps_WD(), localUser.getGps_JD()));
            PlanNode endPoint = PlanNode.withLocation(new LatLng(x, y));
            search.walkingSearch(new WalkingRoutePlanOption().from(startPint).to(endPoint));
            search.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
                /**
                 *��·�滮����ص�������,�����Ҫʵʱ��������Ҫ��location�����е�option.setScanSpan(500)����Ϊ1000����
                 */
                @Override
                public void onGetWalkingRouteResult(WalkingRouteResult result) {
                    //Log.e(TAG,"���н��������");
                    //��ȡ���й滮���
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        Toast.makeText(MapActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
                    }
                    if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                        //Log.e(TAG,"�ڶ���if");
                        WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
                        routeOverlay = overlay;
                        overlay.setData(result.getRouteLines().get(0));
                        overlay.addToMap();
//                        overlay.zoomToSpan();
                    }
                }

                @Override
                public void onGetTransitRouteResult(TransitRouteResult arg0) {

                }

                @Override
                public void onGetDrivingRouteResult(DrivingRouteResult arg0) {

                }
            });
        } catch (Exception e) {
//            Toast.makeText(MapActivity.this,
//                    e.getMessage() + "", Toast.LENGTH_LONG)
//                    .show();
        }
    }

    /**����������·���յ��ͼ��
     */
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
            }
            return null;
        }
    }

    public void showMessageByToast(String Msg) {
        Toast.makeText(MapActivity.this, Msg, Toast.LENGTH_SHORT).show();
    }

    private void addMapMark() {

        /** ��ʼ��mark��ͼ����Դ */
        initMarkBitMap();
        /** Ϊ��ͨ���mark��ע */
        initOverlay();
    }
    /**
     * ��ʼ��mark��ͼ����Դ
     */
    private void initMarkBitMap() {
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        ground = BitmapDescriptorFactory.fromResource(R.drawable.p_ground);
    }
    /**
     * ����Զ��帲����
     */
    private void initOverlay() {
        /** ����Maker����� */
        maker1 = setPicMark(point1, "�ڶ���ѧ¥");
        maker2 = setPicMark(point2, "�ڶ�ʵ��¥");
        maker3 = setPicMark(point3, "��һʵ��¥");
        maker4 = setPicMark(point4, "��һ��ѧ¥");
        maker5 = setPicMark(point5, "B1����¥");
        maker6 = setPicMark(point6, "B2����¥");
        maker7 = setPicMark(point7, "��ͨ����");
        maker8 = setPicMark(point8, "B��ʳ��");
        maker9 = setPicMark(point9, "A��ʳ��");
        maker10 = setPicMark(point10, "����");
        maker11 = setPicMark(point11, "��ͨͼ���");
        maker12 = setPicMark(point12, "˫�Ӻ�");
        maker13 = setPicMark(point13, "������˾糡");
        maker14 = setPicMark(point14, "������ѧ¥");
        maker15 = setPicMark(point15, "���߽�ѧ¥");
        maker16 = setPicMark(point16, "�����ѧ¥");
        maker17 = setPicMark(point17, "���Ľ�ѧ¥");
        maker18 = setPicMark(point18, "������ѧ¥");
        maker19 = setPicMark(point19, "����¥");
        maker20 = setPicMark(point20, "��ͨ����");
        maker21 = setPicMark(point21, "��ͨ����");
        maker22 = setPicMark(point22, "A������¥");
        maker23 = setPicMark(point23, "����ɽ������Ժ");
        maker24 = setPicMark(point24, "��ͨ�ٳ�");
        maker25 = setPicMark(point25, "����");
        maker26 = setPicMark(point26, "C������");
        maker27 = setPicMark(point27, "C��ʳ��");
        maker28 = setPicMark(point28, "��ͨ��Ӿ��");
        maker29 = setPicMark(point29, "C������¥");
        maker30 = setPicMark(point30, "��������ҵ���н�");
    }

    private Overlay setGroundOverlay(LatLng southwest, LatLng northeast,
                                     BitmapDescriptor bdGround) {
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();
        // ����Ground������ѡ��
        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        // �ڵ�ͼ�����Ground������
        baiduMap.addOverlay(ooGround);
        return null;
    }

    /**
     * ����ͼ�θ�����
     *
     * @param point
     *            ͼƬ�����������
     * @param title
     *            �������˵������
     */
    public Marker setPicMark(LatLng point, String title) {
        // ����MarkerOption�������ڵ�ͼ�����Marker
        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap);
        Marker mark = (Marker) baiduMap.addOverlay(option);
        // mark��˵������
        setTextMark(point, title);
        mark.setTitle(title);
        return mark;
    }

    /**
     * �������ָ�����
     *
     * @param text
     *            ���õ�����
     * @param point
     *            �������ֵ�����
     * @return Overlay
     */
    public Overlay setTextMark(LatLng point, String text) {
        // ��������Option���������ڵ�ͼ���������
        OverlayOptions textOption = new TextOptions().fontSize(37)
                .fontColor(0xFFFF7313).text(text).position(point);
        // �ڵ�ͼ����Ӹ����ֶ�����ʾ
        Overlay textOverlay = baiduMap.addOverlay(textOption);
        return textOverlay;
    }

    /**
     * �������ָ�����
     *
     * @param text
     *            ���õ�����
     * @param point
     *            �������ֵ�����
     * @param rotate
     *            ��ת�Ƕȴ�С
     * @return Overlay
     */
    public Overlay setTextMark(LatLng point, String text, int rotate) {
        // ��������Option���������ڵ�ͼ���������
        OverlayOptions textOption = new TextOptions().fontSize(37)
                .fontColor(0xFFFF00FF).text(text).position(point)
                .rotate(rotate);
        // �ڵ�ͼ����Ӹ����ֶ�����ʾ
        Overlay textOverlay = baiduMap.addOverlay(textOption);
        return textOverlay;
    }
    
    

    private void loadAnim() {
        Animation aIn = new AlphaAnimation(0f, 0f);
        aIn.setDuration(1);
        aIn.setFillAfter(true);
        btn_rec_shadow.startAnimation(aIn);
    }

    public BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view ���ٺ��ڴ����½��յ�λ��
            if (location == null || mMapView == null)
                return;

            //γ��
            localUser.setGps_WD(location.getLatitude());
            //localUser.setGps_WD(30.003583);
            //����
            localUser.setGps_JD(location.getLongitude());
            //localUser.setGps_JD(106.246693);

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData); // ���ö�λ����
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory
                        .newLatLngZoom(ll, 18); // ���õ�ͼ���ĵ��Լ����ż���
                baiduMap.animateMapStatus(u);
            }
        }
    };

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ��GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ���ö�λģʽ
        option.setCoorType("bd09ll"); // ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
        option.setScanSpan(5000); // ���÷���λ����ļ��ʱ��Ϊ5000ms
        option.setIsNeedAddress(true); // ���صĶ�λ���������ַ��Ϣ
        option.setNeedDeviceDirect(true); // ���صĶ�λ��������ֻ���ͷ�ķ���
        locationClient.setLocOption(option);
    }

    protected void onDestroy() {
        //�˳�ʱ���ٶ�λ
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
    }

    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
        // ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onResume();
        /**
         * ��ȡ���򴫸���
         * ͨ��SensorManager�����ȡ��Ӧ��Sensor���͵Ķ���
         */
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //Ӧ����ǰ̨ʱ��ע�������
        manager.registerListener(listener, sensor,
                SensorManager.SENSOR_DELAY_GAME);


        Animation aIn1 = new AlphaAnimation(0f, 0f);
        aIn1.setDuration(1);
        aIn1.setStartOffset(1);
        aIn1.setFillAfter(true);
        img_rec_bkg.startAnimation(aIn1);

        walkToPoint(localUser.getGoWhereX(),localUser.getGoWhereY());
    }

    protected void onPause() {
        super.onPause();
        // ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onPause();
        //ָ���봫����Ӧ�ò���ǰ̨ʱ�����ٵ�������
        manager.unregisterListener(listener);
    }

    //ָ���봫����
    private final class SensorListener implements SensorEventListener {
        private float predegree = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;// ����˷���ֵ
            float y = values[1];
            if (y<-45){
                if (!ori_flag) {
                    Intent intent = new Intent(MapActivity.this, navActivity.class);
                    y = 0;
                    startActivity(intent);
                    int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                    if (version > 5) {
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                    }
                    ori_flag=true;
                }
            }else{
                ori_flag=false;
            }

            predegree = -values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

}