package com.firmboy.search.pdf;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

/**
 * @author playboy
 * @create 2019-12-04 9:36 下午
 * @description
 * @serviceLogic
 **/
public class HelloWorld {
    private Logger log = Logger.getLogger(HelloWorld.class);


    public static void main(String[] args) throws IOException
    {
        if( args.length != 2 )
        {
            System.err.println("usage: " + HelloWorld.class.getName() + " <output-file> <Message>");
            System.exit(1);
        }

        String filename = args[0];
        String message = args[1];

        PDDocument doc = new PDDocument();
        try
        {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(message);

            //可以追加
            contents.showText("testshowText");

            contents.addComment("ceshi");

            contents.endText();

            contents.close();

            doc.save(filename);
        }
        finally
        {
            doc.close();
        }
    }
}
