package myservlet.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
    public static void htmlToPdf(String htmlPath, String pdfPath,String exePath) {

        File file = new File(pdfPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String tool;
        StringBuilder cmd = new StringBuilder();
        if (System.getProperty("os.name").contains("Windows")) {
            //非windows 系统
            tool = exePath.isEmpty()?WIN_PDF_TOOL:exePath;
        }else {
            tool = exePath.isEmpty()?LINUX_PDF_TOOL:exePath;
        }
        cmd.append(tool);
        cmd.append(" ");
        cmd.append("-B 0 -L 0 -R 0 -T 0 ");
        //开启本地文件访问
        cmd.append("--enable-local-file-access ");
        //cmd.append(" --header-line");//页眉下面的线
        //cmd.append(" --header-center 这里是页眉这里是页眉这里是页眉这里是页眉 ");//页眉中间内容
        //cmd.append(" --margin-top 3cm ");//设置页面上边距 (default 10mm)
        //cmd.append(" --header-html file:///" + WebUtil.getServletContext().getRealPath("") + FileUtil.convertSystemFilePath("\\style\\pdf\\head.html"));// (添加一个HTML页眉,后面是网址)
        //cmd.append(" --header-spacing 5 ");// (设置页眉和内容的距离,默认0)
        //cmd.append(" --footer-center (设置在中心位置的页脚内容)");//设置在中心位置的页脚内容
        //cmd.append(" --footer-html file:///" + WebUtil.getServletContext().getRealPath("") + FileUtil.convertSystemFilePath("\\style\\pdf\\foter.html"));// (添加一个HTML页脚,后面是网址)
        //cmd.append(" --footer-line");//* 显示一条线在页脚内容上)
        //cmd.append(" --footer-spacing 5 ");// (设置页脚和内容的距离)
        cmd.append(htmlPath);
        cmd.append(" ");
        cmd.append(pdfPath);

        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            InputStreamReader isr = new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println(br.read(new char[1000]));
            while ((line = br.readLine()) != null) {
                System.out.println("html转pdf进度和信息："+ line);
            }
            proc.waitFor();
//            proc.destroy();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public static void htmlToPdf(String htmlPath, String pdfPath){
        htmlToPdf(htmlPath,pdfPath,"");

    }
    public static void htmlToImg(String srcPath, String destPath,String exePath) {
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
                //非windows 系统
                tool = exePath.isEmpty()?WIN_IMAGE_TOOL:exePath;
            }else {
                tool = exePath.isEmpty()?LINUX_PDF_TOOL:exePath;
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
                System.out.println("html转jpg进度和信息："+ line);
            }
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
    }
    }
    public static void htmlToImg(String srcPath, String destPath) {
        htmlToImg(srcPath,destPath,"");
    }

//    public static void main(String[] args) {
//        htmlToPdf("src/test.html","src/test.pdf");
//    }
}