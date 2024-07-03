package myservlet.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SinaNewCraler {

    public static void main(String[] args) {
        String url = "https://news.sina.com.cn/";

        try {
            // 连接到新浪新闻首页并获取 Document 对象
            Document document = Jsoup.connect("https://news.sina.com.cn/").get();
            //热点新闻
            Elements hotNews = document.select(".card_tit a");
            for (Element hotNew : hotNews) {
                System.out.println(hotNew.text());
                String S = hotNews.text();
            }
            // 使用选择器提取新闻标题
            Elements newsTitles = document.select(".uni-blk-list02 a");
            // 处理提取到的数据
            for (Element newsTitle : newsTitles) {
                //热点新闻
                System.out.println("newTitles = "+newsTitle.text()+"  href="+newsTitles.attr("href"));

            }
            //军事
            Elements Military = document.select("#blk_08_lab01 a");
            for (Element MilitaryElement : Military) {
                System.out.println("\n"+MilitaryElement.text());
            }
            Elements military = document.select(".blk_340 a");
            for (Element militaryElement : military) {
                System.out.println("militaryElement = "+militaryElement.text()+"  href="+militaryElement.attr("href"));
            }
            //国内新闻
            Elements HomeNews = document.select("#tab_gnylup_01 a");
            for (Element HomeNew : HomeNews) {
                System.out.println("\n"+HomeNew.text());
            }
            Elements homeNews = document.select("#blk_gnxw_011 a");
            for (Element homeNewsElement : homeNews) {
                System.out.println("homeNewsElement = " + homeNewsElement.text() + "  href=" + homeNewsElement.attr("href"));
            }
            //国际新闻
            Elements WorldNews = document.select("#tab_gnylup_01 a");
            for (Element WorldNewsElement : WorldNews) {
                System.out.println("\n"+WorldNewsElement.text());
            }
            Elements worldNews = document.select("#blk_gjxw_011 a");
            for (Element worldNewsElement : worldNews) {
                System.out.println("worldNewsElement = " + worldNewsElement.text() + "  href=" + worldNewsElement.attr("href"));
            }
            //政务
            Elements GovernmentNews = document.select("#tab_gnylup_01 a");
            for (Element GovernmentNewsElement : GovernmentNews) {
                System.out.println("\n"+GovernmentNewsElement.text());
            }
            Elements governmentNews = document.select(".p_right > ul a");
            for (Element governmentNewsElement : governmentNews) {
                System.out.println("governmentNews = "+ governmentNewsElement.text()+"  href=" + governmentNewsElement.attr("href"));
            }
            // “要闻”提取标题
            Elements time = document.select("div.b_time");
            for (Element element : time) {
                System.out.println("\n要闻");
                System.out.println("time = "+element.text());
            }
            Elements highlights1 = document.select("[data-sudaclick='blk_news_1'] a");
            for(Element element : highlights1) {
                System.out.println("要闻标题："+element.text()+" newURL="+element.attr("href"));
            }
            Elements highlights2 = document.select("[data-sudaclick='blk_news_2'] a");
            for(Element element : highlights2) {
                System.out.println("要闻标题："+element.text()+" newURL="+element.attr("href"));
            }
            Elements highlights3 = document.select("[data-sudaclick='blk_news_3'] a");
            for(Element element : highlights3) {
                System.out.println("要闻标题"+element.text()+" newURL="+element.attr("href"));
            }
            Elements highlights4 = document.select("[data-sudaclick='blk_news_4'] a");
            for(Element element : highlights4) {
                System.out.println("要闻标题"+element.text()+" newURL="+element.attr("href"));
            }
            Elements newURL= document.select(".linkNewsTop");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
