package myservlet.date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import myservlet.webmagic.WebCrawler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

class ScheduledCrawler {
    public static void scheduleCrawl(String url, int interval) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(CrawlJob.class)
                .withIdentity("crawlJob", "group1")
                .usingJobData("url", url)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("crawlTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(interval)
                        .repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    public static class CrawlJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            String url = dataMap.getString("url");
            WebCrawler crawler = new WebCrawler();
            try {
                String title = crawler.crawl(url);
                System.out.println("Crawled title: " + title);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

public class ScheduledCrawlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        int interval = Integer.parseInt(request.getParameter("interval"));

        try {
            ScheduledCrawler.scheduleCrawl(url, interval);
            response.getWriter().println("Scheduled crawl started for URL: " + url);
        } catch (SchedulerException e) {
            e.printStackTrace();
            response.getWriter().println("Error scheduling crawl: " + e.getMessage());
        }
    }
}
