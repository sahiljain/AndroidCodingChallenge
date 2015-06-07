package ca.sahiljain.androidcodingchallenge;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ca.sahiljain.androidcodingchallenge.models.Command;
import ca.sahiljain.androidcodingchallenge.models.CommandSet;

class MyListAdapter extends ArrayAdapter<Command> implements View.OnClickListener {

    private final ListView mListView;

    private final CommandSet commandSet;
    private final Context context;

    public MyListAdapter(Context context, ListView lv, CommandSet commandSet) {
        super(context, R.layout.list_item, commandSet.getList());
        this.context = context;
        this.commandSet = commandSet;
        mListView = lv;
    }

    public void addCommand(Command c) {
        commandSet.add(c);
        notifyDataSetChanged();
    }

    public CommandSet getCommandSet() {
        return commandSet;
    }

    @Override
    public Command getItem(int i) {
        return commandSet.get(i);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final Command currentCommand = commandSet.get(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
        mListView.setItemChecked(i, currentCommand.isActive());

        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(currentCommand.toString());
        view.setTag(R.id.command_id, i);
        view.setOnClickListener(this);
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
        return commandSet.isEmpty();
    }

    @Override
    public void onClick(View view) {
        commandSet.toggleActive((Integer) view.getTag(R.id.command_id));
        TextView mainTextView = (TextView) ((Activity) context).findViewById(R.id.text_view);
        mainTextView.setText(commandSet.getResultColorString());
        mainTextView.setBackgroundColor(commandSet.getResultColor());
        notifyDataSetChanged();
    }
}