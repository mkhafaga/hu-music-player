package com.xeeapps.utils;

import android.os.AsyncTask;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;


/**
 * Created with IntelliJ IDEA.
 * User: khafaga
 * Date: 01/02/13
 * Time: 08:53 ص
 * To change this template use File | Settings | File Templates.
 */
public class GetLyricsTask extends AsyncTask<String,Void,String> {



    @Override
    protected String doInBackground(String... urls) {
//
//                       String all = "";
//        URL yahoo = null;
//        try {
//
//
//                             //hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title="+urls[0]+"&artistName="+urls[1]
//
//
//            String urlText = "http://hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title="+URLEncoder.encode(urls[0],"utf-8")+"&artistName="+ URLEncoder.encode(urls[1],"utf-8");
//            yahoo = new URL(urlText);
//            HttpURLConnection connection = (HttpURLConnection) yahoo.openConnection();
//            connection.setConnectTimeout(20000);
//            connection.setRequestMethod("GET");
//
//            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(
//                        connection.getInputStream()));
//
//
//
//        String inputLine;
//
//        while ((inputLine = in.readLine()) != null)
//            all+=inputLine;
//
//        in.close();
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
           Document doc;
        String text = null  ;
        try {
            //"http://www.azlyrics.com/lyrics/enriqueiglesias/hero.html"

            Connection connection =   Jsoup.connect("http://hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title="+ URLEncoder.encode(urls[0], "utf-8")+"&artistName="+ URLEncoder.encode(urls[1],"utf-8"));
            connection.timeout(60000);
            doc = connection.get();
            Elements lyrics = doc.getElementsByTag("words");
            System.out.println("divs size:"+lyrics.size());;
            Element elem  = lyrics.get(0);
             text =      elem.text();

            // lyricsView.setText(elem.text());
            //  System.out.println("elem text:"+elem.text());
            //   Thread.sleep(10000);
            // System.out.println("doc title: "+doc.title());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        LyricsClient lyricsClient = new LyricsClient();
////        Lyric lyric = lyricsClient.getLyricBySongCriteria(urls[0],urls[1]);
////        lyricsClient.close();
//        return lyricsClient.getLyricBySongCriteria(urls[0],urls[1]).toString();//lyric.getWords();//+" - "+Globals.CURRENT_SONG;  //To change body of implemented methods use File | Settings | File Templates.
                         return text;
    }

}
