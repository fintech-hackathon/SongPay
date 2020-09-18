package com.example.myapplication.mainFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page3.
     */
    // TODO: Rename and change types and number of parameters
    public static Page3 newInstance(String param1, String param2) {
        Page3 fragment = new Page3();
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

    TextView nameTextView, descriptionTextView;
    Button confirmButton;

    TextInputEditText accountEditText, nameEditText;
    TextInputLayout nameLayout;
    String Id;
    LottieAnimationView lottieAnimationView;

    // 가상의 계좌 번호입니다. (이 부분은 DB연동해서 써야 될 겁니다.)
    boolean isAccount = true;


    void init(View v) {
        nameTextView = v.findViewById(R.id.nameTextView);
        descriptionTextView = v.findViewById(R.id.descriptionTextView);
        confirmButton = v.findViewById(R.id.confirmButton);

        accountEditText = v.findViewById(R.id.accountNumberEditText);
        nameLayout = v.findViewById(R.id.nameTextInputLayout);
        nameEditText = v.findViewById(R.id.nameEditText);
        lottieAnimationView = v.findViewById(R.id.lottieAnimationView);

        // 저장한 [ID, Password]를 불러옵니다.
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Id = sharedPreferences.getString("Id", "default Name");  // 불러올려는 key, default Value

        nameTextView.setText(Id + " 님");

        // DB에 등록된 계좌번호가 없을 시 if
        if (!isAccount) {
            // 계좌번호 등록 이벤트
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (accountEditText.getText().toString().equals("") || nameEditText.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "계좌번호 or 입금자 성함을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        // TODO : 계좌 번호 등록 Request
                        Toast.makeText(getContext(), "계좌 등록이 성공되었습니다.\n" + accountEditText.getText().toString() + "\n" + nameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // 등록된 계좌번호가 있을 경우
        else {
            descriptionTextView.setText("등록된 계좌가 있습니다.");
            nameLayout.setVisibility(View.INVISIBLE);
            nameEditText.setVisibility(View.INVISIBLE);

            // TODO : 계좌 정보 불러와 Set 시키면 됩니다.
            accountEditText.setText("123-456789-12");
            accountEditText.setEnabled(false);
            lottieAnimationView.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page3, container, false);
        init(v);

        return v;
    }
}