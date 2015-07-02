package com.sebasira.clase05;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditarLibroActivity extends ActionBarActivity {
	EditText editID;
	EditText editTitulo;
	EditText editDescripcion;
	
	LibrosDataBase libros;
	
	boolean editando;

	
	
	/* ON CREATE */
	/* ********* */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.layout_editar_libro);
	    
	    // Nuevamente abrimos la conexion a la base de datos
		libros = new LibrosDataBase(this);
	    
		// Obtenemos referencia a cada uno de los campos de
		// edicion del libro
	    editID = (EditText) findViewById(R.id.editID);
	    editTitulo = (EditText) findViewById(R.id.editTitulo);
	    editDescripcion = (EditText) findViewById(R.id.editDescripcion);
	 
	    // En caso de que vayamos a editar un libro existente, como
	    // iniciamos la actividad con campos EXTRA (estos campos
	    // extra seria los datos de estos libros); lo que hacemos
	    // es rellenar los editText con los datos del libro
	    cargarDatosEdicion();
	    
	    ActionBar ab = getSupportActionBar();
	    ab.setIcon(R.drawable.ic_launcher);
		ab.setDisplayShowHomeEnabled(true);;
	    if (editando){
			ab.setTitle("Editando Libro");
	    }else{
	    	ab.setTitle("Nuevo Libro");
	    }
	    
	    // BOTON ACEPTAR
	    Button btnACEPTAR = (Button) findViewById(R.id.botonAceptar);
	    btnACEPTAR.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long res = modificarBaseDeDatos();
				
				// Si no se pudo guardar la base de datos, nos devuelve
				// un codigo de error (-1 o mas chico)
				if(res >= 0) {
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "Error guardando el registro", Toast.LENGTH_LONG).show();
				}
			}
		});
	    
	    // BOTON CANCELAR
	    Button btnCANCELAR = (Button) findViewById(R.id.botonCancelar);
	    btnCANCELAR.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	/* CARGAR DATOS PARA EDICION */
	/* ************************* */
	private void cargarDatosEdicion() {
	    Intent intent = getIntent();			// Obtenemos el intento

	    // Si tiene datos extras, los extraemos y levantamos la bandera
	    // de que estamos editando.
	    // Esta bandera es la que nos va a decir si lo que tenemos que
	    // hacer es CREAR un nuevo libro o ACTUALIZAR uno existente en
	    // la base de datos
	    if(null != intent.getExtras()) {
		    String isbn = intent.getExtras().getString("key_id");
		    String titulo = intent.getExtras().getString("key_titulo");
		    String descripcion = intent.getExtras().getString("key_descripcion");
		    
		    editID.setText(isbn);
		    
		    // IMPORTANTE
		    // Bloqueamos la edicion para q no modifique el ID en la base de datos
		    // Sino al actualizar, no lo va a encontrar
		    editID.setEnabled(false); 
		    
		    editTitulo.setText(titulo);
		    editDescripcion.setText(descripcion);
		    
		    editando = true;
	    }else{
	    	editando = false;
	    }
	}

	
	/* MODIFACAR BASE DE DATOS */
	/* *********************** */
	/**
	 * Esta rutina la vamos a llamar cuando vayamos a MODIFICAR la base
	 * de datos, ya sea CREAR o ACTUALIZAR un libro. Para ello nos valemos
	 * de la bandera 'editando'
	 */
	long modificarBaseDeDatos() {
		SQLiteDatabase db = libros.getWritableDatabase();			// Obtenemos una instancia de la base de datos
		// Noten que obtenemos una instancia WRITABLE, es decir que vamos a poder modificarla
		
		// Convertimos a String cada uno de los editText, para
		String id = editID.getText().toString();			
		String titulo = editTitulo.getText().toString();
		String descrip = editDescripcion.getText().toString();

		
	    ContentValues cv = new ContentValues();
		cv.put(LibrosDataBase.COL_ID, id);
		cv.put(LibrosDataBase.COL_TITULO, titulo);
		cv.put(LibrosDataBase.COL_DESCRIPCION, descrip);
		
		// De acuerdo a si estamos editando/creando vamos a actualizar/crear un
		// libro en la base de datos
		if(editando) {
			// Le decimos a que vamos a ACTUALIZAR la tala LIBROS_TABLA de la base de datos LibrosDataBase,
			// con los valores cv (el ContentValue) que cargamos antes.
			// ¿Y cual vamos a actualizar? el libro cuyo ID (COL_iD) se igual a nuestro ID (id)
			return db.update(LibrosDataBase.LIBROS_TABLA, cv, LibrosDataBase.COL_ID+"=?", new String[]{id});
		} else {
			// Creamos un nuevo libro dentro la la tabla LIBROS_TABLA de la base LibrosDataBase,
			// con los valores cv (el ContentValue) que cargamos antes.
			return db.insert(LibrosDataBase.LIBROS_TABLA, null, cv);
		}
		
		// En ambos casos, el valor de retorno, es el valor que retorna la ejecucion
		// de la sentencia SQL sobre la base de datos. Si es 0 (o superior, el resultado
		// es satisfactorio, si es -1 o menor, es erroneo)
	}
	
	/* ON DESTROY */
	/* ********** */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// No olviden que siempre que abren una conexion a la base de datos,
		// es necesario cerrarla en el onDestroy
		libros.close();
	}
}