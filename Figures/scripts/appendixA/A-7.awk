BEGIN{FS=","}
/average/ {header = 1;}
{ if (length($0) == 0) { output = 0; }}
{ if (output&&!header) { print $1 "\t" sprintf("%8.f",$2) "\t" $3 "\t" $4 "\t" sprintf("%.04f", $5) "\t" $6; }}
{ if (output&&header) { print $1 "\t" $2 "\t" $3 "\t" $4 "\t" $5 "\t" $6; }}
/NANOSECONDS/{output =1 }
{header = 0;}
