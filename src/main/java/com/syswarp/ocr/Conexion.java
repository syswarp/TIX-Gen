/*
* PREDATOR
*/

package com.syswarp.ocr;

import java.sql.*;
import java.sql.Connection;
import java.util.*;
import java.io.*;

/**
 *
 * @author SYSWARP SRL Programa: predator version 1.0 utilidad: replicacion de
 *         datos en bases vtv por planta.
 *
 */
public class Conexion {
	private Properties PROPS;

	private Connection CONEXION_LOCAL;
	private String URL_LOCAL;
	private String CLASE_LOCAL;
	private String USUARIO_LOCAL;
	private String CLAVE_LOCAL;
	private String DIRECTORY_IN = "";
	private String DIRECTORY_OUT = "";

	public Conexion() {
		super();
		try {
			PROPS = new Properties();
			PROPS.load(Conexion.class.getResourceAsStream("system.properties"));

			URL_LOCAL = PROPS.getProperty("conn.url").trim();
			CLASE_LOCAL = PROPS.getProperty("conn.clase").trim();
			USUARIO_LOCAL = PROPS.getProperty("conn.usuario").trim();
			CLAVE_LOCAL = PROPS.getProperty("conn.clave").trim();
			DIRECTORY_IN = PROPS.getProperty("directory_in").trim();
			DIRECTORY_OUT = PROPS.getProperty("directory_out").trim();

		} catch (Exception ex) {
			System.out.println("Problemas con el archivo system.properties, por favor revisar." + ex);
		}

	}

	public String getDirectoryIN() {
		;
		return DIRECTORY_IN;
	}

	public String getDirectoryOUT() {
		;
		return DIRECTORY_OUT;
	}

	
	public File getDirectoryInContain() {
		return new File(DIRECTORY_IN);
	}
	
	public void setConexionLocal() {
		try {
			Class.forName(CLASE_LOCAL);
			CONEXION_LOCAL = DriverManager.getConnection(URL_LOCAL, USUARIO_LOCAL, CLAVE_LOCAL);
		} catch (java.lang.ClassNotFoundException cnfException) {
			System.out.println("setConexionLocal()/Error driver : " + cnfException);
		} catch (SQLException sqlException) {
			System.out.println("setConexionLocal()/Error SQL: " + sqlException);
		} catch (Exception ex) {
			System.out.println("setConexionLocal()/Salida por exception: " + ex);
		}
	}

	public Connection getConexionLocal() {
		return CONEXION_LOCAL;
	}

	public boolean isConexionLocal() {
		boolean salida = true;
		if (CONEXION_LOCAL == null)
			salida = false;
		return salida;
	}

	public void cerrarConexionLocal() {
		try {
			if (CONEXION_LOCAL != null)
				CONEXION_LOCAL.close();
		} catch (SQLException sqlException) {
			System.out.println("cerrarConexionLocal()/Error SQL: " + sqlException);
		} catch (Exception ex) {
			System.out.println("cerrarConexionLocal()/Salida por exception: " + ex);
		}
	}

}
