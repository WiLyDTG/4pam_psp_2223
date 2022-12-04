
public class MainParking {
    public static void main(String[] args) throws InterruptedException {
        Parking elParking = new Parking(10);
        Thread[] Coche = new Thread[50];
        for(int i=0; i<Coche.length; i++) {
            Coche[i] = new Coche("Coche-"+i , elParking);
            Coche[i].start();
        }
        for(int i=0; i<Coche.length; i++) {
            Coche[i].join();
        }
        System.out.println("["+Thread.currentThread().getName()+
            "] Programa principal terminado.");
    }
}