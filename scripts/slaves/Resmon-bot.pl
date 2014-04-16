#!/usr/bin/perl

# -----------------------------------------------
#	 			Resource Monitor
# -----------------------------------------------
# This is the bot script.

# Prepare. First of all, cleanup.
use POSIX qw/strftime/;
use Sys::Hostname;

my $dir = '/scripts/resmon';
my $memFile = $dir . '/mem.stat';
my $stop = undef;
my $host = hostname; 
unlink($memFile);

while(!$stop){

	# Check if stop file is present
	opendir (DIR, $dir);
	while (my $f = readdir(DIR)){
		if($f eq 'stop'){
			display('I have been asked to stop.');
			unlink($dir . '/stop');
			$stop = 1;
			last;
		}
	}
	closedir (DIR);
	
#	my $MemFree = `cat /proc/meminfo | grep MemFree`;
#	if($MemFree =~ /^*(\d+)/){
#			$MemFree = $1;
#	}
	#
#	my $MemTotal = `cat /proc/meminfo | grep MemTotal`;
#	if($MemTotal =~ /^*(\d+)/){
#			$MemTotal = $1;
#	}
#	my $MemUse = $MemTotal - $MemFree;
#	
	# Time to write data in a file in CSV format.

	my ($tasks, $running, $sleeping, $cpu, $memTotal, $memUsed) = analyseUse();

	open (MEMSTAT, ">>$memFile");
	my $timestamp = strftime('%d-%m-%Y %H:%M:%S',localtime);
 	my $diskUseJson = analyseDisk();
	my $jsonString = "{\"hostname\":\"$host\", \"timestamp\":\"$timestamp\", \"tasksTotal\":\"$tasks\", \"tasksRunning\":\"$running\"," . 
											"\"tasksSleeping\":\"$sleeping\", \"cpu\":\"$cpu\", \"memTotal\":\"$memTotal\", \"memUsed\":\"$memUsed\", \"diskUse\": $diskUseJson }";
	print MEMSTAT $jsonString . "\n";
	close(MEMSTAT);
	sleep(1);
}

sub display {
	my $str = shift;
	print $str . "\n";
}

sub analyseUse {
	my $txt = `top -b -n 1`;
	$regex = '.*?Tasks.*?(\\d+)\\s.*?total.*?(\\d+)\\s.*?running.*?(\\d+)\\s.*?sleeping.*?Cpu.*?([+-]?\\d*\\.\\d+)(?![-+0-9\\.]).*?Mem.*?(\\d+)k.*?(\\d+)k.*?';
	my ($tasks, $running, $sleeping, $cpu, $memTotal, $memUsed) = $txt =~ m/$regex/is;
	# print "\nTasks:$tasks, Running $running, Sleeping $sleeping, Cpu: $cpu, memTotal:$memTotal, memFree:$memUsed";
	return ($tasks, $running, $sleeping, $cpu, $memTotal, $memUsed);
}

sub analyseDisk {
	my $txt = `df -h`;
  my $regex = '^.*?sda1\\s+(\\d+[.\\d]+)(.)\\s+(\\d+[.\\d]+)(.).*?tmpfs\\s+(\\d+[.\\d]+)(.)\\s+(\\d+[.\\d]+)(.)';
	my ($rootSize, $rootSizeUnit, $rootUsed, $rootUsedUnit, $tmpSize, $tmpSizeUnit, $tmpUsed, $tmpUsedUnit) = $txt =~ m/$regex/is;
  my $jsonString = "{ \"rootSize\":{ \"value\":\"$rootSize\", \"unit\":\"$rootSizeUnit\" }, " .
					"\"rootUsed\":{\"value\":\"$rootUsed\", \"unit\":\"$rootUsedUnit\"}, " .
          "\"tmpSize\":{\"value\":\"$tmpSize\", \"unit\":\"$tmpSizeUnit\"}, " .
          "\"tmpUsed\":{\"value\":\"$tmpUsed\", \"unit\":\"$tmpUsedUnit\"} }";
					# print "\nSize of root partition: $rootSize $rootSizeUnit, Used: $rootUsed $rootUsedUnit\n";
					# print "\nSize of tmp partition: $tmpSize $tmpSizeUnit, Used: $tmpUsed $tmpUsedUnit\n";
	return $jsonString;
}

