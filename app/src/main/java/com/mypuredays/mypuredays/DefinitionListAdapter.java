package com.mypuredays.mypuredays;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class DefinitionListAdapter extends ArrayAdapter<ItemDefinition>{

    private final Context context;
    private final ArrayList<ItemDefinition> values;
    private ViewHolder viewHolder;
    private final int resourceId;

    public DefinitionListAdapter(Context context, int resourceId, ArrayList<ItemDefinition> values) {
        super(context, resourceId, values);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.values = values;
        this.resourceId = resourceId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);

            TextView name = (TextView)convertView.findViewById(R.id.item_name);
            Switch offOn = (Switch)convertView.findViewById(R.id.on_off_switch);


            name.setText(values.get(position).getName().toString());


        }else
        {

        }
//        ItemDefinition list_obj=values.get(position);
//        viewHolder.tv_date.setText(list_obj.get_cleanNotification());
//        viewHolder.tv_event.setText(list_obj.get_minPeriodLength());

        return convertView;
    }





    public class ViewHolder {

        TextView tv_event;
        TextView tv_date;

    }

}