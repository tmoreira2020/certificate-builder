import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * @author Thiago Moreira Basic class that fill up a form PDF usinf a csv file
 *         as input. The file name should certificates.csv
 * 
 *         name,certificateId,hours,issuedDate,pdffile
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (args != null) {
			for (String arg : args) {
				File input = new File(arg);
				if (input.exists()) {
					List<String> lines = IOUtils.readLines(new FileInputStream(input));
					for (String line : lines) {
						if (line.trim().length() != 0) {
							StringTokenizer tokenizer = new StringTokenizer(line, ",");
							while (tokenizer.hasMoreTokens()) {
								String name = tokenizer.nextToken();
								String certificateId = tokenizer.nextToken();
								String hours = tokenizer.nextToken();
								String issuedDate = tokenizer.nextToken();
								String pdfFile = tokenizer.nextToken();

								File output = new File("target", name + "-" + pdfFile);
								FileOutputStream fos = new FileOutputStream(output);
								PdfReader reader = new PdfReader(pdfFile);
								PdfStamper stamper = new PdfStamper(reader, fos);
								AcroFields form = stamper.getAcroFields();

								form.setField("TRAINEE NAME", name);
								form.setField("0000", certificateId);
								form.setField("October, 28th 2010", issuedDate);
								form.setField("24", hours);

								stamper.setFormFlattening(true);
								stamper.close();

								fos.close();
							}
						}
					}
				}
			}
		}

	}

}
