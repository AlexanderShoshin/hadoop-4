package shoshin.alex.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import shoshin.alex.hadoop.io.CityOsWritable;

public class CountCitiesReducer extends Reducer<CityOsWritable, IntWritable, Text, IntWritable> {
    @Override
    public void reduce(CityOsWritable key, Iterable<IntWritable> values, CountCitiesReducer.Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        context.write(new Text(key.getCity()), new IntWritable(sum));
    }
}