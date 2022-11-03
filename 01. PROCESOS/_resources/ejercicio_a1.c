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
        printf("CREADO HIJO  1 DE PADRE %d.\n" , getppid());
        break;   
   default : //PADRE
        wait(NULL);
        pid_hijo2=fork(); 
        if (pid_hijo2==0) {
            printf("CREADO HIJO  2 DE PADRE %d.\n" , getppid());
        }
        else {
            wait(NULL);
            pid_hijo3=fork(); 
            if (pid_hijo3==0) {
                printf("CREADO HIJO  3 DE PADRE %d.\n" , getppid());
            }
            else {
                wait(NULL);
                printf("YA HE CREADO LOS TRES HIJOS.\n" );
            }
        }
        break;
 }
}