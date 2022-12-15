package puente;

import java.util.Random;

public class persona extends Thread{
	private int elPeso;
	public puente elPuente;
	
	public persona(String nombre , int elPeso, puente elPuente) {
		super(nombre);
		this.elPuente = elPuente;
		this.elPeso = elPeso;
	}
	
	public void run() {
		Random random = new Random();
		int espera = 1000 + random.nextInt(3000);
		System.out.println(Thread.currentThread().getName() + 
				" se esta acercando al puente ... (" + espera/1000 + ")" );
		try {
			Thread.sleep(espera);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean autorizado = false;
		while (!autorizado) {
			synchronized(elPuente) {
				autorizado = elPuente.autorizacionPaso(elPeso);
				if (!autorizado) {
					try {
						System.out.println(Thread.currentThread().getName() + 
								" debe esperar");
						elPuente.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
		
		
		elPuente.entra(elPeso);
		
		System.out.println(Thread.currentThread().getName() + 
				" cruzando el puente");
		espera = 1000 + random.nextInt(4000);
		try {
			Thread.sleep(espera);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elPuente.sale(elPeso);
		System.out.println(Thread.currentThread().getName() + 
				" ya ha cruzado el puente");
	}

}
