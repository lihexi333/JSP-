<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>爬虫主页</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
                <button type="submit" class="btn btn-primary">开始定时爬取</button>
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
                    <input id="search" type="radio" value="0" checked name="type">
                    <label for="search">搜索</label>
                    <input id="news" type="radio" value="1" checked name="type">
                    <label for="news">新闻</label>
                    <br>
                    <label for="customUrl">网址:</label>
                    <input type="text" class="form-control" id="customUrl" name="customUrl" required>
                </div>
                <button type="submit" class="btn btn-primary">爬取</button>
            </form>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>