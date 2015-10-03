BEGIN{FS=" ";}
{print $2 "	" substr($5, 0, length($5)-1); }
