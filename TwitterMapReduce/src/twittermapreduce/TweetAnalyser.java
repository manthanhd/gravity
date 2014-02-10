/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twittermapreduce;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author manthanhd
 */
public class TweetAnalyser {
    
    public static class TweetAnalysisMapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable();
        private Text word = new Text();
        private Gson gson = new Gson();
        
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            SocialDatum datum = gson.fromJson(value.toString(), SocialDatum.class);
            String[] words = datum.data.split(" ");
            for(String str : words){
                word.set(str);
                context.write(word, one);
            }
            Thread.sleep(2000);
        }
    }
    
    public static class TweetAnalysisReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int total = 0;
            for(IntWritable val : values){
                total++;
            }
            Thread.sleep(2000);
            context.write(key, new IntWritable(total));
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        Configuration conf = new Configuration();
        Job job = new Job(conf, "Tweet Word Counter");
        job.setJarByClass(TweetAnalyser.class);
        job.setMapperClass(TweetAnalysisMapper.class);
        job.setReducerClass(TweetAnalysisReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
}
