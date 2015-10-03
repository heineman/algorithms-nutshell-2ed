BEGIN{FS=",";output=0;}

{ if(output&&length($0)>0){for (i = 2; i <= 22;i++) { print (i-1) "\t" $i; };print "\n";}}
/MultiThreaded\(r=1/{output=1;}



