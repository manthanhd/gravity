#!/bin/sh
cd /opt/hadoop*/bin
./start-all.sh && ./hadoop namenode -format && ./start-dfs.sh
cd -
