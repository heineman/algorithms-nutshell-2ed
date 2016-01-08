If this directory is properly placed relative to the ADK installation, then
you should be able to build without any changes.

However, if you have the ADK code in a different directory, then you will
need to properly set ADK_ROOT to the directory where you have installed the
ADK. To run the trials in trials.sh you will need to also set the ADK_ROOT
appropriately.

Instructions for installing the ADK can be found in the ZIP file

  http://examples.oreilly.com/9780596516246/Releases/

Once you edit the Makefile accordingly, type 'make' to build the
multi-thread quicksort code.
