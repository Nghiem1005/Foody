package hcmute.nhom35.foody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddDishActivity extends AppCompatActivity {

    int REQUEST_CODE_FOLDER = 123;

    ImageButton ibtnFolder;
    ImageView imvImgDish;
    Button btnAddDishSub;

    EditText eDishName, eDishDes, eDishPrice;

    int REQUEST_CODE_ADD = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dish_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ibtnFolder = (ImageButton) findViewById(R.id.ibtnFolder);
        imvImgDish = (ImageView) findViewById(R.id.imvImgDish);

        eDishName = findViewById(R.id.eDishName);
        eDishDes = findViewById(R.id.eDishDes);
        eDishPrice = findViewById(R.id.eDishPrice);

        ibtnFolder.setOnClickListener(view -> {
            /*Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_FOLDER);*/

            ActivityCompat.requestPermissions(AddDishActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
        });

        btnAddDishSub = findViewById(R.id.btnAddDishSub);

        btnAddDishSub.setOnClickListener(view -> {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imvImgDish.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] img = byteArrayOutputStream.toByteArray();

            Intent intent4 = new Intent();
            intent4.putExtra("name", eDishName.getText().toString());
            intent4.putExtra("des", eDishDes.getText().toString());
            intent4.putExtra("price", eDishPrice.getText().toString());
            intent4.putExtra("img", img);
            intent4.putExtra("i", 1);
            setResult(RESULT_OK, intent4);
            finish();


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_FOLDER);
        } else {
            Toast.makeText(this, "Bạn không cho phép truy cập vô thư viện ảnh", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imvImgDish.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finish() {
        super.finish();
    }
}