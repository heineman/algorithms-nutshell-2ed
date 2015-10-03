#!/bin/bash

  echo "---------------------------------"
  echo "search first"
  echo "---------------------------------"

  ./search -n 100000 -z 4096 -c 1.0 -e 16 -f
  ./search -n 100000 -z 4096 -c 0.5 -e 16 -f 
  ./search -n 100000 -z 4096 -c 0.1 -e 16 -f
  ./search -n 100000 -z 4096 -c 0.0 -e 16 -f

for SRC in `find . -perm 0775 -type f`
#for  SRC in "./linkedList"
do 
  echo "---------------------------------"
  echo $SRC
  echo "---------------------------------"

  $SRC -n 100000 -z 4096 -c 1.0 -e 16
  $SRC -n 100000 -z 4096 -c 0.5 -e 16
  $SRC -n 100000 -z 4096 -c 0.1 -e 16
  $SRC -n 100000 -z 4096 -c 0.0 -e 16
done
