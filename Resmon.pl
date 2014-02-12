#!/usr/bin/perl

# -----------------------------------------------
#	 			Resource Monitor
# -----------------------------------------------
# This is the master script.

use Getopt::Long;

my @machines = (
					'heisenberg',
					'titanium.dnsdynamic.net',
					'tungsten.dnsdynamic.net',
					'silicon.dnsdynamic.net'
				);
my @botMachines = ();
my $defaultUser = 'hduser';
my $botScript = '/scripts/resmon/Resmon-bot.pl';
my $stopFileName = '/scripts/resmon/stop';
my $memStatFile = '/scripts/resmon/mem.stat';
my $start_all;
my $stop_all;

GetOptions (
				"start-all"  => \$start_all,
				"stop-all"  => \$stop_all,
			);
if($start_all){
	startAll();
} elsif($stop_all){
	stopAll();
} else {
	display('Please supply either -start-all or -stop-all to start or stop bots on machines.');
}

sub startAll(){
	display('Will start all bots!');
	foreach $machine (@machines){
		startBot($machine);
	}
}

sub stopAll(){
	display('Will stop all bots!');
	foreach $machine (@machines){
		stopBot($machine);
	}
}

sub remoteExec {
	my $username = shift;
	my $machine = shift;
	my $command = shift;
	
	my $remoteCommand = "ssh -f $username\@$machine \"$command\"";
	
	display('Will execute: ' . $remoteCommand);
	my $rc = system($remoteCommand);
	return $rc;
}

sub scpGet {
	my $username = shift;
	my $machine = shift;
	my $remotePath = shift;
	my $localDestination = shift;
	
	my $scp = "scp $username\@$machine:\"$remotePath\" \"$localDestination\"";
	
	display('Will execute: ' . $scp);
	my $rc = system($scp);
	return rc;
}

sub startBot{
	my $machine = shift;
	my $command = "nohup $botScript 2>&1";
	display('Starting bot on machine ' . $machine . '...');
	$rc = remoteExec($defaultUser, $machine, $command);
	if($rc == 0){
		display('Bot started successfully on ' . $machine);
		push(@botMachines, $machine);
	} else {
		display('Failed to start bot on ' . $machine);
	}
}

sub stopBot{
	my $machine = shift;
	my $command = "touch $stopFileName";
	display('Asking bot on machine ' . $machine . ' to stop...');
	$rc = remoteExec($defaultUser, $machine, $command);
	if($rc == 0){
		display('Bot on ' . $machine . ' has been asked to stop.');
		sleep(2);
		mkdir('stats');
		$rc = scpGet('hduser', $machine, $memStatFile, "stats/$machine.mem.stat");
		if($rc == 0){
			display("Successfully fetched statistics from $machine.");
		} else {
			display("Failed to fetch file from $machine");
		}
	} else {
		display('Failed to start bot on ' . $machine);
	}
}

sub display{
	my $str = shift;
	print $str . "\n";
}
