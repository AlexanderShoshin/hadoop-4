package shoshin.alex.hadoop.io;

import com.google.common.collect.ComparisonChain;
import org.apache.hadoop.io.WritableComparable;
import shoshin.alex.data.OS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 22.07.2016.
 */
public class CityOsWritable implements WritableComparable<CityOsWritable> {
    private String city;
    private OS os;

    public CityOsWritable() {
        this("", OS.WINDOWS);
    }

    public CityOsWritable(String city, OS os) {
        this.city = city;
        this.os = os;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(city);
        dataOutput.writeUTF(os.name());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        city = dataInput.readUTF();
        os = OS.valueOf(dataInput.readUTF());
    }

    public String getCity() {
        return city;
    }

    public OS getOs() {
        return os;
    }

    @Override
    public boolean equals(Object obj) {
        CityOsWritable comparable = (CityOsWritable) obj;
        return city.equals(comparable.city) && os == comparable.os;
    }

    @Override
    public int compareTo(CityOsWritable o) {
        return ComparisonChain.start().compare(city, o.city).compare(os, o.os).result();
    }
}
