package com.sebasira.clase04;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class ActivityActionBar extends AppCompatActivity {

    /* ON CREATE */
	/* ********* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);

        // Manejar la ActionBar es muy sencillo, solo tenemos
        // que requerir la misma y despues seteamos los atributos
        // que queremos.
        // Noten que para que una activity tenga una actionBar, su
        // clase debe extender de ActionBarActivity (y no solo de
        // Activity)
        // [NOTA: Esto se encuentra unas lineas mas arriba donde se
        // declara la clase:
        //		public class MainActivity extends ActionBarActivity
        // Si quisieramos quitar la actionBar basta con poner que
        // extienda solo de Activity]
        //
        // La ActionBar no fue introducida hasta Android 3.0 (API LEVEL 11).
        // Pero nosotros tenemos en nuestro proyecto (en el manifiesto)
        // que nuestra APP puede correr en dispositivos desde api level
        // 8 hasta api level 18. Por suerte tenemos nuestra libreria de
        // compatibilidad, y con ella podemos usar una actionBar incluso
        // hasta API level 7.
        // Recuerden que cada vez que usen una libreria de compatibilidad
        // tienen que:
        // - importar la clase que incluya SUPPORT en su nombre:  Por ejemplo
        //	 android.support.v7.app.ActionBarActivity en lugar de
        //   android.app.ActionBar
        //	 Ya que la ultima hace referencia a la actionBar nativa que
        // 	 como dijimos antes solo existe desde api level 11 (android 3.0)
        //
        // Lo que vamos a hacer a continuacion es requerir la actionBar y
        // vamos a poner un titulo y un subtitulo

        ActionBar ab = getSupportActionBar();
        // Aca arriba llamamos a get[SUPPORT]Actionbar porque usamos la libreria
        // de compatibilidad. Si no la usaramos, el llamado solo seria getActionBar
        ab.setTitle("TITULO");				// Seteamos el titulo
        ab.setSubtitle("Subtitulo");		// Seteamos Subtitulo

        // Si usamos la AppCompatActivity, por defecto no muestra el icono
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setDisplayShowHomeEnabled(true);

        // BackNavigation en ActionBar
        ab.setDisplayHomeAsUpEnabled(true);


        // A esta actionBar le vamos a agregar 2 iconos de accion.
        // Estos iconos, o mejor dicho estas acciones, son las mismas que
        // se definen cuando creamos un MENU DE OPCIONES, solo que en el
        // atributo showAsAction del ITEM (en el xml del menu) le tenemos
        // que decir que la muestre en la barra de acciones, en lugar de en
        // el menu.
        // [Les sugiero que ahora vean esto en el xml del MENU, y luego vean
        // como en onOptionItemSelected capturamos los eventos de los items
        // que estan en la barra, como los que no estan]

        // CUANDO TERMINES DE VER LO DE LA ACTION BAR, PASAMOS A LA
        // TRANSACCION DE FRAGMENTOS

        // Lo que hace a continuacion, es cargar el fragment dentro del contenedor
        // pero sollo si savedInstanceState es nulo. De momento solo necesitan
        // saber que eso quiere decir que va a cargar el fragmento si no lo cargo
        // previamente.
        // Esto lo crea automaticamente cuando creamos un proyecto con fragmento.
        if (savedInstanceState == null) {
            // A continuacion lo que hace es cargar dentro del CONTAINRER el FRAGMENTI
            // Debjo van a ver la misma instruccion desmenuzada, para que puedan
            // comprender como funciona.
            // Yo les voy a dejar comentado el codigo que crea por defecto
            // y les agrego lo mismo escrito de otra manera

			/* CODIGO POR DEFECTO */
            //getSupportFragmentManager().beginTransaction()
            //		.add(R.id.container, new PlaceholderFragment()).commit();

			/* CODIGO DESMENNUZADO */
            FragmentManager fm = getSupportFragmentManager();	// Solicitamos el Administrador de Fragmentos de nuestra actividad
            FragmentTransaction ft = fm.beginTransaction();		// Creamos una nueva transaccion de fragmentos

            // PlaceholderFragment es el nombre del fragmento (nombre de la clase)
            // que vamos a cargar. Este es el fragmento que se crea por defecto
            // y pueden encontrar su clase en este mismo archivo, pero mas abajo
            PlaceholderFragment miFragmento = new PlaceholderFragment();
            ft.add(R.id.container, miFragmento);
            // Aca arriba le decimos a la transaccion que vamos a AGREGAR (add) un FRAGMENTO
            // (llamdo miFragmento) dentro del CONTENEDOR (container: definido en el layout
            // de la actividad)
            ft.commit();			// Finalmente comiteamos (ejecutamos) la transaccion

            // Y listo! Ya tenemos un fragmento en el contenedor

            // Ahora lo que vamos a hacer, es CAMBIAR el fragmento que va en el conteneder
            // segun la accion que elijas en el actionbar.
            // Ahora vamos a crear un nuevo Fragmento (un clase por fuera del este archivo)
            // Y despues vamos a onOptionsItemSelected a realizar el cambio de fragmento
        }
    }


    /* ON CREATE OPTIONS MENU */
	/* ********************** */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_action_bar, menu);
        return true;
    }



    /* ON OPTION ITEM SELECTED */
	/* *********************** */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Si activamos el uso de HomeButton en la ActionBar (ver como esta hecho en onCreate)
        // Podemos referenciar al HomeButton como un item mas del menu. con una ID Particular
        if (id == android.R.id.home){
            Toast.makeText(getApplicationContext(), "Presionaste HOME", Toast.LENGTH_SHORT).show();
            FragmentManager fm = getSupportFragmentManager();				// Solicitamos el Administrador de Fragmentos de nuestra actividad
            fm.popBackStack();


            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Presionaste SETTINGS", Toast.LENGTH_SHORT).show();


            return true;
        }

        if (id == R.id.action_farg1) {
            Toast.makeText(getApplicationContext(), "Presionaste TE GUSTA", Toast.LENGTH_SHORT).show();

            // TRANSACCION DE FRAGMENTO:
            // Mira esto cuando termines de ver la transaccion de fragmento inicial
            // en el onCreate de esta actividad
            // Cuando picas TE GUSTA vamos a cargar el mismo fragmento que se carga
            // por defecto (el PlaceholderFragment)
            // La acciones que realizamos no es ADD sino REPLACE, si pusieramos ADD,
            // agrega un fragmento arriba del otro y veriamos los 2 fragmentos
            // superpuestos
            FragmentManager fm = getSupportFragmentManager();				// Solicitamos el Administrador de Fragmentos de nuestra actividad
            FragmentTransaction ft = fm.beginTransaction();					// Creamos una nueva transaccion de fragmentos
            PlaceholderFragment miFragmento = new PlaceholderFragment();	// Creamos un objeto del fragmento a cargar
            ft.replace(R.id.container, miFragmento);						// Reemplazamos fragmento en el contenedero
            //usen ft.add en lugar de ft.replace y vean que lo que hace es superponerlos
            ft.addToBackStack(null);                                        // [OPCIONAL] para proveer back navigation en fragments
            ft.commit();													// Ejecutamos la transaccion
            return true;
        }


        if (id == R.id.action_farg2) {
            Toast.makeText(getApplicationContext(), "Presionaste NO TE GUSTA", Toast.LENGTH_SHORT).show();

            // TRANSACCION DE FRAGMENTO:
            // Mira esto cuando termines de ver la transaccion de fragmento inicial
            // en el onCreate de esta actividad
            // Cuando picas NO TE GUSTA vamos a cargar el fragmento nuevo que creamo
            // llamado OtroFragmento
            // La acciones que realizamos no es ADD sino REPLACE, si pusieramos ADD,
            // agrega un fragmento arriba del otro y veriamos los 2 fragmentos
            // superpuestos
            FragmentManager fm = getSupportFragmentManager();				// Solicitamos el Administrador de Fragmentos de nuestra actividad
            FragmentTransaction ft = fm.beginTransaction();					// Creamos una nueva transaccion de fragmentos
            OtroFragmento miFragmento = new OtroFragmento();				// Creamos un objeto del fragmento a cargar
            ft.replace(R.id.container, miFragmento);						// Reemplazamos fragmento en el contenedero
            //usen ft.add en lugar de ft.replace y vean que lo que hace es superponerlos
            ft.addToBackStack(null);                                        // [OPCIONAL] para proveer back navigation en fragments
            ft.commit();													// Ejecutamos la transaccion
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*****************************************************************************
     ** FRAGMENTO **
     *****************************************************************************/
    // Este es el fragmento que se crea por defecto. Su nombre es PlaceholderFragment
    // y es el que carga en el contenedor en el onCreate de esta actividad
    // Nosotros tb vamos a crea un fragmento por fuera de la actvidad y lo
    // vamos a agregar al contenedor.
    // Este otro fragmento va a ser una clase aparte y se va a a llamar OTRO FRAGMENTO
    public static class PlaceholderFragment extends Fragment {

        /* CONSTRUCTOR */
		/* *********** */
        public PlaceholderFragment() {
        }


        /* ON CREATE VIEW */
		/* ************** */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);

            // Como YAPA vamos a cambiarle el subtitulo a la actionBar de la
            // activity.
            // Para hacerlo tenemos que pedirle  a nuestra actividad (casteada
            // como una ActionBarActivity, sino seria una actividad comun y
            // no sabe que tiene actionBar); que acceda a la actionBar de soporte
            // xq estamos con las libreias de compatibilidad
            ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
            ab.setSubtitle("Subtitulo");
            // Le pusimos el mismo subtitulo que cuando la creamos en onCreate
            return rootView;
        }
    }
}
