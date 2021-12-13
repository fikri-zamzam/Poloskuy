package com.example.apk_poloskuy.myfragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

import com.example.apk_poloskuy.DashboardActivity;
import com.example.apk_poloskuy.Konek.SharedPrefrencesHelper;
import com.example.apk_poloskuy.LoginActivity;
import com.example.apk_poloskuy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //deklarasi variabel
    private EditText SFemail, SFpass, SFnama, SFno_telp, SFgender,SFalamat;
    private Button Logout;
    private SharedPrefrencesHelper sharedPrefrencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        SFemail = view.findViewById(R.id.email);
        SFpass  = view.findViewById(R.id.pass);
        SFnama = view.findViewById(R.id.nama);
        SFno_telp = view.findViewById(R.id.no_telp);
        SFgender = view.findViewById(R.id.gender);
        SFalamat = view.findViewById(R.id.alamat);

        Logout = view.findViewById(R.id.btn_logout);
        sharedPrefrencesHelper = new SharedPrefrencesHelper(SettingFragment.super.getContext());
        // Verifikasi apakah user sudah login atau belum
        String status = sharedPrefrencesHelper.getStatus();
        if (status.equals("0")) {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            getActivity().finish();
        }

        //Mengambil data dari akun user
        SFemail.setText(sharedPrefrencesHelper.getEmail());
        SFnama.setText(sharedPrefrencesHelper.getFullname());
        SFno_telp.setText(sharedPrefrencesHelper.getNomorTelfon());
        SFgender.setText(sharedPrefrencesHelper.getGender());
        SFalamat.setText(sharedPrefrencesHelper.getAlamat());

        // do code here
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrencesHelper.setEmail(null);
                sharedPrefrencesHelper.setPassword(null);
                sharedPrefrencesHelper.setFullname(null);
                sharedPrefrencesHelper.setNomorTelfon(null);
                sharedPrefrencesHelper.setGender(null);
                sharedPrefrencesHelper.setAlamat(null);
                startActivity(new Intent(SettingFragment.super.getContext(), LoginActivity.class));
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return view;
    }
}