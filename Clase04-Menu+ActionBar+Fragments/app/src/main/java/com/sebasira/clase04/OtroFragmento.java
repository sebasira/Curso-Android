package com.sebasira.clase04;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Esto lo deberias ver cuando termines de ver la actionBar y ya hayas
// visto la transaccion de fragmento inicial, dentro del onCreate en el
// MainActiviry
public class OtroFragmento extends Fragment {

	/* CONSTRUCTOR */
	/* *********** */
	public OtroFragmento() {
		// El constructor es SI O SI requerido, no importa si no hacen nada
		// el. Pero debe estar, sino no pueden crear un fragmento para mostrar
		// dentro del contenedor
	}

	
	/* ON CREATE VIEW */
	/* ************** */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Aca le dicen el layout que vamos inflar y luego, si tuvieran
		// mas vistas dentro de ese laytout (botones, edittext, etc) tb
		// las manejan aca, como vinimos haciendo todas las otras clases
		View rootView = inflater.inflate(R.layout.layout_otro_fragmento, container,
				false);
		
		// Como YAPA vamos a cambiarle el subtitulo a la actionBar de la
		// activity.
		// Para hacerlo tenemos que pedirle  a nuestra actividad (casteada
		// como una ActionBarActivity, sino seria una actividad comun y
		// no sabe que tiene actionBar); que acceda a la actionBar de soporte
		// xq estamos con las libreias de compatibilidad 
		ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
		ab.setSubtitle("Otro Fragmento!");
		return rootView;
	}
}
