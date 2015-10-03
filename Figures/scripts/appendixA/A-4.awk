BEGIN{output=0;}
/Table A-6/ { output=0; }
/Table A-4/ { output=1; header = 1;}
/average/ { header = 1; }
{ if (length($0) == 0) { output = 0; }}
{if (output&&!header) { print $1 "\t" $2/1000 "\t" $3/1000 "\t" $4/1000 "\t" $5/1000 "\t" $6;}}
{if (output&&header) { print $0; }}
{ header = 0; }

