package ejemplo03;

public class HiloJoinMain {
    public static void  main (String[] args) {
        Thread h1 = new Thread (new HiloJoin("Hilo 1")); 
        Thread h2 = new Thread (new HiloJoin ("Hilo 2") ); 
        h1.start();
        h2.start();
        try {
            h1.join();
            h2.join();
        }
        catch (InterruptedException e) {
            System.out.println("[Hilo main] terminado.");
        }
                
        System.out.println("[Hilo main] terminado.");
    }
}
    