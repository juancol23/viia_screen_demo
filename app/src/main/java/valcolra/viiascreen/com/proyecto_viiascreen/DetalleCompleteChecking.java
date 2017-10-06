package valcolra.viiascreen.com.proyecto_viiascreen;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import valcolra.viiascreen.com.proyecto_viiascreen.adapter.CheckingAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar.ListChecking;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DetalleCompleteChecking extends AppCompatActivity {
    /*Declara variables*/
    private FloatingActionButton m_fabCerrarChecking;
    private ImageView m_imageDetalleCompleteChecking;
    private TextInputEditText m_detalle_Id;
    private TextInputEditText m_detalle_punto;
    private TextInputEditText m_detalle_area;
    private TextInputEditText m_detalle_estado;
    private TextInputEditText m_detalle_ClosedCheck;
    private TextInputEditText m_detalle_CreateAt;
    private TextInputEditText m_detalle_observacion1;
    private TextInputEditText m_detalle_observacion2;
    private TextInputEditText m_detalle_responsable;
    private ProgressDialog mProgress;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private Bitmap bitmap = null;
    private String mPath;

    private String URL_V_CHECKING = "https://api.everlive.com/v1/vqfkdc51v767oq7x/viiascreen_checking/";
    private Dialog MyDialog;
    private String subirFoto = "http://legalmovil.com/invian/welcome/onlyImage/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_complete_checking);
        mProgress = new ProgressDialog(this);

        showToolbar("Punto", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        /*Obtener Data*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        /*Obtener data y guardarla*/
        Bitmap bitmap = getIntent().getParcelableExtra("image");
        final String Id = (String)extras.get("Id");
        String punto = (String)extras.get("punto");
        String area = (String)extras.get("area");
        String estado = (String)extras.get("estado");

        String ClosedCheck = (String)extras.get("ClosedCheck");
        String CreateAt = (String)extras.get("CreateAt");
        String observacion1 = (String)extras.get("observacion1");
        String observacion2 = (String)extras.get("observacion2");
        String responsable = (String)extras.get("responsable");

        /*referencias vista*/
        m_fabCerrarChecking = findViewById(R.id.fabCerrarChecking);
        m_imageDetalleCompleteChecking = findViewById(R.id.imageDetalleCompleteChecking);
        m_detalle_Id = findViewById(R.id.detalle_Id);
        m_detalle_punto = findViewById(R.id.detalle_punto);
        m_detalle_area = findViewById(R.id.detalle_area);
        m_detalle_estado = findViewById(R.id.detalle_estado);
        m_detalle_ClosedCheck = findViewById(R.id.detalle_ClosedCheck);
        m_detalle_CreateAt = findViewById(R.id.detalle_CreateAt);
        m_detalle_observacion1 = findViewById(R.id.detalle_observacion1);
        m_detalle_observacion2 = findViewById(R.id.detalle_observacion2);
        m_detalle_responsable = findViewById(R.id.detalle_responsable);

        //los datos recuperados de la otra actividad lo enviamos y seteamos
        /*Setear data*/
        m_detalle_Id.setText(Id);
        m_imageDetalleCompleteChecking.setImageBitmap(bitmap);
        m_detalle_punto.setText(punto);
        m_detalle_estado.setText(estado);
        m_detalle_area.setText(area);
        m_detalle_ClosedCheck.setText(ClosedCheck);
        m_detalle_CreateAt.setText(CreateAt);
        m_detalle_observacion1.setText(observacion1);
        m_detalle_observacion2.setText(observacion2);
        m_detalle_responsable.setText(responsable);

        final String cerrado = m_detalle_estado.getText().toString();

        if(cerrado.equals("cerrado")){
            m_fabCerrarChecking.setVisibility(View.GONE);
        }

        m_fabCerrarChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cerrar Checking "+cerrado, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                MyCustomAlertDialog(Id);

            }

        });

        //referenciamos las vistar editar y guardar

        final FloatingActionButton  m_fab_imageDetalle_checking_editar = findViewById(R.id.fab_imageDetalle_checking_editar);
        final FloatingActionButton m_fab_imageDetalle_checking_save = findViewById(R.id.fab_imageDetalle_checking_save);

        //agregamos un escucador y hacemos editable los campos solo los necesarios
        m_fab_imageDetalle_checking_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_detalle_observacion1.setEnabled(true);
                m_detalle_punto.setEnabled(true);

                m_fab_imageDetalle_checking_editar.setVisibility(View.GONE);
                m_fab_imageDetalle_checking_save.setVisibility(View.VISIBLE);
            }
        });
        //agregamos un escucador y hacemos editable los campos solo los necesarios

        m_fab_imageDetalle_checking_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_detalle_observacion1.setEnabled(true);
                m_detalle_punto.setEnabled(true);

                m_fab_imageDetalle_checking_save.setVisibility(View.GONE);
                m_fab_imageDetalle_checking_editar.setVisibility(View.VISIBLE);
            }
        });




    }
    //agregamos una alerta tipo bootstrap o materialize
    public void MyCustomAlertDialog(final String Id){
        MyDialog = new Dialog(DetalleCompleteChecking.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialogcerrarchecking);

       // MyDialog.setTitle("My Custom Dialog");
        Button close = MyDialog.findViewById(R.id.m_dialog_custom_checking_cerrar_close);
        FloatingActionButton m_fabTakePhotoCheckingCierre = MyDialog.findViewById(R.id.fabTakePhotoCheckingCierre);

        m_fabTakePhotoCheckingCierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        /*Snackbar.make(v, "Foto Tomada ", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();*/
                //SubirFoto();
                //Toast.makeText(DetalleCompleteChecking.this, "Foto No asd Correctamente", Toast.LENGTH_SHORT).show();
                showOptions();
            }
        });
        if(mayRequestStoragePermission()) {
            m_fabTakePhotoCheckingCierre.setEnabled(true);
        }else {
            m_fabTakePhotoCheckingCierre.setEnabled(false);
        }
        //cerrarmos y realizamo sun aaccion
        close.setEnabled(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText m_dialog_custom_checking_cerrar_observacion2 = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_observacion2);
                TextInputEditText m_dialog_custom_checking_cerrar_responsable = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_responsable);

                String observacion = m_dialog_custom_checking_cerrar_observacion2.getText().toString();
                String responsable = m_dialog_custom_checking_cerrar_responsable.getText().toString();

                if(observacion.equals("")||responsable.equals("")){
                    Toast.makeText(DetalleCompleteChecking.this, "Llena Todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    cerrarChecking(Id);
                    MyDialog.cancel();
                    mProgress.setMessage("Subiendo...");
                    mProgress.show();
                }

            }
        });
        MyDialog.show();

    }
    //este metodo es para cerrar el checking deacuaerdo a los servicios
    public void cerrarChecking(String ID){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,URL_V_CHECKING+ID ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //olocamos un mensaje del resutlado que obtenemos a la hora que nos responde
                Toast.makeText(getApplicationContext(),"Actualizado Correctamente "+response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_fabCerrarChecking.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleCompleteChecking.this,"Hubo un error al actualizar"+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                TextInputEditText m_dialog_custom_checking_cerrar_observacion2 = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_observacion2);
                TextInputEditText m_dialog_custom_checking_cerrar_responsable = MyDialog.findViewById(R.id.dialog_custom_checking_cerrar_responsable);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String getFechaTelerik = simpleDateFormat.format(new Date());
                String observacion2 = m_dialog_custom_checking_cerrar_observacion2.getText().toString();
                String responsable= m_dialog_custom_checking_cerrar_responsable.getText().toString();
                params.put("estado","cerrado");
                params.put("responsable",responsable);

                params.put("observacion2",observacion2);
                params.put("closedCheck",getFechaTelerik);
                onlyImage(getFechaTelerik);
                params.put("urlImagen2","http://legalmovil.com/invian/public/uploads/VT_"+getFechaTelerik+".jpg");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    //mandar la imagen al servicio para guardarlo en el servidor
    private void onlyImage(final String getFecha) {
                                        //metodo para el post y subir la foto
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFoto ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgress.dismiss();
                /*Intent i = new Intent(DetalleCompleteChecking.this,MainActivity.class);
                startActivity(i);*/
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                if(imageToString(bitmap) == ""){
                    String imagen_camara = "";
                    params.put("fecha",getFecha);
                    params.put("image",imagen_camara);
                }
                if(imageToString(bitmap) != "" ){
                    String imagen_camara = imageToString(bitmap);
                    params.put("fecha",getFecha);
                    params.put("image",imagen_camara);
                }

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }
    //mostrar el titulo
    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

    }


      /*START METHOD FOR CAMERA*/

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
          /*  Snackbar.make(m_cordinator_crear_checking, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });*/
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(DetalleCompleteChecking.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Log.v("TAG_PHOTO_CHECKING","Imagen Guardada en: "+newFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                    bitmap = BitmapFactory.decodeFile(mPath);

                    if(bitmap==null){
                        Toast.makeText(DetalleCompleteChecking.this, "Foto No Tomada Correctamente", Toast.LENGTH_SHORT).show();

                    }else{
                        ImageView m_imageCloseChecking = MyDialog.findViewById(R.id.imageCloseChecking);
                        m_imageCloseChecking.setImageBitmap(bitmap);

                    }

                case SELECT_PICTURE:
                    // Uri path = data.getData();
                    // bitmap.setImageURI(path);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(DetalleCompleteChecking.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                //mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetalleCompleteChecking.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    /*END FOR METHOD CAMERA*/

    private String imageToString(Bitmap bitmap){
        if(bitmap==null){
            return "";
        }else{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            bitmap.createScaledBitmap(bitmap,5,3,true);
            return Base64.encodeToString(imgBytes,Base64.DEFAULT);
        }

    }






}
