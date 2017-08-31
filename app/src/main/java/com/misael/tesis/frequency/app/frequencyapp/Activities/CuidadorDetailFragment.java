package com.misael.tesis.frequency.app.frequencyapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.misael.tesis.frequency.app.frequencyapp.R;
import com.misael.tesis.frequency.app.frequencyapp.dummy.DummyContent;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Cuidador detail screen.
 * This fragment is either contained in a {@link CuidadorListActivity}
 * in two-pane mode (on tablets) or a {@link CuidadorDetailActivity}
 * on handsets.
 */
public class CuidadorDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CuidadorDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.nombre +" "+ mItem.apellidoP + " " +mItem.apellidoM);
                ImageView imageView = (ImageView) activity.findViewById(R.id.imgCuidadorDetalle);
                Picasso.with(getContext()).load(mItem.imagen).into(imageView);

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.cuidador_detail, container, false);

        // Show the dummy content as text in a TextView.
        //Picasso.with(getApplicationContext()).load(mValues.get(position).imagen).into(holder.imageView);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.rutCuidadorList)).setText(mItem.id);
            ((TextView) rootView.findViewById(R.id.correoCuidadorList)).setText(mItem.correo);
            ((TextView) rootView.findViewById(R.id.direccionCuidadorList)).setText(mItem.direccion);
            ((TextView) rootView.findViewById(R.id.telefonoCuidadorList)).setText(mItem.telefono);
        }

        rootView.findViewById(R.id.lytCelular).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = ((TextView) rootView.findViewById(R.id.telefonoCuidadorList)).getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telefono));
                startActivity(intent);
            }
        });

        return rootView;
    }
}
