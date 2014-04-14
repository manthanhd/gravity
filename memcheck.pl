#!/usr/bin/perl

$MemFree = `cat /proc/meminfo | grep MemFree`;
if($MemFree =~ /^*(\d+)/){
	$MemFree = $1;
}

$MemTotal = `cat /proc/meminfo | grep MemTotal`;
if($MemTotal =~ /^*(\d+)/){
	$MemTotal = $1;
}

$MemUsePercent = ($MemFree / $MemTotal) * 100;
print "$MemUsePercent% is in use.\n";
