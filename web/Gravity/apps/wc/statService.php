<?php
class CpuStat {
	public $timestamp = null;
	public $heisenbergCpu = null;
	public $titaniumCpu = null;
	public $tungstenCpu = null;
	public $siliconCpu = null;

	public function __construct() {

	}

	public function fill($timestamp, $heisenbergCpu, $titaniumCpu, $tungstenCpu, $siliconCpu) {
		$this -> timestamp = $timestamp;
		$this -> heisenbergCpu = $heisenbergCpu;
		$this -> titaniumCpu = $titaniumCpu;
		$this -> tungstenCpu = $tungstenCpu;
		$this -> siliconCpu = $siliconCpu;
	}

}

class MemStat {
	public $timestamp = null;
	public $heisenbergMem = null;
	public $titaniumMem = null;
	public $tungstenMem = null;
	public $siliconMem = null;

	public function __construct() {

	}

	public function fill($timestamp, $heisenbergMem, $titaniumMem, $tungstenMem, $siliconMem) {
		$this -> timestamp = $timestamp;
		$this -> heisenbergMem = $heisenbergMem;
		$this -> titaniumMem = $titaniumMem;
		$this -> tungstenMem = $tungstenMem;
		$this -> siliconMem = $siliconMem;
	}

}

class DiskStat {
	public $timestamp = null;
	public $heisenbergDisk = null;
	public $titaniumDisk = null;
	public $tungstenDisk = null;
	public $siliconDisk = null;

	public function __construct() {

	}

	public function fill($timestamp, $heisenbergDisk, $titaniumDisk, $tungstenDisk, $siliconDisk) {
		$this -> timestamp = $timestamp;
		$this -> heisenbergDisk = $heisenbergDisk;
		$this -> titaniumDisk = $titaniumDisk;
		$this -> tungstenDisk = $tungstenDisk;
		$this -> siliconDisk = $siliconDisk;
	}

}

if (isset($_GET['app_name']) && isset($_GET['tokenid']) && isset($_GET['mode'])) {
	$app_name = $_GET['app_name'];
	$tokenid = $_GET['tokenid'];
	$dir = getcwd() . '/out/' . $tokenid . '.stats';
	if (is_dir($dir)) {
		$hosts = array('heisenberg', 'tungsten', 'titanium', 'silicon');
		$filenames = array();
		foreach ($hosts as $host) {
			array_push($filenames, $dir . '/' . $host . '.mem.stat');
		}
		$files = count($filenames);
		$stats = array();
		foreach ($filenames as $filename) {
			// open each file.
			if (file_exists($filename)) {
				$handle = fopen($filename, "r");
				if ($handle) {
					while (($line = fgets($handle)) !== false) {
						$obj = json_decode($line) or die("Failed to decode json.");
						$found = false;
						$count = 0;
						foreach ($stats as $s) {
							if (strcmp($s -> timestamp, $obj -> timestamp) == 0) {
								$found = true;
								break;
							}
							$count++;
						}
						if ($found == true) {
							if ($_GET['mode'] == 'cpu') {
								if ($obj -> hostname == "heisenberg") {
									$stats[$count] -> heisenbergCpu = $obj -> cpu;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "titanium") {
									$stats[$count] -> titaniumCpu = $obj -> cpu;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "tungsten") {
									$stats[$count] -> tungstenCpu = $obj -> cpu;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "silicon") {
									$stats[$count] -> siliconCpu = $obj -> cpu;
									//$found = true;
									//break;
								}
							} else if ($_GET['mode'] == 'mem') {
								if ($obj -> hostname == "heisenberg") {
									$stats[$count] -> heisenbergMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "titanium") {
									$stats[$count] -> titaniumMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "tungsten") {
									$stats[$count] -> tungstenMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "silicon") {
									$stats[$count] -> siliconMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
									//break;
								}
							} else if ($_GET['mode'] == 'disk_general') {
								$diskObj = $obj -> diskUse;
								if ($obj -> hostname == "heisenberg") {
									$stats[$count] -> heisenbergDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "titanium") {
									$stats[$count] -> titaniumDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "tungsten") {
									$stats[$count] -> tungstenDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
									//break;
								} elseif ($obj -> hostname == "silicon") {
									$stats[$count] -> siliconDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
									//break;
								}
							}

						} else {
							if ($_GET['mode'] == 'cpu') {
								$st = new CpuStat();
								$st -> timestamp = $obj -> timestamp;
								if ($obj -> hostname == "heisenberg") {
									$st -> heisenbergCpu = $obj -> cpu;
									//$found = true;
								} elseif ($obj -> hostname == "titanium") {
									$st -> titaniumCpu = $obj -> cpu;
									//$found = true;
								} elseif ($obj -> hostname == "tungsten") {
									$st -> tungstenCpu = $obj -> cpu;
									//$found = true;
								} elseif ($obj -> hostname == "silicon") {
									$st -> siliconCpu = $obj -> cpu;
									//$found = true;
								}
								array_push($stats, $st);

							} else if ($_GET['mode'] == 'mem') {
								$st = new MemStat();
								$st -> timestamp = $obj -> timestamp;
								if ($obj -> hostname == "heisenberg") {
									$st -> heisenbergMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "titanium") {
									$st -> titaniumMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "tungsten") {
									$st -> tungstenMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "silicon") {
									$st -> siliconMem = ($obj -> memUsed / $obj -> memTotal) * 100;
									//$found = true;
								}
								array_push($stats, $st);
							} else if ($_GET['mode'] == 'disk_general') {
								$st = new DiskStat();
								$st -> timestamp = $obj -> timestamp;
								$diskObj = $obj->diskUse;
								if ($obj -> hostname == "heisenberg") {
									$st -> heisenbergDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "titanium") {
									$st -> titaniumDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "tungsten") {
									$st -> tungstenDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
								} elseif ($obj -> hostname == "silicon") {
									$st -> siliconDisk = ($diskObj -> rootUsed -> value / $diskObj -> rootSize -> value) * 100;
									//$found = true;
								}
								array_push($stats, $st);
							}
						}

					}
				} else {
					// error opening the file.
				}
				fclose($handle);
			}
		}

		// Cleanup null or empty values
		$deleted = 0;
		for ($i = 0; $i < count($stats); $i++) {
			$st = $stats[$i];
			if ($_GET['mode'] == 'cpu') {
				if ($st -> heisenbergCpu == null || $st -> heisenbergCpu == "") {
					unset($stats[$i]);
					$deleted++;
				} else if ($st -> titaniumCpu == null || $st -> titaniumCpu == "") {
					unset($stats[$i]);
					$deleted++;
				} else if ($st -> tungstenCpu == null || $st -> tungstenCpu == "") {
					unset($stats[$i]);
					$deleted++;
				} else if ($st -> siliconCpu == null || $st -> siliconCpu == "") {
					unset($stats[$i]);
					$deleted++;
				}
			} else if ($_GET['mode'] == 'mem') {
				if (is_null($st -> heisenbergMem) || $st -> heisenbergMem == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> titaniumMem) || $st -> titaniumMem == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> tungstenMem) || $st -> tungstenMem == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> siliconMem) || $st -> siliconMem == "") {
					unset($stats[$i]);
					$deleted++;
				}
			} else if ($_GET['mode'] == 'disk_general') {
				if (is_null($st -> heisenbergDisk) || $st -> heisenbergDisk == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> titaniumDisk) || $st -> titaniumDisk == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> tungstenDisk) || $st -> tungstenDisk == "") {
					unset($stats[$i]);
					$deleted++;
				} else if (is_null($st -> siliconDisk) || $st -> siliconDisk == "") {
					unset($stats[$i]);
					$deleted++;
				}
			}
			
		}
		//$stats = array_filter($stats);
		$stats = array_values($stats);
		$stats = array_splice($stats, 0, (count($stats)) - $deleted);
		echo json_encode($stats);
	} else {
		echo "{ \"error\":\"Directory does not exist.\" }";
	}
} else {
	echo "{ \"error\":\"Parameters not set. app_name and tokenid\" }";
}

function getLineCount($filename) {
	$count = 0;
	if (file_exists($filename)) {
		$handle = fopen($filename, "r");
		if ($handle) {
			while (($line = fgets($handle)) !== false) {
				$count++;
			}
		}
		fclose($handle);
	}
	return $count;
}
?>
