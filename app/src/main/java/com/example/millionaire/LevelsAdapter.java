package com.example.millionaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class LevelsAdapter extends SimpleAdapter {

    private int resource;
    private LayoutInflater inflater;
    private List<? extends Map<String, ?>> data;
    private String[] from;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public LevelsAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.resource = resource;
        this.data = data;
        this.from = from;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View container = inflater.inflate(resource, parent, false);
        TextView tvItemLevel = container.findViewById(R.id.tvItemLevel);
        Map<String, String> levelMap = (Map<String, String>) data.get(position);
        tvItemLevel.setText(levelMap.get(from[0]));
        return container;
    }
}
