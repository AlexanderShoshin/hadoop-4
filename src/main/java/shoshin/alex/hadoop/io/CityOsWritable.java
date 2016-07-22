package shoshin.alex.hadoop.io;

import com.google.common.collect.ComparisonChain;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 22.07.2016.
 */
public class CityOsWritable implements WritableComparable<CityOsWritable> {
    private String city;
    private String os;

    public CityOsWritable() {
        this("", "");
    }

    public CityOsWritable(String city, String os) {
        this.city = city;
        this.os = os;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(city);
        dataOutput.writeUTF(os);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        city = dataInput.readUTF();
        os = dataInput.readUTF();
    }

    public String getCity() {
        return city;
    }

    public String getOs() {
        return os;
    }

    @Override
    public String toString() {
        return String.format("%1$s: %2$s", city, os);
    }

    @Override
    public boolean equals(Object obj) {
        CityOsWritable comparable = (CityOsWritable) obj;
        return city.equals(comparable.city) && os.equals(comparable.os);
    }

    @Override
    public int compareTo(CityOsWritable o) {
        return ComparisonChain.start().compare(city, o.city).compare(os, o.os).result();
    }
}
