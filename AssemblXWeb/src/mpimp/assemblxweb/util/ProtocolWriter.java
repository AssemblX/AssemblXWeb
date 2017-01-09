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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.TranscriptionalUnit;

public class ProtocolWriter {

	public ProtocolWriter(AssemblXWebModel model) {
		model_ = model;
		init();
	}

	private void init() {
		workbook_ = new XSSFWorkbook();
		level0Sheet_ = workbook_.createSheet("level_0");
		level1Sheet_ = workbook_.createSheet("level_1");
		level2Sheet_ = workbook_.createSheet("level_2");
		level_0_sheetRowCounter_ = new Counter(0);
	}

	public void prepareLevel_0_Protocol() {
		Level_0_Protocol_Writer level_0_protocolWriter = new Level_0_Protocol_Writer(level0Sheet_, level_0_sheetRowCounter_);
		level_0_protocolWriter.writeOverallHeader();
	}
	
	public void appendLevel_0_Section(TranscriptionalUnit currentTuUnit) throws Exception {
		Level_0_Protocol_Writer level_0_protocolWriter = new Level_0_Protocol_Writer(
				currentTuUnit, model_, level0Sheet_, level_0_sheetRowCounter_);
		level_0_protocolWriter.writeLevel_0_ProtocolSection();
	}

	public void appendLevel_1_Sheet() throws IOException {
		Level_1_ProtocolWriter level_1_protocolWriter = new Level_1_ProtocolWriter(
				model_, level1Sheet_);
		level_1_protocolWriter.writeLevel_1_ProtocolSheet();
	}

	public void appendLevel_2_Sheet() throws IOException {
		Level_2_ProtocolWriter level_2_protocolWriter = new Level_2_ProtocolWriter(
				model_, level2Sheet_);
		level_2_protocolWriter.writeLevel_2_Protocol();
	}

	public void writeProtocolToFile() throws IOException {
		String resultFileName = model_.getOperator().getLogin()
				+ "_AssemblyProtocols.xlsx";
		String resultDirectoryName = AssemblXWebProperties.getInstance()
				.getProperty("resultDirectory");
		String protocolFilePath = model_.getWorkingDirectory() + File.separator
				+ resultDirectoryName + File.separator + resultFileName;
		OutputStream outputStream = FileUtils.openOutputStream(new File(
				protocolFilePath));
		workbook_.write(outputStream);
	}

	private Sheet level0Sheet_;
	private Sheet level1Sheet_;
	private Sheet level2Sheet_;
	private XSSFWorkbook workbook_;
	private Counter level_0_sheetRowCounter_;
	private AssemblXWebModel model_;
}
