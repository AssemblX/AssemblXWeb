package mpimp.assemblxweb.teststuff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.jam.xml.TunnelledException;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.HomologyRegions;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;
import mpimp.assemblxweb.db.TranscriptionalUnit.TUOrientation;
import mpimp.assemblxweb.util.AssemblXWebEnumTranslator;
import mpimp.assemblxweb.util.J5ResultAnalyzer;
import mpimp.assemblxweb.util.ProtocolWriter;

public class TestJ5ResultAnalyzer {

	public static void main(String[] args) {
		TestJ5ResultAnalyzer testJ5ResultAnalyzer = new TestJ5ResultAnalyzer();
		// testJ5ResultAnalyzer.testUnpackResultsAndFindResultFileName();
		// testJ5ResultAnalyzer.testParseResultFile();
		testJ5ResultAnalyzer.testProtocolWriter();
		// testJ5ResultAnalyzer.testParseFinalVectorSequence();
	}

	public void testUnpackResultsAndFindResultFileName() {
		AssemblXWebModel model = new AssemblXWebModel();
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\TestWorkingDir");
		model.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir");
		Module module = new Module();
		module.setName("A");
		TranscriptionalUnit transcriptionalUnit = null;
		try {
			transcriptionalUnit = new TranscriptionalUnitForTesting(module, 2,
					model);
			transcriptionalUnit.setTuDirectoryName("A2");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		transcriptionalUnit
				.setResultDirectoryName("j5_Jgremmels_20151027064856UpFN");

		try {
			J5ResultAnalyzer.unpackResult(transcriptionalUnit, model);
			J5ResultAnalyzer.findResultFileName(transcriptionalUnit, model);
			System.out.println(transcriptionalUnit.getResultFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testParseResultFile() {
		AssemblXWebModel model = new AssemblXWebModel();
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\TestWorkingDir");
		model.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir");
		Module module = new Module();
		module.setName("A");
		TranscriptionalUnit transcriptionalUnit = null;
		try {
			transcriptionalUnit = new TranscriptionalUnitForTesting(module, 1,
					model);
			transcriptionalUnit.setTuDirectoryName("A1");
			transcriptionalUnit.setResultFileName("ppla00001.csv");
			transcriptionalUnit
					.setResultDirectoryName("j5_Jgremmels_20160126081004LKL_");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			J5ResultAnalyzer.parseResultFile(transcriptionalUnit, model);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void testProtocolWriter() {
		AssemblXWebModel model = new AssemblXWebModel();
		AssemblXWebEnumTranslator enumTranslator = new AssemblXWebEnumTranslator();
		model.setAssemblXWebEnumTranslator(enumTranslator);
		// model.setWorkingDirectory("H:\\Programmierung_BioInf\\AssemblX\\TestWorkingDir");
		model.setWorkingDirectory("/home/gremmels/winhome/Programmierung_BioInf/AssemblX/TestWorkingDir");
		Module module = new Module();
		module.setName("A");
		TranscriptionalUnit transcriptionalUnit = null;
		List<TranscriptionalUnit> tuUnits = new ArrayList<TranscriptionalUnit>();
		try {
			transcriptionalUnit = new TranscriptionalUnitForTesting(module, 1,
					model);
			transcriptionalUnit.setName("A1");
			transcriptionalUnit.setOrientation(TUOrientation.SENSE);
			transcriptionalUnit.setParentModule(module);
			transcriptionalUnit.setTuDirectoryName("A1");
			transcriptionalUnit.setResultFileName("pmas00001.csv");
			transcriptionalUnit
					.setResultDirectoryName("j5_Jgremmels_20160126081004LKL_");
			tuUnits.add(transcriptionalUnit);
			transcriptionalUnit = new TranscriptionalUnitForTesting(module, 1,
					model);
			transcriptionalUnit.setName("A2");
			transcriptionalUnit.setOrientation(TUOrientation.SENSE);
			transcriptionalUnit.setParentModule(module);
			transcriptionalUnit.setTuDirectoryName("A2");
			transcriptionalUnit.setResultFileName("pmas00001.csv");
			transcriptionalUnit
					.setResultDirectoryName("j5_Jgremmels_20151027064856UpFN");
			tuUnits.add(transcriptionalUnit);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			ProtocolWriter protocolWriter = new ProtocolWriter(model);
			for (TranscriptionalUnit tuUnit : tuUnits) {
				J5ResultAnalyzer.parseResultFile(tuUnit, model);
				protocolWriter.appendLevel_0_Section(tuUnit);
			}
			// Level_0_Protocol_Writer protocolCreator = new
			// Level_0_Protocol_Writer(transcriptionalUnit, model);
			// protocolCreator.writeLevel_0_ProtocolSection();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	public void testParseFinalVectorSequence() {
		String finalVectorSequence = "ACCTCTGACTTGAGCGTCGATTTTTGTGATGCTCGTCAGGGGGGCGGAGCCTATGGAAAAACGCCAGCAACGCGGC"
				+ "CTTTTTACGGTTCCTGGCCTTTTGCTGGCCTTTTGCTCATGATATAATTAAATTGAAGCTCTAATTTGTGAGTTTA"
				+ "GTATACATGCATTTACTTATAATACAGTTTTTTAGTTTTGCTGGCCGCATCTTCTCAAATATGCTTCCCAGCCTGC"
				+ "TTTTCTGTAACGTTCACCCTCTACCTTAGCATCCCTTCCCTTTGCAAATAGTCCTCTTCCAACAATAATAATGTCA"
				+ "GATCCTGTAGAGACCACATCATCCACGGTTCTATACTGTTGACCCAATGCGTCTCCCTTGTCATCTAAACCCACAC"
				+ "CGGGTGTCATAATCAACCAATCGTAACCTTCATCTCTTCCACCCATGTCTCTTTGAGCAATAAAGCCGATAACAAA"
				+ "ATCTTTGTCGCTCTTCGCAATGTCAACAGTACCCTTAGTATATTCTCCAGTAGATAGGGAGCCCTTGCATGACAAT"
				+ "TCTGCTAACATCAAAAGGCCTCTAGGTTCCTTTGTTACTTCTTCTGCCGCCTGCTTCAAACCGCTAACAATACCTG"
				+ "AAATAGGAAAGTCTGGTAACTCAGGACACTGTATCTGCTACGCTGTTTAT"// HR A0
				+ "GGCCCACCACACCGTGTGCATTCGTAATGTCTGCCCATTCTGCTATTCTGTATACACCCGCAGAGTACTGCAATTT"
				+ "GACTGTATTACCAATGTCAGCAAATTTTCTGTCTTCGAAGAGTAAAAAATTGTACTTGGCGGATAATGCCTTTAGC"
				+ "GGCTTAACTGTGCCCTCCATGGAAAAATCAGTCAAGATATCCACATGTGTTTTTAGTAAACAAATTTTGGGACCTA"
				+ "ATGCTTCAACTAACTCCAGTAATTCCTTGGTGGTACGAACATCCAATGAAGCACACAAGTTTGTTTGCTTTTCGTG"
				+ "CATGATATTAAATAGCTTGGCAGCAACAGGACTAGGATGAGTAGCAGCACGTTCCTTATATGTAGCTTTCGACATG"
				+ "ATTTATCTTCGTTTCCTGCAGGTTTTTGTTCTGTGCAGTTGGGTTAAGAATACTGGGCAATTTCATGTTTCTTCAA"
				+ "CACTACATATGCGTATATATACCAATCTAAGTCTGTGCTCCTTCCTTCGTTCTTCCTTCTGTTCGGAGATTACCGA"
				+ "ATCAAAAAAATTTCAAAGAAACCGAAATCAAAAAAAAGAATAAAAAAAAAATGATGAATTGAATTGAAAAGCTAGC"
				+ "TTATCGATGATAAGCTGTCAAAGATGAGAATTAATTCCACGGACTATAGACTATACTAGATACTCCGTCTACTGTA"
				+ "CGATACACTTCCGCTCAGGTCCTTGTCCTTTAACGAGGCCTTACCACTCTTTTGTTACTCTATTGATCCAGCTCAG"
				+ "CAAAGGCAGTGTGATCTAAGATTCTATCTTCGCGATGTAGTAAAACTAGCTAGACCGAGAAAGAGACTAGAAATGC"
				+ "AAAAGGCACTTCTACAATGGCTGCCATCATTATTATCCGATGTGACGCTGCAGCTTCTCAATGATATTCGAATACG"
				+ "CTTTGAGGAGATACAGCCTAATATCCGACAAACTGTTTTACAGATTTACGATCGTACTTGTTACCCATCATTGAAT"
				+ "TTTGAACATCCGAACCTGGGAGTTTTCCCTGAAACAGATAGTATATTTGAACCTGTATAATAATATATAGTCTAGC"
				+ "GCTTTACGGAAGACAATGTATGTATTTCGGTTCCTGGAGAAACTATTGCATCTATTGCATAGGTAATCTTGCACGT"
				+ "CGCATCCCCGGTTCATTTTCTGCGTTTCCATCTTGCACTTCAATAGCATATCTTTGTTAACGAAGCATCTGTGCTT"
				+ "CATTTTGTAGAACAAAAATGCAACGCGAGAGCGCTAATTTTTCAAACAAAGAATCTGAGCTGCATTTTTACAGAAC"
				+ "ATGTCGAAAGCTACATATAAGGAACGTGCTGCTACTCATCCTAGTCCTGTT" // HR AR
				+ "AGAAATGCAACGCGAAAGCGCTATTTTACCAACGAAGAATCTGTGCTTCATTTTTGTAAAACAAAAATGCAACGCG"
				+ "ACGAGAGCGCTAATTTTTCAAACAAAGAATCTGAGCTGCATTTTTACAGAACAGAAATGCAACGCGAGAGCGCTATT"
				+ "TTACCAACAAAGAATCTATACTTCTTTTTTGTTCTACAAAAATGCATCCCGAGAGCGCTATTTTTCTAACAAAGCAT"
				+ "CTTAGATTACTTTTTTTCTCCTTTGTGCGCTCTATAATGCAGTCTCTTGATAACTTTTTGCACTGTAGGTCCGTTAA"
				+ "GGTTAGAAGAAGGCTACTTTGGTGTCTATTTTCTCTTCCATAAAAAAAGCCTGACTCCACTTCCCGCGTTTACTGAT"
				+ "TACTAGCGAAGCTGCGGGTGCATTTTTTCAAGATAAAGGCATCCCCGATTATATTCTATACCGATGTGGATTGCGCA"
				+ "TACTTTGTGAACAGAAAGTGATAGCGTTGATGATTCTTCATTGGTCAGAAAATTATGAACGGTTTCTTCTATTTTGT"
				+ "CTCTATATACTACGTATAGGAAATGTTTACATTTTCGTATTGTTTTCGATTCACTCTATGAATAGTTCTTACTACAA"
				+ "TTTTTTTGTCTAAAGAGTAATACTAGAGATAAACATAAAAAATGTAGAGGTCGAGTTTAGATGCAAGTTCAAGGAGC"
				+ "GAAAGGTGGATGGGTAGGTTATATAGGGATATAGCACAGAGATATATAGCAAAGAGATACTTTTGAGCAATGTTTGT"
				+ "GGAAGCGGTATTCGCAATGGGAAGCTCCACCCCGGTTGATAATCAGAAAAGCCCCAAAAACAGGAAGATTGTATAAG"
				+ "CAAATATTTAAATTGTAATTCTTTCCTGCGTTATCCCCTGATTCTGTGGATAACCGTATTACCGCCTTTGAGTGAGC"
				+ "TGATACCGCTCGCCGCAGCCGAACGACCGAGCGCAGCGAGTCAGTGAGCGAGGAAGCGGAAGAGGGTCTGACGCTCA"
				+ "GTGGAACGAAAACTCACGTTAAGGGATTTTGGTCATGAGATTATCAAAAAGGATCTTCACCTAGATCCTTTTAAATT"
				+ "AAAAATGAAGTTTTAAATCAATCTAAAGTATATATGAGTAAACTTGGTCTGACAGTCAGAAGAACTCGTCAAGAAGG"
				+ "CGATAGAAGGCGATGCGCTGCGAATCGGGAGCGGCGATACCGTAAAGCACGAGGAAGCGGTCAGCCCATTCGCCGCC"
				+ "AAGCTCTTCAGCAATATCACGGGTAGCCAACGCTATGTCCTGATAGCGGTCCGCCACACCCAGCCGGCCACAGTCGA";
		String moduleName = "A";
		String startHRName = moduleName + 0;
		String endHRName = moduleName;
		endHRName += "R";

		String startHR = HomologyRegions.getHomologyRegion(startHRName);
		String endHR = HomologyRegions.getHomologyRegion(endHRName);

		Integer sequenceLength = finalVectorSequence.length();
		Integer startIndex = finalVectorSequence.indexOf(startHR);
		Integer endIndex = finalVectorSequence.indexOf(endHR);
		endIndex += endHR.length();
		String fragment = finalVectorSequence.substring(startIndex, endIndex);
		System.out.println(fragment);
		System.out.println(fragment.length());
	}

}
