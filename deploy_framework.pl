#!/usr/bin/perl
use File::Copy;
use File::Path qw(make_path remove_tree);
use File::Basename;
use File::Copy::Recursive qw(dircopy);

my $propertiesFileName = "deployment.properties";
if(scalar(@ARGV) != 0){
	my $propFile = shift;
	if(-f $propFile){
		# Provided file exists!
		$propertiesFileName = $propFile;
	} else {
		# Provided file does not exist!
		die("File $propFile does not exist.");
	}
}

open(PROFILE, "<" . $propertiesFileName);
my @lines = <PROFILE>;
close PROFILE;
# The properties file defines the nodes and tells where each file is to be copied.
my @slaveNodes = ();
my $masterNode;
my $lineCount = 1;
foreach my $line (@lines){
	if(index($line, "+define") == 0){
		# What is the line trying to define? Whatever it is, it'll be something like:
		#   +define:master=192.168.1.99
		#   or
		#   +define:master=heisenberg.dnsdynamic.net
		
		# Multiple platforms will be supported in future, however, as of now, linux with SSH
		#   access is assumed.
		
		# Support for specifying platform in () after hostname will be added in future. Eg:
		#   +define:master=heisenberg.dnsdynamic.net(win)
		#   for windows.
		
		my ($defineKey, $roleString) = split(/:/, $line, 2);
		chomp($defineKey); chomp($roleString);
		# display("Role string is $roleString.");
		my ($role, $node) = split(/=/, $roleString, 2);
		chomp($role); chomp($node);
		# display("Role of node $node is $role.");
		if($role eq "master"){
			if(defined($masterNode)){
				die("At line $lineCount in $propertiesFileName: Master node has been defined twice.");
			}
			$masterNode = $node;
		} elsif($role eq "slave") {
			push(@nodes, $node);
		}
	}
	$lineCount++;
}
if(scalar(@node) == 0){
    display("Warning: Slave nodes have not been defined.");
}

if(!defined($masterNode)){
    display("Warning: Master node has not been defined. Assuming we are on the master node.");
} else {
    display("Master: $masterNode");
}
foreach my $node (@nodes){
    display("Slave: $node");
}

if(!defined(@lines)){
	die("Failed to read properties file. Are the permissions correct?");
}
if(scalar(@lines) == 0) {
	die("Properties file is empty.");
}

foreach my $line (@lines){
	if(index($line, "+define") != 0){
	    # Making sure that we are not processing a definition line.
	    my ($options, $file) = split(/=/, $line, 2); # Something like master:/scripts/resmon=scripts/resmon/resmon.pl
	    chomp($options); chomp($file);
	    # Check if the file that is to be copied exists...
	    
	    if(-e $file){
		    my $copyArg = "";
		    if(-d $file){
			    $copyArg = "-r";
		    }
		    my ($clusterLocation, $destinationLocation) = split(/:/, $options, 2);
		    chomp($clusterLocation); chomp($destinationLocation);
		    if($clusterLocation eq "master" or $clusterLocation eq "cluster"){
			    # The file is to be copied onto the master machine or on entire cluster
			    # Assuming we are on the master machine...
			    if(-e $destinationLocation or make_path($destinationLocation)){
				    if(-f $file){
					copy($file, $destinationLocation) or display("Warning: Unable to copy $file to $destinationLocation");
				    } elsif(-d $file){
					dircopy($file, $destinationLocation) or display("Warning: Unable to copy directory $file to $destinationLocation");
				    }
				    display("File $file copied to $destinationlocation on $clusterLocation");
			    } else {
				    display("Warning: Failed to create destination location $destinationLocation");
			    }
		    }
		    # No elsif, this is important.
		    if($clusterLocation eq "slaves" or $clusterLocation eq "cluster"){
			    # Is the file to be copied across the entire cluster or slaves?
			    if($clusterLocation eq "cluster"){
				    foreach my $node (@nodes){
					    # For each node in cluster
					    # First scp file to home dir of remote machine
					    my $login = getlogin || getpwuid($<) || "Kilroy";
					    my $command = "scp " . $copyArg . " $file $login" . '@' . "$node:~/";
					    my $rc = system($command);
					    if($rc != 0){
						display("Warning: Failed to remotely scp file $file to $node.");
						next;
					}
					# Assuming perl is present on each machine on cluster and above scp was successful.
					my $perlCommand = "use File::Copy; use File::Path qw(make_path remove_tree);";
					my $filename = basename($file);
					$perlCommand .= "if(-e $destinationLocation or make_path($destinationLocation)){ if(-f $filename){ copy($filename, $destinationLocation); }elsif(-d $file){ dircopy($filename, $destinationLocation); } }";
					$command = "ssh $login" . '@' . $node . " \"perl -e \'" . $perlCommand . "\'\"";
					$rc = system($command);
					if($rc != 0){
						display("Warning: Failed to remotely move file $filename on $node.");
						next;
					} else {
						display("File $file copied to $destinationLocation on $clusterLocation.");
					}
				}
			}
		}
	    } else {
		    display("Warning: $file does not exist.");
	    }
	}
	# Process options and file.
	
	
}
sub display {
	my $str = shift;
	print $str . "\n";
}
