package ejemplo02;

class HiloRunnable implements Runnable {

    private final String nombre;
    
    HiloRunnable (String nombre) {
        this.nombre = nombre;
        System.out.printf ("[%s] creado\n",
            this.nombre
        );
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            System.out.printf("[%s] -> C = %d\n", 
                this.nombre,
                i
            );
        }
        System.out.printf("[%s] -> TERMINADO.\n",
            this.nombre
        ); 
    }

}



