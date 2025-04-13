package com.prabhakar.sataratourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    RecyclerView attractionsRecyclerView, eventsRecyclerView;
    Button btnTripPlanner, btnAbout, btnContact;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    private Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        attractionsRecyclerView = view.findViewById(R.id.attractionsRecyclerView);
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        btnTripPlanner = view.findViewById(R.id.btnTripPlanner);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnContact = view.findViewById(R.id.btnContact);

        setupAttractions();
        setupEvents();

        btnTripPlanner.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(), BookTripActivity.class))
        );

        btnAbout.setOnClickListener(v ->
                Toast.makeText(requireActivity(), "Satara Tourism App", Toast.LENGTH_LONG).show()
        );

        btnContact.setOnClickListener(v ->
                Toast.makeText(requireActivity(), "Contact: sataratourism@gmail.com", Toast.LENGTH_LONG).show()
        );

        viewPager = view.findViewById(R.id.imageSlider);
        tabLayout = view.findViewById(R.id.tabDots);

        List<Integer> images = Arrays.asList(
                R.drawable.kaas,
                R.drawable.sajjangad,
                R.drawable.thoseghar,
                R.drawable.ajinkyatara,
                R.drawable.marathon,
                R.drawable.dhol_tasha,
                R.drawable.kaas_event,
                R.drawable.yamai_temple,
                R.drawable.natraj_mandir,
                R.drawable.kaas_lake
        );

        ImageSliderAdapter adapter = new ImageSliderAdapter(requireActivity(), images);
        viewPager.setAdapter(adapter);

// Dot indicators using TabLayoutMediator
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // No text, just indicators
                }).attach();
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = currentItem + 1 == images.size() ? 0 : currentItem + 1;
                viewPager.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000); // slide every 3 seconds
            }
        };

// Start sliding
        sliderHandler.postDelayed(sliderRunnable, 3000);

// Pause on user touch
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2500); // Reset timer
            }
        });
        return view;
    }

    private void setupAttractions() {
        List<Place> places = new ArrayList<>();
        places.add(new Place("Kaas Plateau", R.drawable.kaas));
        places.add(new Place("Sajjangad Fort", R.drawable.sajjangad));
        places.add(new Place("Thoseghar Falls", R.drawable.thoseghar));
        places.add(new Place("Ajinkyatara", R.drawable.ajinkyatara));

        attractionsRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        attractionsRecyclerView.setAdapter(new PlaceAdapter(requireActivity(), places));
    }

    private void setupEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Kaas Festival", "Sep 2025", R.drawable.kaas_event));
        events.add(new Event("Satara Hill Marathon", "Oct 2025", R.drawable.marathon));
        events.add(new Event("Navratri Dhol", "Oct 2025", R.drawable.dhol_tasha));

        eventsRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        eventsRecyclerView.setAdapter(new EventAdapter(requireActivity(), events));
    }

}