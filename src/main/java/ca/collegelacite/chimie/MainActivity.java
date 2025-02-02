package ca.collegelacite.chimie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ElementPeriodic> listeDeÉléments;
    private ElementAdapter adaptateur;
    private int sélection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeDeÉléments = ElementPeriodic.lireDonnées(this);

        if (listeDeÉléments != null) {
            adaptateur = new ElementAdapter(this, listeDeÉléments);

            GridView gridView = findViewById(R.id.gridViewId);
            gridView.setAdapter(adaptateur);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    handleShortClick(i);
                }
            });

            adaptateur.notifyDataSetChanged();

            setSélection(0);
        } else {
            Toast.makeText(this, "Erreur lors de la lecture des données", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleShortClick(int position) {
        ElementPeriodic element = listeDeÉléments.get(position);

        // Afficher un Toast pour indiquer que plus d'informations seront affichées.
        String message = "Plus d'informations sur " + element.getNom();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Démarrer la nouvelle activité et passer les données à travers l'Intent.
        Intent intent = new Intent(this, ElementDetailActivite.class);
        intent.putExtra("element", element);
        startActivity(intent);
    }

    public void setSélection(int index) {
        TextView tvNuméro = findViewById(R.id.numeroTextView);
        TextView tvPériode = findViewById(R.id.periodeTextView);
        TextView tvMasse = findViewById(R.id.masseTextView);
        TextView tvDensité = findViewById(R.id.densiteTextView);
        TextView tvPhase = findViewById(R.id.phaseTextView);

        ElementPeriodic élément = this.listeDeÉléments.get(index);

        tvNuméro.setText(élément.getNuméro().toString());
        tvPériode.setText(élément.getPériode().toString());
        tvMasse.setText(élément.getMasseAtomique().toString());
        tvDensité.setText(élément.getDensité().toString());
        tvPhase.setText(élément.getPhase());

        this.sélection = index;
    }

    public class ElementAdapter extends ArrayAdapter<ElementPeriodic> {

        public ElementAdapter(Context context, ArrayList<ElementPeriodic> elements) {
            super(context, 0, elements);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_layout, parent, false);
            }

            ElementPeriodic element = getItem(position);

            TextView gridItemTextView = view.findViewById(R.id.gridItem);
            TextView subItemTextView1 = view.findViewById(R.id.subItem1);
            TextView subItemTextView2 = view.findViewById(R.id.subItem2);
            TextView subItemTextView3 = view.findViewById(R.id.subItem3);
            TextView subItemTextView4 = view.findViewById(R.id.subItem4);
            TextView subItemTextView5 = view.findViewById(R.id.subItem5);

            gridItemTextView.setText(element.getNom());
            subItemTextView1.setText("Numéro                   " + element.getNuméro());
            subItemTextView2.setText("Période                   " + element.getPériode());
            subItemTextView3.setText("Masse atomique   " + element.getMasseAtomique());
            subItemTextView4.setText("Densité                    " + element.getDensité());
            subItemTextView5.setText("Phase                      " + element.getPhase());

            return view;
        }
    }
}
