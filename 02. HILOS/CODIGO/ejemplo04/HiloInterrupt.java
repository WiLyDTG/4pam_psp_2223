package ejemplo04;

public class HiloInterrupt implements Runnable {
    public void run() {
        try{
            //Esperamos 290.00 años ;)
            Thread.sleep(Long.MAX_VALUE);
        }catch(InterruptedException e){
            System.out.printf("[%s] ← ¡Interrumpido por una exception!\n",
                    Thread.currentThread().getName()
            );
        }
        while(!Thread.interrupted()){
            // no hacemos nada
        }
        System.out.printf("[%s] ← Interrumpido por segunda vez.\n",
                Thread.currentThread().getName()
        );
    }
}


