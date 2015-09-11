package obj;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Jonas_Spohr on 9/10/2015.
 */
public class GeneroAdapter extends ArrayAdapter<PetGenero> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private PetGenero[] values;

    public GeneroAdapter(Context context, int resource, PetGenero[] objects) {
        super(context, resource, objects);

        this.context = context;
        this.values = objects;
    }

    public int getCount(){
        return values.length;
    }

    public PetGenero getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getGeneroNome());
        label.setPadding(15, 35, 15, 25);
        label.setTextSize(15);

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getGeneroNome());
        label.setPadding(15, 45, 15, 45);
        label.setTextSize(15);

        return label;
    }
}
