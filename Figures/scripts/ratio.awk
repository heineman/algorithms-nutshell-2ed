# Convert n,s1,s2,... into n,s1,s2,...,rat1,rat2...
# where rat1 is the ration of s1 from row to row (first is empty).
# first line is left alone since it contains the 'n' value
BEGIN{n=1}
{if (n == 1) { s=""; for (i=2; i <=NF; i++) { s = (s "Ratio-" (i-1) "	"); } print $0 "	" s; }}
{if (n ==0) { s=($1 "	"); for (i=2; i<=NF; i++) { last[i] = $i; s = (s $i "	"); } print s; }}
{if (n <0) { s=($1 "	"); t=""; for (i=2; i <=NF; i++) { t = (t ($i/last[i]) "	"); } for (i=2; i<=NF; i++) { last[i] = $i; s = (s $i "	"); } print s t }}
{n--;}
