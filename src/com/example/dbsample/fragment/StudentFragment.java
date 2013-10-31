package com.example.dbsample.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dbsample.R;
import com.example.dbsample.dao.StudentDAO;
import com.example.dbsample.entity.StudentEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentFragment extends Fragment {

    private StudentDAO mDao;
    private StudentEntity mStudentEntity;
    private List<StudentEntity> mStudentEntityList;

    private static final String TAG = StudentFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mDao = new StudentDAO(getActivity());
        setListData();
    }

    private void setListData() {
        View view = getView();
        ListView listView = (ListView)view.findViewById(R.id.listView);

        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        mStudentEntityList = mDao.getAll();
        for (StudentEntity entity : mStudentEntityList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", entity.getName());
            map.put("memo", entity.getMemo());
            mapList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), mapList, android.R.layout.simple_list_item_2,
                new String[]{"name","memo"}, new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStudentEntity = mStudentEntityList.get(position);
                detailDialog();
            }
        });
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mStudentEntity = mStudentEntityList.get(position);
                longTapDialog();
                return true;
            }
        });
    }


    private void detailDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_student_detail, null);
        ((TextView)view.findViewById(R.id.id)).setText("id : "+mStudentEntity.getId());
        ((TextView)view.findViewById(R.id.name)).setText("name : "+mStudentEntity.getName());
        ((TextView)view.findViewById(R.id.age)).setText("age : "+mStudentEntity.getAge());
        ((TextView)view.findViewById(R.id.memo)).setText("memo : "+mStudentEntity.getMemo());

        new AlertDialog.Builder(getActivity())
        .setTitle("Detail")
        .setView(view)
        .create()
        .show();
    }

    private void longTapDialog() {
        final CharSequence[] items = {"edit", "delete"};
        new AlertDialog.Builder(getActivity())
        .setItems(items, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        editDialog();
                        break;
                    case 1:
                        deleteDialog();
                        break;
                }
            }
        })
        .create()
        .show();

    }

    private void editDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View view = inflater.inflate(R.layout.dialog_student_input, null);
        ((TextView)view.findViewById(R.id.editName)).setText(mStudentEntity.getName());
        ((TextView)view.findViewById(R.id.editAge)).setText(mStudentEntity.getAge()+"");
        ((TextView)view.findViewById(R.id.editMemo)).setText(mStudentEntity.getMemo());

        new AlertDialog.Builder(getActivity())
        .setTitle("edit student")
        .setView(view)
        .setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((TextView)view.findViewById(R.id.editName)).getText().toString();
                String age = ((TextView)view.findViewById(R.id.editAge)).getText().toString();
                String memo = ((TextView)view.findViewById(R.id.editMemo)).getText().toString();
                mStudentEntity = new StudentEntity(mStudentEntity.getId(), name, Integer.parseInt(age), memo, "".getBytes());
                mDao.update(mStudentEntity);
            }
        })
        .create()
        .show();
    }

    private void deleteDialog() {
        new AlertDialog.Builder(getActivity())
        .setTitle("Delete")
        .setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDao.delete(mStudentEntity.getId());
            }
        })
        .create()
        .show();
    }

}