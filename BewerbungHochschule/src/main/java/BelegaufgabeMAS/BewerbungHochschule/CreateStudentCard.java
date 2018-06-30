package BelegaufgabeMAS.BewerbungHochschule;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.helpers.NOPLoggerFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;

public class CreateStudentCard implements JavaDelegate {

	/* (non-Javadoc)
	 * In this execution, a Student Card is created as PDF and the type of the following Mail is set to "bestaetigung".
	 */
	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		String vorname = (String) exec.getVariable("vorname"), nachname = (String) exec.getVariable("nachname"),
				matrikelnummer = (String) exec.getVariable("matrikelnummer"),
				geburtsdatum = (String) exec.getVariable("geburtsdatum"),
				studiengang = (String) exec.getVariable("Studienfach");
		this.createCard(vorname, nachname, geburtsdatum, matrikelnummer, studiengang);
		exec.setVariable("type", "bestaetigung");
	}

	/**
	 * createCard:
	 * creates a Student Card from the checked variables of the student.
	 * A PDF-Document is created, an image loaded and then put together inside a table.
	 * It is saved under C:/Users/Felix/studentCard'studentMatriculationNumber'.pdf
	 * @param vorname Name of the student
	 * @param nachname surname of the student
	 * @param geburtsdatum bithday of student
	 * @param matrikelnummer matriculation number of student
	 * @param studiengang major of student
	 * @return true, if creation was succesful, false if not
	 */
	public boolean createCard(String vorname, String nachname, String geburtsdatum, String matrikelnummer,
			String studiengang) {

		PdfWriter pw = null;
		String path = "C:/Users/Felix/studentCard" + matrikelnummer + ".pdf";
		try {
			pw = new PdfWriter(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PdfDocument pdfDoc = new PdfDocument(pw);

		Document doc = new Document(pdfDoc, PageSize.A6.rotate());

		Image img = null;

		try {

			ImageData image = ImageDataFactory.create("pics/studentPic2.png");
			img = new Image(image);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Table table = new Table(UnitValue.createPercentArray(new float[] { 20f, 180f }));
		table.setAutoLayout();
		table.setMinWidth(300f);
		table.addCell(generateCell(img));
		table.addCell(generateCell(
				"Nachname: " + nachname + "\nVorname: " + vorname + "\nGeburtsdatum: " + geburtsdatum + "\n"));
		table.addCell(generateCell(
				"\nMatrikelnummer: " + matrikelnummer + "\nStudiengang: " + studiengang + "\n Gültig bis: 30.09.2018",
				2, 2));

		doc.add(new Paragraph("Studierendenausweis der Imaginary University Berlin"));
		doc.add(table);

		doc.close();

		return (new File(path)).exists();

	}

	/**
	 * generateCell: Returns a borderless Cell filled with text.
	 * 
	 * @param str
	 *            text-content of the cell
	 * @return A Cell, filled with Text and without borders
	 */
	private Cell generateCell(String str) {
		Cell c = new Cell();
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(new Paragraph(str));
		return c;
	}

	/**
	 * generateCell: Returns a borderless Cell filled with text, over n rows and
	 * columns.
	 * 
	 * @param str
	 *            text-content of the cell
	 * @param row
	 *            how many rows it should fill
	 * @param cellEnd
	 *            how many columns should be filled
	 * @return A Cell, filled with Text and without borders
	 */
	private Cell generateCell(String str, int row, int cellEnd) {
		Cell c = new Cell(row, cellEnd);
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(new Paragraph(str));
		return c;
	}

	/**
	 * generateCell: Creates a Cell with an image as content.
	 * 
	 * @param img
	 *            an Image
	 * @return A Cell, filled with an image and without borders
	 */
	private Cell generateCell(Image img) {
		Cell c = new Cell();
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(img);
		return c;
	}

}
