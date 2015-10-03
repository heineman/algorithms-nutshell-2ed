BEGIN{min=0; max=0; minP=0; maxP=0; FS="	"}
{ p=0 + $3; ar[$1,$2,p]=$4; }

# compute min/max ranges
{ if (max == 0) { max = $1; } if ($1 > max) { max = $1; }}
{ if (min == 0) { min = $1; } if ($1 < min) { min = $1; }}

# Count p-vals
{ if (maxP == 0) { maxP = $3; } if ($3 > maxP) { maxP = $3; }}
{ if (minp == 0) { minP = $3; } if ($3 < minP) { minP = $3; }}

END{ 
  print "n	our hashtable 	java.util.Hashtable";
  row="";
  for (c = maxP; c >= minP; c -= 1.0) {
     row = row "p=" c "	";
  }
  for (c = maxP; c >= minP; c -= 1.0) {
     row = row "p=" c "	";
  }
  print "	" row;

  for (r = min; r <= max; r = 2*(r+1)-1) {
    row=r "	";
    for (c = maxP; c >= minP; c -= 1.0) {
      p = 0 + c;
      row = row sprintf("%.2f", ar[r,"hash",p]) "	";
    }
    for (c = maxP; c >= minP; c -= 1.0) {
      p = 0 + c;
      row = row sprintf("%.2f", ar[r,"util",p]) "	";
    }
    print row;
  }
}
