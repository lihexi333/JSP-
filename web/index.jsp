<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="org.jsoup.select.Elements" %>
<%@ page import="org.jsoup.nodes.Element" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>爬虫主页</title>
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
    <script src="./assets/js/bootstrap.js"></script>
    <script src="./assets/js/jquery.js"></script>
</head>
<body>

<div class="container">
    <h1 class="mt-5">爬虫主页</h1>
    <div class="card mt-3">
        <div class="card-header">
            定时爬取文章
        </div>
        <div class="card-body">
            <p>本模块拉取新浪新闻内容，请设定更新时间。</p>
            <form action="timer.jsp" method="post">
                <div class="form-group">
                    <label for="interval">时间间隔（秒）:</label>
                    <input type="number" class="form-control" id="interval" name="interval" required>
                </div>
                <button type="submit" class="btn btn-primary mt-3" formtarget="_blank">开始定时爬取</button>
            </form>
        </div>
    </div>

    <div class="card mt-3">
        <div class="card-header">
            自定义网址爬取
        </div>
        <div class="card-body">
            <form action="customCrawl" method="post">
                <div class="form-group">
                    <div class="m-1">
                    <input id="search" type="radio" value="0" name="type" onclick="showKeyword()">
                    <label for="search">必应搜索爬取</label>
                    </div>
                        <div class="m-1">
                    <input id="news" type="radio" value="1" name="type" onclick="showUrl()">
                    <label for="news">自定义网址</label>
                        </div>
                    <br>
                    <label for="customUrl" class="keyword" style="display: none">关键词:</label>
                    <label for="customUrl" class="url" style="display: none">网址:</label>
                    <input type="text" class="form-control" id="customUrl" name="customUrl" required disabled>
                </div>
                <button type="submit" class="btn btn-primary mt-3">爬取</button>
                <button type="button" class="btn btn-primary mt-3" onclick="fun()">保存</button>
            </form>
        </div>
    </div>

    <%
        out.println("<div class='result border border-1 container mt-3  px-4'>");
        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");
        String desc = (String) request.getAttribute("desc");
        String title = (String) request.getAttribute("title");
        if(title!=null)out.println("<h2>"+title+"</h2>");
        if(desc!=null)out.println("<br><small class=\"text-muted\">"+desc+"</small>");
        int type = request.getParameter("type")!=null?Integer.parseInt( request.getParameter("type")):-1 ;
        if(type==1 &&title!=null){
            try{
                String con = (String) request.getAttribute("content");
                out.println(con);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(type ==0 && title!=null) try {
            Elements el = (Elements) request.getAttribute("el");

            out.println("<div class='row row-cols-2'>");
            for(Element i:el){
                out.println("<div class='card h-100'>");
                out.println("<div class='card-body'>");
                out.println(i.text()+"<br>");
                out.println("<a href="+ el.select("a[href]").attr("href")+" class=\"mt-2\" style='right:0px'>直达链接</a>");
                out.println("</div></div>");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        out.println("</div>");
    %>
</div>
</body>
<script type="text/javascript">
    function fun(){
        var head = $("head")[0].outerHTML.replace(/\.\/assets/g,window.location.href.substring(0,window.location.href.lastIndexOf('/')+1)+"assets");
        $.post("toPdf",{cont:$("head")[0].outerHTML+"<body>"+$(".result").html()+"</body>"});
    }
    function showKeyword(){
        $(".keyword").show();
        $(".url").hide();
        $("#customUrl").attr("disabled",false);
    }
    function  showUrl(){
        $(".keyword").hide();
        $(".url").show();
        $("#customUrl").attr("disabled",false);
    }

</script>
</html>