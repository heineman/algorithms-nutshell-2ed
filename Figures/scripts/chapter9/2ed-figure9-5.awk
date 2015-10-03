BEGIN{FS=",";}
/Generator:algs.chapter9.figure5.SliceGenerator/ { type="slice"; min[type]=-1; max[type] = 0;}
/Generator:algs.model.data.points.UniformGenerator/ { type="uniform"; min[type]=-1; max[type] = 0;}
/Generator:algs.model.data.points.CircleGenerator/ { type="circle"; min[type]=-1; max[type] = 0;}
/Andrew/ { algo = "Andrew"; }
/Heap/ { algo = "Heap"; }
/Balanced/ { algo = "Balanced"; }
/Bucket/ { algo = "Bucket"; }
/Sorting only/ { algo = "Sorting only"; }
/^[0-9,\.]+$/ {if (NF == 6) { val=$1 - 0; ar[type,algo,val]=$2; if (min[type] == -1) { min[type] = val; } if (val < min[type]) { min[type] = val; } if (val > max[type]) { max[type] = val; }}}
END{
  # First is Slice, then Uniform, then Circle
  print "n	Andrew	Heap	Bal.	Bucket	SortingOnly";
  for (n = min["slice"]; n <= max["slice"]; n *= 2) {
    print n "	" sprintf("%2.2f", ar["slice","Andrew",n]) "	" \
          sprintf("%2.2f", ar["slice","Heap",n]) "	"         \
          sprintf("%2.2f", ar["slice","Balanced",n]) "	"         \
          sprintf("%2.2f", ar["slice","Bucket",n]) "	"         \
          sprintf("%2.2f", ar["slice","Sorting only",n]) "	" ;
  }
  print "";
  print "";

  print "n	Andrew	Heap	Bal.	Bucket	SortingOnly";
  for (n = min["uniform"]; n <= max["uniform"]; n *= 2) {
    print n "	" sprintf("%2.2f", ar["uniform","Andrew",n]) "	" \
          sprintf("%2.2f", ar["uniform","Heap",n]) "	"         \
          sprintf("%2.2f", ar["uniform","Balanced",n]) "	"  \
          sprintf("%2.2f", ar["uniform","Bucket",n]) "	"         \
          sprintf("%2.2f", ar["uniform","Sorting only",n]) "	" ;
  }
  print "";
  print "";

  print "n	Andrew	Heap	Bal.	Bucket	SortingOnly";
  for (n = min["circle"]; n <= max["circle"]; n *= 2) {
    print n "	" sprintf("%2.2f", ar["circle","Andrew",n]) "	" \
          sprintf("%2.2f", ar["circle","Heap",n]) "	"         \
          sprintf("%2.2f", ar["circle","Balanced",n]) "	"  \
          sprintf("%2.2f", ar["circle","Bucket",n]) "	"         \
          sprintf("%2.2f", ar["circle","Sorting only",n]) "	" ;
  }
}
