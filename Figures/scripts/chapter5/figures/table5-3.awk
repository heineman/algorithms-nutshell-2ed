BEGIN{min=0; max=0; minP=0; maxP=0; FS="	"}
{ p=0 + $2; bin[$1,p]=$3; }

# compute min/max ranges
{ if (max == 0) { max = $1; } if ($1 > max) { max = $1; }}
{ if (min == 0) { min = $1; } if ($1 < min) { min = $1; }}

# Count p-vals
{ if (maxP == 0) { maxP = $2; } if ($2 > maxP) { maxP = $2; }}
{ if (minp == 0) { minP = $2; } if ($2 < minP) { minP = $2; }}

END{ 
  for (c = maxP; c >= minP; c -= 1.0) {
     row = row "p=" c "	";
  }
  print "n	" row;

  for (r = min; r <= max; r*= 2) {
    row=r "	";
    for (c = maxP; c >= minP; c -= 1.0) {
      p = 0 + c;
      row = row bin[r,p] "	";
    }
    print row;
  }
}
