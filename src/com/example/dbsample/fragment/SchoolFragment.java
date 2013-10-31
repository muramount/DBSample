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
import com.example.dbsample.dao.SchoolDAO;
import com.example.dbsample.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolFragment extends Fragment {

    private static final String TAG = SchoolFragment.class.getName();

    private SchoolDAO mDao;
    private SchoolEntity mSchoolEntity;
    private List<SchoolEntity> mSchoolEntityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_school, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        mDao = new SchoolDAO(getActivity());
        setListData();
    }

    private void setListData() {
        View view = getView();
        ListView listView = (ListView)view.findViewById(R.id.listView);

        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        mSchoolEntityList = mDao.getAll();
        for (SchoolEntity entity : mSchoolEntityList) {
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
                mSchoolEntity = mSchoolEntityList.get(position);
                detailDialog();
            }
        });
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSchoolEntity = mSchoolEntityList.get(position);
                longTapDialog();
                return true;
            }
        });
    }

    private void detailDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_school_detail, null);
        ((TextView)view.findViewById(R.id.id)).setText("id : "+mSchoolEntity.getId());
        ((TextView)view.findViewById(R.id.name)).setText("name : "+mSchoolEntity.getName());
        ((TextView)view.findViewById(R.id.phone)).setText("phone : "+mSchoolEntity.getPhone());
        ((TextView)view.findViewById(R.id.address)).setText("address : "+mSchoolEntity.getAddress());
        ((TextView)view.findViewById(R.id.memo)).setText("memo : "+mSchoolEntity.getMemo());

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
        final View view = inflater.inflate(R.layout.dialog_school_input, null);
        ((TextView)view.findViewById(R.id.editName)).setText(mSchoolEntity.getName());
        ((TextView)view.findViewById(R.id.editPhone)).setText(mSchoolEntity.getPhone());
        ((TextView)view.findViewById(R.id.editAddress)).setText(mSchoolEntity.getAddress());
        ((TextView)view.findViewById(R.id.editMemo)).setText(mSchoolEntity.getMemo());

        new AlertDialog.Builder(getActivity())
        .setTitle("edit school")
        .setView(view)
        .setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = ((TextView)view.findViewById(R.id.editName)).getText().toString();
                String phone = ((TextView)view.findViewById(R.id.editPhone)).getText().toString();
                String address = ((TextView)view.findViewById(R.id.editAddress)).getText().toString();
                String memo = ((TextView)view.findViewById(R.id.editMemo)).getText().toString();
                mSchoolEntity = new SchoolEntity(mSchoolEntity.getId(), name, phone, address, memo, "".getBytes());
                mDao.update(mSchoolEntity);
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
                mDao.delete(mSchoolEntity.getId());
            }
        })
        .create()
        .show();
    }

}