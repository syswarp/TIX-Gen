package com.syswarp.ocr;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO // para detectar moneda tengo que traerme los simbolos de la tabla
 * moneda que tengo el la DB
 * 
 */
public class App {
	public static void main(String[] args) {
		String textoOCR = "tDestacados Parrilla-Restaurantt\r\n" + "Av Entre Rios y Haria Porteiro de Flores\r\n"
				+ "Federación-3206-Entre Rios\r\n" + "Vago, Antonella Beatriz\r\n" + "CUIT Nro.t 27-36056593-4\r\n"
				+ "IVA RESPONSARLE INSCRIPTO\r\n" + "A CONSUMIDOR FINAL\r\n" + "P.V. Nro.: 0002\r\n" + "Nro. T.\r\n"
				+ "Fecha 02/02/19\r\n" + "00003959\r\n" + "Hora 23:21:50\r\n" + "2,0000 x 60,0000\r\n"
				+ "GASEOSA X 500\r\n" + "1,0000 x 360,0000\r\n" + "HATAMDRE A LA PIZZA\r\n" + "1,0000 x 120,0000\r\n"
				+ "PAPAS FRITAS\r\n" + "1,0000 x 340,0000\r\n" + "PESCADO A/LIHON\r\n" + "2,0000 x 25,0000\r\n"
				+ "DERECHO SHOW\r\n" + "2,0000 x 25,0000\r\n" + "SERVICIO DE MESA\r\n" + "TOTAL\r\n" + "PAGO\r\n"
				+ "Susa de sus pagos\r\n" + "Su Vuelto\r\n" + "120,00\r\n" + "360,00\r\n" + "120,00\r\n" + "340,00\r\n"
				+ "50,00\r\n" + "50,00\r\n" + "1040,00\r\n" + "1040,00\r\n" + "1040,00\r\n" + "0,00\r\n"
				+ "V: 22.01 Deneter\r\n" + "REGISTRO Nro.: PED1035027\r\n" + "";

		System.out.println("Razon Social: " + getRazonSocial(textoOCR));
		System.out.println("Fecha: " + getFecha(textoOCR));
		System.out.println("Hora: " + getHora(textoOCR));

		BigDecimal total = getTotal(textoOCR);
		System.out.println("Importe del Ticket: " + total);

		System.out.println("CUIT: " + getCUIT(textoOCR));
		System.out.println("Sucursal Ticket : " + getPuestoVenta(textoOCR));
	
		//System.out.println("Esnumerico " + isNumeric("03959"));
		System.out.println("Comprobante Nro: " + getNroComprob(textoOCR));
	
	}

	private static String getCUIT(String txt) {
		String salida = "99999999999";
		String[] arrayLineas = getArrayOCX(txt);
		for (int i = 0; i < arrayLineas.length; i++) {
			String texto = arrayLineas[i];
			texto = texto.replace("\n", "");
			texto = texto.replaceAll("-", "");
			texto = texto.trim();
			texto = StringUtils.right( texto, 11 );
			if (texto.length()==11 && isNumeric(texto)) {
		       salida = texto;  
			}
		}
		return salida;
	}

	
	private static String getNroComprob(String txt) {
		String salida = "99999999";
		String[] arrayLineas = getArrayOCX(txt);
		for (int i = 0; i < arrayLineas.length; i++) {
			String texto = arrayLineas[i];
			texto = texto.replace("\n", "");
			texto = texto.trim();
			
			texto = StringUtils.right( texto, 8 );
			if (texto.length()==8 &&  NumberUtils.isDigits(texto)) {
		       salida = texto;  
			}
		}
		return salida;
	}

	
	private static BigDecimal getTotal(String txt) {
		BigDecimal total = new BigDecimal(-1);
		String[] arrayLineas = getArrayOCX(txt);
		for (int i = 0; i < arrayLineas.length; i++) {
			String texto = arrayLineas[i];
			texto = texto.replace("\n", "");
			texto = texto.replaceAll(",", ".");
			texto = texto.trim();
			if (isNumeric(texto)) {
				BigDecimal valor = new BigDecimal(texto);
				if (valor.compareTo(total) == 1) {
					total = valor;
				}
			}
		}
		return total;
	}

	private static String getPuestoVenta(String _textoOCR) {
		// devuelvo la primer linea del ticket ( al menos por ahora)
		String salida = "";
		int pos = 0;
		pos = _textoOCR.indexOf("P.V. Nro.: ");
		if (pos > 0) {
			salida = _textoOCR.substring(pos + 11, pos + 11 + 4).trim();
			salida = salida.replaceAll("\n", "");
		}
		return salida;
	}

	
	private static String getRazonSocial(String _textoOCR) {
		// devuelvo la primer linea del ticket ( al menos por ahora)
		String salida = "";
		int pos = 0;
		pos = _textoOCR.indexOf("\n");
		if (pos > 0) {
			salida = _textoOCR.substring(0, pos).trim();
			salida = salida.replaceAll("\n", "");
		}
		return salida;
	}

	private static String getFecha(String _textoOCR) {
		// Busco la ocurrencia fecha
		String salida = "";
		int posFecha = 0;
		posFecha = _textoOCR.toLowerCase().indexOf("fecha");
		if (posFecha > 0) {
			// le saco la palabra fecha sumando 5
			salida = _textoOCR.substring(posFecha + 5, posFecha + 5 + 11).trim();
			salida = salida.replaceAll("\n", "");

		}
		return salida;
	}

	private static String getHora(String _textoOCR) {
		// Busco la ocurrencia fecha
		String salida = "";
		int posHora = 0;
		posHora = _textoOCR.toLowerCase().indexOf("hora");
		if (posHora > 0) {
			// le saco la palabra fecha sumando 5
			salida = _textoOCR.substring(posHora + 5, posHora + 5 + 9).trim();
			salida = salida.replaceAll("\n", "");

		}
		return salida;
	}

	private static String[] getArrayOCX(String _textoOCR) {
		// Busco la ocurrencia fecha
		int pos = 0;
		String[] arrayLineas = _textoOCR.split("\n");
		return arrayLineas;
	}

	public static boolean isNumeric(String str) {
		// TODO: Reemplazar la coma x numero
		// System.out.println(str +": "+NumberUtils.isCreatable(str));
		return NumberUtils.isCreatable(str);
	}

}
