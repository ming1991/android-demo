package com.example.ming;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by xyzing on 2018/5/30.
 */

public class XmlTast extends AsyncTask<String, Void, GameVideoBean> {

    @Override
    protected GameVideoBean doInBackground(String... strings) {
        GameVideoBean gameVideoBean = new GameVideoBean();
        OtherLineBean otherLineBean = new OtherLineBean();
        SpareLineBean spareLineBean = new SpareLineBean();


        try {
            URL url = new URL("http://wv1.tp33.net:67/CNGameTypeXML.xml");
            //打开连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            httpURLConnection.setRequestMethod("GET");
            //设置请求连接超时时间(优化)
            httpURLConnection.setConnectTimeout(5000);
            //获取结果码(状态)  成功：200    未修改:304
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                //获取服务器返回过来的结果
                InputStream is = httpURLConnection.getInputStream();
                //03.使用Pull解析(类似SAX)
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                Document doc = builderFactory.newDocumentBuilder().parse(is);
                /**
                 *  解析OTHER_Line里面的节点
                 */
                NodeList node1 = doc.getElementsByTagName("OTHER_Line");
                Element OTHER_Line = (Element) node1.item(2);
                String other_type = OTHER_Line.getAttribute("type");
                String other_folder = OTHER_Line.getAttribute("folder");
                String other_url = OTHER_Line.getElementsByTagName("V").item(0).getTextContent().toString();
                //String[] other_line=new String[1];
               // List<String> space_line_list = new ArrayList<>();
                //space_line_list.add(OTHER_Line.getElementsByTagName("V").item(0).getTextContent().toString());
                gameVideoBean.setOtherLineBean(new OtherLineBean(other_type,other_folder,other_url));
                /**
                 *  解析SPARE_Line里面的节点
                 */
                NodeList node2 = doc.getElementsByTagName("SPARE_Line");
                Element SPARE_Line = (Element) node2.item(2);
                String space_type = SPARE_Line.getAttribute("type");
                String space_folder = SPARE_Line.getAttribute("folder");
                NodeList space_v = SPARE_Line.getElementsByTagName("V");
                //String[] space_line=new String[6];
                List<String> space_line_list = new ArrayList<>();
                for (int n = 0; n < space_v.getLength(); n++) {
                    space_line_list.add(space_v.item(n).getTextContent().toString());
                    //space_line[n]=space_v.item(n).getTextContent().toString();
                   // mList.add(space_v.item(n).getTextContent().toString());
                }
                gameVideoBean.setSpareLineBean(new SpareLineBean(space_type,space_folder,space_line_list));
                /**
                 *  解析GameTypePrames里面的节点
                 *
                 */
                List<TypeBean> typeBeanList=new ArrayList<>();
                NodeList nodeT = doc.getElementsByTagName("Type");
                for (int i = 0; i < nodeT.getLength(); i++) {
                    Element item = (Element) nodeT.item(i);
                    String name = item.getAttribute("name");
                    String liveFQ = item.getAttribute("liveFQ");
                    TypeBean typeBean = new TypeBean();
                    typeBean.setName(name);
                    List<FNbean> fNbeans = new ArrayList<>();
                    String[] gameFlag = liveFQ.split("[|][|]");
                    for (int n = 0; n < gameFlag.length; n++) {
                        String liveXc = gameFlag[n].split(",")[1];//流名
                        liveXc=liveXc.substring(0,liveXc.length()-1)+"3";
                        String folder = gameFlag[n].split(",")[0];//folder
                        FNbean fNbean = new FNbean();
                        fNbean.setLive(liveXc);
                        fNbean.setFolder(folder);
                        fNbeans.add(fNbean);


                    }
                    typeBean.setLiveFQ(fNbeans);
                    typeBeanList.add(typeBean);
                    Log.d("tast",fNbeans.toString());
                }

                gameVideoBean.setTypeBeanList(typeBeanList);
            }
//            Log.e("XmlTast", "mGameFlagList.get():" + mGameFlagList.get("LP")[0].split(",")[1]);
//            LineBean OTHER_Line = mLinelist.get("OTHER_Line");
//            Log.e("XmlTast", OTHER_Line.getFolder());
//            Log.e("XmlTast", OTHER_Line.getType());
//            Log.e("XmlTast", OTHER_Line.getSpace_line_list()[0]);
//            LineBean SPARE_Line  = mLinelist.get("SPARE_Line");
//            Log.e("XmlTast", SPARE_Line.getFolder());
//            Log.e("XmlTast", SPARE_Line.getType());
//            for (int i = 0; i < SPARE_Line.getSpace_line_list().length; i++) {
//                Log.e("XmlTast", SPARE_Line.getSpace_line_list()[i]);
//            }
            return gameVideoBean;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(GameVideoBean s) {
        Log.d("tast",s.toString());
        super.onPostExecute(s);
    }


}
