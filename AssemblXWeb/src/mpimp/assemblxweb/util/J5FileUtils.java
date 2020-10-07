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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.TuSubunit;

import org.apache.commons.codec.binary.Base64;

public class J5FileUtils {

	public static String encodeFileBase64(String filePath) throws Exception {
		Path path = Paths.get(filePath);
		String result = "";
		try {
			byte[] content = Files.readAllBytes(path);
			result = Base64.encodeBase64String(content);
		} catch (IOException e) {
			String message = "Error while encoding file " + filePath + ". " + e.getMessage();
			throw new AssemblXException(message, J5FileUtils.class);
		}
		return result;
	}

	public static void zipDirectory(String dirPath, String targetPath) throws Exception {
		BufferedInputStream origin = null;
		FileOutputStream dest = new FileOutputStream(targetPath);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		out.setMethod(ZipOutputStream.DEFLATED);
		int BUFFER = 2048;
		byte data[] = new byte[BUFFER];
		zipDirectoryRecursively(dirPath, origin, BUFFER, out, data, new File(dirPath).getName());
		out.close();

	}

	private static void zipDirectoryRecursively(String dirPath, BufferedInputStream origin, int BUFFER,
			ZipOutputStream out, byte[] data, String parentDirName) throws Exception {
		File directory = new File(dirPath);
		File content[] = directory.listFiles();

		for (int i = 0; i < content.length; i++) {
			if (content[i].isDirectory()) {
				String parentPath = parentDirName + File.separator + content[i].getName();
				zipDirectoryRecursively(content[i].getAbsolutePath(), origin, BUFFER, out, data, parentPath);
			} else {
				String filePathInDirectory = parentDirName + File.separator + content[i].getName();
				FileInputStream in = new FileInputStream(content[i].getAbsolutePath());
				origin = new BufferedInputStream(in, BUFFER);
				ZipEntry zipEntry = new ZipEntry(filePathInDirectory);
				out.putNextEntry(zipEntry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				in.close();
			}
		}
	}

	public static void deleteTuSubunitRelatedFiles(TuSubunit tuSubunit, AssemblXWebModel model) throws IOException {
		if (tuSubunit.getSequenceFileLoaded()) {
			// check first if the sequence file is not used by another subunit
			// as well
			List<TuSubunit> tuSubunits = tuSubunit.getParentTuUnit().getTuSubunits();
			int counter = 0;
			for (TuSubunit currentTuSubunit : tuSubunits) {
				if (currentTuSubunit.getSequenceFileName() != null
						&& currentTuSubunit.getSequenceFileName().equals(tuSubunit.getSequenceFileName())) {
					counter++;
				}
			}
			if (counter > 1) {
				return; // sequence file is used more than once, so we must
						// not delete it along with the subunit
			}
			String sequenceFilePathName = model.getWorkingDirectory() + File.separator
					+ model.getCurrentTuUnit().getTuDirectoryName() + File.separator
					+ AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory") + File.separator
					+ tuSubunit.getSequenceFileName();
			Files.deleteIfExists(Paths.get(sequenceFilePathName));
			String sequencesDirPath = model.getWorkingDirectory() + File.separator
					+ model.getCurrentTuUnit().getTuDirectoryName() + File.separator
					+ AssemblXWebProperties.getInstance().getProperty("sequenceFileDirectory");
			File sequencesDir = new File(sequencesDirPath);
			if (sequencesDir.isDirectory() && sequencesDir.list().length == 0) {
				Files.delete(Paths.get(sequencesDirPath));
			}
			String tuDirectoryPath = model.getWorkingDirectory() + File.separator
					+ model.getCurrentTuUnit().getTuDirectoryName();
			File tuDirectory = new File(tuDirectoryPath);
			if (tuDirectory.isDirectory() && tuDirectory.list().length == 0) {
				Files.delete(Paths.get(tuDirectoryPath));
			}
		}
	}

	public static void removeDirRecursively(String dirPath) throws Exception {
		File dir = new File(dirPath);
		File[] content = dir.listFiles();
		for (int i = 0; i < content.length; i++) {
			if (content[i].isDirectory()) {
				removeDirRecursively(content[i].getAbsolutePath());
			} else {
				if (!content[i].delete()) {
					String message = "Could not delete file '" + dir.getAbsolutePath() + "'.";
					throw new AssemblXException(message, J5FileUtils.class);
				}
			}
		}
		if (!dir.delete()) {
			String message = "Could not delete file '" + dir.getAbsolutePath() + "'.";
			throw new AssemblXException(message, J5FileUtils.class);
		}
	}

	public static String createDownloadTempDir(AssemblXWebModel model) throws Exception {
		String downloadTempDirName = AssemblXWebProperties.getInstance().getProperty("downloadTempDirBaseName")
				+ File.separator + model.getOperator().getLogin();
		String downloadTempDirPath = model.getServletContextRealPath() + downloadTempDirName;
		File downloadTempDir = new File(downloadTempDirPath);
		if (downloadTempDir.exists()) {
			J5FileUtils.removeDirRecursively(downloadTempDirPath);
		}
		if (!downloadTempDir.mkdirs()) {
			String message = "Error while creating temporary download directory " + downloadTempDirPath + ".";
			throw new AssemblXException(message, J5FileUtils.class);
		}
		return downloadTempDirPath;
	}

	public static String createDownloadUrl(AssemblXWebModel model, String filePath) throws Exception {
		String downloadUrl = "";
		URL url = null;
		String host = model.getHost();
		String protocol = model.getProtocol();
		int serverPort = model.getServerPort();

		if (!filePath.startsWith("/")) {
			filePath = "/" + filePath;
		}
		try {
			if (serverPort == 0) {
				url = new URL(protocol, host, filePath);
			} else {
				url = new URL(protocol, host, serverPort, filePath);
			}
		} catch (MalformedURLException me) {
			throw new AssemblXException(me.getMessage(), J5FileUtils.class);
		}
		if (url != null) {
			downloadUrl = url.toString();
		}
		return downloadUrl;
	}

	public static String removeForbiddenCharctersFromFileName(String rawFileName) {
		String cleanFileName = "";
		cleanFileName = rawFileName.replaceAll("\\s", "_");
		return cleanFileName;
	}
}
