package PractMultiproceso2_Hijo;

public class Ej01 {

	public static void main(String[] args) {
		int retorno;
		if (args.length == 0 || args[0].equals("")) {
			retorno = -1;
		} else {
			try {
				int señal = Integer.parseInt(args[0]);
				if (señal > 0) {
					retorno = -3;
				} else if(señal==0){
					retorno = 1;
				}else {
					retorno = 0;
				}
			} catch (NumberFormatException e) {
				retorno = -2;
			}
		}
		System.exit(retorno);

	}

}
