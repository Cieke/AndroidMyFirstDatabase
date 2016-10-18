package be.ciesites.myfirstdatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CharSequence mTitle;
    private MyDBAdapter dbAdapter;
    private ListView list;
    private Spinner faculties;
    private Button addStudent;
    private Button deleteEngineers;
    private Button deleteAllStudents;
    private EditText studentName;
    private String[] allFaculties = { "Engineering", "Business", "Arts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTitle = getTitle();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        faculties = (Spinner) findViewById(R.id.faculties_spinner);
        studentName = (EditText) findViewById(R.id.student_name);
        addStudent = (Button) findViewById(R.id.add_student);
        deleteEngineers = (Button) findViewById(R.id.delete_engineers);
        deleteAllStudents = (Button) findViewById(R.id.delete_all_students);
        list = (ListView)findViewById(R.id.student_list);

        dbAdapter = new MyDBAdapter(MainActivity.this);
        dbAdapter.open();

        addStudent.setOnClickListener(this);
        deleteEngineers.setOnClickListener(this);
        deleteAllStudents.setOnClickListener(this);

        faculties.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, allFaculties));

        loadlist();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadlist(){
        ArrayList<String> allStudents = new ArrayList<String>();
        allStudents = dbAdapter.selectAllStudents();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, allStudents);
        list.setAdapter(adapter);

    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.add_student:
                dbAdapter.insertStudent(studentName.getText().toString(), faculties.getSelectedItemPosition() +1) ;
                loadlist();
                break;
            case R.id.delete_engineers:
                dbAdapter.deleteAllEngineers();
                loadlist();

            case R.id.delete_all_students:
                    dbAdapter.deleteAllStudents();
                    loadlist();
        }
    }
}
