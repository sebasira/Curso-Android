package com.sebasira.clase05;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite es una base de datos de código abierto que está integrado en Android. SQLite compatible con
 * las características estándar de bases de datos relacionales.
 * SQLite está disponible en todos los dispositivos Android. Utilizar una base de datos SQLite en
 * Android no requiere ningún tipo de configuración/administración.
 *
 * SQLiteOpenHelper nos permite  crear y actualizar una base de datos. En el constructor de la misma
 * se debe llamar al método "super ()" de SQLiteOpenHelper, especificando el nombre de la base y la
 * versión de la base de datos actual.
 * Deberemos redefinir los métodos  onCreate () y onUpgrade ().
 * 		- onCreate () es llamado por android, si la base de datos no existe.
 * 		- onUpgrade () permite actualizar el esquema de base de datos.
 *
 * SQLiteOpenHelper ofrece los métodos getReadableDatabase() y getWriteableDatabase() para obtener
 * acceso a un objeto SQLiteDatabase, ya sea en modo de lectura o escritura.
 *
 * Las tablas de bases de datos deben utilizar el identificador _id como clave principal de la tabla.
 */
public class LibrosDataBase extends SQLiteOpenHelper {
	// Base de datos
	public static final String myDATABASE_name = "BD_LIBROS.db";	// Nombre de la base de datos
	private static final int myDATA_BASE_ver = 1;					// Version de mi base de datos
	
	// Tabla
	public static final String LIBROS_TABLA = "libros";				// Nombre de nuestra tabla
	
	// Columnas
	public static final String COL_ID = "_id";						// Nombre Columna ID
	public static final String COL_TITULO = "titulo";				// Nombre Columna Titulo
	public static final String COL_DESCRIPCION = "descripcion";		// Nombre Columna Descripcion
	
	
	
	/* CONSTRUCTOR */
	/* *********** */
	/**
	 * Crea la base de datos
	 */
	public LibrosDataBase(Context c) {
		// Llamamos al constructor de SQLite para crear la base
		// de datos
		super(c, myDATABASE_name, null, myDATA_BASE_ver);
	}

	
	/* ON CREATE */
	/* ********* */
	/**
	 * Se llama si la base de datos todavia no fue creada
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Vamos a crear una tabla dentro de la base de datos,
		// para ello necesitamos ejecutar una sentencia SQL
		//
		// Vamos a usar el campo ID como PRIMARY KEY de la tabla, 
		// con lo cual no pueden haber 2 iguales
		String sql = "CREATE TABLE " + LIBROS_TABLA + " (" +
				COL_ID + " TEXT primary key, " +
				COL_TITULO + " TEXT, " +
				COL_DESCRIPCION + " TEXT);";
		db.execSQL(sql);
	}

	
	
	
	/* ON UPGRADE */
	/* ********** */
	/**
	 * Metodo que debemos implementar si o si al extender de SQLiteOpenHelper.
	 * Se llama en caso de que actualicemos nuestra version de la base de datos,
	 * y la aplicacion este instalada y tenga una version anterior.
	 * La actualizacion la maneja solo android, no es mucho lo que debemos hacer
	 * pero esto escapa al alcance de nuestro curso
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}