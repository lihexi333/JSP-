package timer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class TimerCrawler {
    public static String  getHtml(String title,Elements els,String subtitle) {
        //用html表示获取结果，每个榜单封装成一个card
        Element content = new Element("div").addClass("card m-3");
        content.append("<div class='card=header'><h2>"+title+"</h2></div>");
        if(!subtitle.isEmpty())content.append("<small class=\"text-muted\">"+subtitle+"</small>");
        Element body = new Element("ul").addClass("list-group list-group-flush");
        body.append(els.addClass("list-group-item").wrap("<li class=\"list-group-item\"></li>").outerHtml());
        content.appendChild(body);
        return content.outerHtml();
    }
    public static String  getHtml(String title,Elements els){
        return getHtml(title,els,"");
    }
    public static String startCrawler() {
        String url = "https://news.sina.com.cn/";
        try {
            // 连接到新浪新闻首页并获取 Document 对象
            Document document = Jsoup.connect(url).get();
            String html = "";
            //热点新闻
            Element hotNews = document.select(".card_tit a").first();
            Elements newsTitles = document.select(".uni-blk-list02 a");
            html = html.concat(getHtml(hotNews.text(),newsTitles));

            //军事
            Element Military = document.select("#blk_08_lab01 a").first();
            Elements military = document.select(".blk_340 a");
            html =  html.concat(getHtml(Military.text(),military));

            //国内新闻
            Element HomeNews = document.select("#tab_gnylup_01 a").first();
            Elements homeNews = document.select("#blk_gnxw_011 a");
            html =  html.concat(getHtml(HomeNews.text(),homeNews));

            //国际新闻
            Element WorldNews = document.select("#blk_gjxwup_01 a").first();
            Elements worldNews = document.select("#blk_gjxw_011 a");
            html =  html.concat(getHtml(WorldNews.text(),worldNews));
            //政务
            Element GovernmentNews = document.select(".p_right .mb_10 .selected a").first();
            Elements governmentNews = document.select(".p_right > ul a");
            html =   html.concat(getHtml(GovernmentNews.text(),governmentNews));

            // “要闻”提取标题
            Element time = document.select("div.b_time").first();
            Elements highlights = document.select("[data-sudaclick='blk_news_1'] a");
            highlights.addAll(document.select("[data-sudaclick='blk_news_2'] a"));
            highlights.addAll(document.select("[data-sudaclick='blk_news_3'] a"));
            highlights.addAll(document.select("[data-sudaclick='blk_news_4'] a"));
            html =  html.concat(getHtml("要闻",highlights,time.text()));
            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
