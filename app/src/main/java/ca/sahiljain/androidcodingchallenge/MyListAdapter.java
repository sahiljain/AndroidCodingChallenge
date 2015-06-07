package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
        recalculateActiveCommands();
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
        final Command currentCommand = commandList.get(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }

        TextView tv = (TextView) view.findViewById(R.id.textView);
        setCheckBoxes();
        tv.setText(currentCommand.toString());

        TextView mainTextView = (TextView) ((Activity) context).findViewById(R.id.text_view);
        mainTextView.setText(ColorUtils.getColorString(commandList));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCommand.setActive(!currentCommand.isActive());
                recalculateActiveCommands();
                setCheckBoxes();
                TextView mainTextView = (TextView) ((Activity) context).findViewById(R.id.text_view);
                mainTextView.setText(ColorUtils.getColorString(commandList));
                notifyDataSetChanged();
                Log.d("sahil", "view selected: " + currentCommand.isActive());
            }
        });

        return view;
    }

    private void setCheckBoxes() {
        for(int x = 0; x < commandList.size(); x++) {
            mListView.setItemChecked(x, commandList.get(x).isActive());
        }
    }

    private void recalculateActiveCommands() {
        boolean absoluteFound = false;
        for(int i = commandList.size()-1; i >= 0; i--) {
            Command c = commandList.get(i);
            if(c.getCommandType() == CommandType.Absolute && c.isActive()) {
                if(absoluteFound) {
                    c.setActive(false);
                } else {
                    absoluteFound = true;
                }
            }
        }
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