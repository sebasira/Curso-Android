package com.sebasira.clase05;

public class Libro {
	public String id;
	public String titulo;
	public String descripcion;
	
	/* CONSTRUCTOR */
	/* *********** */
	public Libro (String id, String tit, String desc){
		this.id = id;
		this.titulo = tit;
		this.descripcion = desc;
	}
	
	
	/* TO STRING */
	/* ********* */
	/**
	 * Tenemos que hacer un OVERRIDE al metodo toString que convierte
	 * nuestro objeto en un String.
	 * Esto lo vamos a usar en el ArrayAdapter, para rellenar el listView.
	 * Lo que nosotros queremos es que cuando conviertan a String a 
	 * nuestro objeto, nos devuelva el nombre del libro (es decir, el titulo)
	 */
	@Override
	public String toString() {
		return titulo;
	}
}
