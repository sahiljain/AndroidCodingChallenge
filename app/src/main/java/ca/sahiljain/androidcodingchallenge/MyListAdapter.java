package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sahil Jain on 15/10/2014.
 */
public class MyListAdapter extends ArrayAdapter<Command> {

    private ListView mListView;

    ArrayList<Command> commandList;
    private Context context;

    public MyListAdapter(Context context, ListView lv, ArrayList<Command> list) {
        super(context, R.layout.list_item, list);
        this.context = context;
        commandList = list;
        mListView = lv;
    }

    public void addCommand(Command c) {
        commandList.add(c);
        notifyDataSetChanged();
    }

    @Override
    public Command getItem(int i) {
        return commandList.get(i);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }

        TextView tv = (TextView) view.findViewById(R.id.textView);
        final Command currentCommand = commandList.get(i);
        final CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
        cb.setChecked(currentCommand.isActive());
        tv.setText(currentCommand.toString());

        if(currentCommand.getCommandType() == CommandType.Absolute && currentCommand.isActive()) {
            for(int x = 0; x < commandList.size(); x++) {
                Command c = commandList.get(x);
                if (c != currentCommand && c.getCommandType() == CommandType.Absolute) {
                    c.setActive(false);
                    View childAt = mListView.getChildAt(i);
                    if(childAt != null) {
                        CheckBox cb1 = (CheckBox) childAt.findViewById(R.id.checkBox);
                        if(cb1!=null) {
                            cb1.setChecked(false);
                        }
                    }
                }
            }
        }

        TextView mainTextView = (TextView) ((Activity) context).findViewById(R.id.text_view);
        mainTextView.setText(ColorUtils.getColorString(commandList));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCommand.setActive(!currentCommand.isActive());
                if(currentCommand.getCommandType() == CommandType.Absolute && currentCommand.isActive()) {
                    for(int i = 0; i < commandList.size(); i++) {
                        Command c = commandList.get(i);
                        if (c != currentCommand && c.getCommandType() == CommandType.Absolute) {
                            c.setActive(false);
                            View childAt = mListView.getChildAt(i);
                            if(childAt!=null) {
                                CheckBox cb = (CheckBox) childAt.findViewById(R.id.checkBox);
                                if(cb!=null) {
                                    cb.setChecked(false);
                                }
                            }
                        }
                    }
                }
                TextView mainTextView = (TextView) ((Activity) context).findViewById(R.id.text_view);
                mainTextView.setText(ColorUtils.getColorString(commandList));
                notifyDataSetChanged();
                Log.d("sahil", "view selected: " + currentCommand.isActive());
            }
        });

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return commandList.size() == 0;
    }
}