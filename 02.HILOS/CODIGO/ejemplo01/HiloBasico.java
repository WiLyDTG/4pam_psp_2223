package ejemplo01;
public class HiloBasico extends Thread {
    public HiloBasico(String name) {
        //mediante esta instrucción, le decimos al constructor el 
        //nombre del hilo usando el constructor de la clase Thread
        //asi podemos obtener su nombre con el método existente getName()
        super(name);
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
          System.out.printf("[%s] -> C = %d\n",
            Thread.currentThread().getName(),
            i
          );
        }
        System.out.printf("[%s] -> TERMINADO.\n",
            Thread.currentThread().getName()
        );
    }
}
