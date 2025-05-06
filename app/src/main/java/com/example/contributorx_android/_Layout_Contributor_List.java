package com.example.contributorx_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class _Layout_Contributor_List extends BaseAdapter {
    List<Contributor> contributors;
    LayoutInflater mInflater;
    Context context;
    public String error;

    public _Layout_Contributor_List(Context c, List<Contributor> t) {
        contributors = t;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = c;
        error = "";
    }

    @Override
    public int getCount() {
        return contributors.size();
    }

    @Override
    public Object getItem(int i) {
        return contributors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_contributor_list, parent, false);

            holder = new ViewHolder();
            holder.lblFlatNumber = convertView.findViewById(R.id.lblFlatNumber);
            holder.lblFullName = convertView.findViewById(R.id.lblFullName);
            holder.lblAmountDue = convertView.findViewById(R.id.lblAmountDue);
            holder.lblStatus = convertView.findViewById(R.id.lblStatus);
            holder.imgContributor = convertView.findViewById(R.id.imgContributor);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contributor contributor = contributors.get(i);

        holder.lblFlatNumber.setText(contributor.getUserName());
        holder.lblFullName.setText(context.getString(R.string.full_name_display, contributor.getFirstname(), contributor.getLastname()));
        holder.lblStatus.setText(contributor.isActive() ? "Active" : "Inactive");
        holder.lblStatus.setTextColor(contributor.isActive() ? Color.BLUE : Color.RED);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            APIExpectationsResponse response = _DAO_Expectation.GetExpectationsForContributor(contributor.getId());

            handler.post(() -> {
                if (response.getIsSuccess() && response.getExpectations() != null) {
                    float totalAmountOwed = 0.00f;
                    float totalAmountPaid = 0.00f;
                    for (int index = 0; index < response.getExpectations().size(); index++) {
                        Expectation expectation = response.getExpectations().get(index);
                        totalAmountPaid += expectation.getAmountPaid();
                        Contribution contribution = _DAO_Contribution.GetContribution(expectation.getContributionId());
                        if (contribution != null)
                            totalAmountOwed += contribution.getAmount();
                    }

                    holder.lblAmountDue.setText(context.getString(R.string.amount_display, (totalAmountOwed > totalAmountPaid) ? totalAmountOwed - totalAmountPaid : 0.00f));
                }
            });
        });

        setImage(holder.imgContributor, getPicturePath(contributor.getPicture()));

        return convertView;
    }

    private String getPicturePath(String picture) {
        return String.format(context.getString(R.string.pictures_folder), picture);
    }

    // ViewHolder class for efficient view reuse
    static class ViewHolder {
        TextView lblFlatNumber;
        TextView lblFullName;
        TextView lblAmountDue;
        TextView lblStatus;
        ImageView imgContributor;
    }

    public void setImage(ImageView img, String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                //Toast.makeText(context, imagePath, Toast.LENGTH_SHORT).show();

                // Get screen width
                int screenWidth;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    WindowMetrics metrics = windowManager.getCurrentWindowMetrics();
                    screenWidth = metrics.getBounds().width();
                } else {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    screenWidth = displayMetrics.widthPixels;
                }

                // Decode with inJustDecodeBounds=true to check dimensions
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, options);

                // Calculate proper inSampleSize
                int imgWidth = options.outWidth;
                options.inSampleSize = Math.max(1, imgWidth / screenWidth);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                Bitmap scaledImg = BitmapFactory.decodeFile(imagePath, options);

                if (scaledImg != null) {
                    img.setImageBitmap(scaledImg);
                } else {
                    error += "Failed to decode image. ";
                }
            } else {
                error += "File does not exist! ";
            }
        }
    }
}
