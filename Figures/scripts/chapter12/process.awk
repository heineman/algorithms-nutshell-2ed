BEGIN{output = 0; FS=","; split("2048,4096,8192,16384,32768,65536,131072,262144,524288,1048576",allkeys,",");}  
/Num Threads/ { numThreads = substr($0,13);  }
/native:/ { native = 1; }
{if (length($0)==0){output=0;native=0;}}
{if (native) { values[0,$1] = sprintf("%.4f", $2); }}
{if (output&&!native) { values[numThreads,$1] = sprintf("%.4f", $2); }}
/multithread:/ {output = 1;}
END{print "n\tnative\t1 thread\t2 thread\t3 thread\t4 thread"; for (keys in allkeys) print allkeys[keys] "\t" values[0,allkeys[keys]] "\t" values[1,allkeys[keys]] "\t" values[2,allkeys[keys]] "\t" values[3,allkeys[keys]] "\t" values[4,allkeys[keys]]}
