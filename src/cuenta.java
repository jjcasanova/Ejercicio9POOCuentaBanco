
public class cuenta {

	private double saldo;
	private fecha fapertura;
	private int nummov;
	
	public cuenta() {
		// TODO Auto-generated constructor stub
	}
	
	public cuenta(double saldo, fecha fapertura) {
		this.saldo=saldo;
		this.fapertura=fapertura;
	}

	protected void ingresar(double cantidad) {
		saldo+=cantidad;
		nummov++;
		System.out.println("Dinero ingresado, el saldo actual en cuenta es de " +saldo+ " euros");	
	}
	
	protected double getSaldo() {
		return saldo;
	}
	
	protected fecha getFecha() {
		return fapertura;
	}
	
	protected boolean retirar(double cantidad) {
		if(cantidad<=saldo) {
			saldo-=cantidad;
			nummov++;
			System.out.println("Dinero retirado, el saldo actual en cuenta es de " +saldo+ " euros");
			return true;
		}else {
			System.out.println("No hemos podido retirar "+cantidad+" euros de la cuenta. Saldo insuficiente");
			return false;
		}
	}
	
	protected int obtenertransacciones() {
		return nummov;
	}
	
	protected void premiofidelidad(int annos) {
		int numannos=fapertura.numAnnos();
		if (numannos>annos) {
			saldo+=(100*numannos);
			System.out.println("El saldo de la cuenta es de " + saldo);
			System.out.println("Antigüedad: " + numannos);
			nummov++;
			System.out.println("Cuenta premiada con 100 euros más en saldo por año de antigüedad");
		}else System.out.println("No he podido premiar la cuenta. Número de años insuficiente");
	}
	
	protected void transferirsaldo(cuenta micuenta, double cantidad) {
		retirar(cantidad);
		micuenta.ingresar(cantidad);
	}
}
