package myservlet.pdf;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToPdfServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String htmlContent = request.getParameter("cont");
        System.out.println(htmlContent);
        if (htmlContent != null && !htmlContent.trim().isEmpty()) {
            HtmlUtils.htmlStringToPdfStream(htmlContent, response);
        } else {
            response.getWriter().println("HTML content is empty or missing.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
