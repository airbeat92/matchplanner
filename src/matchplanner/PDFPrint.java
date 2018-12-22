package matchplanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.JFileChooser;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

/*
 * @author marvin
 */
public class PDFPrint {

	public static final DateTimeFormatter DF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	public PDFPrint(Matchplan mp) {
		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		// Dialog zum Oeffnen von Dateien anzeigen
		int select = chooser.showDialog(null, "Exportieren nach:");
		if (select == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getCurrentDirectory().getAbsolutePath();
			String name = chooser.getSelectedFile().getName();
			try {
				exportToPdf(mp, name, path);

			} catch (IOException e) {
				System.out.println("Speichern nicht erfolgreich: " + LocalTime.now() + " " + LocalDate.now());
				e.printStackTrace();
			}

		}
		if (select == JFileChooser.CANCEL_OPTION) {

		}

	}

	public static void exportToPdf(Matchplan mp, String name, String path) throws FileNotFoundException {
		// Erstellen des PDF Dokuments
		PdfWriter writer = new PdfWriter(path + "/" + name + ".pdf");
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);

		// Teams
		Text headerT = new Text("Teams:").setFontSize(22);
		document.add(new Paragraph(headerT));
		for (Team t : mp.teams) {
			document.add(new Paragraph(t.getName()));
		}

		// Spieltage
		Text headerD = new Text("Spieltage:").setFontSize(22);
		document.add(new Paragraph(headerD));
		for (Matchday m : mp.season) {
			Text date = new Text(m.getMatchDate().format(DF)).setFontSize(20);
			document.add(new Paragraph(date));
			for (Match mm : m.getMd()) {
				document.add(new Paragraph(mm.matchAsString(mp)));
			}
			document.add(new Paragraph(""));
		}

		System.out.println("erfolgreich!");
		document.close();
	}

}
