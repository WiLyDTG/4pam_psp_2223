#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
int main (void)
{
  fprintf(stdout, "El padre de este proceso tiene el ID %ld\n", (long)getppid());

  fprintf(stdout, "Éste proceso tiene el ID %ld\n", (long)getpid());

}
