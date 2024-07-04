package myservlet.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;


public class CustomCrawlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.setProperty("webdriver.chrome.driver", "E:\\work\\driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-headless");//设置driver nohead

        // 初始化WebDriver
        WebDriver driver = new ChromeDriver(options);

        String url = request.getParameter("customUrl");
        int type =  Integer.parseInt(request.getParameter("type")) ;
        String searchurl = new String("https://www.bing.com/search?q=".concat(url));
        driver.get(type == 0 ? searchurl : url);
        // 等待页面完全加载
        try {
            sleep(2000);
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
        if(type == 1){//拉取文章咨询等页面
            System.out.println("文章");
            while(true){
                if(document.select("div").first().select("[id~=(?i)(result|content|news)],[class~=(?i)(result|content|news)],article") == null)continue;
                else {
                   Elements els = document.select("[id~=(?i)(result|content|news)],[class~=(?i)(result|content|news)],article");
                    if(els == null)continue;
                   Element content = new Element("div");

                   for(Element e:els){
                       Element inner_con = new Element("div");
                       try {
                           e.select("[id~=(?i)(result|content|news)],[class~=(?i)(result|content|news)],article > h2,h1,[class~=(?i)title],[id~=(?i)title]").first().addClass("card-title").appendTo(inner_con);//获取标题
                           inner_con.append(e.select("[id~=(?i)(result|content|news)],[class~=(?i)(result|content|news)],article > p,pre,span").outerHtml());//获取内容

//                       System.out.println(content);
                       }catch (Exception ex2){
                           continue;
                       }
                       if(inner_con.text()!=null){//清洗空元素
                           content.append(inner_con.outerHtml()).addClass("card");
                       }

                   }
                    request.setAttribute("content", content.outerHtml().isEmpty() ?"<h2>内容获取失败</h2>":content.outerHtml());
                    break;
                }

            }
        }
        else if(type == 0) {//拉取搜索结果
            System.out.println("搜索");
            Elements el = document.select("div h2");
            if(el != null)request.setAttribute("el", el);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}