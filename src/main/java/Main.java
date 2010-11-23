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
 *         as input
 * 
 *         name;certificateId;hours;pdffile
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		File input = new File(args[0]);
		if (input.exists()) {
			List<String> lines = IOUtils.readLines(new FileInputStream(input));
			for (String line : lines) {
				if (line.trim().length() != 0) {
					StringTokenizer tokenizer = new StringTokenizer(line, ",");
					while (tokenizer.hasMoreTokens()) {
						String name = tokenizer.nextToken();
						String certificateId = tokenizer.nextToken();
						String hours = tokenizer.nextToken();
						String pdfFile = tokenizer.nextToken();

						FileOutputStream fos = new FileOutputStream(name + "-" + pdfFile);
						PdfReader reader = new PdfReader(pdfFile);
						PdfStamper stamper = new PdfStamper(reader, fos);
						AcroFields form = stamper.getAcroFields();

						form.setField("TRAINEE NAME", name);
						form.setField("0000", certificateId);
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
