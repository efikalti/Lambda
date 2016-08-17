package logpro;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class LogProcess {
	public static void main(String[] args) {
		// Retrieve parsed command line arguments
		CommandLine line = setupOptions(args);

		if (line.hasOption("e")) {
			// Execute eleasticsearch application
		} else {
			// Execute hadoop application
			Process proc;
			try {
				proc = Runtime.getRuntime().exec("java -jar A.jar");
				// Then retreive the process output
				InputStream in = proc.getInputStream();
				InputStream err = proc.getErrorStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static CommandLine setupOptions(String[] args) {
		// Create the argument options
		Options options = new Options();

		options.addOption("hadoop", false, "Execute the hadoop log processing application. This is the default one");

		options.addOption("elastic", false, "Execute the elasticsearch log processing application");

		options.addOption("help", false, "Display usage message");

		Option input = Option.builder("input").desc("input file/directory").hasArg(true).argName("input").required()
				.build();
		options.addOption(input);

		Option output = Option.builder("output").desc("output file/directory").hasArg(true).argName("output").required()
				.build();
		options.addOption(output);

		// Create the argument parser
		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			// parse the command line arguments
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			// Something went wrong with parsing
			// TODO log information
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			System.exit(1);
		}

		if (line.hasOption("help")) {
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("LogProcess", options);
		}

		return line;
	}
}
