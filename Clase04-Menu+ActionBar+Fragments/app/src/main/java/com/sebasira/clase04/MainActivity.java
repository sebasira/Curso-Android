package com.sebasira.clase04;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    protected ActionMode mActionMode;

	/* ON CREATE */
	/* ********* */
    /**
     * En este caso vamos a trabajar con una Actividad SIN FRAGMENT. Lo mismo
     * que antes haciamos dentro de onCreateView del fragment, ahora lo hacemos
     * dentro del onCreate de la Activity.
     * A diferencia del fragmento, aca no tenemos un contenedor, por tanto no
     * hay una "vista madre" donde estan todas las otras vistas, y es por eso
     * que podemos llamar directamente a findViewById.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		/*
		 * Para ingresar al MENU CONTEXTUAL, debemos registrar que vista es la que
		 * va a acceder (puede ser una o muchas)
		 * Y luego, asi como tenemos los metodos del MENU DE OPCIONES onCreateOptionsMenu
		 * y onOptionsItemsSelected; tendremos los análogos para el menu contextual
		 * Cuando registramos una vista para el menú contextual, para ingresar a dicho
		 * menu debemos hacer un tap-largo sobre la vista
		 */
        Button btn = (Button) findViewById(R.id.btnMenuContextual);
        registerForContextMenu(btn);


		/*
		 * Otra manera mas moderna de mostrar un menu contextual, es hacerlo "dentro"
		 * de la ActionBar. A esto se lo llama pasar a actionMode. Es un poco mas
		 * complejo.
		 * No deben registrar la vista, sino que simplemente deben "iniciar el action mode"
		 * Y esto lo pueden hacer como quieran, por ejemplo nosotros lo vamos a hacer en
		 * el LONG CLICK de otro boton
		 */
        Button btnAM = (Button) findViewById(R.id.btnActionMode);
        btnAM.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if (mActionMode == null){
                    // Solo ingreamos al modo si aun no estamos en él.
                    mActionMode = startSupportActionMode(myActionMode);
                }
                return false;
            }
        });


        /*
         * Boton para ir a nuestra proxima activity donde exploraremos la action bar y
         * los fragmentos
         */
        Button btnActionActivity = (Button) findViewById(R.id.btnActionActivity);
        btnActionActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityActionBar.class);
                startActivity(i);
            }
        });

    }

/**********************************************************************************/
/** MENU DE OPCIONES **/
/**********************/

	/* ON CREATE OPTIONS MENU */
	/* ********************** */
    /**
     * Cuando creamos un proyecto nuevo, automáticamente nos crea un menu de
     * opciones con una UNICA opcion ("Settings").
     * Al menu de opciones se accede con el boton de hardware del equipo; o
     * si es uno de los mas nuevos donde ya no contamos con este botón, en la barra
     * de accion (ActionBar) encontraremos un icon de tres puntos en linea vertical
     * (se lo conoce como action-overflow). Incluso el mismo podría encontrarse en
     * la barra de navegacion (NavigationBar)
     *
     * Como su nombre lo indica, este método se llama "Al Crear el Menu de Opciones"
     * Fijense que lo único que hacemos es decirle cual es el xml del menu que vamos
     * a inflar.
     *
     * La definicion del menú (XML) la encontraremos dentro de los recurso, en la
     * carpeta menu (res->menu).
     *
     * Noten la similitud con inflar el layout de un fragmento.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


	/* ON OPTIONS MENU ITEM SELECTED */
	/* ***************************** */
    /**
     * Como su nombre lo indica, aquí accedemos cuando "Se Selecciona un Item
     * del Menu de Opciones"
     *
     * Como argumento tenemos un MenuItem, luego a ese item le extraemos su ID
     * (esto sería la ID del item que fue seleccionado) y luego simplemente
     * nos fijamos a cual de todoas las IDs de los items (definidos en el
     * XML del menu) corresponde.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Seleccionaste Settings", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



/**********************************************************************************/
/** MENU CONTEXTUAL - FLOATING **/
/*******************************/

	/* ON CREATE CONTEXT MENU */
	/* ********************** */
    /**
     * Analogo al del menu de opciones
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }


	/* ON CONTEXT MENU ITEM SELECTED */
	/* ***************************** */
    /**
     * Analogo al del menu de opciones
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.borrar) {
            Toast.makeText(getApplicationContext(), "Seleccionaste Borrar", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.copiar) {
            Toast.makeText(getApplicationContext(), "Seleccionaste Copiar", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



/**********************************************************************************/
/** MENU CONTEXTUAL - ACTION MODE **/
/***********************************/
    private ActionMode.Callback myActionMode = new ActionMode.Callback() {

        /**
         * Como su nombre indica se llama cuando ingresamos al ActionMode.
         * Y como tal vez imaginen aca lo que hacemos es decir qué menu inflar
         * al igual que el onCreate del Option Menu y del Contextual Menu.
         *
         * Para que vean lo simple que es pasar de un menu contextual al
         * menu contextual en modo accion, vamos a inflar el mismo menu
         * que el menu contextual
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
            return true;
        }

        /**
         * Se llama luego de llamar al onCreate. Por ahora no lo vamos a usar
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }


        /**
         * Se llama cada vez que seleccionamos una de las opciones del ActionMode.
         * Noten que ademas de realizar las acciones que queramos, para salir
         * de este modo, debemos llamar a mode.finnish().
         *
         * Aca lo que hicimos fue copiar/pegar lo mismo que hacemos cuando
         * seleccionamos un item del memu contextual
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.borrar) {
                Toast.makeText(getApplicationContext(), "Seleccionaste Borrar", Toast.LENGTH_SHORT).show();
                mode.finish();
                return true;
            }

            if (id == R.id.copiar) {
                Toast.makeText(getApplicationContext(), "Seleccionaste Copiar", Toast.LENGTH_SHORT).show();
                mode.finish();
                return true;
            }

            return false;
        }

        /**
         * Se llama cada vez que salimos del modo action, Lo que hacemos aca es
         * nulificar el objeto que contenia a nuestro modo accion, de modo de
         * volver a permitir el ingreso
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
}
