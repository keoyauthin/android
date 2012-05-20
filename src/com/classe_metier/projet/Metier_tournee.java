package com.classe_metier.projet;


import android.content.Context;

import com.classes.projet.Colis;
import com.classes.projet.CoordGPS;
import com.classes.projet.Destinataire;
import com.classes.projet.Expediteur;
import com.classes.projet.Livraison;
import com.classes.projet.Livreur;
import com.classes.projet.Tournee;

public class Metier_tournee 
{	
	/*Constructeur*/
	
	public Metier_tournee() {		
		
	}
		
	GestionXML xml = new GestionXML();
	//Tournee la_tournee = new Tournee();
	Tournee la_tournee = Tournee.getInstance();
	
	public void ajout_livreur()
	{
		Livreur le_livreur=new Livreur();
		le_livreur.setId(xml.recup_id("/sdcard/tournee.xml","//livreur/id"));
		le_livreur.setNom(xml.recup_donnee("/sdcard/tournee.xml", "//livreur/nom"));
		la_tournee.setlivreur(le_livreur);
	}
	
	public void ajout_nbr_livraison()
	{
		la_tournee.setNbr(xml.nbr_livraison("/sdcard/tournee.xml"));
	}
	
	public void ajout_coord_GPS(Destinataire un_destinataire,Livraison une_livraison, Context context)
	{
		String test=un_destinataire.getNom()+un_destinataire.getRue()+un_destinataire.getCp()+un_destinataire.getVille();
		CoordGPS gps = new CoordGPS(test,context);
		une_livraison.setCoordGPS(gps);
	}
	
	public void ajout_info_livraison(Livraison la_livraison, int nbr_livraison, Context le_contexte)
	{
		la_livraison.setId(xml.recup_donnee("/sdcard/tournee.xml","//livraison["+nbr_livraison+"]/id"));
		ajout_expediteur(la_livraison,nbr_livraison);
		ajout_destinataire(la_livraison,nbr_livraison);
		ajout_nbr_colis_livraison(nbr_livraison,la_livraison);
		ajout_coord_GPS(la_livraison.getDestinataire(),la_livraison,le_contexte);
	}

	public void mai_ajout_livraison(Context le_contexte)
	{
		int nbr_livraison=0;
		ajout_nbr_livraison();
		ajout_livreur();
		ajout_date_tournee();
		
		while(la_tournee.getNbr()>nbr_livraison)
		{
			int nbr_colis=0;
			Livraison une_livraison=new Livraison();
			nbr_livraison++;
			
			ajout_info_livraison(une_livraison , nbr_livraison, le_contexte);
			
			while(une_livraison.getNbr_colis()>nbr_colis)
			{
				nbr_colis++;
				ajout_colis(une_livraison, nbr_livraison, nbr_colis);				
			}
			la_tournee.setLivraison(une_livraison);
		}
	}
	
	public void ajout_nbr_colis_livraison(int nbr_livraison, Livraison la_livraison)
	{
		la_livraison.setNbr_colis(xml.recup_id("/sdcard/tournee.xml","//livraison["+nbr_livraison+"]/colis/nombre"));
	}
	
	public void ajout_date_tournee()
	{
		la_tournee.setDate_tournee(xml.recup_donnee("/sdcard/tournee.xml", "//date_tournee"));
	}
	
	public void ajout_expediteur(Livraison la_livraison,int num_livraison)
	{
		Expediteur un_expediteur = new Expediteur();
		
		un_expediteur.setNom(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/nom"));
		un_expediteur.setRue(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/rue"));
		un_expediteur.setCp(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/cp"));
		un_expediteur.setVille(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/ville"));
		un_expediteur.setTelephone(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/telephone"));
		
		la_livraison.setExpediteur(un_expediteur);
	}
	
	public void ajout_destinataire(Livraison la_livraison,int num_livraison)
	{
		Destinataire un_destinataire= new Destinataire();
		
		un_destinataire.setNom(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/nom"));
		un_destinataire.setRue(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/rue"));
		un_destinataire.setCp(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/cp"));
		un_destinataire.setVille(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/ville"));
		un_destinataire.setComplement_adresse(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/complement_adresse"));
		un_destinataire.setTelephone(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/telephone"));
		un_destinataire.setPortable(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+num_livraison+"]/expediteur/portable"));
	
		la_livraison.setDestinataire(un_destinataire);
	}
	
	public void ajout_colis(Livraison la_livraison, int nbr_livraison, int nbr_colis)
	{
		Colis un_colis = new Colis();
		un_colis.setCode_barre(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+nbr_livraison+"]/colis/paquet["+nbr_colis+"]/code_barre"));
		un_colis.setTaille(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+nbr_livraison+"]/colis/paquet["+nbr_colis+"]/taille"));
		un_colis.setPoids(xml.recup_donnee("/sdcard/tournee.xml", "//livraison["+nbr_livraison+"]/colis/paquet["+nbr_colis+"]/poids"));
		la_livraison.setColis(un_colis);
	}
	
}
