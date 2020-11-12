package mpimp.assemblxweb.teststuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRunPerlScripts {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		String command = "C:\\Perl\\perl\\bin\\perl"
				+ " H:\\Programmierung_BioInf\\AssemblX\\j5-examples\\j5_xml_rpc_client_create_new_j5_session_id_withArguments.pl";
		// String command = "C:\\Perl\\bin\\perl H:\\PerlTest.pl";
		command += " Jgremmels";
		command += " kmZumGkmGuK";
		Process process = runtime.exec(command);
		process.waitFor(); // wait for process to finish then continue.

		BufferedReader bri = new BufferedReader(new InputStreamReader(
				process.getInputStream()));

		String line;
		String result = "";

		while ((line = bri.readLine()) != null) {
			result += line;
		}

		// System.out.println(result);

		Pattern pattern = Pattern.compile("(New j5 session id: )(.*)");
		Matcher matcher = pattern.matcher(result);

		String sessionId = "";

		while (matcher.find()) {
			sessionId = matcher.group(2);
		}

		String workingDirectory = "H:\\Programmierung_BioInf\\AssemblX\\WorkingDir";

		String command2 = "C:\\Perl\\perl\\bin\\perl"
				+ " H:\\Programmierung_BioInf\\AssemblX\\j5-examples\\j5_xml_rpc_client_get_last_updated_user_files_workingDir.pl";
		command2 += " ";
		command2 += sessionId;
		command2 += " ";
		command2 += workingDirectory;
		runtime = Runtime.getRuntime();
		process = runtime.exec(command2);
		process.waitFor();

		bri = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		result = "Result: \n";

		while ((line = bri.readLine()) != null) {
			result += line;
		}

		System.out.println(result);
	}

}
