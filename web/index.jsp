<%@page contentType="text/html;charset=utf-8" %>
<%@ page import="org.jsoup.select.Elements" %>
<%@ page import="org.jsoup.nodes.Element" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>爬虫主页</title>
    <link href="./assets/css/bootstrap.css" rel="stylesheet">
    <script src="./assets/js/bootstrap.js"></script>
    <link>
</head>
<body>

<div class="container">
    <h1 class="mt-5">爬虫主页</h1>
    <div class="card mt-3">
        <div class="card-header">
            定时爬取文章
        </div>
        <div class="card-body">
            <form action="startScheduledCrawl" method="post">
                <div class="form-group">
                    <label for="url">网址:</label>
                    <input type="text" class="form-control" id="url" name="url" required>
                </div>
                <div class="form-group">
                    <label for="interval">时间间隔（分钟）:</label>
                    <input type="number" class="form-control" id="interval" name="interval" required>
                </div>
                <button type="submit" class="btn btn-primary mt-3">开始定时爬取</button>
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
                    <input id="search" type="radio" value="0" name="type">
                    <label for="search">搜索</label>
                    <input id="news" type="radio" value="1" name="type">
                    <label for="news">新闻</label>
                    <br>
                    <label for="customUrl">网址:</label>
                    <input type="text" class="form-control" id="customUrl" name="customUrl" required>
                </div>
                <button type="submit" class="btn btn-primary mt-3">爬取</button>
<button type="button" class="btn btn-primary mt-3" onclick="toPDF">保存</button>
            </form>

        </div>

    </div>
    <%
        out.println("<div class='result border border-1 container mt-3  px-4'>");
        request.setCharacterEncoding("utf-8");
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
                out.println("<div class='card-body position-relative'>");
                out.println(i.text()+"<br>");
                out.println("<a href="+ el.select("a[href]").attr("href")+" class=\"btn btn-primary\">Go somewhere</a>");
                out.println("</div></div>");
            }

        }catch (Exception e){
            e.printStackTrace();
//            out.println("error");
        }
        out.println("</div>");
    %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>