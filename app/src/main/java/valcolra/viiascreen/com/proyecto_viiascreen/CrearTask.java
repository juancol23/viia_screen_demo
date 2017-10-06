package valcolra.viiascreen.com.proyecto_viiascreen;

import android.annotation.TargetApi;
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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CrearTask extends AppCompatActivity {
    private ProgressDialog mProgress;

    private ImageView m_imageFoto;
    private Button m_btnCrearIncidencia;
    private AutoCompleteTextView m_crear_incidencia_punto;
    private AutoCompleteTextView m_crear_incidencia_service;
    private AutoCompleteTextView m_crear_incidencia_area;
    private TextInputEditText m_crear_incidencia_observation;

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;
    private Button mOptionButton;

    private Bitmap bitmap = null;
    private CoordinatorLayout m_cordinator_crear_instancia;
    private FloatingActionButton fab;

    private String subirFotoVictor = "https://api.everlive.com/v1/vqfkdc51v767oq7x/Functions/pushEmail";
    private String subirFoto = "http://legalmovil.com/invian/welcome/onlyImage/";
    //private String subirFoto = "https://api.everlive.com/v1/e82dy3vmux1jchlu/Functions/pushEmail";
    private String subirDatosTelerik = "https://api.everlive.com/v1/vqfkdc51v767oq7x/viiascreen_incidentes/";
    //private String subirDatosTelerik = "https://api.everlive.com/v1/e82dy3vmux1jchlu/Functions/alerts";

    String[] autoCompleteStringPuntos = {"VMT-010 A", "SJM-004 A", "SUR-028 A", "SUR-026 B", "SUR-028 A", "SUR-027 A", "SUR-027 B", "LAV-004 A", "MRN-004 A", "SIN-001 A", "LAV-004 B"};
    String[] autoCompleteStringService = {"Módulo Malogrado","Mantenimiento", "Limpieza de pantalla", "Cambio de fuente", "Tablero averiado", "PC averiada", "Limpieza"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_task);

        mProgress = new ProgressDialog(this);
        AutoCompleteTextView autoCompleteTextViewPuntos = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_punto);
        AutoCompleteTextView autoCompleteTextViewService = (AutoCompleteTextView) findViewById(R.id.crear_incidencia_service);

        m_imageFoto =   findViewById(R.id.imageFoto);
        m_crear_incidencia_punto =  findViewById(R.id.crear_incidencia_punto);
        m_crear_incidencia_service =   findViewById(R.id.crear_incidencia_service);

        m_crear_incidencia_area = findViewById(R.id.crear_incidencia_area);

        m_crear_incidencia_observation =   findViewById(R.id.crear_incidencia_observation);
        m_btnCrearIncidencia = findViewById(R.id.btnCrearInc);
        mOptionButton =  findViewById(R.id.showOMenu);
        m_cordinator_crear_instancia = (CoordinatorLayout) findViewById(R.id.cordinator_crear_instancia);
        ArrayAdapter adapterPuntos = new ArrayAdapter(this, android.R.layout.select_dialog_item, autoCompleteStringPuntos);
        autoCompleteTextViewPuntos.setAdapter(adapterPuntos);

        ArrayAdapter adapterServicio = new ArrayAdapter(this, android.R.layout.select_dialog_item, autoCompleteStringService);
        autoCompleteTextViewService.setAdapter(adapterServicio);

        if(mayRequestStoragePermission()) {
            mOptionButton.setEnabled(true);
        }else {
            mOptionButton.setEnabled(false);
        }

        m_btnCrearIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Subiendo...");
                mProgress.show();
                String cPunto = m_crear_incidencia_punto.getText().toString();
                String cService = m_crear_incidencia_service.getText().toString();
                String caArea= m_crear_incidencia_area.getText().toString();

                if((cPunto.equals("") && cService.equals(""))|| (cPunto.equals("") || cService.equals(""))){
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(),"Porfavor complete los campos",Toast.LENGTH_SHORT).show();

                }else{

                    upTelerik();

                    // Toast.makeText(getApplicationContext(),"Campos llenoscampos",Toast.LENGTH_SHORT).show();


                }
            }
        });

        showToolbar("Crear Task", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

        fab= (FloatingActionButton) findViewById(R.id.fabTomarFoto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Foto Tomada ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //SubirFoto();
                showOptions();

            }
        });


    }


    private void onlyImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFoto ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_imageFoto.setImageResource(0);
                m_crear_incidencia_punto.setText("");
                m_crear_incidencia_service.setText("");

                m_crear_incidencia_observation.setText("");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(CrearIncidencia.this,"error "+error,Toast.LENGTH_LONG).show();
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
                    String usuario = ("Kelson");
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

    private void upImage(final String getFecha) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirFotoVictor ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
                m_imageFoto.setImageResource(0);
                m_crear_incidencia_punto.setText("");
                m_crear_incidencia_service.setText("");

                m_crear_incidencia_observation.setText("");
                /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearTask.this,"error "+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                if(imageToString(bitmap) == ""){
                    String imagen_camara = "";
                    String name ="Viia Task";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();

                    String area= m_crear_incidencia_area.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();

//Parametros para victor email

                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("area",area);
                    params.put("observacion" +
                            "",observacion_);
                    params.put("fecha",getFecha);

                    params.put("image","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                    params.put("email_viia","valdcolra@gmail.com");
                    Log.v("getFecha1",getFecha);
                }
                if(imageToString(bitmap) != "" ){
                    String imagen_camara = imageToString(bitmap);

                    String name ="viia_incidencia";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();

                    String area= m_crear_incidencia_area.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();

//Parametros para victor email
                    params.put("producto","viia_incidencia");
                    params.put("usuario",usuario);
                    params.put("punto",punto_);
                    params.put("area",area);
                    params.put("observacion",observacion_);

                    params.put("fecha",getFecha);
                    params.put("image","http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg");
                    params.put("email_viia","valdcolra@gmail.com");


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

    /*Enviar a telerik*/
    private void upTelerik() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,subirDatosTelerik ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Enviado Correctamente a Telerik"+response,Toast.LENGTH_LONG).show();
                mProgress.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearTask.this,"Hubo un error en el envio a telerik "+error,Toast.LENGTH_LONG).show();
                mProgress.dismiss();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                if(imageToString(bitmap) == "") {
                    String imagen_camara = "";
                     /*Obtener Fecha en dispositivo*/
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String getFecha = s.format(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());
                    String urlImagen ="";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String area= m_crear_incidencia_area.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();

                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    params.put("usuario",usuario);
                    params.put("servicio",servicio_);
                    params.put("punto",punto_);
                    params.put("observacion1","");
                    params.put("observacion2",observacion_);
                    params.put("observacion3","");
                    params.put("urlImagen2","");
                    params.put("estado","tarea");
                    params.put("area",area);
                    //params.put("responsable","resp");
                    params.put("createdTask",getFechaTelerik);
                    params.put("closedTask","");
                    // params.put("fecha",getFecha);
                    params.put("urlImagen1","");
                    upImage(getFecha);
                    onlyImage(getFecha);
                    // Log.v("FECHA","fecha: "+getFecha);
                }

                if(imageToString(bitmap) != "" ) {
                    String imagen_camara = imageToString(bitmap);
                     /*Obtener Fecha en dispositivo*/
                    SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss");
                    String getFecha = s.format(new Date());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String getFechaTelerik = simpleDateFormat.format(new Date());
                    String urlImagen ="http://legalmovil.com/invian/public/uploads/VT_"+getFecha+".jpg";
                    String usuario = ("Kelson");
                    String punto_= m_crear_incidencia_punto.getText().toString().trim();
                    String servicio_= m_crear_incidencia_service.getText().toString().trim();

                    String area= m_crear_incidencia_area.getText().toString().trim();
                    String observacion_= m_crear_incidencia_observation.getText().toString().trim();
                    params.put("usuario",usuario);
                    params.put("servicio",servicio_);
                    params.put("punto",punto_);
                    params.put("observacion1","");
                    params.put("observacion2",observacion_);
                    params.put("observacion3","");
                    params.put("urlImagen2","");
                    params.put("estado","tarea");
                    params.put("area",area);
                    //params.put("responsable","resp");
                    params.put("createdTask",getFechaTelerik);
                    params.put("closedTask","");
                    // params.put("fecha",getFecha);
                    params.put("urlImagen1",urlImagen);
                    upImage(getFecha);
                    // Log.v("FECHA","fecha: "+getFecha);
                    onlyImage(getFecha);

                }

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarCI);

    }

    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(m_cordinator_crear_instancia, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(CrearTask.this);
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

            Log.v("TAG_IMAGEN","IMAGEN GUARDADA"+newFile);

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
                        Toast.makeText(CrearTask.this, "Null", Toast.LENGTH_SHORT).show();

                    }else{
                        m_imageFoto.setImageBitmap(bitmap);

                    }



                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
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
                Toast.makeText(CrearTask.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearTask.this);
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