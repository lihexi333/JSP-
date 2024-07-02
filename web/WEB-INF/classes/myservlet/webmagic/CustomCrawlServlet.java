package myservlet.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;

public class CustomCrawlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("customUrl");
//        System.out.println(url);
//        Document document = Jsoup.connect(url).get();
        // 设置ChromeDriver路径
        System.setProperty("webdriver.chrome.driver", "E:\\work\\driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        // 初始化WebDriver
        WebDriver driver = new ChromeDriver();
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
        driver.close();
//        URL sd = new URL(url);
        Document document = Jsoup.parse(pageSource);
        System.out.println(document.title());
        Elements el = document.select("div h2");
        request.setAttribute("el",el);
        request.setAttribute("title",document.title());
        System.out.println(document);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}