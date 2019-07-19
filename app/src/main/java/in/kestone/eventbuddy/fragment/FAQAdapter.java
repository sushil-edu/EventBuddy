package in.kestone.eventbuddy.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.faq_model.FAQList;

class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {


    Activity mActivity;
    private ArrayList<FAQList> faqLists;

     FAQAdapter(FragmentActivity activity, ArrayList<FAQList> faqList) {
        this.mActivity = activity;
        this.faqLists = faqList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.faq_cell, viewGroup, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.card.setOnClickListener(v -> {
            if (holder.expandedLl.isExpanded()) {
                holder.expandedLl.close();
            } else {
                holder.expandedLl.expand();
            }
        });

        holder.contact_us_Text.setText( faqLists.get( position ).getQuestionText());
        holder.et_invoice.setText( faqLists.get( position ).getAnswerText() );
    }

    @Override
    public int getItemCount() {
        return faqLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout card;
        ExpandableLayout expandedLl;
        TextView contact_us_Text, et_invoice;

         ViewHolder(@NonNull View itemView) {
            super( itemView );
            card =  itemView.findViewById( R.id.faq_card );
            expandedLl = itemView.findViewById( R.id.expandedLl );
            contact_us_Text = itemView.findViewById( R.id.contact_us_Text );
            et_invoice = itemView.findViewById( R.id.et_invoice );
        }
    }
}
