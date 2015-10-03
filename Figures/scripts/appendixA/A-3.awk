BEGIN{output=0;}
{if (output) { print $0; }}
/Table A-3/ { output=1; }

