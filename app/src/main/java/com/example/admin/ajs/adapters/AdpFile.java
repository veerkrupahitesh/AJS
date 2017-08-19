package com.example.admin.ajs.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.ajs.MyApplication;
import com.example.admin.ajs.R;
import com.example.admin.ajs.model.FileModel;

import java.util.ArrayList;

/**
 * Created by ABC on 8/17/2017.
 */

public class AdpFile extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private ArrayList<FileModel> fileList;

    public AdpFile(Context mContext, ArrayList<FileModel> fileList) {
        this.mContext = mContext;
        this.fileList = fileList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_file, parent, false);

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {

            FileModel categoryModel = fileList.get(position);

            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.linCategoryList.setTag(categoryModel);

            myViewHolder.tvFile.setText(categoryModel.getFilePath());
//
//            if (categoryModel.isSelected()) {
//                Glide.with(mContext).load(categoryModel.getIcon1()).into(myViewHolder.imgCategory);
//                //myViewHolder.imgCategory.setImageResource(R.drawable.img_map_pin_pink);
//            } else {
//                Glide.with(mContext).load(categoryModel.getIcon2()).into(myViewHolder.imgCategory);
//                //myViewHolder.imgCategory.setImageResource(R.drawable.img_map_pin_blue);
//            }
            categoryModel.setPosition(position);
        }
    }


    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public void refreshList(ArrayList<FileModel> categoryList) {
        this.fileList = categoryList;
        notifyDataSetChanged();
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvFile;
        LinearLayout linCategoryList;

        MyViewHolder(View itemView) {
            super(itemView);


            tvFile = (TextView) itemView.findViewById(R.id.tv_pdf_file);
            tvFile.setTypeface(MyApplication.getInstance().FONT_WORKSANS_REGULAR);
            linCategoryList = (LinearLayout) itemView.findViewById(R.id.lin_pdf_file_List);
        }
    }
}
