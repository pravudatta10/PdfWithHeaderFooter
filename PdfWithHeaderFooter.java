package com.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException; 
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte; 
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;  
import com.itextpdf.text.Image; 
//Header Footer
class HeaderFooter  extends PdfPageEventHelper 
{
	  
  public void onEndPage(PdfWriter writer, Document document) 
  {
  	Font yourCustomFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20 ,BaseColor.GRAY);    	 
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Heading",yourCustomFont),300, 810, 0);
  	 Image FooterImage = null;
		 // here i am adding image you can add text also
		try {
			FooterImage = Image.getInstance("F:\\Assets\\FooterImage.png"); 
			//FooterImage.setAlignment(Element.ALIGN_BOTTOM);
			FooterImage.setAbsolutePosition(48, 40);  
			FooterImage.scaleAbsolute(510, 120);
		} catch (BadElementException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
   	
   	  try {
			document.add(FooterImage);
		} catch (DocumentException e) {
			 
			e.printStackTrace();
		} 
			
  	 //Page no
   	Font FooterPageNo = FontFactory.getFont(FontFactory.COURIER_BOLD, 12 ,BaseColor.RED);
   	Font  PageNo = FontFactory.getFont(FontFactory.COURIER_BOLD, 12 ,BaseColor.GRAY);
   	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("P a g e",PageNo), 530, 25, 0);
   	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase( " |"+document.getPageNumber() ,FooterPageNo), 562, 25, 0);
  }
}
public class PdfWithHeaderFooter {

	 
		 

		private static void PdfTablePage(Document document)throws Exception 
		{
			//set column of pdf like 3 ,4 ,5
	        PdfPTable table = new PdfPTable(3);
			//set width of column
	        table.setWidths(new float[] {1.5f, 6.0f, 2.0f}); 
	        //set total width of table
	        table.setWidthPercentage(100);
	        
	        Font yourCustomFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	      
	        PdfPCell c1 = new PdfPCell(new Phrase("Top heading ",yourCustomFont));
	       //   c1.setTop(Element.ALIGN_JUSTIFIED_ALL);   
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			// set top heading span
	        c1.setColspan(3);
	        c1.setRowspan(3);  
	        c1.setPadding(10);
	        //c1.setBorder(8);
	        table.addCell(c1); 
	        float  widthval =20;
	        
	        String [] Data= {"1.0","Description","",
	        		"1.1.","Engine Manufacturer:","",
	        		"1.1.1.","Manufacturer's engine model code ","",        		 
	        		};  
	              
	        for(int i=0;i<Data.length;i++)
	        { 
	        	table.addCell(Data[i]);
	        }     
	        document.add(table);
	    	}
		 
	    public static void main(String[] args) throws Exception 
	    {
		Document document = new Document();	   
		document.setMargins(50, 45, 40, 170);  //set height and width of margin like top ,left right ,bottom 
		PdfWriter writer ;
		String dest="C:\\Users\\NICHEBIT\\Desktop\\OUTPUT\\Report1.pdf";
		writer= PdfWriter.getInstance(document, new FileOutputStream(dest));
		writer.setFullCompression();    
		document.open();  
		HeaderFooter event = new HeaderFooter();
	    writer.setPageEvent(event);
	        //Table added here only
	        PdfTablePage(document);        
	        document.add(new Paragraph("\n"));   
			
			//For new Page    //adding image to pdf with new page 
	        document.newPage();
	        //Image added here
	        try
	        {
	        	Image img = Image.getInstance("F:\\Assets\\Tourism.png");
	            img.setAlignment(Element.ALIGN_CENTER);  
	            document.add(img);
	        }
	        catch (Exception e) 
	        {
			 e.printStackTrace();
			}
	         
	        //Adding External Pdf To your document.
	        try
	        {
	        	 PdfContentByte contentbyte = writer.getDirectContent();
	             String templateInputStream ="F:\\Assets\\sample.pdf";  
	            	// Load existing PDF
	             PdfReader reader = new PdfReader(templateInputStream);
	     		//Read Pdf page one by one         
	             for(int i=1;i<=reader.getNumberOfPages();i++)
	             {
	                  PdfImportedPage page = writer.getImportedPage(reader, i);       
	                  // Copy  page of existing PDF into output PDF             
	                  document.newPage();              
	                  contentbyte.addTemplate(page, 1.0, 0, 0, 0.78, 0, 160);     
	                  
	             }
	        }
	       
	         catch (Exception e) {
				 e.printStackTrace();
			}
	        //Close the Document 
	        document.close();        
	        
		    System.out.println("Pdf Created Successfully");
		     
		    
	    }

	}


