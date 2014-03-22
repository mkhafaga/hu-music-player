package com.xeeapps.utils;

import android.os.AsyncTask;
import android.webkit.WebView;
import com.xeeapps.service.SongDetails;
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
public class GetLyricsTask extends AsyncTask<SongDetails,WebView,String> {
        private WebView lyricsView ;
    public GetLyricsTask(WebView lyricsView){
                                       this.lyricsView = lyricsView;
    }

    @Override
    protected void onPostExecute(String s) {
        lyricsView.loadDataWithBaseURL(null, s, "text/html", "UTF-8", null);
    }

    @Override
    protected String doInBackground(SongDetails... currentSongDetails) {
//
        Document doc;
        String text = null  ;
        try {
        String[] songSlices = currentSongDetails[0].getSongData().split("/");
        String artistName = currentSongDetails[0].getArtistName().trim();

        String title = songSlices[songSlices.length - 1].replace(".mp3", "").replace(artistName, "").replaceAll("(.-)*[0-9]*", "").trim();
       title =  URLEncoder.encode(title, "utf-8");
            artistName =  URLEncoder.encode(artistName,"utf-8");
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


            //"http://www.azlyrics.com/lyrics/enriqueiglesias/hero.html"
//            Log.i("serviceLink:","http://hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title="+ title+"&artistName="+ artistName);
//            Connection connection =   Jsoup.connect("http://hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title="+ title+"&artistName="+ artistName);
         //   http://hulyrics.com/lyric.xhtml?song=Ladies&artist=ANDY+GRAMMER
              Connection connection =   Jsoup.connect("http://hulyrics.com/lyric.xhtml?song="+ title+"&artist="+ artistName);

            connection.timeout(60000);
            doc = connection.get();
            Elements lyrics = doc.getElementsByClass("ui-fieldset-content");
            System.out.println("divs size:"+lyrics.size());;
            Element elem  = lyrics.get(0);
             text =      elem.text();

            if (text == null) text = "";
            text = "<body style=' -webkit-text-stroke: 1px black;\n" +
                    "   color: white;\n" +
                    "   text-shadow:\n" +
                    "       3px 3px 0 #000,\n" +
                    "     -1px -1px 0 #000,  \n" +
                    "      1px -1px 0 #000,\n" +
                    "      -1px 1px 0 #000,\n" +
                    "       1px 1px 0 #000;;font-weight:bolder;'>" + text + "</body>";


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

         return text;
    }

}
