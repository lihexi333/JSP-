<%@ page import="timer.TimerCrawler" %>
<%@ page import="java.util.Date" %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    // 获取刷新间隔时间
    Date time = new Date();
    session.setAttribute("time",time);
    String interval="";
//    out.println(request.getParameter("interval"));
    if(session.getAttribute("interval")==null || request.getParameter("interval")!=null){//当刷新时间不存在或者再次设定刷新时间时
        interval= request.getParameter("interval");
        session.setAttribute("interval", interval);
    }
    else {
        interval= (String) session.getAttribute("interval");
    }
    int refreshInterval = (interval != null && !interval.isEmpty()) ? Integer.parseInt(interval) : 60; // 默认60秒

    // 设置页面刷新时间
    response.setHeader("Refresh", String.valueOf(refreshInterval));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>定时爬取结果</title>
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
    <script src="./assets/js/bootstrap.js"></script>
</head>
<body>

<div class="container">
    <h1 class="mt-5">定时爬取结果</h1>
    <small class="text-muted">每 <%= refreshInterval %> 秒刷新一次,上一次刷新时间为<%=session.getAttribute("time") %></small>
    <div class="card mt-3">
        <div class="card-body">
            <%
                String content = TimerCrawler.startCrawler();
                out.println(content);
            %>
        </div>
    </div>
</div>
</body>
</html>