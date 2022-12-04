package ejemplo05;

public class ProblemaAMain {
    public static void main (String[] args) {
        Contador cont = new Contador (100) ;
        final HiloA a = new HiloA("Hilo A", cont) ;
        final HiloB b = new HiloB("Hilo B", cont) ;
        a.start();
        b.start();
        
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
