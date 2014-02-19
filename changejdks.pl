#!/usr/bin/perl

use Getopt::Long;

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
		if(!$auto){display("Symlink created.");}
	} else {
		die("Failed to create symlink.");
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
			
			if($auto){print "fail";}
		} else {
			if(!$auto){display("$machine changed it's JDK successfully.");}
			print "success";
		}
	}
}


sub display {
	my $str = shift;
	print $str . "\n";
}
