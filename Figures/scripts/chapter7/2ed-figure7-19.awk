BEGIN{FS=",";line=0;numSeen=0;tbl="";tblNum=0;maxRow=0;}
{if (NF == 1 && tblNum < 3) { numSeen++; tbl = tbl "/" $0; if (numSeen == 3) { table[tblNum++] = "[" tbl "]"; numSeen = 0; tbl=""; }}}
{if (NF == 3) { row[tblNum,$1] = $2; if ($1 > maxRow) { maxRow = $1; }}}
END{
  rowS = "depth-bound d	";
  for (j = 0; j < tblNum; j++) {
    rowS = rowS "B-" (j+1) "	";
  }
  print rowS;

  for (i = 1; i <= maxRow; i++) {
    rowS = "";
    for (j = 0; j <= tblNum; j++) {
      rowS = rowS row[j,i] "	";
    }

    if (i == maxRow) {
      print "UNBOUNDED" rowS;
    } else {
      print i "	" rowS;
    }
  }

  print " ";
  for (i = 0; i < tblNum; i++) {
    print "# B-" (i+1) " = " table[i];
  }


}