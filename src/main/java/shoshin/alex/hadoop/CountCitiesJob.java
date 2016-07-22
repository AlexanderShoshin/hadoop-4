package shoshin.alex.hadoop;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import shoshin.alex.hadoop.io.CityOsWritable;

public class CountCitiesJob extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new CountCitiesJob(), args);
        System.exit(res);
    }
    
    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf());
        job.addCacheFile(new Path(args[2]).toUri());
        job.setJobName("highBidPricedCities");
        job.setJarByClass(CountCitiesJob.class);
        job.setOutputKeyClass(CityOsWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(FilterCitiesMapper.class);
        job.setReducerClass(CountCitiesReducer.class);
        job.setCombinerClass(CountCitiesCombiner.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setPartitionerClass(OSPartitioner.class);
        job.setNumReduceTasks(2);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        return job.waitForCompletion(true) ? 0 : 1;
    }
}