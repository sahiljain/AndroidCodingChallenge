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

    private final CommandSet mCommandSet;
    private final Context mContext;

    public MyListAdapter(Context context, ListView lv, CommandSet commandSet) {
        super(context, R.layout.list_item, commandSet.getList());
        this.mContext = context;
        this.mCommandSet = commandSet;
        mListView = lv;
    }

    public void addCommand(Command c) {
        mCommandSet.add(c);
        notifyDataSetChanged();
    }

    public CommandSet getCommandSet() {
        return mCommandSet;
    }

    @Override
    public Command getItem(int i) {
        return mCommandSet.get(i);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final Command currentCommand = mCommandSet.get(i);

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
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
        return mCommandSet.isEmpty();
    }

    @Override
    public void onClick(View view) {
        mCommandSet.toggleActive((Integer) view.getTag(R.id.command_id));
        TextView mainTextView = (TextView) ((Activity) mContext).findViewById(R.id.text_view);
        mainTextView.setText(mCommandSet.getResultColorString());
        mainTextView.setBackgroundColor(mCommandSet.getResultColor());
        notifyDataSetChanged();
    }
}