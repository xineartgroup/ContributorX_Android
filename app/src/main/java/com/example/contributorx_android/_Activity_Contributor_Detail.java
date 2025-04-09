package com.example.contributorx_android;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Objects;

public class _Activity_Contributor_Detail extends AppCompatActivity {
    int SELECT_PHOTO = 1;
    int id = -1;
    boolean status = true;
    Contributor contributor = null;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributor_detail);

        Intent intent = getIntent();

        ImageView imgContributor = findViewById(R.id.imgContributor);
        EditText txtFlatNumber = findViewById(R.id.txtFlatName);
        EditText txtFirstname = findViewById(R.id.txtFirstname);
        EditText txtLastName = findViewById(R.id.txtLastName);
        TextView lblDateStart = findViewById(R.id.lblDateStart);
        Button btnSaveContributor = findViewById(R.id.btnSaveContributor);
        Button btnStatus = findViewById(R.id.btnStatus);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnPayment = findViewById(R.id.btnPayment);

        imgContributor.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("Image/*");
            //startActivityForResult(intent1, SELECT_PHOTO);
        });

        lblDateStart.setOnClickListener(view -> {
            int year = date.get(Calendar.YEAR);
            int month = date.get(Calendar.MONTH);
            int day = date.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dlg = new DatePickerDialog(_Activity_Contributor_Detail.this,
                    android.R.style.Theme_DeviceDefault_Dialog,
                    mDateSetListener,
                    year, month, day);
            //dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dlg.show();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            date.set(year, month, day);
            String strDate = GetDateString(date);
            lblDateStart.setText(strDate);
        };

        if (intent.hasExtra("com.example.contributorx_android.ITEMINDEX")){
            //EXISTING ITEM
            id = Objects.requireNonNull(intent.getExtras()).getInt("com.example.contributorx_android.ITEMINDEX");

            contributor = _DAO_Contributor.GetContributor(id);

            if (contributor != null) {
                //setImage(imgContributor, contributor.getPictureId());
                txtFlatNumber.setText(contributor.getUserName());
                txtFirstname.setText(contributor.getFirstname());
                txtLastName.setText(contributor.getLastname());
                btnStatus.setText(contributor.isActive() ? "Deactivate" : "Activate");
                lblDateStart.setText(GetDateString(contributor.getStartDate()));
                status = !contributor.isActive();
            }
        }

        btnPayment.setOnClickListener(view -> {
            if (contributor != null) {
                Intent startIntent = new Intent(getApplicationContext(), _Activity_Payment_Confirmation.class);
                startIntent.putExtra("com.example.contributorx_android.ITEMINDEX", contributor.getId());
                startActivity(startIntent);/**/
            }
        });

        btnStatus.setOnClickListener(view -> {
            if (contributor != null) {
                contributor.setActive(status);
                _DAO_Contributor.UpdateContributor(contributor);
            }

            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });

        btnSaveContributor.setOnClickListener(view -> {
            if (contributor == null) {
                contributor = new Contributor();

                contributor.setFirstname(txtFirstname.getText().toString());
                contributor.setLastname(txtLastName.getText().toString());
                contributor.setUserName(txtFlatNumber.getText().toString());
                contributor.setStartDate(date);

                int contributorId = _DAO_Contributor.AddContributor(contributor);

                contributor.setId(contributorId);
            }
            else {
                _DAO_Contributor.UpdateContributor(contributor);
            }

            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });

        btnDelete.setOnClickListener(view -> {
            if (contributor != null) {
                _DAO_Contributor.DeleteContributor(id);
            }
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });

        btnCancel.setOnClickListener(view -> {
            Intent startIntent = new Intent(getApplicationContext(), _Activity_Contributor_List.class);
            startActivity(startIntent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgContributor = findViewById(R.id.imgContributor);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgContributor.setImageBitmap(bmp);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public void setImage(ImageView img, int pictureID) {
        if (pictureID >= 0) {
            Display screen = getWindowManager().getDefaultDisplay();
            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), pictureID, options);

            int imgWidth = options.outWidth;
            int screenWidth = screen.getWidth(); //img.getWidth(); //

            if (imgWidth > screenWidth) {
                options.inSampleSize = Math.round((float) imgWidth / (float) screenWidth);
            }

            options.inJustDecodeBounds = false;
            Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pictureID, options);
            img.setImageBitmap(scaledImg);
        }
    }

    private String GetDateString(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        return day + "/" + (month + 1) + "/" + year;
    }
}