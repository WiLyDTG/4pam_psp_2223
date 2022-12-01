package ejemplo05;

class HiloB extends Thread {

    private Contador contador;

    public HiloB(String n, Contador C) {
        super(n);
        contador = C;
    }

    public void run () {
        for (int j = 0 ; j < 300; j++) {
            contador.decrementa (); //incrementa el contador
            try {
                sleep(10) ;
            } catch (InterruptedException e) {}

        }
        System.out.printf("[%s] -> Contador vale %d\n" ,
            Thread.currentThread().getName(),
            contador.valor()
        );
    }
}