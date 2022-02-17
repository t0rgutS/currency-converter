package com.example.currconverter.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currconverter.databinding.CurrencyElementBinding;
import com.example.currconverter.model.Currency;
import com.example.currconverter.viewModel.CurrencyViewModel;

import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    private List<Currency> currencyList;
    private CurrencyViewModel viewModel;

    public CurrencyListAdapter(List<Currency> currencyList, CurrencyViewModel viewModel) {
        this.currencyList = currencyList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrencyElementBinding binding = CurrencyElementBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CurrencyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        if (currency != null) {
            holder.binding.currencyView.setText(currency.getName());
            if (currency.getId() != null)
                holder.binding.sync.setChecked(true);
            holder.binding.sync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked)
                            viewModel.synchronizeCurrency(currency);
                        else
                            viewModel.removeCurrency(currency);
                    } catch (Exception e) {
                        Toast.makeText(buttonView.getContext(),
                                e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    @Override
    public int getItemCount() {
        if (currencyList == null)
            return 0;
        return currencyList.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private CurrencyElementBinding binding;

        public CurrencyViewHolder(CurrencyElementBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
