class Parking {
  private static int plazas = 0 ; //numero de plazas
  private int plazasDisponibles = 0;
 
  Parking (int c) { 
    plazas = c;
    plazasDisponibles = c;
  }
  public synchronized void aparcar () { 
    plazasDisponibles--;
  }
  public synchronized void salir () { 
    plazasDisponibles++; 
  }
  public synchronized boolean hayPlaza() { 
    return (plazasDisponibles>0);
  }
} 