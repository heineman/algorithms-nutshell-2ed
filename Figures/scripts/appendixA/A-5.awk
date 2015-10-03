BEGIN{output=0;}
/Table A-7/ { output=0; }
{if (output) { print $0; }}
/Table A-5/ { output=1; }

