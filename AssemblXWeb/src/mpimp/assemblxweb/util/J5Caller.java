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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import mpimp.assemblxweb.db.AssemblXWebConstants;
import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.TranscriptionalUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class J5Caller {

	@SuppressWarnings("unchecked")
	public static void callGetSessionId(AssemblXWebModel model) throws Exception {
		Object result = null;
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("https://j5.jbei.org/bin/j5_xml_rpc.pl"));
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("username", model.getOperator().getLogin());
			paramsMap.put("password", model.getOperator().getPassword());
			Object[] params = new Object[] { paramsMap };
			result = client.execute("CreateNewSessionId", params);
			if (result instanceof HashMap) {
				if (((HashMap<String, String>) result)
						.containsKey("j5_session_id"))
					model.setJ5SessionId(((Map<String, String>) result)
							.get("j5_session_id"));
			}
		} catch (Exception e) {
			String message = "Could not get a J5 session id. Reason: " + e.getMessage()
			+ ". You may also check if j5 (https://j5.jbei.org/) is available at all.";
			throw new AssemblXException(message, J5Caller.class);
		}
	}

	public static void callDesignAssemblyScript(AssemblXWebModel model,
			TranscriptionalUnit currentTranscriptionalUnit) throws Exception {
		Object result = null;
		currentTranscriptionalUnit.setJ5ErrorMessage("");
		currentTranscriptionalUnit.setJ5FaultString("");
		try {
			String inputFileDirectory = model.getWorkingDirectory()
					+ File.separator
					+ currentTranscriptionalUnit.getTuDirectoryName()
					+ File.separator
					+ AssemblXWebProperties.getInstance().getProperty(
							"parameterFileDirectory");
			String masterFilesDirectory = model.getPathToMasterFilesDirectory();

			HashMap<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("j5_session_id", model.getJ5SessionId());
			parameterMap.put("reuse_j5_parameters_file", "FALSE");
			String j5ParametersFileName = inputFileDirectory + File.separator
					+ currentTranscriptionalUnit.getJ5ParametersFileName();
			parameterMap.put("encoded_j5_parameters_file",
					J5FileUtils.encodeFileBase64(j5ParametersFileName));
			parameterMap.put("reuse_sequences_list_file", "FALSE");
			String sequencesListFileName = inputFileDirectory + File.separator
					+ currentTranscriptionalUnit.getSequencesListFileName();
			parameterMap.put("encoded_sequences_list_file",
					J5FileUtils.encodeFileBase64(sequencesListFileName));
			parameterMap.put("reuse_zipped_sequences_file", "FALSE");
			String zippedSequencesFileName = inputFileDirectory
					+ File.separator
					+ currentTranscriptionalUnit.getZippedSequencesFileName();
			parameterMap.put("encoded_zipped_sequences_file",
					J5FileUtils.encodeFileBase64(zippedSequencesFileName));
			parameterMap.put("reuse_parts_list_file", "FALSE");
			String partsListFileName = inputFileDirectory + File.separator
					+ currentTranscriptionalUnit.getPartsListFileName();
			parameterMap.put("encoded_parts_list_file",
					J5FileUtils.encodeFileBase64(partsListFileName));
			parameterMap.put("reuse_target_part_order_list_file", "FALSE");
			String targetPartsOrderListFileName = inputFileDirectory
					+ File.separator
					+ currentTranscriptionalUnit
							.getTargetPartOrderListFileName();
			parameterMap.put("encoded_target_part_order_list_file",
					J5FileUtils.encodeFileBase64(targetPartsOrderListFileName));
			parameterMap.put("reuse_eugene_rules_list_file", "FALSE");
			String eugeneRulesListFileName = inputFileDirectory
					+ File.separator
					+ currentTranscriptionalUnit.getEugeneRulesListFileName();
			parameterMap.put("encoded_eugene_rules_list_file",
					J5FileUtils.encodeFileBase64(eugeneRulesListFileName));
			parameterMap.put("reuse_master_plasmids_file", "FALSE");
			String masterPlasmidsFilePath = masterFilesDirectory
					+ File.separator
					+ AssemblXWebProperties.getInstance().getProperty(
							"masterPlasmidsListFileName");
			parameterMap.put("encoded_master_plasmids_file ",
					J5FileUtils.encodeFileBase64(masterPlasmidsFilePath));
			parameterMap.put(
					"master_plasmids_list_filename",
					AssemblXWebProperties.getInstance().getProperty(
							"masterPlasmidsListFileName"));
			if (currentTranscriptionalUnit.getIsMockAssembly() == false) {
				parameterMap.put("reuse_master_oligos_file", "FALSE");
				String masterOligosListFilePath = masterFilesDirectory
						+ File.separator
						+ AssemblXWebProperties.getInstance().getProperty(
								"masterOligosListFileName");
				parameterMap.put("encoded_master_oligos_file",
						J5FileUtils.encodeFileBase64(masterOligosListFilePath));
				parameterMap.put(
						"master_oligos_list_filename",
						AssemblXWebProperties.getInstance().getProperty(
								"masterOligosListFileName"));
				parameterMap.put("reuse_master_direct_syntheses_file", "FALSE");
				String masterDirectSynthesisListFilePath = masterFilesDirectory
						+ File.separator
						+ AssemblXWebProperties.getInstance().getProperty(
								"masterDirectSynthesisListFileName");
				parameterMap
						.put("encoded_master_direct_syntheses_file",
								J5FileUtils
										.encodeFileBase64(masterDirectSynthesisListFilePath));
				parameterMap.put(
						"master_direct_syntheses_list_filename",
						AssemblXWebProperties.getInstance().getProperty(
								"masterDirectSynthesisListFileName"));
			}
			parameterMap.put("assembly_method", AssemblXWebConstants
					.getAssemblyMethod(currentTranscriptionalUnit
							.getAssemblyMethod()));

			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

			config.setServerURL(new URL("https://j5.jbei.org/bin/j5_xml_rpc.pl"));

			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);

			Object[] params = new Object[] { parameterMap };
			result = client.execute("DesignAssembly", params);

			if (result instanceof HashMap) {
				@SuppressWarnings("unchecked")
				String faultString = ((Map<String, String>) result)
						.get("faultString");
				if (faultString != null) {
					currentTranscriptionalUnit.setJ5FaultString(faultString);
				}
				@SuppressWarnings("unchecked")
				String errorMessage = ((Map<String, String>) result)
						.get("error_message");
				if (errorMessage != null) {
					currentTranscriptionalUnit.setJ5ErrorMessage(errorMessage);
				}
				@SuppressWarnings("unchecked")
				String outputFileName = ((Map<String, String>) result)
						.get("output_filename");
				String outputFilePath = model.getWorkingDirectory()
						+ File.separator
						+ currentTranscriptionalUnit.getTuDirectoryName()
						+ File.separator + outputFileName;
				Path path = Paths.get(outputFilePath);
				@SuppressWarnings("unchecked")
				byte[] outputFile = Base64
						.decodeBase64(((Map<String, String>) result)
								.get("encoded_output_file"));
				Files.write(path, outputFile);
				String resultDirectoryName = outputFileName.replace(".zip", "");
				currentTranscriptionalUnit
						.setResultDirectoryName(resultDirectoryName);
			}
		} catch (Exception e) {
			String message = "Generating of design assembly by J5 failed. Reason: " + e.getMessage()
			+ ". You may also check if j5 (https://j5.jbei.org/) is available at all.";
			throw new AssemblXException(message, J5Caller.class);
		}
	}

}
