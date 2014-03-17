#!/usr/bin/perl

use Getopt::Long;

# Slave machines array. We don't need the master as this script will be executed on master.
my @machines = (
										'titanium.dnsdynamic.net',
										'tungsten.dnsdynamic.net',
										'silicon.dnsdynamic.net'
								);
my $defaultUser = 'hduser';
my $jdkSymlink = '/opt/jdk';
my $targetJDK = undef;
my $clusterwide = undef;
my $auto = undef;
my $failFlag = undef;

GetOptions (
								"jdk=s"		=> \$targetJDK,
								"all" => \$clusterwide,
								"auto" => \$auto
						);

if($targetJDK){
	if(!-d $targetJDK){
		die("$targetJDK not found. Cannot continue.");
	}
	if( -l $jdkSymlink ){
		if(!$auto){display("Removing old symlik...");}
		my $command = "sudo rm $jdkSymlink";
		my $rc = system($command);
		if($rc == 0){
			if(!$auto){display("Symlink removed.");}
		} else {
			die("Failed to remove symlink.");
		}
	}
	if(!$auto){display("Creating new symlink...");}
	my $command = "sudo ln -s $targetJDK $jdkSymlink";
	my $rc = system($command);
	if($rc == 0){
		if(!$auto){display("Symlink created.");} else {display("success");}
	} else {
		die("fail");
	}
}

if($clusterwide){
	foreach my $machine (@machines){
		my $rcommand = '/home/hduser/changejdks.pl -auto -jdk ' . $targetJDK;
		my $command = 'ssh ' . $defaultUser . '@' . $machine . ' "' . $rcommand . '";';
		my $output = `$command`;
		chomp($output);
		if($output ne "success"){
			if(!$auto){display("Failed to change jdk on machine $machine. Output was:\n\t$output");}
			
			if($auto){print "fail"; $failFlag = 1;}
		} else {
			if(!$auto){display("$machine changed it's JDK successfully.");}
			print "success";
		}
	}
}
if(defined($failFlag)){
	exit 1;
} else {
	exit 0;
}

sub display {
	my $str = shift;
	print $str . "\n";
}
