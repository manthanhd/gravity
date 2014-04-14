
display(banner());

my $command = "";

print prompt();
while ($line = <STDIN>){
	my @commands = split(' ', $line);
	if(scalar(@commands) == 0 || $line eq '' || $line eq ' '){
		display(help());
		
		print "\n" . prompt();
		next;
	}
	my $currCommand = shift(@commands);
	if($currCommand eq 'connect'){
		display("\tOperation: connect");
		handleConnect(@commands);
	} elsif($currCommand eq 'shutdown') {
		display("\tOperation: shutdown");
		handleShutdown(@commands);
	}
	print prompt();
}

sub handleConnect {
	my @commands = @_;
	if(scalar(@commands) == 0){
		display("Method must be specified.");
		return;
	}
	
	my $currCommand = shift;
	if($currCommand eq 'ssh'){
		display("\tMethod: SSH");
		# Code for handling ssh connect
		handleConnectSSH(@commands);
	}
}

sub handleConnectSSH{
	my @commands = @_; 
	if(scalar(@commands) == 2){
		display("Target and user both must be specified.");
		return;
	}
	my $currCommand = shift(@commands);
	$currCommand = chomp($currCommand);
	my $user = shift(@commands);
	$user = chomp($user);
	if($currCommand eq 'master'){
		display("\tTarget: Master node");
		my $master = master();
		my $command = "putty $user\@$master";
		display("Executing $command");
		my $rc = system($command);
		
	} elsif($currCommand eq 'slaves'){
		display("\tTarget: All slave nodes");
		my @slaves = slaves();
		foreach $slave (@slaves){
			exec("putty $user\@$slave");
		}
	} elsif($currCommand eq 'all'){
		display("\tTarget: All");
		my @all = all();
		foreach $node (@all){
			exec("putty $user\@$node");
		}
	}
	
}

sub handleShutdown{
	my @commands = @_;
	if(scalar(@commands) == 0){
		display("Target must be specified.");
	}
	my $currCommand = shift;
	if($currCommand eq 'master'){
		display("\tTarget: Master node");
		# Code for shutting down master node
	} elsif($currCommand eq 'slaves'){
		display("\tTarget: All slave nodes");
		# Code for shutting down all slaves
	} elsif($currCommand eq 'all'){
		display("\tTarget: All");
		# Code for shutting down all machines in the cluster
	}
}

sub help {
	my $help = '';
	$help .= "Commands:\n";
	$help .= "connect \t shutdown \t restart \t cat";
	return $help;
}

sub display {
	my $str = shift;
	print $str . "\n";
}

sub banner{
	my $banner =  	"*************************************" . "\n";
	$banner .= 		"* WELCOME TO CLUSTER COMMAND CENTER *" . "\n";
	$banner .= 		"*                   BY MANTHAN DAVE *" . "\n";
	$banner .= 		"*************************************" . "\n";
	return $banner;
}

sub prompt{
	my $prompt = ">> ";
	return $prompt;
}

sub master {
	return 'heisenberg.dnsdynamic.net';
}

sub slaves {
	my @slaves = {'titanium.dnsdynamic.net', 'tungsten.dnsdynamic.net', 'silicon.dnsdynamic.net'};
	return @slaves;
}

sub all {
	my @slaves = slaves();
	push(@slaves, master());
}