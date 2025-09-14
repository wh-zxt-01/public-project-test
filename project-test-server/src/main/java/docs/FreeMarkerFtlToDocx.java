package docs;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/**
 * @Author: wh
 * @Date: 2025/02/24/9:54
 * @Description:  该功能主要实现复杂的docx格式，比如在.docx中的文档 直接重命名 -> .xml -> .ftl   =》 然后对ftl进行填充，
 */
public class FreeMarkerFtlToDocx {
    public static void main(String[] args) {
        exportWord(null,"D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\退货单.ftl","D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\output.docx");
    }
    public static void exportWord(Map<String, Object> dataMap, String templateName, String path) {
        try {
            // 创建 FreeMarker 配置对象
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
            configuration.setDefaultEncoding("utf-8");

            // 分解路径
            File templateFile = new File(templateName);
            File templateDir = templateFile.getParentFile();
            String templateFileName = templateFile.getName();

            // 设置模板目录
            configuration.setDirectoryForTemplateLoading(templateDir);

            // 输出文档路径及名称
            File outFile = new File(path);

            // 以 utf-8 的编码读取 ftl 文件
            Template template = configuration.getTemplate(templateFileName, "utf-8");

            // 写入输出文件
            Writer out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outFile.toPath()), StandardCharsets.UTF_8));
            template.process(dataMap, out);

            // 关闭 writer
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
