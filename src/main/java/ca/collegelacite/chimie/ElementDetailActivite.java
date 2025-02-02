package ca.collegelacite.chimie;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ElementDetailActivite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_detail_activite);

        // Récupérer les données de l'Intent.
        ElementPeriodic element = (ElementPeriodic) getIntent().getSerializableExtra("element");

        // Afficher les données dans les TextView.
        TextView nameTextView = findViewById(R.id.detailNameTextView);
        TextView numberTextView = findViewById(R.id.detailNumberTextView);
        TextView periodTextView = findViewById(R.id.detailPeriodTextView);
        TextView atomicMassTextView = findViewById(R.id.detailAtomicMassTextView);
        TextView densityTextView = findViewById(R.id.detailDensityTextView);
        TextView phaseTextView = findViewById(R.id.detailPhaseTextView);

        nameTextView.setText(element.getNom());
        numberTextView.setText("Numéro                           " + element.getNuméro());
        periodTextView.setText("Période                            " + element.getPériode());
        atomicMassTextView.setText("Masse atomique   " + element.getMasseAtomique());
        densityTextView.setText("Densité                " + element.getDensité());
        phaseTextView.setText("Phase                          " + element.getPhase());

        // Charger la page Web du wiki dans le WebView.
        WebView wikiWebView = findViewById(R.id.wikiWebView);
        wikiWebView.setWebViewClient(new WebViewClient());
        wikiWebView.getSettings().setJavaScriptEnabled(true);
        wikiWebView.loadUrl(element.getWikiUrl());
    }
}
