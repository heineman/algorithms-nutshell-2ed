BEGIN{min=0; max=0; minP=0; maxP=0; FS="	";type="";c1="c1";c2="c2";c3="c3";c4="c4";}

# determine build type
/MAIN/ { type=c1;}
/EXTENDED/ { type=c2;}
/MODEST/ { type=c3;}
/NO REHASH/ { type=c4;}

# determine max/min bin sizes
/[0-9]/ { if (NF == 6) { if (max == 0) { max = $1; } if ($1 > max) { max = $1; }}}
/[0-9]/ { if (NF == 6) { if (min == 0) { min = $1; } if ($1 < min) { min = $1; }}}

# Extract build times
{ if (NF == 6) { bt[type,$1] = sprintf("%.2f", $2);}}

# Extract rehash times
/0.75/ { if (NF == 3) { rh75[$1] = $3; }}
/4.0/ { if (NF == 3) { rh40[$1] = $3; }}

END{ 

  print "n	our_BT	.75_BT	.75_#R	4.0_BT	4.0_#R	JDK_0R";

  for (r = min; r <= max; r = (r+1)*2-1) {
    print r "	" bt[c1,r] "	" bt[c2,r] "	" rh75[r] "	" bt[c3,r] "	" rh40[r] "	" bt[c4,r];
  }
}
