package ejemplo01;

public class HiloBasicoMain {
    public static void main(String[] args) throws InterruptedException {
        HiloBasico Hilo1 = new HiloBasico("Hilo 1");
        HiloBasico Hilo2 = new HiloBasico("Hilo 2");
        HiloBasico Hilo3 = new HiloBasico("Hilo 3");
        Hilo1.start();
        Hilo2.start();
        Hilo3.start();
        System.out.println("[Hilo main] -> TERMINADO."); 
    }
}
