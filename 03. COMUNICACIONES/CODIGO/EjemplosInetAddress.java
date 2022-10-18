
import java.net.*;

public class EjemplosInetAddress {
	public static final String SEPARADOR = "**************************************************************";
	public static void main(String[] args) {
	   InetAddress dir = null;
	   System.out.println(SEPARADOR);
	   System.out.println("INFORMACIÓN DE LOCALHOST: ");
	   try {
	 	//LOCALHOST
		dir = InetAddress.getByName("localhost");
		metodosEjemplo(dir);//

		//URL	www.google.es		
	    System.out.println(SEPARADOR);
		System.out.println("INFORMACIÓN DE UNA URL:");
		dir = InetAddress.getByName("www.google.es");
		metodosEjemplo(dir);
				
	     // Array de tipo InetAddress con todas las direcciones IP 
	     //asignadas a google.es
	     System.out.println("\tDIRECCIONES IP DE: " + dir.getHostName());
	     InetAddress[] direcciones = 
	                   InetAddress.getAllByName(dir.getHostName());
	     for (int i = 0; i < direcciones.length; i++)
	          System.out.println("\t\t"+direcciones[i].toString());
				
		System.out.println(SEPARADOR);
				
	  } catch (UnknownHostException e1) {e1.printStackTrace();}
	}// main
	
	private static void metodosEjemplo(InetAddress dir) {		
	    System.out.println("\tMétodo getByName():  " + dir);
		InetAddress dir2;
		try {
			dir2 = InetAddress.getLocalHost();
			System.out.println("\tMétodo getLocalHost(): " + dir2);
		} catch (UnknownHostException e) {e.printStackTrace();}	

		// USAMOS METODOS DE LA CLASE
		System.out.println("\tMetodo getHostName(): "+dir.getHostName());
		System.out.println("\tMetodo getHostAddress(): "+ 
	                                                 dir.getHostAddress());
		System.out.println("\tMetodo toString(): " + dir.toString());
		System.out.println("\tMetodo getCanonicalHostName(): " + 
	                                          dir.getCanonicalHostName());		
		}//metodosEjemplo	
		
}//fin



