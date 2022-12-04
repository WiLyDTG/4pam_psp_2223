package ejemplo03;

import java.util.Random;

class HiloJoin implements Runnable { 
  private final String nombre;
  
  HiloJoin(String nombre) { 
   this.nombre = nombre;
  }
  
  @Override
  public void run () {
    System.out.printf("[%s] CREADO.\n", this.nombre); 
	Random r = new Random();
    for (int i =0; i<5; i++) {
      int pausa = 10 + r.nextInt(500-10); 
		  System.out.printf("[%s] hace pausa de %d ms.\n", this.nombre, pausa ); 
		  try {
			  Thread.sleep (pausa );
      } 
		  catch (InterruptedException e) {
        System.out.printf ("[%s] interrumpido.\n" , this.nombre ); 
      }
    }
    System.out.printf("[%s] terminado.\n", this.nombre); 
  }
}