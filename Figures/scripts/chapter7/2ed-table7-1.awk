/1-move/ {ct1++; ab[1,ct1] = $3; abs1 += $3; mm[1,ct1] = $4; mms1 += $4; ratio[1,ct1] = ($3*1.0/$4); sum1 += ratio[1,ct1];}
 /2-move/ {ct2++; ab[2,ct2] = $4; abs2 += $4; mm[2,ct2] = $5; mms2 += $5; ratio[2,ct2] = ($4*1.0/$5); sum2 += ratio[2,ct2];}
  /3-move/ {ct3++; ab[3,ct3] = $5; abs3 += $5; mm[3,ct3] = $6; mms3 += $6; ratio[3,ct3] = ($5*1.0/$6); sum3 += ratio[3,ct3];}

END{
  print "k	#Minimax	#AlphaBeta	Agg. Reduction	Individual Variation";

  sumsq=0.0;
  for (x=1; x<=ct1; x++) {
    sumsq += ((ratio[1,x]-(1.0*sum1/ct1))**2);
  } 

  var1 = sprintf("+/- %2.1f%", 100*sqrt(sumsq/(ct1-1)));
  red = sprintf("%2.f%",100*(1-abs1/mms1));
  print "1	" mms1 "		" abs1 "		" red "		" var1;

  sumsq=0.0;
  for (x=1; x<=ct2; x++) {
    sumsq += ((ratio[2,x]-(1.0*sum2/ct2))**2);
  } 

  var2 = sprintf("+/- %2.1f%", 100*sqrt(sumsq/(ct2-1)));
  red = sprintf("%2.f%",100*(1-abs2/mms2));
  print "2	" mms2 "		" abs2 "		" red "		" var2;

  sumsq=0.0;
  for (x=1; x<=ct3; x++) {
    sumsq += ((ratio[3,x]-(1.0*sum3/ct3))**2);
  } 

  var3 = sprintf("+/- %2.1f%", 100*sqrt(sumsq/(ct3-1)));
  red = sprintf("%2.f%",100*(1-abs3/mms3));
  print "3	" mms3 "		" abs3 "		" red "		" var3;
}