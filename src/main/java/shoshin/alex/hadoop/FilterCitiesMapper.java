package shoshin.alex.hadoop;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import shoshin.alex.data.BiddingLog;
import shoshin.alex.data.MapErrors;
import shoshin.alex.hadoop.io.CityOsWritable;
import shoshin.alex.io.KeyValueReader;

/**
 *
 * @author Alexander_Shoshin
 */
public class FilterCitiesMapper extends Mapper<LongWritable, Text, CityOsWritable, IntWritable> {
    Map<String, String> cityNames;
    private static final int MIN_PRICE = 250;
    private static final IntWritable one = new IntWritable(1);
    
    @Override
    protected void setup(Mapper.Context context) throws IOException, InterruptedException {
        Path[] cacheFiles = context.getLocalCacheFiles();
        if (cacheFiles != null && cacheFiles.length > 0) {
            cityNames = KeyValueReader.read(new File(cacheFiles[0].toString()));
        }
    }
    
    @Override
    public void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        try {
            BiddingLog log = BiddingLog.parse(line);
            if (log.getBidPrice() > MIN_PRICE) {
                String cityName = cityNames.get(log.getCityId());
                if (cityName != null) {
                    context.write(new CityOsWritable(cityName, log.getOSFamily()), one);
                }
            }
        } catch (IllegalArgumentException exception) {
            context.getCounter(MapErrors.WRONG_LOG_FORMAT).increment(1);
            return;
        }
    }
}