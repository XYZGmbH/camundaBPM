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

public class CreateStudentCard implements JavaDelegate{

	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		String vorname = (String) exec.getVariable("vorname"), 
				nachname = (String) exec.getVariable("nachname"),
				matrikelnummer = (String) exec.getVariable("matrikelnummer"),
				geburtsdatum  = (String) exec.getVariable("geburtsdatum"),
				studiengang = (String) exec.getVariable("Studienfach");
		this.createCard(vorname, nachname, geburtsdatum, matrikelnummer, studiengang);
		exec.setVariable("type", "bestaetigung");
	}
	

	public boolean createCard(String vorname, String nachname, String geburtsdatum, String matrikelnummer, String studiengang) {
		
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
			//canvas.addImage(ImageDataFactory.create("./src/main/resources/pics/studentCardBackground.png" , com.itextpdf. , false);
			//background = new Image(ImageDataFactory.create());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Table table = new Table(UnitValue.createPercentArray( new float[] {20f,180f}));
		table.setAutoLayout();
		table.setMinWidth(300f);
		table.addCell(generateCell(img));
		table.addCell(generateCell("Nachname: " + nachname + "\nVorname: " + vorname + "\nGeburtsdatum: " + geburtsdatum + "\n"));
		table.addCell(generateCell("\nMatrikelnummer: " + matrikelnummer + "\nStudiengang: " + studiengang + "\n Gültig bis: 30.09.2018", 2 , 2));	
		
		doc.add(new Paragraph("Studierendenausweis der Imaginary University Berlin"));
		doc.add(table);
		
		doc.close();
		
		return (new File(path)).exists();
		
	}
	
	private Cell generateCell(String str) {
		Cell c = new Cell();
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(new Paragraph(str));
		return c;
	}
	
	private Cell generateCell(String str, int row, int cellEnd) {
		Cell c = new Cell(row, cellEnd);
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(new Paragraph(str));
		return c;
	}
	
	private Cell generateCell(Image img) {
		Cell c = new Cell();
		c.setBorder(com.itextpdf.layout.borders.Border.NO_BORDER);
		c.add(img);
		return c;
	}
	
	
	
}
