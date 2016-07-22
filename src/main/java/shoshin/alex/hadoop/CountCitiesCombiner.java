package shoshin.alex.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import shoshin.alex.hadoop.io.CityOsWritable;

import java.io.IOException;

/**
 * Created by Administrator on 23.07.2016.
 */
public class CountCitiesCombiner extends Reducer<CityOsWritable, IntWritable, CityOsWritable, IntWritable> {
    @Override
    public void reduce(CityOsWritable key, Iterable<IntWritable> values, CountCitiesCombiner.Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}