#!/usr/bin/perl
use Getopt::Long;

my $inputFile = '';
my $hadoopJar = '';
my $outputPath = '';
my $displayHelp = '';
my $hadoopExecutableClass = '';
GetOptions (  "dataset=s" => \$inputFile,
              "outputDir=s"   => \$outputPath,
              "hadoopJar=s"  => \$hadoopJar,
			  "hadoopClass=s" => \$hadoopExecutableClass,
			  "help"			=> \$displayHelp
		    );
if($displayHelp){
	display('-dataset	<Path to the file containing data to be processed>');
	display('-outputDir <Path to the directory where you want the output to be>');
	display('-hadoopJar <Hadoop jar to be executed>');
	display('-hadoopClass <Executable class in the jar file>');
	display('-help 		<Display this help message>');
	die("Help displayed.");
}

if(-f $inputFile){
	display("Existence of input file... CHECKED.\n");
} else {
	die("Failed to find the input file.");
}
if(-d $outputPath){
	display("Existence of output path... CHECKED.\n");
} else {
	die("Failed to find the output path.");
}
if(-f $hadoopJar){
	display("Existence of hadoop jar... CHECKED.\n");
} else {
	die("Failed to find the hadoop jar.");
}
my $hadoop = $ENV{'HADOOP_HOME'} . '/bin/hadoop';
my $command = "";	# Used by subsequent code.
my $inputHDFSDir = '/user/hadoop/data';
my $outputHDFSDir = '/user/hadoop/out';

if(-f $hadoop){
	display("Hadoop executable is located in " . $hadoop);
} else {
	die('Cannot find hadoop executable. Make sure $HADOOP_HOME environment variable is set to base of hadoop directory.');
}


# Create working directory in hdfs first
display('Creating working directory in HDFS.');
$command = "$hadoop dfs -mkdir $inputHDFSDir";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Created directory $inputHDFSDir in HDFS.");


# Put the input into the directory
$command = "$hadoop dfs -copyFromLocal $inputFile $inputHDFSDir";
display('Copying the input dataset to HDFS.');
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Successfully copied $inputFile to $inputHDFSDir on HDFS.");

# Start resource monitor to monitor resources across cluster
system('mv stat/ stat.old');
system('./Resmon.pl -start-all');
my $start = time;

# Run the hadoop jar file.
display('Running Hadoop MapReduce.');
$command = "$hadoop jar $hadoopJar $hadoopExecutableClass $inputHDFSDir $outputHDFSDir";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Successfully completed MapReduce.");

# Stop the resource monitor
my $duration = time - $start;
system('./Resmon.pl -stop-all');

# Retrieve output from HDFS.
display('Retrieving output from HDFS.');
$command = "$hadoop dfs -copyToLocal $outputHDFSDir $outputPath";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Successfully retrieved output to $outputPath.");

# Cleaning up...
display('Cleaning up...');
$command = "$hadoop dfs -rmr $inputHDFSDir $outputHDFSDir";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Successfully cleaned up.");

display("Total execution time: $duration seconds.");
display("All done!");


sub display {
	my $str = shift;
	print $str . "\n";
}
