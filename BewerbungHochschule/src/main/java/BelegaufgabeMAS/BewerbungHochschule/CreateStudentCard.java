package BelegaufgabeMAS.BewerbungHochschule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import javafx.scene.paint.Color;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;

public class CreateStudentCard implements JavaDelegate{

	private String result = "";
	
	@Override
	public void execute(DelegateExecution exec) throws Exception {
		// TODO Auto-generated method stub
		String vorname = (String) exec.getVariable("vorname"), 
				nachname = (String) exec.getVariable("nachname"),
				matrikelnummer = (String) exec.getVariable("matrikelnummer"),
				geburtsdatum  = (String) exec.getVariable("geburtsdatum"),
				studiengang = (String) exec.getVariable("studiengang");
		this.createCard(vorname, nachname, geburtsdatum, matrikelnummer, studiengang);
		exec.setVariable("type", "bestaetigung");
	}
	

	public boolean createCard(String vorname, String nachname, String geburtsdatum, String matrikelnummer, String studiengang) {
		
		PdfWriter pw = null;
		String path = "./target/studentCard" + matrikelnummer + ".pdf";
		try {
			pw = new PdfWriter(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PdfDocument pdfDoc = new PdfDocument(pw);
		
		Document doc = new Document(pdfDoc, PageSize.A6.rotate());
		
		Image img = null;
		//Image background = null; 
				
		try {
			img = new Image(ImageDataFactory.create("./src/main/resources/pics/studentPic2.png"));
			//canvas.addImage(ImageDataFactory.create("./src/main/resources/pics/studentCardBackground.png" , com.itextpdf. , false);
			//background = new Image(ImageDataFactory.create());
		} catch (MalformedURLException e) {
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
