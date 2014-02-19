#!/bin/sh
cd /opt/hadoop*/bin
./start-all.sh && ./stop-dfs.sh && ./hadoop namenode -format && ./start-dfs.sh
cd -
