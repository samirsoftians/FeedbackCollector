package com.softians.yogesh.newproject2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lenovo on 2/28/2017.
 */

public class CustomList extends ArrayAdapter<String> {
    private String[] ids;
    private String[] names;
    private String[] emails;
    private String[] ratings;
    private Activity context;

    public CustomList(Activity context, String[] ids, String[] names, String[] emails, String[] ratings) {
        super(context, R.layout.list_view_layout, ids);
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.emails = emails;
        this.ratings = ratings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_view_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageViewlist);

        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.happy);
        Bitmap bitmap2= BitmapFactory.decodeResource(context.getResources(),R.drawable.sa);

        if (ratings[position].equals("good"))
      {
          textViewName.setText(names[position]);
          textViewEmail.setText(emails[position]);
          imageView.setImageBitmap(bitmap);

      }
        else
      {
          textViewName.setText(names[position]);
          textViewEmail.setText(emails[position]);
          imageView.setImageBitmap(bitmap2);
      }

        return listViewItem;
    }
}
