BEGIN{FS=",";  print "V" "	" "E" "		" "DPQ" "	" "DDG" "	" "ODDG" "	" "BF";}
/[0-9]/ { if ($2 <= 9999999) { tabs="		"; } else {tabs="	";} print $1 "	" $2 tabs $3 "	" $4 "	" $5 "	" $6; }
