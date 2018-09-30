package khairy.com.softtask.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import khairy.com.softtask.R;
import khairy.com.softtask.database.DBHelper;

public class SignupFragment extends Fragment {

    DBHelper dbHelper;

    @BindView(R.id.rshowPass)
    CheckBox show_check;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText pass;
    @BindView(R.id.cnfrmpassword)
    EditText cnfrmpass;
    @BindView(R.id.user)
    EditText user1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.signup_view, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getContext());
        show_Pass();

    }

    @OnClick(R.id.signup)
    public void sign_up(){
        if(name.getText().toString().equals("")||
                user1.getText().toString().equals("")||
                pass.getText().toString().equals("")||cnfrmpass.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Please Enter Your Details", Toast.LENGTH_LONG).show();
            return;
        }

        // check if both password matches
        if(!pass.getText().toString().equals(cnfrmpass.getText().toString()))
        {
            Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_LONG).show();
        }



        else {
            dbHelper.addUser(name.getText().toString(),
                    user1.getText().toString(), pass.getText().toString(),
                    cnfrmpass.getText().toString());

            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();

            getActivity().getSupportFragmentManager()
                    .popBackStack(LoginFragment.BACK_STACK_ROOT_TAG , FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }

    }

    public void show_Pass(){
        show_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    cnfrmpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    cnfrmpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}
