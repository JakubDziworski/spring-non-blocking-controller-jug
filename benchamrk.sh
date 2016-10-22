#!/usr/bin/env bash

echo "****  BENCHMARKING http://localhost:8080/sync  *****"
ab -n 500 -c 500  http://localhost:8080/sync
echo
echo "**** BENCHMARKING http://localhost:8080/async *****"
ab -n 500 -c 500  http://localhost:8080/async
echo
echo "**** BENCHMARKING http://localhost:8080/futureCombinedQueries *****"
ab -n 500 -c 500  http://localhost:8080/futureCombinedQueries
