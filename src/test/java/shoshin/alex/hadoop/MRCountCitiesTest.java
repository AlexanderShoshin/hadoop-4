package shoshin.alex.hadoop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.*;
import org.junit.Before;
import org.junit.Test;
import shoshin.alex.data.MapErrors;

/**
 *
 * @author Alexander_Shoshin
 */
public class MRCountCitiesTest {
    private static final String CITIES_FILE = "cities.testData";
    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    private ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    private String[] dataSet;
    
    @Before
    public void setUp() throws URISyntaxException, FileNotFoundException {
        Mapper mapper = new FilterCitiesMapper();
        Reducer reducer = new CountCitiesReducer();
        
        fillCitiesFile();
        fillDataSet();
        
        mapDriver = new MapDriver<>();
        mapDriver.addCacheFile(CITIES_FILE);
        mapDriver.setMapper(mapper);
        reduceDriver = new ReduceDriver<>();
        reduceDriver.setReducer(reducer);
    }
    
    private void fillCitiesFile() throws FileNotFoundException {
        try(PrintWriter out = new PrintWriter(CITIES_FILE)) {
            out.println("1 city1");
        }
    }
    
    private void fillDataSet() {
        dataSet = new String[] {"709b7cffadaefd399738439386ece34d	20131020104600653	1	CAOI72FHgYj	Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)	123.88.187.*	216	1	2	abc5a8aaa90bafdd7d6b60683e6f7ed3	a6e53cda7ceff62ea2dac7f281446fa5	null	4254532549	300	250	FirstView	Na	5	7323	277	84	null	2259	11278,13800,10684,13042,10006,10110,10123,13776,10031,10063",
                                "5d60c1165af43fb3851718efa27464be	20131020113001347	1	D63DYg3wGfe	Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Maxthon/4.0.6.2000 Chrome/26.0.1410.43 Safari/537.1	219.136.2.*	216	2	2	736b664a908f401bfad1fd2f859c8636	d6dfe5dfa85fe27d85a12ee732efae16	null	3200272529	200	200	OtherView	Na	23	7319	250	46	null	2259	14273,10076,10075,10102,10024,10006,11423,10111,10146,13403,10115,10063,11092",
                                "corrupted data"};
    }
   
    @Test
    public void mapper_should_join_city_name_by_id() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text(dataSet[0]));
        mapDriver.withOutput(new Text("city1"), new IntWritable(1));
        mapDriver.runTest();
    }
    
    @Test
    public void mapper_count_corrupted_input() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text(dataSet[2]));
        mapDriver.withCounter(MapErrors.WRONG_LOG_FORMAT, 1);
        mapDriver.runTest();
    }
    
    @Test
    public void mapper_should_filter_low_price_records() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text(dataSet[0]));
        mapDriver.withInput(new LongWritable(1), new Text(dataSet[1]));
        mapDriver.withOutput(new Text("city1"), new IntWritable(1));
        mapDriver.runTest();
    }
    
    @Test
    public void reducer_should_summarize_cities_count() throws IOException {
        List<IntWritable> citiesCount = new ArrayList<>();
        citiesCount.add(new IntWritable(1));
        citiesCount.add(new IntWritable(3));
        reduceDriver.withInput(new Text("city"), citiesCount);
        reduceDriver.withOutput(new Text("city"), new IntWritable(4));
        reduceDriver.runTest();
    }
}