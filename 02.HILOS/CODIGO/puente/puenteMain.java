package puente;

import java.util.Random;
public class puenteMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		puente elPuente = new puente();
		persona Personas[] = new persona[10];
		
		Random random = new Random();
		int peso;
		
		for (int i=0; i<Personas.length; i++) {
			peso = 40 + random.nextInt(80);
			String nombre = "Persona " + i;
			Personas[i] = new persona(nombre , peso, elPuente);
		}
		
		for (int i=0; i<Personas.length; i++) {
			Personas[i].start();
		}
		

	}

}
