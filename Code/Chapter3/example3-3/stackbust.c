/**
 * @file stackbust.c    Task to show code that crashes a C program.
 * @brief 
 *    Provide code that exhausts all available memory by using infinite
 *    recursion. This will not likely crash your machine.
 * 
 * @author George Heineman
 * @date 6/15/08
 */

/** When recovering core file, this contains the number of times code executed. */
int n;

void f() {
  n++;
  f();
}

int main (int argc, char **argv) {
  f();

  return 0;
}
