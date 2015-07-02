package com.sebasira.clase05;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private LibrosDataBase  libros;
	private List<Libro> ListaLibros;
	private int posicionItem;

	/* ON CREATE */
	/* ********* */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar ab = getSupportActionBar();
		ab.setTitle("Lista de Lbros");
		ab.setIcon(R.drawable.ic_launcher);
		ab.setDisplayShowHomeEnabled(true);;
		
	    // Registramos nuestra listView para que despliegue un menu
	    // contextual al hacerle long click.
	    // Debemos crear este menu contextual, para que tengas las
	    // opciones de:
	    //	- BORRAR
	    //	- EDITAR
	    //
	    // Luego, en onContextItemSelected, realizamos las acciones
	    // correspondientes
	    registerForContextMenu(findViewById(R.id.listview));
	}
	
	
	/* ON RESUME */
	/* ********* */
	@Override
	public void onResume(){
		super.onResume();

		// Obtenemos una conexion a la base de datos
		// No olviden que deben cerrar esta conexion. La mejor practica
		// es cerrarla cuando ya no usan mas la actividad, es decir en
		// en onDestroy.
		//
		// Si la base de datos no existe, antes de abrir la conexion
		// la crea
		libros = new LibrosDataBase(this);

		// Creamos una funcion para mostrar la lista de libros y aqui
		// la llamamos
		mostrarListaLibros();
	}

	/* ON DESTROY */
	/* ********** */
	@Override
	public void onDestroy(){
		super.onDestroy();

		// Fundamental cerrar la conexion a la base de datos
		// cuando ya no la usamos mas, es decir, cuando deja
		// de existir la activity
		libros.close();
	}

	
	/* MOSTRAR LISTA LIBROS */
	/* ******************** */
	/**
	 * Aca es donde entra en juego el método toStrnig de {@link Libro}
	 * Ya que como estamos usando un simple_list_item_1 como adapter
	 * al ponerlo en el list view, lo que muestra es el toString del objeto
	 */
	public void mostrarListaLibros(){
		// Vamos a obtener una lista rellena de objetos libros, con
		// cada uno de nuestros Libros (que esten en la base de datos)
		ListaLibros = getLibros();			// Obtenemos una Lista

		// Ahora creamos un Adaptador de esta lista, para poder mostrarla
		// en nuestro listView.
		ArrayAdapter<Libro> adapter = new ArrayAdapter<Libro>(this,
		        android.R.layout.simple_list_item_1, ListaLibros);
		
		// Finalmente obtenemos la referencia a nuestro listView, y
		// le seteamos el adaptador que tiene los libros
		ListView listview = (ListView) findViewById(R.id.listview);
	    listview.setAdapter(adapter);
	}
/**************************************************************************
** OPERACIONES SOBRE LA BASE DE DATOS ** 
***************************************************************************/
	
	/* OBTENER LOS LIBROS DE LA BASE DE DATOS */
	/* ************************************** */
	public List<Libro> getLibros() {
		List<Libro> listaLibros = new ArrayList<Libro>();		// Lista de libros
		
		SQLiteDatabase db = libros.getReadableDatabase();	// Obtenemos una instancia de la base de datos
		// Noten que obtenemos una instancia READABLE, es decir que
		// vamos a poder leerla, pero no modificarla

		
	    // Vamos a crear un array de String con el nombre de las columnas
	    // que queremos obtener.
	    String columnas[] = {
				LibrosDataBase.COL_ID,
				LibrosDataBase.COL_TITULO,
				LibrosDataBase.COL_DESCRIPCION};
	    
	    // Creamos un cursor (un apuntador) que va a ser el resultado de nuestra
	    // QUERY (consulta) a la base de datos
	    Cursor cursor = db.query(
				LibrosDataBase.LIBROS_TABLA,
				columnas,
	    		null, null, null, null, null);

	    // Es imprescindible mover este cursor al inicio de la lista, para
	    // asi poder obtener todos los libros, desde el primero hasta el
	    // ultimo
	    cursor.moveToFirst();
	    
	    // Ahora, hasta que no lleguemos al final de los libros, vamos a 
	    // crear una lista con todos los libros
	    while (!cursor.isAfterLast()) {
	    	Libro l = new Libro(cursor.getString(0), cursor.getString(1), cursor.getString(2));
	    	listaLibros.add(l);
	    	cursor.moveToNext();
	    }
	    
	    cursor.close();								// Cerramos el cursor
	    
	    return listaLibros;
	  }
	
	
	/* BORRAR LIBRO */
	/* ************ */
	public long borrarLibro (int pos){
		SQLiteDatabase db = libros.getWritableDatabase();			// Obtenemos una instancia WRITABLE de la base de datos
		
		// Obtenemos el ID del libro segun la posicion en la que hayamos desplegado
		// el menu contextual
		String id = ListaLibros.get(pos).id;
		
		// Ejecutamos la sentencia para eliminar de la tabla LIBROS_TABLA, de la
		// base LibrosDataBase, cuyo ID es el id que obtuvimos anteriormente
		return (db.delete(LibrosDataBase.LIBROS_TABLA, LibrosDataBase.COL_ID
		        + " = " + id, null));
	} 

/**************************************************************************
** MENU DE OPCIONES ** 
***************************************************************************/
	
	/* ON CREATE OPRIONS MENU */
	/* ********************** */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	/* ON OPTION MENU ITEM SELECTED */
	/* **************************** */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_nuevoLibro) {
			// Al picar sobre la opcion de un nuevo libro, lanzamos la activity
			// de edicion, pero sin campos extras
			Intent itennto = new Intent(this, EditarLibroActivity.class);
			startActivity(itennto);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
/**********************************************************************************/
/** MENU CONTEXTUAL **/
/*********************/
	
	/* ON CREATE CONTEXT MENU */
	/* ********************** */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_contextual, menu);
		
		// Es muy importante que cuando se cree el menu contextual, guardemos
		// la posicion en la lista en la que desplegamos el menu, para luego
		// pdoer obtener los datos de ESE libro
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		posicionItem = info.position;
	}

	
	/* ON CONTEXT MENU ITEM SELECTED */
	/* ***************************** */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		// EDITAR
		// Si seleccionamos EDITAR, lo que tenemos que hacer es obtener los
		// datos del libro seleccionado, y luego inciar la actividad de edicion
		// con los datos de ese libro, para poder modificarlos.
		// Los datos, los pasamos como EXTRAS del intento, cada uno de ellos
		// con un KEY (un identificador) para luego poder extraerlos
		if (id == R.id.editar) {
			String libroID = ListaLibros.get(posicionItem).id;
			String libroTitulo = ListaLibros.get(posicionItem).titulo;
			String libroDescripcion = ListaLibros.get(posicionItem).descripcion;
			
			Intent i1 = new Intent(getApplicationContext(), EditarLibroActivity.class);
			i1.putExtra("key_id", libroID);
			i1.putExtra("key_titulo", libroTitulo);
			i1.putExtra("key_descripcion", libroDescripcion);
			startActivity(i1);
			return true;
		}
		
		// BORRAR
		// Llamamos  a nuestra funcion que borra un libro
		if (id == R.id.borrar) {
			if (borrarLibro(posicionItem) < 0){
				// Error
				Toast.makeText(getApplicationContext(), "No se pudo eliminar el libro", Toast.LENGTH_LONG).show();
			}else{
				// Ok
				Toast.makeText(getApplicationContext(), "Libro eliminado", Toast.LENGTH_LONG).show();
				
				// Luego, lo correcto seria refrescar la lista de libros.
				// Para ello volvemos a llamar a nuestra funcion que nos
				// muestra la lista de libros
				mostrarListaLibros();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
