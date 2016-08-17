package log.process.batch;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PreProcessMap extends Mapper<Object, Text, Text, Text> {

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		
		String request = value.toString();
		Random rand = new Random();

		
		if (request.contains("http"))
		{

			int n = rand.nextInt(50) + 1;
			context.write(new Text("" + n), value);
		}
	}

}
