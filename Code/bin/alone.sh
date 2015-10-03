#!/bin/bash

# Usage:
#  
#    ./alone.sh [Full-Unix-Command]
#
# Function:
#
# Use unix 'time' utility to process the actual wall-clock speed
# of the given Shell command. The reason we place this in a separate
# script file is because 'time' produced output on 'stderr' in such
# a way that it cannot easily be redirected.
#
# Author:
#
#    George Heineman 

time -p $*


