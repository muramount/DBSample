
package com.example.dbsample;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dbsample.dao.SchoolDAO;
import com.example.dbsample.dao.StudentDAO;
import com.example.dbsample.entity.SchoolEntity;
import com.example.dbsample.entity.StudentEntity;
import com.example.dbsample.fragment.SchoolFragment;
import com.example.dbsample.fragment.StudentFragment;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    private SchoolDAO mSchoolDAO;
    private StudentDAO mStudentDAO;

    public static final int MENU_SCHOOL_INSERT = 0;
    public static final int MENU_STUDENT_INSERT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSchoolDAO = new SchoolDAO(this);
        mStudentDAO = new StudentDAO(this);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Scool")
                .setTabListener(new MainTabListener<SchoolFragment>(this, "school", SchoolFragment.class)));
        actionBar.addTab(actionBar.newTab().setText("Student")
                .setTabListener(new MainTabListener<StudentFragment>(this, "student", StudentFragment.class)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SCHOOL_INSERT, 0, "add school");
        menu.add(0, MENU_STUDENT_INSERT, 0, "add student");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SCHOOL_INSERT:
                inputSchoolDialog();
                return true;
            case MENU_STUDENT_INSERT:
                inputStudentDialog();
                return true;
        }
        return false;
    }

    private void inputSchoolDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog_school_input, null);

        new AlertDialog.Builder(this)
        .setTitle("add school")
        .setView(view)
        .setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((TextView)view.findViewById(R.id.editName)).getText().toString();
                String phone = ((TextView)view.findViewById(R.id.editPhone)).getText().toString();
                String address = ((TextView)view.findViewById(R.id.editAddress)).getText().toString();
                String memo = ((TextView)view.findViewById(R.id.editMemo)).getText().toString();
                SchoolEntity schoolEntity = new SchoolEntity(0, name, phone, address, memo, "".getBytes());
                mSchoolDAO.insert(schoolEntity);
            }
        })
        .create()
        .show();
    }

    private void inputStudentDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog_student_input, null);

        new AlertDialog.Builder(this)
        .setTitle("add student")
        .setView(view)
        .setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((TextView)view.findViewById(R.id.editName)).getText().toString();
                String age = ((TextView)view.findViewById(R.id.editAge)).getText().toString();
                String memo = ((TextView)view.findViewById(R.id.editMemo)).getText().toString();
                StudentEntity studentEntity = new StudentEntity(0, name, Integer.parseInt(age), memo, "".getBytes());
                mStudentDAO.insert(studentEntity);
            }
        })
        .create()
        .show();
    }

    /**
     * ActionBar のタブリスナー
     */
    public static class MainTabListener<T extends Fragment> implements TabListener {

        private Fragment fragment;
        private final Activity activity;
        private final String tag;
        private final Class<T> cls;

        public MainTabListener(Activity activity, String tag, Class<T> cls){
            Log.d(TAG, "MainTabListene#Constructor");
            this.activity = activity;
            this.tag = tag;
            this.cls = cls;
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Log.d(TAG, "MainTabListene#onTabReselected");
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            Log.d(TAG, "MainTabListene#onTabSelected");
            if (fragment == null) {
                fragment = Fragment.instantiate(activity, cls.getName());
                ft.add(android.R.id.content, fragment, tag);
            } else {
                ft.attach(fragment);
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            Log.d(TAG, "MainTabListene#onTabUnselected");
            if (fragment != null) {
                ft.detach(fragment);
            }
        }
    }

}
