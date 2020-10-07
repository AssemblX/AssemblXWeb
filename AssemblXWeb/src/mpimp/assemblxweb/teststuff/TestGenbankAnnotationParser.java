package mpimp.assemblxweb.teststuff;

import java.io.IOException;
import java.util.List;

import mpimp.assemblxweb.db.AnnotationPartItem;
import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.util.GenbankAnnotationParser;
import mpimp.assemblxweb.util.GenbankWriter;
import mpimp.assemblxweb.util.SequenceFileParser;

public class TestGenbankAnnotationParser {

	public static void main(String[] args) throws IOException {
		//String filePath = "H:\\Programmierung_BioInf\\CodonUsage\\RawData\\Chloroplast genomes\\Arabidopsis_chloroplast_Columbia.gb";
		String filePath = "H:\\Programmierung_BioInf\\CodonUsage\\GenbankTest_simple.gb";
		//String filePath = "H:\\Programmierung_BioInf\\AssemblX\\linearisierteVektoren\\Level_0\\pL0A_0-1_linear.gb";
		List<AnnotationRecord> annotationRecords = null;
		
		String wholeSequence = SequenceFileParser.extractSequenceFromGenbankFile(filePath).toUpperCase();
		
		try {
			annotationRecords = GenbankAnnotationParser.parseAnnotations(filePath, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GenbankWriter gw = new GenbankWriter();
		if (annotationRecords != null) {
			for (AnnotationRecord annotationRecord : annotationRecords) {
				System.out.println("Annotation name: " + annotationRecord.getAnnotationName());
				for (String qualifier : annotationRecord.getQualifiers()) {
					System.out.println("Qualifier: " + qualifier);
				}
				//System.out.println("Part sequence: " + annotationRecord.getAnnotationPartItem().getPartSequence());
				AnnotationPartItem item = annotationRecord.getAnnotationPartItem();
				printAnnotationPartItemContent(item);
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				
				System.out.println("Parse result begin ##############################");
				String parseResult = gw.parseAnnotationPartItem(item, wholeSequence);
				System.out.println(parseResult);
				System.out.println("Parse result end ##############################");
				System.out.println();
				System.out.println();
			}
		}
	}

	public static void printAnnotationPartItemContent(AnnotationPartItem item) {
		System.out.println();
//		System.out.println("Is join item? " + item.getIsJoinItem());
//		System.out.println("Is complement item? " + item.getIsReverseComplement());
//		System.out.println("Part sequence of item: " + item.getPartSequence());
//		System.out.println("Start position: " + item.getStartPosition());
//		System.out.println("End position: " + item.getEndPosition());
//		System.out.println("Position of item: " + item.getPositionOfItem());
		if (item.getChildItems() != null && item.getChildItems().size() > 0) {
			for (AnnotationPartItem childItem : item.getChildItems()) {
				printAnnotationPartItemContent(childItem);
			}
		}
	}

}
