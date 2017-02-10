package com.example.mlyang.ConquerMobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mlyang.ConquerMobile.JoinGroup;
import com.example.mlyang.ConquerMobile.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;
import static java.lang.System.setOut;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,OnChartValueSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ArrayList<String> menuLists;

    private ArrayAdapter<String> adapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    static public String User;

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    final public static int BAR = 1;
    final public static int PIE = 0;

    private int chart = PIE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); //初始化睡眠操作相关权限及设置
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        menuLists = new ArrayList<String>();



        menuLists.add("登录或注册");
        menuLists.add("PK小组");
        menuLists.add("应用统计图谱");
        menuLists.add("详细使用信息");
        menuLists.add("应用管理");
        menuLists.add("关于我们");


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menuLists);


        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(this);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Button buttonpie = (Button) findViewById(R.id.piechartbutton);
        buttonpie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chart != PIE) {
                    chart = PIE;
                    onResume();
                }
            }
        });
        Button buttonbar = (Button) findViewById(R.id.barchartbutton);
        buttonbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chart != BAR) {
                    chart = BAR;
                    onResume();
                }
            }
        });

        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));   //查看是否为应用设置了权限
    }

    private void SetButtonColor() {
        Button buttonpie = (Button) findViewById(R.id.piechartbutton);
        Button buttonbar = (Button) findViewById(R.id.barchartbutton);


        buttonpie.setTextColor(Color.WHITE);
        buttonbar.setTextColor(Color.WHITE);

        switch (chart) {
            case PIE:
                buttonpie.setTextColor(Color.GREEN);
                break;
            case BAR:
                buttonbar.setTextColor(Color.GREEN);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?>  arg0, View arg1, int position, long arg3){

        if(arg0==mDrawerList) {
            switch (position) {
                case 0:
                    Intent i =new Intent(MainActivity.this, Login.class);
                    startActivityForResult(i,0);

                    break;
                case 1://启动activity不保留抽屉
                    Intent ig =new Intent(MainActivity.this,JoinGroup.class);
                    startActivityForResult(ig,1);
                    break;
                case 2:
                    Intent intent2 = new Intent(MainActivity.this, PiePolylineChartActivity.class);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(MainActivity.this, AppStatisticsList.class);
                    startActivity(intent3);
                    break;
                case 4:
                    Intent intent4 = new Intent(MainActivity.this, AppManageList.class);
                    startActivity(intent4);
                    break;
                case 5:

                    break;
                default:
                    exit(0);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String s=data.getStringExtra("name");
            User = s;
            s="您好:\n"+s;
            menuLists.set(0,s);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, menuLists);
            mDrawerList.setAdapter(adapter);
            mDrawerList.setOnItemClickListener(this);
        }
        else if(resultCode==0){
            String s=data.getStringExtra("group");
            User = s;
            s="已加入： "+s+"PK小组";
            menuLists.set(1,s);
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, menuLists);
            mDrawerList.setAdapter(adapter);
            mDrawerList.setOnItemClickListener(this);
        }
    }

    private PieChart mChart;
    private int style = StatisticsInfo.DAY;
    private long totaltime;

    private BarChart bChart;

    private Typeface tf;


    @Override
    protected void onResume() {
        super.onResume();
        SetButtonColor();
        if(chart == PIE) {
            findViewById(R.id.chartinmain).setVisibility(View.VISIBLE);
            findViewById(R.id.barchartmain).setVisibility(View.INVISIBLE);
            findViewById(R.id.textViewTimeMain).setVisibility(View.VISIBLE);
            DrawPipeChart();
        }
        else if(chart == BAR) {
            findViewById(R.id.chartinmain).setVisibility(View.INVISIBLE);
            findViewById(R.id.barchartmain).setVisibility(View.VISIBLE);
            findViewById(R.id.textViewTimeMain).setVisibility(View.INVISIBLE);
            DrawBarChart();
        }

        try {
            getSetList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        punish();
    }

    private void punish() {
        if(SetList.size() > 0) {
            for (final AppInformation appInformation : ShowList) {
                if (FoundAppSet(appInformation.getLabel())) {
                    final long time = appInformation.getUsedTimebyDay() / 1000;
                    if (time > Integer.parseInt(appSet.getTime())) {
                        if (appSet.getType() == AppSet.TIP) {
                            NoticeMesg noticeMesg = new NoticeMesg(MainActivity.this, appInformation.getLabel());
                            noticeMesg.Toast(String.valueOf(appSet.getType()));
                            noticeMesg.Time_Notice(appSet.getTime(), String.valueOf(time), String.valueOf(appSet.getType()));

                        } else if (appSet.getType() == AppSet.SLEEP) {
                            Toast.makeText(this.getApplicationContext(), "应用" + appInformation.getLabel() + "超时，手机将于5秒钟后睡眠", Toast.LENGTH_SHORT).show();
                            NoticeMesg noticeMesg = new NoticeMesg(MainActivity.this, appInformation.getLabel());
                            noticeMesg.Time_Notice(appSet.getTime(), String.valueOf(time), String.valueOf(appSet.getType()));
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                //执行惩罚之后删除之前的设定
                                try {
                                    delete_label(appInformation.getLabel());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
                                componentName = new ComponentName(MainActivity.this, AdminReceiver.class);

                                if (devicePolicyManager.isAdminActive(componentName))
                                    devicePolicyManager.lockNow();
                                else {
                                    Registration();
                                }
                                }
                            }, 5000);
                        } else if (appSet.getType() == AppSet.REBOOT) {
                            Toast.makeText(this.getApplicationContext(), "应用" + appInformation.getLabel() + "超时，手机将于5秒钟后重启", Toast.LENGTH_SHORT).show();
                            NoticeMesg noticeMesg = new NoticeMesg(MainActivity.this, appInformation.getLabel());
                            noticeMesg.Time_Notice(appSet.getTime(), String.valueOf(time), String.valueOf(appSet.getType()));

                            //执行惩罚之后删除之前的设定
                            try {
                                delete_label(appInformation.getLabel());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            new Handler().postDelayed(new Runnable() {
                                public void run() {

                                    PowerManager pManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
                                    pManager.reboot(null);//重启</span>
                                    try {
                                        delete_label(appInformation.getLabel());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 5000);

                        } else if (appSet.getType() == AppSet.SHUTDOWN) {
                            Toast.makeText(this.getApplicationContext(), "应用" + appInformation.getLabel() + "超时，手机将于5秒钟后关机", Toast.LENGTH_SHORT).show();
                            NoticeMesg noticeMesg = new NoticeMesg(MainActivity.this, appInformation.getLabel());
                            noticeMesg.Time_Notice(appSet.getTime(), String.valueOf(time), String.valueOf(appSet.getType()));

                            //执行惩罚之后删除之前的设定
                            try {
                                delete_label(appInformation.getLabel());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    try {
                                        //获得ServiceManager类
                                        Class<?> ServiceManager = Class
                                                .forName("android.os.ServiceManager");

                                        //获得ServiceManager的getService方法
                                        Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

                                        //调用getService获取RemoteService
                                        Object oRemoteService = getService.invoke(null,Context.POWER_SERVICE);

                                        //获得IPowerManager.Stub类
                                        Class<?> cStub = Class
                                                .forName("android.os.IPowerManager$Stub");
                                        //获得asInterface方法
                                        Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
                                        //调用asInterface方法获取IPowerManager对象
                                        Object oIPowerManager = asInterface.invoke(null, oRemoteService);
                                        //获得shutdown()方法
                                        Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
                                        shutdown.invoke(oIPowerManager,false,true);

                                    } catch (Exception e) {

                                    }
                                }
                            }, 5000);

                        }
                    }
                }
            }
        }
    }

    private void delete_label(String label) throws IOException{
        File path = new File(getExternalCacheDir(),"TEST.txt");
        String content = "";
        BufferedReader in = new BufferedReader(new FileReader(path));
        String line = in.readLine();
        while (line != null) {
            if (line.contains(label)) {

            } else {
                content += line + "\n";
            }
            line = in.readLine();
        }
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(path.getAbsoluteFile()));
        bw.write(content);
        bw.flush();
        bw.close();
    }

    private ArrayList<AppSet> SetList;
    private AppSet appSet;

    private void getSetList() throws IOException {
        SetList = new ArrayList<AppSet>();
        File path = new File(getExternalCacheDir(),"TEST.txt");
        if(path.exists()) {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line = in.readLine();
            while (line != null) {
                int n1 = line.indexOf("#");
                int n2 = line.indexOf("#",n1 + 1);
                String label = line.substring(0,n1);
                String type = line.substring(n1 + 1,n2);
                String time = line.substring(n2 + 1);

                AppSet appSet = new AppSet(label,Integer.parseInt(type),time);
                SetList.add(appSet);
                line = in.readLine();
            }
        }
    }

    boolean FoundAppSet(String label) {
        if(SetList.size() > 0) {
            for(AppSet appSet1 : SetList) {
                if(appSet1.getLabel().equals(label)) {
                    appSet = appSet1;
                    return true;
                }
            }
        }
        return false;
    }

    ArrayList<AppInformation> ShowList;
//    long YLength;
//    int cellHeight;

    private void DrawBarChart() {
//        YLength = 0;
        StatisticsInfo statisticsInfo = new StatisticsInfo(this,style);
        ShowList = statisticsInfo.getShowList();
//        for(int i=0;i<ShowList.size();i++) {
//            if(ShowList.get(i).getUsedTimebyDay() > YLength)
//                YLength = ShowList.get(i).getUsedTimebyDay();
//        }

        bChart = (BarChart) findViewById(R.id.barchartmain);
        bChart.setOnChartValueSelectedListener(this);

        bChart.setDrawBarShadow(false);
        bChart.setDrawValueAboveBar(true);

        bChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        bChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        bChart.setPinchZoom(false);

        bChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int)value;
                if(ShowList.size() > i) {
                    if(i >= 5)
                        return "其他应用";
                    else {
                        String str = ShowList.get(i).getLabel();
                        if (str.length() < 8)
                            return str;
                        else return (str.substring(0, 8) + "..");
                    }
                }
                else return "";
            }
        };

        XAxis xAxis = bChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                long i = (long)value;
//                i = i * ((YLength  / 1000 / 60 + 20) / 60 ) * 10;
                return value + "min";
            }
        };

        YAxis leftAxis = bChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = bChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = bChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(bChart); // For bounds control
        bChart.setMarker(mv); // Set the marker to the chart

        setDataBarChart();

        // setting data

        // mChart.setDrawLegend(false);
    }

    private void setDataBarChart() {


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        if(ShowList.size() < 5) {
            for (int i = 0; i < ShowList.size(); i++) {
                yVals1.add(new BarEntry(i, (float)(1.0 * ShowList.get(i).getUsedTimebyDay() / 1000 / 60)));
            }
        }
        else {
            for(int i = 0;i < 5;i++) {
                yVals1.add(new BarEntry(i, (float)(1.0 * ShowList.get(i).getUsedTimebyDay() / 1000 / 60)));
            }
            long otherTime = 0;
            for(int i=5;i<ShowList.size();i++) {
                otherTime += ShowList.get(i).getUsedTimebyDay();
            }
            yVals1.add(new BarEntry(5,(float)(1.0 * otherTime / 1000 / 60)));
        }
        BarDataSet set1;

        if (bChart.getData() != null &&
                bChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) bChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            bChart.getData().notifyDataChanged();
            bChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Different APPs");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            bChart.setData(data);
        }
    }

    private void DrawPipeChart() {
        mChart = (PieChart) findViewById(R.id.chartinmain);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(generateCenterSpannableText(style));

        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setEntryLabelColor(R.color.dimgrey);

        //设置内圈半径的角度
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        setData(StatisticsInfo.DAY);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(chart == BAR) {
            getMenuInflater().inflate(R.menu.bar, menu);
        }
        else if(chart == PIE)
            getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(chart == PIE) {
            switch (item.getItemId()) {
                case R.id.actionToggleValues: {
                    for (IDataSet<?> set : mChart.getData().getDataSets())
                        set.setDrawValues(!set.isDrawValuesEnabled());

                    mChart.invalidate();
                    break;
                }
                case R.id.actionToggleHole: {
                    if (mChart.isDrawHoleEnabled())
                        mChart.setDrawHoleEnabled(false);
                    else
                        mChart.setDrawHoleEnabled(true);
                    mChart.invalidate();
                    break;
                }
                case R.id.actionDrawCenter: {
                    if (mChart.isDrawCenterTextEnabled())
                        mChart.setDrawCenterText(false);
                    else
                        mChart.setDrawCenterText(true);
                    mChart.invalidate();
                    break;
                }
                case R.id.actionToggleXVals: {

                    mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
                    mChart.invalidate();
                    break;
                }
                case R.id.actionSave: {
                    // mChart.saveToGallery("title"+System.currentTimeMillis());
                    mChart.saveToPath("title" + System.currentTimeMillis(), "");
                    break;
                }
                case R.id.actionTogglePercent:
                    mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                    mChart.invalidate();
                    break;
                case R.id.animateX: {
                    mChart.animateX(1400);
                    break;
                }
                case R.id.animateY: {
                    mChart.animateY(1400);
                    break;
                }
                case R.id.animateXY: {
                    mChart.animateXY(1400, 1400);
                    break;
                }
            }
        }
        else if(chart == BAR) {
            switch (item.getItemId()) {
                case R.id.actionToggleValues: {
                    for (IDataSet set : bChart.getData().getDataSets())
                        set.setDrawValues(!set.isDrawValuesEnabled());

                    bChart.invalidate();
                    break;
                }
                case R.id.actionToggleHighlight: {
                    if (bChart.getData() != null) {
                        bChart.getData().setHighlightEnabled(!bChart.getData().isHighlightEnabled());
                        bChart.invalidate();
                    }
                    break;
                }
                case R.id.actionTogglePinch: {
                    if (bChart.isPinchZoomEnabled())
                        bChart.setPinchZoom(false);
                    else
                        bChart.setPinchZoom(true);

                    bChart.invalidate();
                    break;
                }
                case R.id.actionToggleAutoScaleMinMax: {
                    bChart.setAutoScaleMinMaxEnabled(!bChart.isAutoScaleMinMaxEnabled());
                    bChart.notifyDataSetChanged();
                    break;
                }
                case R.id.actionToggleBarBorders: {
                    for (IBarDataSet set : bChart.getData().getDataSets())
                        ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                    bChart.invalidate();
                    break;
                }
                case R.id.animateX: {
                    bChart.animateX(3000);
                    break;
                }
                case R.id.animateY: {
                    bChart.animateY(3000);
                    break;
                }
                case R.id.animateXY: {

                    bChart.animateXY(3000, 3000);
                    break;
                }
                case R.id.actionSave: {
                    if (bChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                        Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                                Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                                .show();
                    break;
                }
            }
        }
        return true;
    }


    private void setData(int style) {
        StatisticsInfo statisticsInfo = new StatisticsInfo(this,style);
        ShowList = statisticsInfo.getShowList();

        totaltime = statisticsInfo.getTotalTime();
        TextView textView =(TextView) findViewById(R.id.textViewTimeMain);

        SpannableString sp = new SpannableString("今天手机使用时间: " + DateUtils.formatElapsedTime(totaltime / 1000));
        sp.setSpan(new RelativeSizeSpan(1.35f), 0, sp.length(), 0);
        sp.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 0, sp.length(), 0);
        textView.setText(sp);

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        if(ShowList.size() < 6) {
            for (int i = 0; i < ShowList.size(); i++) {
                float apptime = (float)ShowList.get(i).getUsedTimebyDay() / 1000;
                if(apptime / totaltime * 1000>= 0.001)
                    entries.add(new PieEntry(apptime, ShowList.get(i).getLabel()));
            }
        }
        else {
            for(int i = 0;i < 6;i++) {
                float apptime = (float)ShowList.get(i).getUsedTimebyDay() / 1000;
                if(apptime / totaltime * 1000>= 0.001)
                    entries.add(new PieEntry(apptime, ShowList.get(i).getLabel()));
            }
            long otherTime = 0;
            for(int i=6;i<ShowList.size();i++) {
                otherTime += ShowList.get(i).getUsedTimebyDay() / 1000;
            }
            if(1.0 * otherTime / totaltime  * 1000 >= 0.001)
                entries.add(new PieEntry((float)otherTime, "其他应用"));
        }


        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

        private SpannableString generateCenterSpannableText(int style) {

        String s1 = "应用数据统计";
        String s2;
        if(style == StatisticsInfo.WEEK) {
            s2 = "一周内应用使用情况";
        }
        else if(style == StatisticsInfo.MONTH)
            s2 = "30天应用使用情况";
        else if(style == StatisticsInfo.YEAR)
            s2 = "一年应用使用情况";
        else s2 = "当天应用使用情况";

        SpannableString s = new SpannableString(s1 + "\n" + s2);
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s1.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);

//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - s2.length(), s.length(), 0);
        return s;
    }


    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if(chart == BAR) {
            if (e == null)
                return;

            RectF bounds = mOnValueSelectedRectF;
            bChart.getBarBounds((BarEntry) e, bounds);
            MPPointF position = bChart.getPosition(e, YAxis.AxisDependency.LEFT);

            Log.i("bounds", bounds.toString());
            Log.i("position", position.toString());

            Log.i("x-index",
                    "low: " + bChart.getLowestVisibleX() + ", high: "
                            + bChart.getHighestVisibleX());

            MPPointF.recycleInstance(position);
        }
    }

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }

    private ComponentName componentName;

    //赋予app相应的sleep的权限
    DevicePolicyManager devicePolicyManager;
    private void init() {
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(MainActivity.this, AdminReceiver.class);
        Registration();

    }

    private void Registration() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "程式的描述");
        startActivityForResult(intent, 0);
    }
}


