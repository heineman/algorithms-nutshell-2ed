# Show (b) speed-up of R=5, 4 threads
BEGIN{FS=",";}
/SingleThreaded/ { st = 1; mt = 0;}
/MultiThreaded/ { mt = 1; st = 0; }
{if ($1 != "n") { keys[$1,st,mt] = $2; }}
END{ for(n=65536; n<=1048576; n*=2) print n "\t" keys[n,1,0]/ keys[n,0,1]}
