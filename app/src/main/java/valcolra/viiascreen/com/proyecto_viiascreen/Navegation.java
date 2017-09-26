package valcolra.viiascreen.com.proyecto_viiascreen;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos.CameraIncidencia;
import valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos.GaleriaFragmento;
import valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos.ListarIncidencias;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.PostAdapter;

public class Navegation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // Atributos
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListarIncidencias ListarIncidencias = new ListarIncidencias();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenido,ListarIncidencias)
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .addToBackStack(null).commit();

        showToolbar("Lista de Incidentes");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    public void showToolbar(String tittle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarCI);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_incidencias) {
            // Handle the camera action
            Toast.makeText(getApplicationContext(), "Listar Incidencias", Toast.LENGTH_SHORT).show();
            ListarIncidencias listarIncidencias = new ListarIncidencias();
            showToolbar("Listar Incidencias");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            getSupportFragmentManager().beginTransaction().replace(R.id.contenido,listarIncidencias)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_tarea) {
            Toast.makeText(getApplicationContext(), "nav_gallery", Toast.LENGTH_SHORT).show();
            GaleriaFragmento galeriaFragmento = new GaleriaFragmento();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido,galeriaFragmento)
                    .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                    .addToBackStack(null).commit();
            showToolbar("Listar Tareas");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


        } else if (id == R.id.nav_checklist) {
            Toast.makeText(getApplicationContext(), "nav_slideshow", Toast.LENGTH_SHORT).show();
            showToolbar("Listar Checklist");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.nav_dashboard) {
            Toast.makeText(getApplicationContext(), "nav_manage", Toast.LENGTH_SHORT).show();
            showToolbar("Dashboard");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.nav_reporte) {
            Toast.makeText(getApplicationContext(), "nav_reporte", Toast.LENGTH_SHORT).show();
            showToolbar("Reporte");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        } else if (id == R.id.nav_perfil) {
            Toast.makeText(getApplicationContext(), "nav_perfil", Toast.LENGTH_SHORT).show();
            showToolbar("Perfil");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        }
        else if (id == R.id.nav_puntos) {
            Toast.makeText(getApplicationContext(), "nav_puntos", Toast.LENGTH_SHORT).show();
            showToolbar("Puntos");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        }
        else if (id == R.id.nav_usuarios) {
            Toast.makeText(getApplicationContext(), "nav_usuarios", Toast.LENGTH_SHORT).show();
            showToolbar("Usuarios");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        }else if (id == R.id.nav_rol) {
            Toast.makeText(getApplicationContext(), "rol", Toast.LENGTH_SHORT).show();
            showToolbar("Roles");
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
