package com.example.myapplication.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.util.NetworkTask;
import com.google.android.material.textfield.TextInputEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

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
    AutoCompleteTextView bankDropdownMenu;
    Button confirmButton;

    TextInputEditText accountEditText, nameEditText;

    TextInputLayout nameLayout, accountInputLayout, bankInputLayout;
    LottieAnimationView lottieAnimationView;

    String Id;


    // 가상의 계좌 번호입니다. (이 부분은 DB연동해서 써야 될 겁니다.)
    String bank = "";
    String accountNumber = "";
    String name = "";
    String result = null;
    String url1 = "http://115.85.180.70:3001/user/updateAccount";
    String url2 = "http://115.85.180.70:3001/user/getinfo";

    void init(View v) throws JSONException, ExecutionException, InterruptedException {
        nameTextView = v.findViewById(R.id.nameTextView);
        descriptionTextView = v.findViewById(R.id.descriptionTextView);
        confirmButton = v.findViewById(R.id.confirmButton);

        accountEditText = v.findViewById(R.id.accountNumberEditText);
        nameEditText = v.findViewById(R.id.nameEditText);
        bankDropdownMenu = v.findViewById(R.id.bankDropdownMenu);
        lottieAnimationView = v.findViewById(R.id.lottieAnimationView);

        nameLayout = v.findViewById(R.id.nameTextInputLayout);
        accountInputLayout = v.findViewById(R.id.accountInputLayout);
        bankInputLayout = v.findViewById(R.id.bankInputLayout);

        // 은행선택용 메뉴
        bankDropdownMenu.setInputType(InputType.TYPE_NULL); // 선택 시(focused), 키보드가 안뜨게.
        bankDropdownMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu bankSelectMenu = new PopupMenu(getContext(), view);

                // TODO : 은행사들을 메뉴에 추가합니다.
                bankSelectMenu.getMenu().add("산업은행");
                bankSelectMenu.getMenu().add("기업은행");
                bankSelectMenu.getMenu().add("국민은행");
                bankSelectMenu.getMenu().add("하나은행");
                bankSelectMenu.getMenu().add("농협은행");
                bankSelectMenu.getMenu().add("우리은행");
                bankSelectMenu.getMenu().add("하나은행");
                bankSelectMenu.getMenu().add("신한은행");
                bankSelectMenu.show();

                bankSelectMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // TODO : 드랍다운 메뉴를 선택하면, getTitle(), 제목이 bank변수에 담깁니다. -> bank 이용하시면 되요.
                        bankDropdownMenu.setText(menuItem.getTitle().toString());
                        bank = menuItem.getTitle().toString();
                        return onOptionsItemSelected(menuItem);
                    }
                });
            }
        });

        final JSONObject object = new JSONObject();

        // 저장한 [ID, Password]를 불러옵니다.
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Id = sharedPreferences.getString("Id", "default Name");  // 불러올려는 key, default Value

        object.put("u_id", Id);
        Log.i("msg", Id);

        NetworkTask parser = new NetworkTask(url2, object, "POST");
        result = parser.execute().get();
        JSONObject obj = new JSONObject(result);



        accountNumber = obj.get("u_account").toString();
        bank = obj.get("u_bank").toString();
        nameTextView.setText(Id + " 님");


        // DB에 등록된 계좌번호가 없을 시 if
        if (accountNumber.isEmpty() || accountNumber.equals("null")) {
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            // 계좌번호 등록 이벤트
                            try {
                                //TODO : 은행사 추가 하시면 되겠습니다.

                                accountNumber = accountEditText.getText().toString();
                                name = nameEditText.getText().toString();
                                object.put("u_bank", nameTocode(bank));
//                                object.put("u_bank", nameTocode(bankDropdownMenu.getText().toString()));
                                object.put("u_account", accountNumber);
                                object.put("u_name", name);

                                NetworkTask networkTask = new NetworkTask(url1, object, "POST");
                                String result = null;
                        try {
                            result = networkTask.execute().get();
                            Toast.makeText(getContext(), "첫 계좌 등록이 성공되었습니다.\n" + accountNumber + "\n" + name, Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(getContext(), MainActivity.class);
                            startActivity(homeIntent);

                        } catch (Exception e) {
                            Log.i("error", e.getMessage());
                        }

                        if (result.equals("success")) {
                            Log.i("msg", "success");
                        } else {
                            Log.i("msg", "fail");
                        }
                    } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
            }
                    });

            // 등록된 계좌번호가 있을 경우
        } else {

            descriptionTextView.setText("등록된 계좌가 있습니다.");
            nameLayout.setVisibility(View.INVISIBLE);
            nameEditText.setVisibility(View.INVISIBLE);

            // TODO : 계좌 정보 불러와 Set 시키면 됩니다.
            bankDropdownMenu.setText(codeToname(bank));
            accountEditText.setText(accountNumber);
            accountEditText.setEnabled(false);
            bankDropdownMenu.setEnabled(false);
            lottieAnimationView.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);

            bankInputLayout.setHint("등록된 은행사");
            accountInputLayout.setHint("등록된 계좌번호");
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page3, container, false);
        try {
            init(v);


        } catch (Exception e){
            Log.i("msg", e.getMessage());
        }
        return v;
    }

    String nameTocode(String name){
        String result = "";
        if(name.equals("산업은행")){
            result = "002";
        }
        else if(name.equals("기업은행")){
            result = "003";
        }
        else if(name.equals("국민은행")){
            result = "004";
        }
        else if(name.equals("하나은행")){
            result = "005";
        }
        else if(name.equals("농협은행")){
            result = "011";
        }
        else if(name.equals("우리은행")){
            result = "020";
        }
        else if(name.equals("하나은행")){
            result = "081";
        }
        else if(name.equals("신한은행")){
            result = "088";
        }
        return result;
    }

    String codeToname(String name){
        String result = "";
        if(name.equals("002")){
            result = "산업은행";
        }
        else if(name.equals("003")){
            result = "기업은행";
        }
        else if(name.equals("004")){
            result = "국민은행";
        }
        else if(name.equals("005")){
            result = "하나은행";
        }
        else if(name.equals("011")){
            result = "농협은행";
        }
        else if(name.equals("020")){
            result = "우리은행";
        }
        else if(name.equals("081")){
            result = "하나은행";
        }
        else if(name.equals("088")){
            result = "신한은행";
        }
        return result;
    }

}