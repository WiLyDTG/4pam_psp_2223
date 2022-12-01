package ejemplo05;

class HiloA extends Thread {

    private Contador contador;

    public HiloA(String n, Contador C) {
        super(n);
        contador = C;
    }

    public void run () {
        for (int j = 0 ; j < 300; j++) {
            contador.incrementa (); //incrementa el contador
            try {
                Thread.sleep(10) ;
            } catch (InterruptedException e) {}

        }
        System.out.printf("[%s] -> Contador vale %d\n" ,
            Thread.currentThread().getName(),
            contador.valor()
        );
    }
}