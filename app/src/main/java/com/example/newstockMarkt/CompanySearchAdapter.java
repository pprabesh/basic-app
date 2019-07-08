package com.example.newstockMarkt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newstockMarkt.Models.CompanySearchModel;
import com.example.newstockmarkt.R;

import java.util.ArrayList;

public class CompanySearchAdapter extends RecyclerView.Adapter<CompanySearchAdapter.SearchItemViewHolder> {

    Context context;
    ArrayList<CompanySearchModel> companyData;

    CompanySearchAdapter(Context context) {
        this.context = context;
    }

    CompanySearchAdapter(Context context,ArrayList<CompanySearchModel> data) {
        this.context = context;
        this.companyData = data;
    }
    public ArrayList<CompanySearchModel> getCompanyData() {
        return companyData;
    }

    public void setCompanyData(ArrayList<CompanySearchModel> companyData) {
        this.companyData = companyData;
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_item, viewGroup, false);
        return new SearchItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        CompanySearchModel companySearchModel = companyData.get(position);
        holder.companyNameTxt.setText(companySearchModel.getCompanyName());
        holder.companySymblTxt.setText(companySearchModel.getCompanySmybl());
    }

    @Override
    public int getItemCount() {
        return companyData.size();
    }

    class SearchItemViewHolder extends RecyclerView.ViewHolder {

        TextView companySymblTxt;
        TextView companyNameTxt;

        public SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTxt = itemView.findViewById(R.id.company_name);
            companySymblTxt = itemView.findViewById(R.id.company_symbol);
        }
    }
}
