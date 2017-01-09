/*
AssemblX: A three step assembly process for up to 25 genes

To express large sets of proteins in yeast or other host organisms, we
have developed a cloning framework which allows the modular cloning of
up to 25 genes into one circular artificial yeast chromosome.
	
AssemblXWeb assists the user with all design and assembly 
steps and therefore greatly reduces the time required to complete complex 
assemblies.
	
Copyright (C) 2016,  gremmels(at)mpimp-golm.mpg.de

AssemblXWeb is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

Contributors:
gremmels(at)mpimp-golm.mpg.de - initial API and implementation
*/
package mpimp.assemblxweb.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import mpimp.assemblxweb.db.AnnotationRecord;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.GenbankData;
import mpimp.assemblxweb.db.GenbankData.Conformation;
import mpimp.assemblxweb.db.Module;
import mpimp.assemblxweb.db.TranscriptionalUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Level_1_ProtocolWriter {

	public Level_1_ProtocolWriter(AssemblXWebModel model, Sheet sheet) {
		model_ = model;
		sheet_ = sheet;
		rowCounter_ = new Counter(0);
	}

	public void writeLevel_1_ProtocolSheet() throws IOException {
		List<Module> modules = model_.getModules();
		//TODO: go through all annotation records to check
		//if a sequence has been annotated more than once 
		//using different names
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				writeModuleSection(currentModule);
			}
		}
		writeFinalSequencesToFiles();
	}

	private void writeModuleSection(Module currentModule) {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		String levelHeader = "Level 1" + currentModule.getName();
		cell.setCellValue(levelHeader);
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("ID Number");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Name");
		cell = row.createCell(2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Alias");
		cell = row.createCell(3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Strategy");
		cell = row.createCell(4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Enzyme");
		cell = row.createCell(5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Fragment Length");
		cell = row.createCell(6, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Warnings");

		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(0);
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue(currentModule.getLevel_1_vectorName());
		cell = row.createCell(2,Cell.CELL_TYPE_BLANK);//no alias for level 1 vector
		cell = row.createCell(3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("DIGEST");
		cell = row.createCell(4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Pacl");
		cell = row.createCell(5, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(currentModule.getLevel_1_vectorLength());
		cell = row.createCell(6, Cell.CELL_TYPE_STRING);
		cell.setCellValue("");

		List<TranscriptionalUnit> tuUnits = currentModule.getTranscriptionalUnits();
		int id = 1;
		for (TranscriptionalUnit currentTuUnit : tuUnits) {
			if (currentTuUnit.isEvaluable() == false) {
				continue;
			}
			row = sheet_.createRow(rowCounter_.getValueAndIncrement());
			cell = row.createCell(0, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(id);
			cell = row.createCell(1, Cell.CELL_TYPE_STRING);
			cell.setCellValue(currentTuUnit.getName());
			cell = row.createCell(2, Cell.CELL_TYPE_STRING);
			cell.setCellValue(currentTuUnit.getPlasmidName());
			cell = row.createCell(3, Cell.CELL_TYPE_STRING);
			if (currentTuUnit.getLevel_0_Protocol().getNoFreeEnzyme()) {
				cell.setCellValue("PCR amplify");
			} else {
				cell.setCellValue("DIGEST");
			}
			cell = row.createCell(4, Cell.CELL_TYPE_STRING);
			cell.setCellValue(currentTuUnit.getLevel_0_Protocol().getEnzyme());
			cell = row.createCell(5, Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(currentTuUnit.getLevel_0_Protocol().getFragmentLength());
			cell = row.createCell(6, Cell.CELL_TYPE_STRING);
			String warningString = "";
			if (currentTuUnit.getLevel_0_Protocol().getNoFreeEnzyme()) {
				warningString += "Level 0 unit contains all restriction sites from "
						+ "the MCS. Use PCR amplification.";
			}
			if (currentTuUnit.getLevel_0_Protocol().getSimilarLengths()) {
				warningString += "Fragment and vector backbone have similar sizes."
						+ " Use low percentage gel or consider PCR amplification";
			}
			cell.setCellValue(warningString);
			id++;
		}
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Assembly via TAR or HiFi");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Size of final Level 1 construct");
		cell = row.createCell(1, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(currentModule.getFinalLevel_1_Construct().length());
		writeBlankRow(3);

	}

	private void writeBlankRow(int numberOfCells) {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		for (int i = 0; i < numberOfCells; i++) {
			row.createCell(i + 1, Cell.CELL_TYPE_BLANK);
		}
	}
	
	private void writeFinalSequencesToFiles() throws IOException {
		List<Module> modules = model_.getModules();
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				writeFinalSequenceToTextFile(currentModule);
				writeFinalSequenceToGenbankFile(currentModule);
			}
		}
	}

	private void writeFinalSequenceToTextFile(Module module) throws IOException {
		String filePath = model_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory") + File.separator + "Level1"
				+ module.getName() + ".txt";
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			bufferedWriter.write(module.getFinalLevel_1_Construct());
			bufferedWriter.close();
		} catch (IOException ioe) {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			throw new IOException(ioe);
		}
	}

	private void writeFinalSequenceToGenbankFile(Module module) throws IOException {
		GenbankData genbankData = new GenbankData();
		genbankData.setLocusName("Level1" + module.getName());
		genbankData.setSequence(module.getFinalLevel_1_Construct());
		genbankData.setConformation(Conformation.CIRCULAR);
		List<TranscriptionalUnit> tuUnits = module.getTranscriptionalUnits();
		List<AnnotationRecord> annotationRecords = new ArrayList<AnnotationRecord>();
		for (TranscriptionalUnit currentTuUnit : tuUnits) {
			annotationRecords.addAll(currentTuUnit.getAnnotationRecords());
		}
		genbankData.setAnnotationRecords(annotationRecords);
		GenbankWriter genbankWriter = new GenbankWriter();
		String filePath = model_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory");
		genbankWriter.writeGenbankFile(genbankData, filePath);
	}

	private AssemblXWebModel model_;
	private Sheet sheet_;
	private Counter rowCounter_;
}
