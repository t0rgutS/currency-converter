package com.example.currconverter.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.currconverter.R;
import com.example.currconverter.databinding.ConvertFragmentBinding;
import com.example.currconverter.model.Currency;
import com.example.currconverter.viewModel.ConvertViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class ConvertFragment extends Fragment {

    private ConvertViewModel mViewModel;

    private ConvertFragmentBinding mBinding;

    public static ConvertFragment newInstance() {
        return new ConvertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = ConvertFragmentBinding.inflate(getLayoutInflater(), container, false);
        mBinding.bottomAppBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.toCurrencies)
                    Navigation.findNavController(mBinding.bottomAppBar)
                            .navigate(R.id.action_convertFragment_to_currencyFragment);
                return true;
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        mViewModel = ViewModelProviders.of(this).get(ConvertViewModel.class);
        mViewModel.getAllCurrencies().observe(getViewLifecycleOwner(), (List<Currency> currencyList) -> {
            if (currencyList.size() > 0) {
                String[] currencyNames = Arrays.copyOf(currencyList.stream()
                        .map(Currency::getName).toArray(), currencyList.size(), String[].class);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.setTitle("Choose currency");
                        builder.setCancelable(false);
                        builder.setSingleChoiceItems(currencyNames, 0, null)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                        int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                        ((TextView) v).setText(currencyNames[position]);
                                    }
                                })
                                .show();
                    }
                };
                mBinding.youSendCur.setOnClickListener(listener);
                mBinding.theyGetCur.setOnClickListener(listener);
                mBinding.convert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mBinding.youSend.getText().toString().equals("")
                                && !mBinding.youSendCur.getText().equals("")
                                && !mBinding.theyGetCur.getText().equals("")) {
                            Currency source = currencyList.stream()
                                    .filter(cur -> cur.getName().contentEquals(mBinding.youSendCur.getText()))
                                    .findFirst().get();
                            Currency target = currencyList.stream()
                                    .filter(cur -> cur.getName().contentEquals(mBinding.theyGetCur.getText()))
                                    .findFirst().get();
                            mViewModel.convert(Double.parseDouble(mBinding.youSend.getText().toString()),
                                    source, target).observe(getViewLifecycleOwner(), new Observer<Double>() {
                                @Override
                                public void onChanged(Double aDouble) {
                                    mBinding.theyGet.setText(String.format("%4.3f", aDouble));
                                }
                            });
                        } else if (mBinding.youSend.getText().toString().equals(""))
                            Toast.makeText(getContext(), "'You send' field can't be empty!", Toast.LENGTH_LONG).show();
                        else if (mBinding.youSendCur.getText().equals(""))
                            Toast.makeText(getContext(), "Set source currency!", Toast.LENGTH_LONG).show();
                        else if (mBinding.theyGetCur.getText().equals(""))
                            Toast.makeText(getContext(), "Set target currency!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}