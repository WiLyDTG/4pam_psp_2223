import java.util.Random;

class Coche extends Thread {

    private Parking parking;
    private int intentos;
    private Random rand = new Random(1500);

    public Coche(String n, Parking C) {
        super(n);
        parking = C;
        intentos = 5;
    }

    public void run () {
        boolean finalizado=false;
        while (!finalizado) {
            if (parking.hayPlaza()) {                
                parking.aparcar();
                System.out.println("Coche " +
                    Thread.currentThread().getName() +
                    " aparcado.");
                try {
                    Thread.sleep(rand.nextInt());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                parking.salir();
                System.out.println("Coche " +
                    Thread.currentThread().getName() +
                    " saliendo.");
                finalizado = true;
            } 
            else {
                if (intentos==0) {
                    finalizado=true;
                    System.out.println("Coche " +
                    Thread.currentThread().getName() +
                    "no ha encotrado plaza tras varios intentos y se va.");
                } 
                else {
                    System.out.println("Coche " +
                        Thread.currentThread().getName() +
                        "no tiene plaza y espera.");
                    try {
                        Thread.sleep(rand.nextInt());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
            }
            intentos--;
        }
    }
}