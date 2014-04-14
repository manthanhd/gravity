my $command = shift;
my @servers = @ARGV;
for my $server (@servers){
	display("Executing: " . $command . " on " . $server);
	# First is server name, second is username, third is the actual command.
	my $rc = remote_exec($server, $server, $command);
	if($rc == 0) {
		display("Successful command completion.");
	} else {
		display("Failed to execute command. Return code: " . $rc);
	}
}

sub display {
	print shift . "\n";
}

# First parameter: server name.
# Second parameter: User name.
# Third parameter: command.

sub remote_exec {
	my $server = shift;
	my $username = shift;
	my $command = shift;
	my $cmd = "plink $username\@$server $command";
	return system($cmd);
}