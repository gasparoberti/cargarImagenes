package com.example.cargarimagenes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    final int COD_GALERIA = 10;
    final int COD_CAMARA = 20;
    ImageView imagenId;
    Button btnCargarImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagenId = findViewById(R.id.imagenId);
        btnCargarImg = findViewById(R.id.btnCargarImg);
    }

    public void onclick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        final CharSequence[] opciones = {"Cámara", "Galería", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MainActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Cámara")){
                    tomarFoto();
                }else {
                    if (opciones[i].equals("Galería")){
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),COD_GALERIA);
                    }else {
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, COD_CAMARA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case COD_GALERIA :
                    Uri miPath = data.getData();
                    imagenId.setImageURI(miPath);
                    break;
                case COD_CAMARA :
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imagenId.setImageBitmap(bitmap);
                    break;
            }
        }
    }
}
