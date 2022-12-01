package ejemplo04;

public class HiloInterruptMain {
    public static void main(String[] args) throws InterruptedException {
        Thread miHilo = new Thread(new HiloInterrupt(), "miHilo");
        miHilo.start();
        System.out.printf("[%s] ← Durmiendo en hilo main durante 5s...\n",
                Thread.currentThread().getName()
        );
        Thread.sleep(5000);
        System.out.printf("[%s] ← Interrumpiendo miHilo.\n",
                Thread.currentThread().getName()
        );
        miHilo.interrupt();
        System.out.printf("[%s] ← Durmiendo en hilo main durante 5s.\n",
                Thread.currentThread().getName()
        );
        Thread.sleep(5000);
        System.out.printf("[%s] ← Interrumpiendo miHilo.\n",
                Thread.currentThread().getName()
        );
        miHilo.interrupt();
    }
}
