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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

public class Level_2_ProtocolWriter {

	public Level_2_ProtocolWriter(AssemblXWebModel model, Sheet sheet) {
		model_ = model;
		sheet_ = sheet;
	}

	public void writeLevel_2_Protocol() throws IOException {
		//TODO: go through all annotation records to check
		//if a sequence has been annotated more than once 
		//using different names
		writeSheetContent();
		writeFinalSequenceToTextFile();
		writeFinalSequenceToGenbankFile();
	}

	private void writeSheetContent() {
		Row row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Level 2");
		row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("ID Number");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Name");
		cell = row.createCell(2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Strategy");
		cell = row.createCell(3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Enzyme");
		cell = row.createCell(4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Length");
		cell = row.createCell(5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Warnings");

		row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("0");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue(model_.getLevel_2_vectorName());
		cell = row.createCell(2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("DIGEST");
		cell = row.createCell(3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("EcoR1");
		cell = row.createCell(4, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(model_.getLevel_2_vectorLength());
		cell = row.createCell(5, Cell.CELL_TYPE_BLANK);

		List<Module> modules = model_.getModules();
		Integer id = 1;
		for (Module currentModule : modules) {
			if (currentModule.hasEvaluableUnits()) {
				id++;
				row = sheet_.createRow(rowCounter_);
				rowCounter_++;
				cell = row.createCell(0, Cell.CELL_TYPE_STRING);
				cell.setCellValue(id);
				cell = row.createCell(1, Cell.CELL_TYPE_STRING);
				String name = "Level 1 " + currentModule.getName();
				cell.setCellValue(name);
				cell = row.createCell(2, Cell.CELL_TYPE_STRING);
				cell.setCellValue("DIGEST");
				cell = row.createCell(3, Cell.CELL_TYPE_STRING);
				cell.setCellValue("I-SceI");
				cell = row.createCell(4, Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(currentModule.getLevel_2_fragmentLength());
				cell = row.createCell(5, Cell.CELL_TYPE_STRING);
				String warnings = "";
				if (currentModule.getSimilarSizes()) {
					warnings += "Fragment and vector backbone have similar sizes. "
							+ "Use low percentage gel or consider PCR amplification.";
				}
				if (currentModule.getContainsI_Scel_I_sites()) {
					warnings += "Level 0 unit contains I-SceI site(s)."
							+ " Later Level 2 assemblies and subcloning will"
							+ " not be possible without PCR amplification of "
							+ "the Level 1 module containing this unit.";
				}
				cell.setCellValue(warnings);
			}
		}
		row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Assembly via TAR or HiFi");
		row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		row.createCell(0, Cell.CELL_TYPE_BLANK);
		row = sheet_.createRow(rowCounter_);
		rowCounter_++;
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Size of final Level 2 construct");
		cell = row.createCell(1, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(model_.getFinalLevel_2_Construct().length());
	}

	private void writeFinalSequenceToTextFile() throws IOException {
		String filePath = model_.getWorkingDirectory()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"resultDirectory") + File.separator + "Level2.txt";
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filePath));
			bufferedWriter.write(model_.getFinalLevel_2_Construct());
			bufferedWriter.close();
		} catch (IOException ioe) {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
			throw new IOException(ioe);
		}
	}

	private void writeFinalSequenceToGenbankFile() throws IOException {
		String directoryPath = model_.getWorkingDirectory()
				+ File.separator
				+ AssemblXWebProperties.getInstance().getProperty(
						"resultDirectory");
		GenbankData genbankData = new GenbankData();
		genbankData.setLocusName("Level2");
		genbankData.setSequence(model_.getFinalLevel_2_Construct());
		genbankData.setConformation(Conformation.CIRCULAR);
		List<Module> modules = model_.getModules();
		List<AnnotationRecord> annotationRecords = new ArrayList<AnnotationRecord>();
		for (Module currentModule : modules) {
			List<TranscriptionalUnit> tuUnits = currentModule
					.getTranscriptionalUnits();
			for (TranscriptionalUnit currentTuUnit : tuUnits) {
				annotationRecords.addAll(currentTuUnit.getAnnotationRecords());
			}
		}
		genbankData.setAnnotationRecords(annotationRecords);
		GenbankWriter genbankWriter = new GenbankWriter();
		genbankWriter.writeGenbankFile(genbankData, directoryPath);
	}

	private AssemblXWebModel model_;
	private Sheet sheet_;
	private int rowCounter_ = 0;
}
