package com.example.travelfake;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelfake.Entity.Expense;
import com.example.travelfake.Entity.Trip;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    public List<Expense> expenseList;
    public ExpenseAdapter() {
        this.expenseList = new ArrayList<>();
    }
    public void setListExpense(List<Expense> expenseList){
        this.notifyDataSetChanged();
        this.expenseList = expenseList;
    }
    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.expense_form,parent,false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.type.setText(expense.getExpense_type());
        holder.amount.setText(expense.getExpense_amount());
        holder.date.setText(expense.getExpense_timeOfExpense());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder{
        TextView type,amount,date;
        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.textView_type);
            amount = itemView.findViewById(R.id.textView_amount);
            date = itemView.findViewById(R.id.textView_date);
        }
    }
}
