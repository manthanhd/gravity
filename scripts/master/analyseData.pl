#!/usr/bin/perl
use Getopt::Long;

my $inputFile = '';
my $hadoopJar = '';
my $outputPath = '';
my $displayHelp = '';
my $hadoopExecutableClass = '';
my $mode = '';
my $mapper = '';
my $reducer = '';
my $clean = undef;
my $initHadoop = undef;
my $package = undef;
my $tokenid = undef;
my $statDir = undef;
my $jdkChangeScript = "/home/hduser/changejdks.pl";
my $targetJDKPath = undef;
my $oldJDK = undef;
my $prepTime = undef;
my $hdfsInTime = undef;
my $hdfsOutTime = undef;
my $mapreduceTime = undef;
my $cleanupTime = undef;
GetOptions (  "dataset=s" 				=> \$inputFile,
              "outputDir=s"  		  => \$outputPath,
              "mode=s"  					=> \$mode,
              "hadoopJar=s"  			=> \$hadoopJar,
						  "hadoopClass=s" 		=> \$hadoopExecutableClass,
						  "help"							=> \$displayHelp,
              "mapper=s" 					=> \$mapper,
              "reducer=s" 				=> \$reducer,
						  "clean"							=> \$clean,
						  "initHadoop"				=> \$initHadoop,
							"package"						=> \$package,
							"tokenid=s"					=> \$tokenid,
							"statdir=s"					=> \$statDir,
						  "targetJDK=s"       => \$targetJDKPath
		    	);
my $prepStart = time();
if($displayHelp){
	display('-dataset	<Path to the file containing data to be processed>');
	display('-outputDir <Path to the directory where you want the output to be>');
	display('-hadoopJar <Hadoop jar to be executed>');
	display('-hadoopClass <Executable class in the jar file>');
	display('-help 		<Display this help message>');
	die("Help displayed.");
}
if(-f $inputFile){
	display("Existence of input file... CHECKED.");
} else {
	die("Failed to find the input file.");
}
if(-d $outputPath){
	display("Existence of output path... CHECKED.");
} else {
	display("Failed to find the output path. Creating it.");
	mkdir($outputPath);
}
if($mode eq 'native'){
	if(-f $hadoopJar){
		display("Existence of hadoop jar... CHECKED.");
	} else {
		die("Failed to find the hadoop jar.");
	}
} elsif ($mode eq 'stream'){
	if(-f $mapper){
		display("Existence of hadoop mapper file... CHECKED.");
	} else {
		die("Failed to find mapper file.");
	}
	if(-f $reducer){
		display("Existence of hadoop reducer file... CHECKED.");
	} else {
		die("Failed to find reducer file.");
	}
}

if($targetJDKPath){
	display("Determining current JDK...");
	my $com = '/home/hduser/getCurrentJDK.pl';
	$oldJDK = `$com`;
	chomp($oldJDK);
	chomp($targetJDKPath);
	if($oldJDK ne $targetJDKPath){
		display("Target JDK has been specified and is not equal to current JDK. Location is $targetJDKPath. Requesting a cluster wide JDK change.");
		$com = "$jdkChangeScript -all -jdk $targetJDKPath";
		my $rc = system($com);
		if($rc == 0){
			display("Cluster wide JDK change has been made. All JDKs are now $targetJDKPath");
		} else {
			die("Failed to make a cluster wide JDK change. Cannot proceed as jar execution might fail.");
		}
	} else {
		display("Target JDK ($targetJDKPath) is same as current JDK ($oldJDK). No change is required.");
	}
} else {
	display("JDK Target has not been set. Will go with the current JDK.");
}

if($tokenid){
	report($tokenid, 20, "initHadoop");
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

if($initHadoop){
	# Startup hadoop
	display("Starting up hadoop...");
	$command = $ENV{'HADOOP_HOME'} . '/bin/start-all.sh';
	$rc = system($command);
	if($rc != 0){
		die('Failed to startup hadoop cluster.');
	}

	if($clean){
		display('Preparing to format namenode.');

		# Stop dfs
		$command = $ENV{'HADOOP_HOME'} . '/bin/stop-dfs.sh';
		$rc = system($command);
		if($rc != 0){
			die('Failed to stop dfs.');
		}

		display('Formatting namenode.');
		$command = $ENV{'HADOOP_HOME'} . '/bin/hadoop namenode -format';
		$rc = system($command);
		if($rc != 0){
			die('Failed to format namenode.');
		}

		# Start dfs
		$command = $ENV{'HADOOP_HOME'} . '/bin/start-dfs.sh';
		$rc = system($command);
		if($rc != 0){
			die('Failed to start dfs.');
		}
	}
	display('Hadoop has been started.');
	display('Patiently waiting for 30 seconds for hadoop to settle itself.');
	sleep(30);
}
my $prepEnd = time();
$prepTime = $prepEnd - $prepStart;
my $hdfsInStart = time();
if($tokenid){
	report($tokenid, 30, "hdfs");
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
if($statDir == undef){
	if(-d "/home/hduser/tmp-store/$tokenid.stats"){
		system("mv /home/hduser/tmp-store/$tokenid.stats /home/hduser/tmp-store/$tokenid.stats.old");
	}
} else {
	if(-d $statDir){
		system("mv $statDir $statDir.old");
		if($? != 0){
			die("Failed to execute previous command. Failed to move $statDir despite it\'s existence");
		}
	}
}
system('/home/hduser/Resmon.pl -start-all');
my $hdfsInEnd = time();
$hdfsInTime = $hdfsInEnd - $hdfsInStart;

my $mapreduceStart = time();

if($tokenid){
	report($tokenid, 50, "mapreduce");
}

# Run the hadoop jar file.
display('Running Hadoop MapReduce.');
if($mode eq 'native'){
	$command = "$hadoop jar $hadoopJar $hadoopExecutableClass $inputHDFSDir $outputHDFSDir";
} elsif ($mode eq 'stream'){
	my $streamingJar = $ENV{'HADOOP_HOME'} . '/contrib/streaming/hadoop-streaming-1.2.1.jar';
  $command = "$hadoop jar $streamingJar -mapper $mapper -reducer $reducer -input $inputHDFSDir -output $outputHDFSDir";
}
display("Executing:\n $command \n");
system($command);
 if($? != 0){
 	display('Failed to execute previous command. Something is wrong. Please check error log above.');
 }
display("Successfully completed MapReduce. Not checking if it failed or not.");
if($tokenid){
	report($tokenid, 70, "retrieve");
}
my $mapreduceEnd = time();
my $mapreduceTime = $mapreduceEnd - $mapreduceStart;

my $hdfsOutStart = time();

# Stop the resource monitor
# display("Value of statdir is $statDir.");
if($statDir != undef){
	display("Asking resource monitor to stop and retrieve all stats to $statDir directory.");
	system('/home/hduser/Resmon.pl -stop-all -statCollectionDirectory ' . $statDir) or die("Failed to get stats");
} else {
	display("Asking resource monitor to stop and retrieve all stats to /home/hduser/tmp-store/$tokenid.stats directory.");
	system('/home/hduser/Resmon.pl -stop-all -statCollectionDirectory ' . "/home/hduser/tmp-store/$tokenid.stats");
	if(-d "/home/hduser/tmp-store/$tokenid.stats"){
		system("mv /home/hduser/tmp-store/$tokenid.stats $outputPath.stats");
		if($? != 0){
			die("Failed to move /home/hduser/tmp-store/$tokenid.stats to $outputPath.stats");
		}
	} else {
		die("Resource monitor has failed to retrieve files from bots across the cluster. /home/hduser/tmp-store/$tokenid.stats directory does not exist.");
	}
}

# Retrieve output from HDFS.
display('Retrieving output from HDFS.');
# Delete backup if it's there.
if(-d "$outputPath.old"){
	system("rm -rf $outputPath.old");
}
# Create a new backup.
if(-d $outputPath){
	system("mv $outputPath $outputPath.old");
}
mkdir($outputPath);

# Pull the files out of hdfs.
$command = "$hadoop dfs -copyToLocal $outputHDFSDir $outputPath";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong. OutputPath is ' . $outputPath);
}
display("Successfully retrieved output to $outputPath.");

# Now that we have output copied out to $outputPath, we can package it is package flag is set.
if($package){
	$command = "tar -czf $outputPath.tar.gz $outputPath";
	display("Executing:\n $command \n");
	system($command);
	if($? != 0){
		die("Failed to package output.");
	} 
	display("Successfully packaged output as $outputPath.tar.gz.\nRemoving output directory...");
	$command = "rm -rf $outputPath";
	display("Executing:\n $command \n");
	system($command);
	if($? != 0){
		die("Failed to remove directory after packaging.");
	}
	display("Successfully removed directory $outputPath after packaging.");
}
my $hdfsOutEnd = time();
$hdfsOutTime = $hdfsOutEnd- $hdfsOutStart;

my $cleanupStart = time();
# Cleaning up...
display('Cleaning up...');
$command = "$hadoop dfs -rmr $inputHDFSDir $outputHDFSDir";
display("Executing:\n $command \n");
system($command);
if($? != 0){
	die('Failed to execute previous command. Something is wrong.');
}
display("Successfully cleaned up.");

if($targetJDKPath){
	# If we have reached this point, we probably were successful at setting jdk last time. 
	# Need to find a way to determine old JDK path and need to reset it back.
	if($oldJDK && $oldJDK != $targetJDKPath){
		display("Old JDK was determined earlier and it was changed. Restoring it.");
		my $com = "$jdkChangeScript -all -jdk $oldJDK";
		my $rc = system($com);
		if($rc == 0){
			display("JDKs have been restored cluster wide.");
		} else {
			die("Failed to restore JDK to $oldJDK.");
		}
	} else {
		display("Not touching JDKs.");
	}
} else {
	display("No need to touch any JDKs.");
}

if($initHadoop){
	# Stop Hadoop
	display('Stopping hadoop...');
	$command = $ENV{'HADOOP_HOME'} . '/bin/stop-all.sh';
	$rc = system($command);
	if($rc != 0){
		die('Failed to stop hadoop.');
	}
	display('Successfully stopped hadoop.');
}
display("Total execution time: $duration seconds.");
display("All done!");

my $cleanupEnd = time();
$cleanupTime = $cleanupEnd - $cleanupStart;
# Commit all times to database
display("Reporting process execution times.");
reportTime($tokenid, $prepTime, $hdfsInTime, $mapreduceTime, $hdfsOutTime, $cleanupTime);
display("Reporting done.");
sub display {
	my $str = shift;
	print $str . "\n";
}

sub remoteExec {
	my $username = shift;
	my $machine = shift;
	my $command = shift;
	
	my $remoteCommand = "ssh -f $username\@$machine \"$command\"";
	
	display('Will execute: ' . $remoteCommand . " on machine $machine.");
	my $rc = system($remoteCommand);
	return $rc;
}

sub report {
	my $tokenid = shift;
	my $progress = shift;
	my $message = shift;
	my $command = "php /home/hduser/reportProgress.php -t $tokenid -p $progress -m \"$message\";";
	my $rc = system($command);
	return $rc;
}

sub reportTime {
	my $tokenid = shift;
	my $prepTime = shift;
	my $hdfsInTime = shift;
	my $mapreduceTime = shift;
	my $hdfsOutTime = shift;
	my $cleanupTime = shift;
  my $command	= "php /home/hduser/reportTime.php -t $tokenid -p $prepTime -i $hdfsInTime -m $mapreduceTime -o $hdfsOutTime -c $cleanupTime;";
	display("About to run " . $command);
	my $rc = system($command);
	return $rc;
}

