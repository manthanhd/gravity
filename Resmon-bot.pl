#!/usr/bin/perl

# -----------------------------------------------
#	 			Resource Monitor
# -----------------------------------------------
# This is the bot script.

# Prepare. First of all, cleanup.
my $dir = '/scripts/resmon';
my $memFile = $dir . '/mem.stat';
my $stop = undef;

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
	
	my $MemFree = `cat /proc/meminfo | grep MemFree`;
	if($MemFree =~ /^*(\d+)/){
			$MemFree = $1;
	}

	my $MemTotal = `cat /proc/meminfo | grep MemTotal`;
	if($MemTotal =~ /^*(\d+)/){
			$MemTotal = $1;
	}

	# Time to write data in a file in CSV format.

	open (MEMSTAT, ">>$memFile");
	print MEMSTAT $MemTotal . ',' . $MemFree . "\n";
	close(MEMSTAT);
	sleep(1);
}

sub display {
	my $str = shift;
	print $str . "\n";
}