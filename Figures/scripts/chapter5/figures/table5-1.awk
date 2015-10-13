BEGIN{min=0; max=0; ct=0; FS="	"; p[0] = -1}
{ ar[$1,$2]=$3;}

# compute min/max ranges
{ if (max == 0) { max = $1; } if ($1 > max) { max = $1; }}
{ if (min == 0) { min = $1; } if ($1 < min) { min = $1; }}

# Count p-vals
{ if (p[ct] == -1) { p[ct] = $2; }}
{ if (p[ct] != $2) { p[++ct] = $2; }}

END{ 
  row="n	";
  for (c = 0; c <= ct; c++) {
    row = row "p=" p[c] "	";
  }
  print row;

  for (r = min; r <= max; r*= 2) {
    row=r "	";
    for (c = 0; c <= ct; c++) {
      row = row ar[r,p[c]] "	";
    }
    print row;
  }
}
