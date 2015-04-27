package com.gsb.suividevosfrais;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.BaseAdapter;

public class FraisHfAdapter extends BaseAdapter {

	ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	LayoutInflater inflater ;
	Integer key ;  // annee et mois (clé dans la liste)
	Context context ; // contexte pour gèrer la sérialisation
	
	/**
	 * Constructeur de l'adapter pour valoriser les propriétés
	 * @param context
	 * @param lesFrais
	 * @param key
	 */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais, Integer key) {
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais ;
		this.key = key ;
		this.context = context ;
	}
	
	/**
	 * retourne le nombre d'�l�ments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview � un index pr�cis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
        Button btnsuppr ;
    }
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, null) ;
			holder.txtListJour = (TextView)convertView.findViewById(R.id.txtListJour) ;
			holder.txtListMontant = (TextView)convertView.findViewById(R.id.txtListMontant) ;
			holder.txtListMotif = (TextView)convertView.findViewById(R.id.txtListMotif) ;
            holder.btnsuppr = (Button)convertView.findViewById(R.id.btnsuppr) ;
			convertView.setTag(holder) ;
		}else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtListJour.setText(lesFrais.get(index).getJour().toString()) ;
        holder.txtListMontant.setText(lesFrais.get(index).getMontant().toString()) ;
        holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;
        holder.btnsuppr.setTag(index) ;
        holder.btnsuppr.setOnClickListener(new OnClickListener(){

            //Suppression d'une ligne
            @Override
            public void onClick(View v) {
                int ligne = (Integer)v.getTag() ;
                Global.listFraisMois.get(key).getLesFraisHf().remove(ligne) ;
                Serializer.serialize(Global.filename, Global.listFraisMois, context) ;
                notifyDataSetChanged() ;
            }
        }) ;
        return convertView ;
    }

}