package myservlet.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;


public class CustomCrawlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("customUrl");
        int type =  Integer.parseInt(request.getParameter("type")) ;
        System.out.println(type);
        System.setProperty("webdriver.chrome.driver", "E:\\work\\driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-headless");
//        options.addArguments("--window-size=1920,1080");
        // 初始化WebDriver
        WebDriver driver = new ChromeDriver(options);
        //设置driver nohead

        driver.get(url);
        // 等待页面完全加载
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 获取页面源代码
        String pageSource = driver.getPageSource();
        driver.quit();
        Document document = Jsoup.parse(pageSource);
        request.setAttribute("title",document.title());
        //获取meta中的网页详细
        Element desc = (document.select("meta[name=description]").first() != null) ? document.select("meta[name='description']").first() : document.select("meta[property='og:description']").first();
        if (desc != null) request.setAttribute("desc", desc.attr("content"));
        if(type == 1){
            while(true){
                if(document.select("div").first() == null || document.select("div").first().select("h2,h1") == null)continue;
                else {
                   Elements els = document.select("[id~=(?i)result],[id~=(?i)content]");
//                   System.out.println(els);
                    if(els == null)continue;
                   Element content = new Element("div");
                   for(Element e:els){
                       try {
                           e.select("h2,h1,div[class~=(?i)title],div[id~=(?i)title]").first().appendTo(content);
                           content.append(e.select("p,pre,li").after("<br>").outerHtml());
//                       System.out.println(content);
                       }catch (Exception ex){
                           continue;
                       }

                   }
                    request.setAttribute("content",content.outerHtml().equals("")?"<h2>内容获取失败</h2>":content.outerHtml());
                    break;
                }

            }
        }
        else if(type == 0) {

            Elements el = document.select("div h2");
            if(el != null)request.setAttribute("el", el);
//            System.out.println(desc);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}