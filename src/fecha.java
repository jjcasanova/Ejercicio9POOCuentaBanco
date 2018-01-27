import java.util.Calendar;
import java.util.GregorianCalendar;

public class fecha {

	private int dia,mes,anno;
	
	public fecha() {
		
	}

	protected int getDia() {
		return dia;
	}

	protected void setDia(int dia) {
		this.dia = dia;
	}

	protected int getMes() {
		return mes;
	}

	protected void setMes(int mes) {
		this.mes = mes;
	}

	protected int getAnno() {
		return anno;
	}

	protected void setAnno(int anno) {
		this.anno = anno;
	}

	protected int numAnnos() {
		int numannos=0;
		GregorianCalendar factual= new GregorianCalendar();
		numannos=factual.get(Calendar.YEAR)-anno;
		if (factual.get(Calendar.MONTH+1)<mes) {
			numannos--;
		}else {
			if (factual.get(Calendar.MONTH+1)==mes && factual.get(Calendar.DAY_OF_MONTH)<dia) {
				numannos--;
			}
		}
		return numannos;
	}

	protected boolean fechavalida(int dia2, int mes2, int anno2) {
		boolean fechavalida=false;
		if (fechamenor(dia2,mes2,anno2)) {
			switch(mes2) {
				case 1: case 3: case 5: case 7: case 8: case 10: case 12:
					if (dia2<=31 && dia2>0) {
							fechavalida=true;
					}
					break;
				case 2:
					if (annobisiesto(anno2)) {
						if (dia2<=29 && dia2>0) {
								fechavalida=true;
						}					
					}else {
						if (dia2<=28 && dia2>0) {
								fechavalida=true;
						}					
					}
					break;
				case 4: case 6: case 9: case 11:
					if (dia2<=30 && dia2>0) {
							fechavalida=true;
					}					
					break;
					default:
						fechavalida=false;
						break;
			}
		}
		return fechavalida;
	}
	
	private boolean annobisiesto(int anno2) {
		boolean annobisiesto=false;
		if (anno2%4==0 && (anno2%100!=0 || anno2%400==0)) annobisiesto=true;
		return annobisiesto;
	}
	
	private boolean fechamenor(int dia2, int mes2, int anno2) {
		boolean validada=false;
		GregorianCalendar factual=new GregorianCalendar();
		if (anno2==factual.get(Calendar.YEAR)) {
			if (mes2==factual.get(Calendar.MONTH)+1) {
				if (dia2<=factual.get(Calendar.DAY_OF_MONTH)) {
					validada=true;
				}else {
					System.out.println("La fecha actual es menor que la introducida");
				}
			}else {
				if (mes2>factual.get(Calendar.MONTH)+1) {
					System.out.println("La fecha actual es menor que la introducida");
				}else {
					validada=true;
				}
			}
		}else {
			if (anno2>factual.get(Calendar.YEAR)) {
				System.out.println("La fecha actual es menor que la introducida");
			}else {
				validada=true;
			}
		}
		return validada;		
	}
}
