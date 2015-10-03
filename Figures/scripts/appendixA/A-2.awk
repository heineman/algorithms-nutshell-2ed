{ if (length($0) == 0) { output = 0; }}
{ if (output) { print $0; }}
/Table A-2/{ output = 1; }
