package designPattern;

/*The Factory Method Design Pattern is a creational pattern that provides an 
interface for creating objects in a superclass, but allows subclasses to alter 
the type of objects that will be created.*/

interface Document{
	String getHeader();
	String formatRow(String[] data);
	String getFooter();
	String getFileExtension();
}

class PdfDocument implements Document{
	@Override
	public String getHeader() {
		return " PDF DOCUMENT START ";
	}

	@Override
	public String formatRow(String[] data) {
		return "| " + String.join(" | ", data) + " |";
	}

	@Override
	public String getFooter() {
		return " PDF DOCUMENT END ";
	}

	@Override
	public String getFileExtension() {
		return ".pdf";
	}
}

class HtmlDocument implements Document{

	@Override
	public String getHeader() {
		return "<html><body><table>";
	}

	@Override
	public String formatRow(String[] data) {
		StringBuilder sb = new StringBuilder("<tr>");
		for(String cell : data){
			sb.append("<td>").append(cell).append("</td>");
		}
		sb.append("</tr>");
		return sb.toString();
	}

	@Override
	public String getFooter() {
		// TODO Auto-generated method stub
		return "</table></body></html>";
	}

	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return ".html";
	}
	
}

class CsvDocument implements Document{
	@Override
	public String getHeader() {
		return "";
	}

	@Override
	public String formatRow(String[] data) {
		return String.join(",", data);
	}

	@Override
	public String getFooter() {
		return "";
	}

	@Override
	public String getFileExtension() {
		return ".csv";
	}
	
}

abstract class ExportCreator{
	public abstract Document createDocument();
	
	public void export(String[][] data) {
		Document doc = createDocument();
		System.out.println("Exporting to " + doc.getFileExtension() + " format");
		
		String header = doc.getHeader();
		if(!header.isEmpty())
			System.out.println(header);
		
		for(String[] row : data)
			System.out.println(doc.formatRow(row));
		
		String footer = doc.getFooter();
		if(!footer.isEmpty())
			System.out.println(footer);
	
		System.out.println("Export Completed");
		System.out.println();
	}
}

class PdfCreator extends ExportCreator{
	@Override
	public Document createDocument() {
		return new PdfDocument();
	}	
}

class HtmlCreator extends ExportCreator{
	@Override
	public Document createDocument() {
		return new HtmlDocument();
	}	
}

class CsvCreator extends ExportCreator{
	@Override
	public Document createDocument() {
		return new CsvDocument();
	}	
}

public class Factory {
	public static void main(String[] args) {
		String [][] reportData = {
				{"Name", "Department", "Salary"},
	            {"Alice", "Engineering", "120000"},
	            {"Bob", "Marketing", "95000"},
	            {"Charlie", "Design", "105000"}
		};
		
		ExportCreator pdfExporter = new PdfCreator();
		pdfExporter.export(reportData);
		
		ExportCreator htmlExporter = new HtmlCreator();
		htmlExporter.export(reportData);
		
		ExportCreator csvExporter = new CsvCreator();
		csvExporter.export(reportData);
	}
}

/*
 * Bad Code
 * 
 * class ExportService {
 * 
 * public void export(String type, String[][] data) {
 * 
 * Document document = null;
 * 
 * // BAD: Large if-else block creates concrete objects directly if
 * (type.equalsIgnoreCase("PDF")) { document = new PdfDocument(); } else if
 * (type.equalsIgnoreCase("HTML")) { document = new HtmlDocument(); } else if
 * (type.equalsIgnoreCase("CSV")) { document = new CsvDocument(); } else {
 * System.out.println("Invalid format"); return; }
 * 
 * // BAD: Export logic is tightly coupled to format selection if
 * (type.equalsIgnoreCase("PDF")) { System.out.println("PDF DOCUMENT START"); }
 * else if (type.equalsIgnoreCase("HTML")) {
 * System.out.println("<html><body><table>"); }
 * 
 * for (String[] row : data) { if (type.equalsIgnoreCase("PDF")) {
 * System.out.println( "| " + String.join(" | ", row) + " |" ); } else if
 * (type.equalsIgnoreCase("HTML")) {
 * System.out.println(document.formatRow(row)); } else if
 * (type.equalsIgnoreCase("CSV")) { System.out.println(String.join(",", row)); }
 * }
 * 
 * if (type.equalsIgnoreCase("PDF")) { System.out.println("PDF DOCUMENT END"); }
 * else if (type.equalsIgnoreCase("HTML")) {
 * System.out.println("</table></body></html>"); }
 * 
 * System.out.println("Export completed: " + document.getFileExtension());
 * System.out.println(); } }
 * 
 * public class Factory { public static void main(String[] args) {
 * 
 * String[][] reportData = { {"Name", "Department", "Salary"}, {"Alice",
 * "Engineering", "120000"}, {"Bob", "Marketing", "95000"}, {"Charlie",
 * "Design", "105000"} };
 * 
 * ExportService service = new ExportService();
 * 
 * service.export("PDF", reportData); service.export("HTML", reportData);
 * service.export("CSV", reportData); } }
 */