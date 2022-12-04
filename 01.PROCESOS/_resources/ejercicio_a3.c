#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
int main () { 
    int num;
    pid_t pid;
    for (num= 0; num< 3; num++) {
        pid= fork();
        printf ("Soy el proceso de PID %d y mipadre tiene %d dePID.\n", getpid(), getppid()); 
        if (pid!= 0)
            break; 
        srandom(getpid()); 
        sleep (random()%3);
    }
    if (pid!= 0)
    printf ("Fin del procesode PID %d.\n", wait(NULL));
    return 0; 
}