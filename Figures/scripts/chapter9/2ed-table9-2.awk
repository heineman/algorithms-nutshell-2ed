BEGIN{FS=",";min=-1;max=0;}
/[0-9]+\.\.\./ { n = $1-0; }
/NumRemoved:/ { ct = substr($0, 12, length($0)); rem[n] = ct-0; }
/NumPointsOnHull:/ { ct = substr($0, 17, length($0)); poh[n] = ct-0;  }
/native:/ { type="native"; }
/heuristic:/ { type="heuristic";}
/both:/ { type="both";}
/^[0-9,\.]+$/ {if (NF == 6) { val=$1 - 0; ar[type,val]=$2; if (min == -1) { min = val; } if (val < min) { min = val; } if (val > max) { max = val; }}}
END{
  print "n	#pt	avgTime	#Heur	TimeH	TimeBoth";
  for (n = min; n <= max; n *= 2) {
    print n "	" poh[n] "	" sprintf("%2.2f", ar["native",n]) "	" sprintf("%d",rem[n]) "	" sprintf("%2.2f",ar["heuristic",n]) "	" sprintf("%2.2f", ar["both",n]);
  }
}