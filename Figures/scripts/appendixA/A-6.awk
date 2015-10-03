BEGIN{output=0;}
/Table A-4/ { output=0; }
/Table A-6/ { output=1; header = 1;}
/average/ { header = 1; }
{ if (length($0) == 0) { output = 0; }}
{if (output&&!header) { print $1 "\t" int($2) "\t" $3 "\t" $4 "\t" sprintf("%.04f", $5) "\t" $6;}}
{if (output&&header) { print $0; }}
{ header = 0; }
