package com.example.currconverter.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.currconverter.R;
import com.example.currconverter.databinding.CurrencyFragmentBinding;
import com.example.currconverter.model.Currency;
import com.example.currconverter.view.adapter.CurrencyListAdapter;
import com.example.currconverter.viewModel.CurrencyViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CurrencyFragment extends Fragment {
    private CurrencyFragmentBinding mBinding;
    private CurrencyViewModel mViewModel;

    public static CurrencyFragment newInstance() {
        return new CurrencyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = CurrencyFragmentBinding.inflate(getLayoutInflater(), container, false);
        mBinding.currencyList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.bottomAppBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.toConvert)
                    Navigation.findNavController(mBinding.bottomAppBar)
                            .navigate(R.id.action_currencyFragment_to_convertFragment);
                return true;
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);
        mViewModel.getAllCurrencies().observe(getViewLifecycleOwner(), (List<Currency> currencyList) -> {
            mBinding.currencyList.setAdapter(new CurrencyListAdapter(currencyList, mViewModel));
        });
    }

}