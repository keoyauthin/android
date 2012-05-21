package com.exia.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Classe contenant quelques fonctions utils
 * 
 * @author Benoit
 * 
 */
public class Utils {

	/**
	 * @param context
	 *            Le context de l'application n�cessaire pour g�rer le GPS
	 * 
	 * @return Retourne un tableau contenant les coordonn�es de la derni�re
	 *         position courante
	 */
	public static double[] getLocationGPS(Context context) {
		double[] coord = new double[2];
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = lm
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			coord[0] = location.getLatitude();
			coord[1] = location.getLongitude();
		}
		else
		{
			location = lm
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			coord[0] = location.getLatitude();
			coord[1] = location.getLongitude();
		}
		return coord;
	}

	/**
	 * @param context
	 *            Le context de l'application n�cessaire afin de conna�tre
	 *            l'�tat du GPS
	 * @return Retourne true si le GPS est Actif
	 */
	public static boolean isGPSActif(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param context
	 *            Le context de l'application n�cessaire afin de conna�tre
	 *            l'�tat du GPS
	 */
	public static void checkGPS(final Context context) {
		if (!isGPSActif(context)) {
			AlertDialog.Builder adb = new AlertDialog.Builder(context);
			adb.setCancelable(false);
			adb.setTitle("GPS D�sactiv�");
			adb.setMessage("Le GPS doit �tre activ� si vous souhaitez utiliser cette application !");
			adb.setPositiveButton("Activer",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							context.startActivity(i);
						}
					});
			adb.show();
		}
	}

}
