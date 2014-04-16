#!/usr/bin/perl

my $output = `ls -ld /opt/jdk`;
my @arr = split("-> ", $output);
chomp($arr[1]);
print $arr[1];
