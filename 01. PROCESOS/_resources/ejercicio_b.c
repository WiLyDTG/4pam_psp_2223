#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
 int  tuberia[2];
 pid_t pid_hijo1, pid_hijo2, pid_hijo3;
 
 pid_hijo1=fork(); 

 switch(pid_hijo1) {
   case -1 : //ERROR
        printf("NO SE HA PODIDO CREAR HIJO...");
        exit(-1);
        break;
   case 0 : //HIJO  
        printf("CREADO HIJO %d DE PADRE %d.\n" , getpid() , getppid());
        pid_hijo2=fork(); 
        if (pid_hijo2==0) {
            printf("CREADO NIETO %d DE PADRE %d.\n" , getpid() , getppid());
        }
        else {
            wait(NULL);
            pid_hijo3=fork(); 
            if (pid_hijo3==0) {
                printf("CREADO NIETO %d DE PADRE %d.\n" , getpid() , getppid());
            }
            else {
                wait(NULL);
                //printf("PROCESO %d terminado .\n" , dsds);
            }
        } 
        break;
   default : //PADRE
        wait(NULL);
        //printf("EL PADRE/ABUELO HA TERMINADO.\n" );
        break;
 }
}