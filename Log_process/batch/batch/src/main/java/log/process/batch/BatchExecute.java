package log.process.batch;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BatchExecute {

	public static void main(String[] args) {
		try {
			Boolean delete = Boolean.valueOf(args[2]);
			run(args[0], args[1], delete);
		} catch (IllegalArgumentException | ClassNotFoundException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Execution of batch application
	 * 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void run(String input, String out, Boolean del)
			throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		// Get configuration
		Configuration conf = new Configuration();
		
		// Recursively search input directory
		conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);

		// Create job and set mapper, reducer classes
		Job job = Job.getInstance(conf, "Preprocessing batch job");
		job.setJarByClass(PreProcessMap.class);
		job.setMapperClass(PreProcessMap.class);
		job.setCombinerClass(PreProcessReduce.class);
		job.setReducerClass(PreProcessReduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Handle input and output path, delete output if already exists
		FileInputFormat.addInputPath(job, new Path(input));
		FileSystem hdfs = FileSystem.get(conf);
		Path output = new Path(out);
		if (hdfs.exists(output)) {
			hdfs.delete(output, true);
		}
		FileOutputFormat.setOutputPath(job, output);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
