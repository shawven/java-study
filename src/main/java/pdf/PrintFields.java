package pdf;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleanUpTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shoven
 * @since 2019-06-03 9:18
 */
public class PrintFields {
    public static void main(String[] args) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader("f:/form.pdf"), new PdfWriter("f:/newForm.pdf"));
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", true);

        Table table = new Table(new float[]{1, 2, 3, 4});
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        addTableHeader(table, font);
        addTableBodies(table, font);

        Document document = new Document(pdfDoc);
        float[] floats = getKeyWords(pdfDoc, "订单详情");

        manipulatePdf(pdfDoc, floats, 100);

        table.setFixedPosition(floats[0], floats[1] - 100, UnitValue.createPercentValue(70));

        document.add(table);


        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFormField borrower = form.getField("borrower");
        PdfFormField platformName = form.getField("platformName");
        PdfFormField url = form.getField("url");
        PdfFormField date = form.getField("date");


        form.setNeedAppearances(true);
        borrower.setValue("深圳某某有限公司");
        platformName.setValue("深圳微企宝有限公司");
        date.setValue("2019.03.01");

        document.close();
    }

    private static void addTableBodies(Table table, PdfFont font) {
        table.addCell(new Cell().add(new Paragraph("10010").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("云记账").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("1").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("999").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("10020").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("云代账").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("1").setFont(font)));
        table.addCell(new Cell().add(new Paragraph("1999").setFont(font)));
    }

    private static void addTableHeader(Table table, PdfFont font) {
        table.addHeaderCell(new Cell().add(new Paragraph("订单号").setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("商品名称").setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("数量").setFont(font)).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("单价").setFont(font)).setBold());
    }

    public static void manipulatePdf(PdfDocument pdfDoc, float[] floats, float height)
            throws IOException {
        PdfPage firstPage = pdfDoc.getFirstPage();
        Rectangle pageSize = firstPage.getPageSize();

        Rectangle toMove = new Rectangle(0, 0, pageSize.getWidth(), floats[1] - 10);

//        pdfDoc.setDefaultPageSize(new PageSize(pageSize));
//        pdfDoc.addNewPage();

        PdfFormXObject pageXObject = firstPage.copyAsFormXObject(pdfDoc);

        PdfFormXObject xObject1 = new PdfFormXObject(pageSize);
        PdfCanvas canvas1 = new PdfCanvas(xObject1, pdfDoc);
        canvas1.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
        canvas1.rectangle(toMove.getLeft(), toMove.getBottom(),
                toMove.getWidth(), toMove.getHeight());
        canvas1.eoClip();
        canvas1.newPath();
        canvas1.addXObject(pageXObject, 0, 0);

        PdfFormXObject xObject2 = new PdfFormXObject(pageSize);
        PdfCanvas canvas2 = new PdfCanvas(xObject2, pdfDoc);
        canvas2.rectangle(toMove.getLeft(), toMove.getBottom(),
                toMove.getWidth(), toMove.getHeight());
        canvas2.clip();
        canvas2.newPath();
        canvas2.addXObject(pageXObject, 0, 0);

        List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<>();
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(toMove.getLeft(), toMove.getBottom(),
                toMove.getWidth(), toMove.getHeight()), ColorConstants.WHITE));
        PdfCleanUpTool cleaner = new PdfCleanUpTool(pdfDoc, cleanUpLocations);
        cleaner.cleanUp();

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.addXObject(xObject1, 0, 0);
        canvas.addXObject(xObject2, 0, -height);
    }

    private static float[] getKeyWords(PdfDocument pdfDocument, String keyWorld) {
        PdfDocumentContentParser pdfReaderContentParser = new PdfDocumentContentParser(pdfDocument);
        int numberOfPages = pdfDocument.getNumberOfPages();
        float[] arrays = new float[3];
        for (int i = 1; i <= numberOfPages; i++) {
            int finalI = i;
            final Boolean[] booleans = {false};
            pdfReaderContentParser.processContent(i, new IEventListener() {
                @Override
                public void eventOccurred(IEventData iEventData, EventType eventType) {
                    if (iEventData instanceof TextRenderInfo) {
                        TextRenderInfo textRenderInfo = (TextRenderInfo) iEventData;
                        String text = textRenderInfo.getText();

                        if (null != text && text.contains(keyWorld)) {
                            Rectangle boundingRectangle = textRenderInfo.getBaseline().getBoundingRectangle();
                            arrays[0] = boundingRectangle.getX();
                            arrays[1] = boundingRectangle.getY();
                            arrays[2] = finalI;
                            booleans[0] = true;
                        }
                    }
                }

                @Override
                public Set<EventType> getSupportedEvents() {
                    HashSet<EventType> eventTypes = new HashSet<>();
                    eventTypes.add(EventType.RENDER_TEXT);
                    return eventTypes;
                }
            });
            if (booleans[0]) {
                return arrays;
            }
        }
        return arrays;
    }
}
