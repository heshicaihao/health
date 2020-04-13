package com.itheima.test;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * IText方式创建PDF报表入门案例
 * @author wangxin
 * @version 1.0
 */
public class ITextDemo {

    //@Test
    public void testCreatePDF() throws Exception {
        //PDF文档对象
        Document document = new Document();
        //PDF文档写入对象  创建空的文档
        PdfWriter.getInstance(document,new FileOutputStream("C:\\working\\itext.pdf"));
        //打开文档
        document.open();
        //设置中文字体
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //往文档中写入内容
        document.add(new Paragraph("我是中文内容，不能正常显示!",new Font(baseFont)));
        document.close();

    }
}
