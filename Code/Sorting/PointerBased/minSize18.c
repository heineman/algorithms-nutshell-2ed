/**
 * @file minSize18.c    Defines MinSize set of 18
 * @brief
 *   Done in this way to be able to assemble various alternative sorting
 *   arrangements staticly, rather than requiring one to recompile an 
 *   application as part of running a test suite. In this regard, we traded
 *   off the low-overhead of having lots of very small functions that do
 *   very little with the benefit of writing easy Makefiles that select
 *   which minimum size to use at static linking time.
 *
 * @author George Heineman
 * @date 6/15/08
 */
int minSize = 18;
