package myservlet.pdf;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *   html转换工具
 * </p>
 *
 * @author Garcia
 * @since 2023-09-12
 */
public class HtmlUtils {

    private final static String WIN_PDF_TOOL = "E:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
    private final static String WIN_IMAGE_TOOL = "E:\\wkhtmltopdf\\bin\\wkhtmltoimage.exe";
    private final static String LINUX_PDF_TOOL = "/usr/local/bin/wkhtmltopdf";
    private final static String LINUX_IMAGE_TOOL = "/usr/local/bin/wkhtmltoimage";


    /**
     * html转PDF
     *
     * @param htmlPath html路径
     * @param pdfPath  pdf路径
     */
    public static void htmlToPdf(String htmlPath, String pdfPath, String exePath) {
        File file = new File(pdfPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String tool;
        StringBuilder cmd = new StringBuilder();
        if (System.getProperty("os.name").contains("Windows")) {
            tool = exePath.isEmpty() ? WIN_PDF_TOOL : exePath;
        } else {
            tool = exePath.isEmpty() ? LINUX_PDF_TOOL : exePath;
        }
        cmd.append(tool);
        cmd.append(" ");
        cmd.append("-B 0 -L 0 -R 0 -T 0 ");
        //开启本地文件访问
        cmd.append("--enable-local-file-access ");
        cmd.append(htmlPath);
        cmd.append(" ");
        cmd.append(pdfPath);

        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            InputStreamReader isr = new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("html转pdf进度和信息：" + line);
            }
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void htmlToPdf(String htmlPath, String pdfPath) {
        htmlToPdf(htmlPath, pdfPath, "");
    }

    /**
     * html字符串转PDF并输出到HttpServletResponse流
     *
     * @param htmlContent html字符串内容
     * @param response    HttpServletResponse
     */
    public static void htmlStringToPdfStream(String htmlContent, HttpServletResponse response, String exePath) {
        try {
            // 创建临时HTML文件
            File tempHtmlFile = File.createTempFile("tempHtml", ".html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempHtmlFile));
            writer.write(htmlContent);
            writer.close();

            // 创建临时PDF文件
            File tempPdfFile = File.createTempFile("tempPdf", ".pdf");

            // 使用已有方法将临时HTML文件转换为临时PDF文件
            htmlToPdf(tempHtmlFile.getAbsolutePath(), tempPdfFile.getAbsolutePath(), exePath);

            // 将临时PDF文件内容写入响应输出流
            response.setContentType("application/pdf;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=generated.pdf");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(tempPdfFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0, bytesRead);
            }
            inputStream.close();
            outputStream.flush();

            // 删除临时文件
            tempHtmlFile.delete();
            tempPdfFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void htmlStringToPdfStream(String htmlContent, HttpServletResponse response) {
        htmlStringToPdfStream(htmlContent, response, "");
    }

    public static void htmlToImg(String srcPath, String destPath, String exePath) {
        try {
            File file = new File(destPath);
            File parent = file.getParentFile();
            //如果pdf保存路径不存在，则创建路径
            if (!parent.exists()) {
                parent.mkdirs();
            }
            String tool;
            StringBuilder cmd = new StringBuilder();
            if (System.getProperty("os.name").contains("Windows")) {
                tool = exePath.isEmpty() ? WIN_IMAGE_TOOL : exePath;
            } else {
                tool = exePath.isEmpty() ? LINUX_IMAGE_TOOL : exePath;
            }
            cmd.append(tool);
            cmd.append(" ");
            cmd.append(srcPath);
            cmd.append(" ");
            cmd.append(destPath);

            Process proc = Runtime.getRuntime().exec(cmd.toString());
            InputStreamReader isr = new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("html转jpg进度和信息：" + line);
            }
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void htmlToImg(String srcPath, String destPath) {
        htmlToImg(srcPath, destPath, "");
    }
}