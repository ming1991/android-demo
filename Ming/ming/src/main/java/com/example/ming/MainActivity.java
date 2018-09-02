package com.example.ming;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mBtnStartPull;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartPull = findViewById(R.id.startPull);
        mTvContent = findViewById(R.id.content);

        mBtnStartPull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run() {
                        super.run();

                       // startPullXml();
                    }
                }.start();

                new XmlTast().execute();
            }
        });

        HandlerThread handlerThread = new HandlerThread("ming");

    }

    private int eventType;

//    private void startPullXml() {
//        XmlPullParser pullParser = Xml.newPullParser();
//        InputStream is = null;
//        OtherLineBean otherLineBean = null;
//        SpareLineBean spareLineBean = null;
//        List<TypeBean> typeBeanList = null;
//        GameVideoBean gameVideoBean = null;
//        try {
//
//            URL url = new URL("http://wv1.tp33.net:67/CNGameTypeXML.xml");
//            //打开连接
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            //设置请求方式
//            httpURLConnection.setRequestMethod("GET");
//            //设置请求连接超时时间(优化)
//            httpURLConnection.setConnectTimeout(5000);
//            //获取结果码(状态)  成功：200    未修改:304
//            int code = httpURLConnection.getResponseCode();
//            if (code == 200) {
//                //获取服务器返回过来的结果
//                is = httpURLConnection.getInputStream();
//
//                //is = getAssets().open("values.xml");
//                pullParser.setInput(is, "utf-8");
//                eventType = pullParser.getEventType();
//                while (eventType != XmlPullParser.END_DOCUMENT) {
//                    switch (eventType) {
//                        case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件
//                            otherLineBean = new OtherLineBean();
//                            spareLineBean = new SpareLineBean();
//                            typeBeanList = new ArrayList<>();
//                            TypeBean typeBean = null;
//                            gameVideoBean = new GameVideoBean();
//
//                            break;
//                        case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件
//
//
//                            if ("XcVideo".equals(pullParser.getName())) {
//                                eventType = pullParser.next();
//                                while (!("OTHER_Line".equals(pullParser.getName()))) {
//                                    eventType = pullParser.next();
//                                }
//                                /** 解析 OTHER_Line */
//                                if ("OTHER_Line".equals(pullParser.getName())) {
//                                    otherLineBean.setHelp(pullParser.getAttributeValue(0));
//                                    otherLineBean.setType(pullParser.getAttributeValue(1));
//                                    otherLineBean.setFolder(pullParser.getAttributeValue(2));
//                                    eventType = pullParser.next();
//                                    while (!("V".equals(pullParser.getName()))) {
//                                        eventType = pullParser.next();
//                                    }
//                                    if ("V".equals(pullParser.getName())) {
//                                        otherLineBean.setV(pullParser.nextText());
//
//                                        //mTvContent.setText(otherLineBean.toString());
//                                        Log.d("ming007", "otherLineBean.toString() = " + otherLineBean.toString());
//                                    }
//                                }
//                                eventType = pullParser.next();
//                                while (!("SPARE_Line".equals(pullParser.getName()))) {
//                                    eventType = pullParser.next();
//                                }
//
//                                /** 解析 SPARE_Line */
//                                if ("SPARE_Line".equals(pullParser.getName())) {
//                                    List<VBean> vBeanList = new ArrayList<>();
//                                    spareLineBean.setHelp(pullParser.getAttributeValue(0));
//                                    spareLineBean.setType(pullParser.getAttributeValue(1));
//                                    spareLineBean.setFolder(pullParser.getAttributeValue(2));
//                                    eventType = pullParser.next();
//                                    while (!("V".equals(pullParser.getName()))) {
//                                        eventType = pullParser.next();
//                                    }
//                                    if ("V".equals(pullParser.getName())) {
//                                        while (("V".equals(pullParser.getName()))) {
//                                            VBean vBean = new VBean();
//                                            vBean.setContent(pullParser.nextText());
//                                            vBeanList.add(vBean);
//                                            eventType = pullParser.next();
//                                            eventType = pullParser.next();
//                                        }
//                                        spareLineBean.setvBean(vBeanList);
//
//                                        //mTvContent.setText(spareLineBean.toString());
//                                        Log.d("ming007", "spareLineBean.toString() = " + spareLineBean.toString());
//                                    }
//                                }
//                            }
//
//
//                            /** 解析 Type */
//                            if ("GameTypePrames".equals(pullParser.getName())) {
//
//                                while (!(eventType == XmlPullParser.START_TAG && ("Node".equals(pullParser.getName())))) {
//                                    eventType = pullParser.next();
//                                }
//                                if ("Node".equals(pullParser.getName())) {
//
//                                    while (("Node".equals(pullParser.getName()))) {
//
//                                        while (!(eventType == XmlPullParser.START_TAG && ("Type".equals(pullParser.getName())))) {
//                                            eventType = pullParser.next();
//                                        }
//                                        //Log.d("ming001", " pullParser.getName() 1= " + pullParser.getName() );
//                                        if (("Type".equals(pullParser.getName()))) {
//                                            Log.d("ming001", " pullParser.getName() 2= " + pullParser.getName());
//                                            typeBean = new TypeBean();
//                                            int count = pullParser.getAttributeCount();
//                                            for (int i = 0; i < count; i++) {
//                                                if ("name".equals(pullParser.getAttributeName(i))) {
//                                                    typeBean.setName(pullParser.getAttributeValue(i));
//                                                }
//                                                if ("liveFQ".equals(pullParser.getAttributeName(i))) {
//                                                    typeBean.setLiveFQ(pullParser.getAttributeValue(i));
//                                                }
//                                            }
//                                            typeBeanList.add(typeBean);
//                                            Log.d("ming001", " pullParser.getName() 3= " + pullParser.getName());
//                                        }
//
//                                        while (!(eventType == XmlPullParser.START_TAG && ("Node".equals(pullParser.getName())))) {
//                                            eventType = pullParser.next();
//                                            if (eventType == XmlPullParser.END_TAG && "GameTypePrames".equals(pullParser.getName())) {
//                                                break;
//                                            }
//                                        }
//                                        Log.d("ming001", " pullParser.getName() 4= " + pullParser.getName());
//
//                                    }
//
//                                }
//
//                            }
//
//
////                        /** 解析 Type */
////
////                        if ("Type".equals(pullParser.getName())) {
////
////                            //Log.d("ming007", "Type: " + pullParser.getName());
////
////                            typeBean = new TypeBean();
////                            int count = pullParser.getAttributeCount();
////                            for (int i = 0; i < count; i++) {
////                                if ("name".equals(pullParser.getAttributeName(i))){
////                                    typeBean.setName(pullParser.getAttributeValue(i));
////                                }
////                                if ("liveFQ".equals(pullParser.getAttributeName(i))){
////                                    typeBean.setLiveFQ(pullParser.getAttributeValue(i));
////                                }
////                            }
////                            typeBeanList.add(typeBean);
////                        }
//
//                            break;
//                        case XmlPullParser.END_TAG:
//                            break;
//                    }
//                    eventType = pullParser.next();
//                }
//
//                //mTvContent.setText(typeBeanList.toString());
//                Log.d("ming007", "typeBeanList.toString() = " + typeBeanList.toString());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//    }
}
