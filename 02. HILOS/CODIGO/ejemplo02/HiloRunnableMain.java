package ejemplo02;

public class HiloRunnableMain {
    public static void main(String[] args) {
        //Creamos las instancias de los tres hilos
        Thread h1 = new Thread(
            new HiloRunnable("Hilo 1")
        );
        Thread h2 = new Thread(
            new HiloRunnable("Hilo 2")
        );
        Thread h3 = new Thread(
            new HiloRunnable("Hilo 3")
        );
            
        //Los ejecutamos
        h1.start();
        h2.start();
        h3.start();
        System.out.println("[Hilo main] -> TERMINADO."); 
    
    }

}


