package matchplanner;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PDFPrint {
	
	public PDFPrint (){
		
	}
	
	public void exportToPdf() throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("AppData/" + "new.pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		document.add(new Paragraph("Hello World!"));
		System.out.println("erfolgreich!");
		document.close();
	}




}
