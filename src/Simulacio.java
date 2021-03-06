import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Simulacio {

	/**
	 */
	//Variables necessaries
	//Arrays 1 i 2 dimensions
	static  String[] VHome;
	static int[][] VDistanciaCiutats;
	//Contadors possicions i fitxer acabat o no
	static int C1;
	static int C2;
	static int n;
	//Conexio amb la classe GetSetTXT (Getters i Setters del TXT)
	static GetSetTXT ObtencioValors = new GetSetTXT();
	//Per poder llegir de teclat
	static final InputStreamReader ISRLectura = new InputStreamReader(System.in);
	static final BufferedReader BRLectura=new BufferedReader(ISRLectura);
	//Variables necessaries
	//Boleans per distingir Ciutat i distancia
	static boolean BCiutat=true,BDistancia=false;
	//Introduccio nom i distancia ciutat (despres conversio a Int) i total del fitxer (Nombre total de nodes), per determinar la longitud del array
	static String SNomCiutat="", SDistancia="", STotal="", CiutatDesti="", CiutatOrigen="";
	//Obting caracter de lletra
	static char lletra;
	
	public static void main(String[] args) throws IOException {
	CarregarInformacio();
	}
	private static void CarregarInformacio() throws IOException {
		//Missatges de Benvinguda i pregunta pel pais
		System.out.println("***********************************");
		System.out.println("CIM160 Global Positioning System");
		System.out.println("***********************************");
		System.out.println("Benvingut al GPS economic.");
		//Indico el Pais
		System.out.println("Determini el pais on estan les ciutats origen i desti del seu viatge.");
		//Llegeixo per teclat
		String Pais=BRLectura.readLine();
		//En cas de que tingui aquest Pais
		if (Pais.equalsIgnoreCase("ESPANA") || Pais.equalsIgnoreCase("DEUTSHLAND")){
			URL direccionFichero = Simulacio.class.getResource(Pais + ".txt");
			FileInputStream FitxerGPS= new FileInputStream(direccionFichero.getPath());
			//Llegeixo
			n=FitxerGPS.read();
			//Obtinc caracter
			lletra=(char)n;
			//Si no es Enter
			while (lletra!='\n'){
				//Obting String (caracter a caracter)
				STotal=STotal+lletra;
				n=FitxerGPS.read();
				//Obtinc caracter
				lletra=(char)n;
			}
			//Longitud dels arrays
			VHome= new String[Integer.parseInt(STotal.trim())];
			VDistanciaCiutats= new int[Integer.parseInt(STotal.trim())][Integer.parseInt(STotal.trim())];
			//Si no he acabat el fitxer del pais
			while (n!=-1){
				//Llegeixo fitxer
				n=FitxerGPS.read();
				//obtinc caracter
				lletra=(char)n;
				//Si es enter
				if(lletra != '\n'){
					//Si es diferent a 2 punts i analitzo ciutat
					if(lletra!=':' && BCiutat){
						//Concateno
						SNomCiutat= SNomCiutat+lletra;
					}
					//Sino
					else{
						//Si es 2 punts
						if (lletra==':'){
							//Enlla??? amb  GetSetTXT
							ObtencioValors = new GetSetTXT();
							//Relleno informacio
							ObtencioValors.LlocActual = SNomCiutat.trim();
							//Afegeixo al vector la informacio
							VHome[C1]=SNomCiutat.trim();
							BCiutat=false;
							SNomCiutat="";
						}
					}
					//Si es parentesis obert
					if(n=='('){
						//Distancia
						BDistancia=true;
					}
					//Si analitzo distancia i lletra es parentesis (obert o tancat)
					if(BDistancia && lletra!='(' && lletra!=')'){
						//Concateno
						SDistancia=SDistancia+lletra;
					}
					//Sino
					else{
						//Si es parentesis tancat
						if (lletra==')'){
							BDistancia=false;
							//Relleno informacio
							ObtencioValors.DistanciaKM = Integer.parseInt(SDistancia.trim());
							//Afegeixo al vector la informacio
							VDistanciaCiutats[C1][C2]=Integer.parseInt(SDistancia.trim());
							SDistancia="";
							C2=C2+1;
						}
					}
				}
				//Sino
				else{
					//Inicialitzo variables
					C1=C1+1;
					C2=0;
					BCiutat=true;
					BDistancia=false;
				}
			}
			//Tanco fitxer
			FitxerGPS.close();
			//Representacio
			for (String s : VHome) {
				System.out.print(s + "  ");
			}
			System.out.println();
			for (int i = 0; i < VDistanciaCiutats.length; i++) {
				System.out.print(VHome[i] + " :  ");
				for (int j = 0; j < VDistanciaCiutats.length; j++) {
					System.out.print("  " +VDistanciaCiutats[i][j]);
				}
				System.out.println();
			}
			//Indico lo realitzat i pregunto per ciutats
			System.out.println("\nCartografia de " + Pais + " llesta per buscar ciutats.");
			//Ciutat Origen
			boolean Bolea=false;
			//Comprovacio ciutat origen
			do{
				System.out.println("Determina la ciutat d'origen del vostre tragecte");
				CiutatOrigen= BRLectura.readLine().toUpperCase();
				for (String s : VHome) {
					//Si existeix
					if (s.equals(CiutatOrigen)) {
						Bolea = true;
						break;
					}
				}
				if (!Bolea){
					System.out.println("No disposo d'aquesta ciutat");
				}
			}while (!Bolea);
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.println("Has escollit " + CiutatOrigen + " com a ciutat Origen");
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			//Ciutat Desti
			Bolea=false;
			//Comprovacio ciutat desti
			do{
				System.out.println("Determina la ciutat desti del vostre tragecte");
				CiutatDesti= BRLectura.readLine().toUpperCase();
				for (String s : VHome) {
					//Si existeix
					if (s.equals(CiutatDesti)) {
						Bolea = true;
						break;
					}
				}
				if (!Bolea){
					System.out.println("No disposo d'aquesta ciutat.");
				}
			}while (!Bolea);
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			System.out.println("Has escollit " + CiutatDesti + " com a ciutat Desti");
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			AlgoritmeEstrella();
		}
		//En els diferents casos d'errors
		else{
			//Si ho escriuen en l'idiome incorrecte
			if (Pais.equalsIgnoreCase("ALEMANYA") || Pais.equalsIgnoreCase("ALEMA???A") || Pais.equalsIgnoreCase("GERMANY") || Pais.equalsIgnoreCase("SPAIN") || Pais.equalsIgnoreCase("SPANIEN")){
				System.out.println("Aquest pais es diu aixi aqui i a la Xina popular.");
			}
			else{
				//Si escriuen amb catala
				if(Pais.equalsIgnoreCase("ESPANYA")){
					System.out.println("\n||*||    ||*||   ||*||   ||*||   ||*||\n");
					System.out.println("Aquest mapa s'ha d'actualitzar el 2014.\n");
					System.out.println("||*||    ||*||   ||*||   ||*||   ||*||");
				}
				//Si no existeix el mapa
				else{
					//No tinc la cartografia adient
					System.out.println("Pais no disponible actualment");
					System.out.println("Si vol disposar aquest o altres paisos, siusplau, actualitzi el seu GPS economic.");
				}
			}
		}
	}	

	//Metode estructura Algoritme estrella
	private static void AlgoritmeEstrella() {
		long startTime = System.nanoTime();
		Ciudad ciudad = Algoritmo.getBestRoute(VHome, VDistanciaCiutats, CiutatOrigen, CiutatDesti);
		long stopTime = System.nanoTime();
		System.out.println("Temps d'execucio de l'algorisme: " + (stopTime - startTime) + "ns");
		if (ciudad == null) {
			System.out.println("No s'ha trobat una ruta ??ptima. Prova de nou entre diferents punts");
		}
		else {
			System.out.println("La millor ruta per arribar a " + ciudad.getName() + " ??s la seg??ent:");

			for (int i = 0; i < ciudad.getRoute().size(); i++) {
				if (i == (ciudad.getRoute().size() - 1)) {
					System.out.print(ciudad.getRoute().get(i).getName());
				}
				else {
					System.out.print(ciudad.getRoute().get(i).getName() + " -> ");
				}
			}

			System.out.println("\nAmb un cost de " + ciudad.getCost());
		}
	}
	
}
