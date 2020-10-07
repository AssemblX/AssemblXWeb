package mpimp.assemblxweb.teststuff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpTester {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("((complement|join)?\\(?[0-9]+(\\.{2}|\\^)[0-9]+\\)?)(,(complement|join)?\\(?([0-9]+(\\.{2}|\\^)[0-9]+\\)?))+");
		//Pattern pattern = Pattern.compile("[0-9]+(\\.{2}|\\^)[0-9]+");
		// String testString =
		// "tcactgcccactttccagtctggaaacctgtscttgctagctgcatgagtgaatcagccaatgcccüggggagaggcagtttgtgtattgggtgccagggtggtttttctcttcaccagtgaga";
		//String testString = "complement(69611..69724),139856..140087,join(140625..140650),86312..86385";
		String testString = "400..450,430^431";
		//String testString = "430^431";
		Matcher matcher = pattern.matcher(testString);
		while (matcher.find()) {
			System.out.println(matcher.groupCount());
			for (int i = 0; i <= matcher.groupCount(); i++) {
				System.out.println("Group " + i + ": " + matcher.group(i));
			}
		}
	}

}