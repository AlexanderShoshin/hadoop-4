## Testing environment

Programm was tested on HDP 2.4 sandbox.

## How to deploy

1. Make hadoop-4-1.0-SNAPSHOT.jar by running maven command:
```
mvn clean package
```
2. Copy hadoop-4-1.0-SNAPSHOT.jar to machine with hadoop installed.
3. Download dataset http://goo.gl/lwgoxw and put files imp.20131019.txt.bz2 - imp.20131027.txt.bz2, city.en.txt to hdfs.
4. Run MapReduce job, passing hdfs path to dataset, hdfs path to output directory and hdfs path to city.en.txt:
```
hadoop jar <path_to_jar>/hadoop-4-1.0-SNAPSHOT.jar <hdfs_path_to_dataset_directory> <hdfs_path_to_output_directory> <hdfs_path_to_txt_file>/city.en.txt
```