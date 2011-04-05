#! /bin/bash

javac Encroach.java -d ./build
cd build
./epackage.sh
