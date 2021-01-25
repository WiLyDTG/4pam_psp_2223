#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
int main() {
  pid_t pid, Hijo_pid;
  int num=6;
  printf( "Valor inicial de la variable: %d \n" , num);
  pid = fork();

  if (pid == -1 ) //Ha ocurrido un error 
  {
    printf("No se ha podido crear el proceso hijo...");
    exit(-1);       
  }
  if (pid == 0 )  //Nos encontramos en Proceso hijo 
  {        
    //printf("Soy el proceso hijo \n\t Mi PID es %d, El PID de mi padre es: %d.\n", getpid(), getppid() );
    printf ("\t Variable en Proceso Hijo: %d \n" , num-5);	

  }
  else    //Nos encontramos en Proceso padre 
  { 
    Hijo_pid = wait(NULL); //espera la finalización del proceso hijo
    //printf("Soy el proceso padre:\n\t Mi PID es %d, El PID de mi padre es: %d.\n\t Mi hijo: %d terminó.\n", getpid(), getppid(), pid);          
    printf ("\t Variable en Proceso Padre: %d \n" , num+5);
   }
   exit(0);
}