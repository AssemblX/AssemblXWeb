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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.Level_0_Protocol;
import mpimp.assemblxweb.db.Level_0_ProtocolSubunit;
import mpimp.assemblxweb.db.Level_0_Protocol_WetLabSubunit;
import mpimp.assemblxweb.db.TranscriptionalUnit;

public class Level_0_Protocol_Writer {

	public Level_0_Protocol_Writer(TranscriptionalUnit currentTuUnit, AssemblXWebModel model, Sheet sheet,
			Counter rowCounter) {
		currentTuUnit_ = currentTuUnit;
		model_ = model;
		sheet_ = sheet;
		rowCounter_ = rowCounter;
		protocol_ = currentTuUnit_.getLevel_0_Protocol();
	}

	public Level_0_Protocol_Writer(Sheet sheet, Counter rowCounter) {
		sheet_ = sheet;
		rowCounter_ = rowCounter;
	}

	public void writeLevel_0_ProtocolSection() throws Exception {
		writeHeader();
		writeTargetPartsSection();
		writeWetLabSection();
		writeFinalVectorLength();
		copyJ5GenbankFileToResultFolder();
		complementMasterPlasmidList();
	}

	public void writeOverallHeader() {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue(
				"The Level 0 assembly protocols, including primers and gene syntheses are derived from j5 results. For more information please refer to:");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue(
				"Hillson, N. J., et al. (2011). \"j5 DNA Assembly Design Automation Software.\" ACS Synthetic Biology 1(1): 14-21.");
	}

	private void writeHeader() {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Level 0 unit");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Alias");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		String tuUnitConstructedName = currentTuUnit_.getName();
		if (!currentTuUnit_.getIdentifier().equals(currentTuUnit_.getName())) {
			tuUnitConstructedName += " (" + currentTuUnit_.getIdentifier() + ")";
		}
		cell.setCellValue(tuUnitConstructedName);
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		String resultName = currentTuUnit_.getPlasmidName();
		cell.setCellValue(resultName);
		writeBlankRow(3);
	}

	private void writeTargetPartsSection() {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Your design");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0);
		cell.setCellValue("Name");
		cell = row.createCell(1);
		cell.setCellValue("Direction");
		cell = row.createCell(2);
		cell.setCellValue("Strategy");
		List<Level_0_ProtocolSubunit> parts = protocol_.getTargetParts();
		for (Level_0_ProtocolSubunit part : parts) {
			row = sheet_.createRow(rowCounter_.getValueAndIncrement());
			cell = row.createCell(0, Cell.CELL_TYPE_STRING);
			cell.setCellValue(part.getName());
			String direction = model_.getAssemblXWebEnumTranslator().getTuSubunitDirectionName(part.getDirection());
			cell = row.createCell(1, Cell.CELL_TYPE_STRING);
			cell.setCellValue(direction);
			String strategy = model_.getAssemblXWebEnumTranslator()
					.getTuSubunitAssemblyStrategyName(part.getStrategy());
			cell = row.createCell(2, Cell.CELL_TYPE_STRING);
			cell.setCellValue(strategy);
		}
		writeBlankRow(4);
		writeBlankRow(4);
	}

	private void writeWetLabSection() {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Wet lab section - PCRs, digestions & oligo annealing steps");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Type");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Primary Template");
		cell = row.createCell(2, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Primer forward");
		cell = row.createCell(3, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Primer reverse");
		cell = row.createCell(4, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Mean Oligo Tm (3' only)");
		cell = row.createCell(5, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Length");
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("HindIII Digestion");
		cell = row.createCell(1, Cell.CELL_TYPE_STRING);
		cell.setCellValue(protocol_.getBackboneName());
		row.createCell(2, Cell.CELL_TYPE_BLANK);
		row.createCell(3, Cell.CELL_TYPE_BLANK);
		row.createCell(4, Cell.CELL_TYPE_BLANK);
		cell = row.createCell(5, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(protocol_.getBackboneSize());

		if (protocol_.getAnnealedOligos() != null && protocol_.getAnnealedOligos().size() > 0) {
			List<Level_0_Protocol_WetLabSubunit> parts = protocol_.getAnnealedOligos();
			int annealingCounter = 0;
			for (Level_0_Protocol_WetLabSubunit subunit : parts) {
				row = sheet_.createRow(rowCounter_.getValueAndIncrement());
				cell = row.createCell(0, Cell.CELL_TYPE_STRING);
				cell.setCellValue("Annealing " + annealingCounter);
				annealingCounter++;
				row.createCell(1, Cell.CELL_TYPE_BLANK);
				cell = row.createCell(2, Cell.CELL_TYPE_STRING);
				cell.setCellValue(subunit.getTopOligoName());
				cell = row.createCell(3, Cell.CELL_TYPE_STRING);
				cell.setCellValue(subunit.getBottomOligoName());
				row.createCell(4, Cell.CELL_TYPE_BLANK);
				row.createCell(5, Cell.CELL_TYPE_BLANK);
			}
		}

		if (protocol_.getPcrReactions() != null && protocol_.getPcrReactions().size() > 0) {
			List<Level_0_Protocol_WetLabSubunit> parts = protocol_.getPcrReactions();
			int pcrCounter = 0;
			for (Level_0_Protocol_WetLabSubunit subunit : parts) {
				row = sheet_.createRow(rowCounter_.getValueAndIncrement());
				cell = row.createCell(0, Cell.CELL_TYPE_STRING);
				cell.setCellValue("PCR " + pcrCounter);
				pcrCounter++;
				cell = row.createCell(1, Cell.CELL_TYPE_STRING);
				cell.setCellValue(subunit.getPrimaryTemplate());
				cell = row.createCell(2, Cell.CELL_TYPE_STRING);
				cell.setCellValue(subunit.getPrimerForward());
				cell = row.createCell(3, Cell.CELL_TYPE_STRING);
				cell.setCellValue(subunit.getPrimerReverse());
				cell = row.createCell(4, Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(subunit.getMeanOligoTm_3_prime());
				cell = row.createCell(5, Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue(subunit.getLength());
			}
		}
		writeBlankRow(6);
		writeBlankRow(6);
	}

	private void writeFinalVectorLength() {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Purify PCR products if necessary and perform assembly"
				+ " via SLiCE, Gibson, HiFi or TAR. Select transformed E.coli cells "
				+ "on LB + Kanaymcin or transformed yeast cells on SD-Ura.");
		writeBlankRow(6);
		row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		cell = row.createCell(0, Cell.CELL_TYPE_STRING);
		cell.setCellValue("Length of final vector");
		row.createCell(1, Cell.CELL_TYPE_BLANK);
		cell = row.createCell(2, Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(protocol_.getLengthOfFinalVector());
		writeBlankRow(2);
	}

	private void writeBlankRow(int numberOfCells) {
		Row row = sheet_.createRow(rowCounter_.getValueAndIncrement());
		for (int i = 0; i < numberOfCells; i++) {
			row.createCell(i + 1, Cell.CELL_TYPE_BLANK);
		}
	}

	private void copyJ5GenbankFileToResultFolder() throws AssemblXException {
		String j5ResultGenbankFilePath = model_.getWorkingDirectory() + File.separator
				+ currentTuUnit_.getTuDirectoryName() + File.separator + currentTuUnit_.getResultDirectoryName()
				+ File.separator + currentTuUnit_.getResultGenbankFileName();
		List<String> content = new ArrayList<String>();
		BufferedReader reader = null;
		Pattern headerPattern = Pattern.compile("(LOCUS|ACCESSION|VERSION)( *)(\\S*)( *)");

		try {
			reader = new BufferedReader(new FileReader(j5ResultGenbankFilePath));
			String line = "";
			while ((line = reader.readLine()) != null) {
				Matcher headerMatcher = headerPattern.matcher(line);
				if (headerMatcher.find()) {
					String oldPlasmidName = headerMatcher.group(3);
					line = line.replace(oldPlasmidName, currentTuUnit_.getPlasmidName());
				}
				content.add(line);
			}
			reader.close();
		} catch (IOException ioe) {
			String message = "Error during copying result file to result directory: " + ioe.getMessage();
			throw new AssemblXException(message, this.getClass());
		}

		String finalResultGenbankFilePath = model_.getWorkingDirectory() + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("resultDirectory") + File.separator
				+ currentTuUnit_.getPlasmidName() + ".gb";

		Path path = Paths.get(finalResultGenbankFilePath);
		try {
			Files.write(path, content, StandardCharsets.UTF_8);
		} catch (IOException ioe) {
			String message = "Error during copying result file to result directory: " + ioe.getMessage();
			throw new AssemblXException(message, this.getClass());
		}
	}

	private void complementMasterPlasmidList() throws Exception {
		if (model_.getMasterPlasmidMap() == null) {
			model_.setMasterPlasmidMap(new HashMap<String, String>());
		}
		String resultDirectoryPath = model_.getWorkingDirectory() + File.separator + currentTuUnit_.getTuDirectoryName()
				+ File.separator + currentTuUnit_.getResultDirectoryName();
		String masterPlasmidListPathCurrentResult = resultDirectoryPath + File.separator
				+ AssemblXWebProperties.getInstance().getProperty("masterPlasmidsListFileName");
		List<String> masterPlasmidList = J5ResultAnalyzer.parseMasterPlasmidFile(masterPlasmidListPathCurrentResult);
		int indexOfLastElement = masterPlasmidList.size() - 1;
		model_.getMasterPlasmidMap().put(currentTuUnit_.getIdentifier(), masterPlasmidList.get(indexOfLastElement));
	}

	private Sheet sheet_;
	private TranscriptionalUnit currentTuUnit_;
	private AssemblXWebModel model_;
	private Level_0_Protocol protocol_;
	private Counter rowCounter_;
}
