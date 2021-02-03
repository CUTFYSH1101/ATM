package elico.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//RecyclerView.Adapter
//RecyclerView.ViewHolder
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder>{
    Context context;
    private final String[] functions;

    FunctionAdapter(Context context){
        this.context = context;
        functions = context.getResources().getStringArray(R.array.functions);
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get from context -> simple_list_item_1
        // set simple_list_item_1 -> context.xml.root(parent)
        // get context -> text1
        View view = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        holder.setTextView.setText(functions[position]);
    }

    @Override
    public int getItemCount() {
        return functions.length;
    }


    public static class FunctionViewHolder extends RecyclerView.ViewHolder{
        TextView setTextView;
        public FunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            setTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
