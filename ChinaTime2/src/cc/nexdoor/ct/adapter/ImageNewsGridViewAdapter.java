package cc.nexdoor.ct.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.nexdoor.android.module.News;
import cc.nexdoor.ct.activity.CTHome;
import cc.nexdoor.ct.activity.ImageNewsList;
import cc.nexdoor.ct.activity.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/* �۩w�qAdapter�A�~��BaseAdapter */
public class ImageNewsGridViewAdapter extends BaseAdapter 
{ 
  private Context activity;
  private List data;
  private ImageNewsGridAction gridAction;

  public ImageNewsGridViewAdapter(ImageNewsList activity,List data) {
	  this.activity = activity;
	  this.data = data;
	  this.gridAction = new ImageNewsGridAction(activity);
  }
  

  @Override
  public int getCount()
  {
    return data.size();
  }

  @Override
  public Object getItem(int arg0)
  {
    return data.get(arg0);
  }

  @Override
  public long getItemId(int position)
  {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent)
  {
    LayoutInflater factory = LayoutInflater.from(activity);
    /* �ϥ�grid.xml���C�@��item��Layout */
    View v = (View) factory.inflate(R.layout.grid4imagenews, null);
    /* ���oView */
    final ImageButton ib = (ImageButton) v.findViewById(R.id.imagenews);
    /* �]�w��ܪ�Image�P��r */
    ib.setBackgroundColor(0);
    News news = (News)data.get(position);
    ib.setImageBitmap( ((News)data.get(position)).getBitmap() );
    
    
    ib.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        	/*
        	 * Action
        	 */
        	// --------------------------------//
			HashMap<String, Object> args = new HashMap();
        	args.put("logInfo", position);
        	args.put("module", data.get(position));
        	gridAction.actionRegister(args);
			// --------------------------------//
        	
        }
      }
    );
    
    return v;


  } 
} 