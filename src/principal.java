import java.io.*;
import java.util.GregorianCalendar;


public class principal {

	private static int numcuentas;
	
	public static void main(String[] args) throws IOException {
		int opcion=0;
		cuenta[] micuenta;
		numcuentas=numerocuentas("número de cuentas con el que desea trabajar");
		micuenta= new cuenta[numcuentas];
		micuenta=rellenar();
		mostrar(micuenta);
		do {
			opcion=menu();
			enrutar(opcion,micuenta);
		}while(opcion!=6);
	}

	private static void mostrar(cuenta[] micuenta) {
		System.out.println("Datos de las cuentas");
		for (int i=0;i<micuenta.length;i++) {
			System.out.println("Cuenta número " + (i+1));
			System.out.println("Saldo: " + micuenta[i].getSaldo());
			System.out.println("Fecha de apertura:  " + micuenta[i].getFecha().getDia()+ " de " + micuenta[i].getFecha().getMes()+ " de " + micuenta[i].getFecha().getAnno());
		}
	}

	private static int menu() throws IOException {
		int opcion=0;
		System.out.println("Seleccione una entre las siguientes opciones");
		System.out.println("1. Ingresar");
		System.out.println("2. Retirar");
		System.out.println("3. Obtener transacciones");
		System.out.println("4. Premio de fidelidad");
		System.out.println("5. Transferir saldo a otra cuenta");
		System.out.println("6. Salir");
		try {
			opcion=Integer.parseInt(introducirdato());
		}catch(NumberFormatException ex) {
			System.out.println("Ha de indicar un número, no un carácter");
			opcion=-1;
		}
		return opcion;
	}

	private static String introducirdato() throws IOException {
		String teclado=null;
		BufferedReader leer= new BufferedReader(new InputStreamReader(System.in));
		teclado=leer.readLine();
		return teclado;
	}

	private static void enrutar(int opcion, cuenta[] micuenta) throws IOException {
		int numero=0;
		double cantidad=0;
		switch(opcion) {
		case 1:
			numero=indicarnumcuenta("ingresar");
			do {
				System.out.println("Indique la cantidad a ingresar");
				cantidad=indicarcantidad();
				if (cantidad<0) System.out.println("La cantidad no puede ser negativa. Introduce otra positiva");
			}while(cantidad<0);
			if (confirmar("ingresar")) ingresar(numero,micuenta,cantidad);
			else System.out.println("No ha confirmado el ingreso. No puedo realizarlo");
			break;
		case 2:
			numero=indicarnumcuenta("retirar");
			do {
				System.out.println("Indique la cantidad a retirar");
				cantidad=indicarcantidad();
				if (cantidad<0) System.out.println("La cantidad no puede ser negativa. Introduce otra positiva");
			}while(cantidad<0);
			if (confirmar("retirar")) retirar(numero,micuenta,cantidad);
			else System.out.println("No ha confirmado la retirada. No puedo realizarla");
			break;
		case 3:
			numero=indicarnumcuenta("obtener el número de movimientos");
			nummov(numero,micuenta);
			break;
		case 4:
			numero=indicarnumcuenta("premiar");
			premiar(numero,micuenta);
			break;
		case 5:
			transferir(micuenta);
			break;
		case 6:
			break;
			default:
				System.out.println("Seleccione entre 1 y 6");
			break;
		}
	}

	private static void transferir(cuenta[] micuenta) throws IOException {
		int numero1=0, numero2=0;
		double cantidad=0;
		numero1=indicarnumcuenta("retirar");
		if (cuentaexiste(micuenta, numero1)) {
			numero2=indicarnumcuenta("ingresar");
			if (cuentaexiste(micuenta, numero2)) {
				do {
					System.out.println("Indique la cantidad a transferir");
					cantidad=indicarcantidad();
					if (cantidad<0) System.out.println("La cantidad no puede ser negativa. Introduce otra positiva");
				}while(cantidad<0);
				if (confirmar("transferir")) {
					if (retirar(numero1,micuenta,cantidad)) if (ingresar(numero2,micuenta,cantidad)) System.out.println("Transferencia realizada correctamente");
				}else System.out.println("No ha confirmado la transferencia. No se puede realizar");
			}else System.out.println("No eixste la cuenta para ingresar. No se puede realizar la transferencia");
		}else System.out.println("La cuenta para retirar no existe. No se puede realizar la transferencia");
	}

	private static void premiar(int numero, cuenta[] micuenta) throws IOException {
		int annosfidelidad=25;
		if (cuentaexiste(micuenta, numero)) {
			if (confirmar("premiar")) {
				micuenta[numero-1].premiofidelidad(annosfidelidad);
			}else System.out.println("No ha confirmado que desea premiar la cuenta");
		}else System.out.println("El número de cuenta no existe. No se puede premiar");
	}

	private static void nummov(int numero, cuenta[] micuenta) {
		if(cuentaexiste(micuenta,numero)) System.out.println("El número de movimientos de la cuenta número "+numero+" es "+ micuenta[numero-1].obtenertransacciones()+ " transacciones");
		else System.out.println("La cuenta no existe, por lo tanto no puedo decirle el número de movimientos");
	}

	private static boolean retirar(int numero, cuenta[] micuenta, double cantidad) throws IOException {
		boolean retirada=false;
		if (cuentaexiste(micuenta,numero)) {
				if (micuenta[numero-1].retirar(cantidad)) retirada=true;
		}
		else System.out.println("La cuenta no existe. No puedo retirar la cantidad");
		return retirada;
	}

	private static boolean ingresar(int numero, cuenta[] micuenta, double cantidad) throws IOException {
		boolean ingresada=false;
		if (cuentaexiste(micuenta,numero)) {
				micuenta[numero-1].ingresar(cantidad);
				ingresada=true;
		}
		else System.out.println("La cuenta no existe. No puedo ingresar la cantidad");
		return ingresada;	
	}

	private static double indicarcantidad() throws IOException {
		double cantidad=0;		
		boolean introducido=false;
		do {
			try {
				cantidad=Double.parseDouble(introducirdato());
				introducido=true;
			}catch(NumberFormatException ex) {
				System.out.println("Introduce un número, no un carácter");
			}
		}while(!introducido);
		return cantidad;
	}

	private static boolean cuentaexiste(cuenta[] micuenta, int numero) {
		if (numero>micuenta.length || numero<0) return false;
		else return true;
	}

	private static boolean confirmar(String string) throws IOException {
		String confirmar;
		System.out.println("Confirme con Y/y ó S/s que desea " + string);
		confirmar=introducirdato();
		if (confirmar.charAt(0)=='Y' || confirmar.charAt(0)=='y' || confirmar.charAt(0)=='S' || confirmar.charAt(0)=='s') return true;
		else return false;
	}

	private static cuenta[] rellenar() throws IOException {
		cuenta[] micuenta= new cuenta[numcuentas];
		double saldo=0;
		int dia=0,mes=0,anno=0;
		fecha fapertura;
		for (int i=0;i<micuenta.length;i++) {
			fapertura=new fecha();
			do {
				System.out.println("Introduce el saldo de la cuenta " + (i+1));
				saldo=indicarcantidad();
				if (saldo<0) System.out.println("El saldo inicial no puede ser negativo");
			}while(saldo<0);
			boolean valida=false;
			do {
				System.out.println("Introduce la fecha de apertura. Día, mes y año no pueden ser negativos. El año ha de ser posterior a 1900");
				dia=numerocuentas("día");
				mes=numerocuentas("mes");
				anno=numerocuentas("año");
				valida=fapertura.fechavalida(dia, mes, anno);
				if (!valida) System.out.println("Fecha no válida");
			}while(!valida);
			fapertura.setDia(dia);
			fapertura.setMes(mes);
			fapertura.setAnno(anno);
			micuenta[i]=new cuenta(saldo, fapertura);
		}
		return micuenta;
	}

	private static int numerocuentas(String mensaje) throws IOException {
		int numerocuentas=0;
		boolean introducido=false;
		System.out.println("Indique el " + mensaje);
		do {
			try {
				numerocuentas=Integer.parseInt(introducirdato());
				introducido=true;
			}catch(NumberFormatException ex) {
				System.out.println("Introduce un número, no un carácter");
			}
		}while(!introducido);
		return numerocuentas;
	}

	private static int indicarnumcuenta(String string) throws IOException {
		int numcuenta=0;
		boolean introducido=false;
		do {
			System.out.println("Indique el número de cuenta en el que desee " + string);
			try {
				numcuenta=Integer.parseInt(introducirdato());
				introducido=true;
			}catch(NumberFormatException ex) {
				System.out.println("Introduce un número, no un carácter");
			}
		}while(!introducido);
		return numcuenta;
	}
}
