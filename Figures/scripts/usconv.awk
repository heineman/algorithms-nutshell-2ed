# Convert n,s1,s2,... into n,mcs1,mcs2,... where original values
# are in seconds and resulting values are in microseconds
# first line is left alone since it contains the 'n' value
# second to microsecond conversions for colums 2 and higher
BEGIN{n=1}
{if (n == 1) { print $0; }}
{if (n != 1) { s=($1 "	"); for (i=2; i<=NF; i++) { s = (s $i*1000000 "	"); } print s; }}
{n--;}

