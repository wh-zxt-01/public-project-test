package docs;

/**
 * @Author: wh
 * @Date: 2025/02/21/9:21
 * @Description:
 */

import cn.hutool.extra.template.TemplateException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在 Java 中使用 FreeMarker 填充 .ftl 模板后，生成的内容通常是纯文本或 XML 格式的字符串。
 * 如果需要生成 .docx 文件，可以使用 Apache POI 的 XWPFDocument 模块。以下是生成 .docx 文件的示例代码：
 * <!-- FreeMarker -->
 * <dependency>
 * <groupId>org.freemarker</groupId>
 * <artifactId>freemarker</artifactId>
 * <version>2.3.31</version>
 * </dependency>
 * <p>
 * <!-- Apache POI for .doc files (OLE2 Format) -->
 * <dependency>
 * <groupId>org.apache.poi</groupId>
 * <artifactId>poi-scratchpad</artifactId>
 * <version>5.2.3</version>
 * </dependency>
 */
public class FreeMarkerToDocx {

    public static void main(String[] args) throws IOException, TemplateException, freemarker.template.TemplateException {
        // 配置 FreeMarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(FreeMarkerToDocx.class, "/");
        cfg.setDefaultEncoding("UTF-8");

        // 加载模板,目前这个template.ftl 模板是手动输入的，无法渲染出word的格式、表格、等问题，如需实现带有格式的 ，参考：.docx -> .xml -> .ftl
        Template template = cfg.getTemplate("./docx/template.ftl");

        // 准备数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "张三");
        dataModel.put("age", 25);

        List<String> items = Arrays.asList("项目1", "项目2", "项目3");
        dataModel.put("items", items);


        // 生成文档内容,这种适合于 =》 template.ftl ，如果是 .docx -> .xml -> .ftl的话 ，创建出来的word是 一段代码
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        String content = writer.toString();

        // 创建一个空的 .docx 文件
        XWPFDocument document = new XWPFDocument();

        // 将生成的内容插入到文档中
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);

        // 保存文档
        FileOutputStream fos = new FileOutputStream("D:\\code\\jjy-test\\jjy-test\\public-project-test\\project-test-server\\src\\main\\resources\\docx\\output.docx");
        document.write(fos);
        fos.close();
        document.close();

        System.out.println("文档生成成功！");
    }
}
