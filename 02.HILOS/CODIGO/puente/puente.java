package puente;

public class puente {

	private int pesoAcumulado, personasDentro;
	private static int pesoMaximo=200,personasMaximas=3;
	
	public puente() {
		pesoAcumulado = 0;
		personasDentro = 0;
	}
	
	public boolean autorizacionPaso(int peso) {
		if ((pesoAcumulado+peso)<pesoMaximo && (personasDentro+1)<personasMaximas)
			return true;
		else return false;
	}
	
	public synchronized void entra(int peso) {
		personasDentro++;
		pesoAcumulado = pesoAcumulado + peso;
	}
	
	public synchronized void sale(int peso) {
		personasDentro--;
		if (personasDentro<0) personasDentro=0;
		pesoAcumulado = pesoAcumulado - peso;
		if (pesoAcumulado<0) pesoAcumulado=0;
		this.notifyAll();
	}
}
