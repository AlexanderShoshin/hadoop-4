package shoshin.alex.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;
import shoshin.alex.data.OS;
import shoshin.alex.hadoop.io.CityOsWritable;

public class OSPartitioner extends Partitioner<CityOsWritable, IntWritable>
{
    @Override
    public int getPartition(CityOsWritable key, IntWritable value, int numReduceTasks)
    {
        if (numReduceTasks == 0) {
            return 0;
        } else if (key.getOs() == OS.WINDOWS) {
            return 0;
        } else {
            return 1 % numReduceTasks;
        }
    }
}