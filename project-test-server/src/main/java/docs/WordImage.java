package docs;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.List;

public class WordImage {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String filePath = "D:\\code\\jjy-test\\jjy-test\\public-project-test\\project-test-server\\src\\main\\resources\\docx\\output.docx";
        FileInputStream fis = new FileInputStream(filePath);
        // 如果是 .docx -> .xml -> .ftl的话 ，然后再转回.docx的话会报错。The supplied data appears to be a raw XML file. Formats such as Office 2003 XML are not supported
        XWPFDocument document = new XWPFDocument(fis);

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        XWPFParagraph lastParagraph = paragraphs.get(paragraphs.size() - 1);

        String[] imagePaths = {
                "D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\img1.jpg",
                "D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\img2.jpg",
                "D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\img3.jpg",
                // "D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\img4.jpg",
                "D:\\code\\db\\szaf-jdbc\\src\\main\\resources\\img5.jpg"
        };


        int imagesPerLine = 3;
        int imageCount = 0;

        for (String imagePath : imagePaths) {
            FileInputStream imageStream = new FileInputStream(imagePath);

            // 读取图片字节数据（兼容 Java 8）
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            buffer.flush();
            byte[] imageBytes = buffer.toByteArray();

            imageStream.close();
            // 插入图片-使用 addPictureData 将图片数据添加到文档中。
            document.addPictureData(imageBytes, Document.PICTURE_TYPE_JPEG);
            int pictureIndex = document.getAllPictures().size() - 1;
            lastParagraph.setSpacingBefore(200);
            // 使用 createRun().addPicture 将图片插入到段落中。
            lastParagraph.createRun().addPicture(
                    new ByteArrayInputStream(imageBytes),
                    Document.PICTURE_TYPE_JPEG,
                    imagePath,
                    Units.toEMU(100), // 图片宽度
                    Units.toEMU(150)  // 图片高度
            );
            // 图片间距
            lastParagraph.createRun().addTab();

            imageCount++;
            if (imageCount % imagesPerLine == 0) {
                // 换行-每插入三张图片后，创建一个新的段落，以实现换行效果。
                lastParagraph = document.createParagraph();
            }
        }

        FileOutputStream fos = new FileOutputStream("D:\\code\\jjy-test\\jjy-test\\public-project-test\\project-test-server\\src\\main\\resources\\docx\\test1.docx");
        document.write(fos);
        fos.close();
        document.close();
        fis.close();
    }
}
