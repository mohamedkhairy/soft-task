package khairy.com.softtask.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import khairy.com.softtask.MainActivity;
import khairy.com.softtask.R;
import khairy.com.softtask.SecondView;
import khairy.com.softtask.database.DBHelper;

public class LoginFragment extends Fragment {

    public static final String BACK_STACK_ROOT_TAG = "root_fragment";

    Cursor cursor;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @BindView(R.id.showPass)
    CheckBox show;
    @BindView(R.id.luser)
    EditText username1;
    @BindView(R.id.lpass)
    EditText pass;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
        showPass();
    }

    @OnClick(R.id.login)
    public void login() {

        if (username1.getText().toString().equals("") ||
                pass.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Username and Password can't be empty", Toast.LENGTH_LONG).show();
            return;
        } else {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.USER_TABLE + " WHERE "
                            + DBHelper.COLUMN_USERNAME + " =? AND " + DBHelper.COLUMN_PASSWORD + " =?",
                    new String[]{username1.getText().toString(), pass.getText().toString()});
        }

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                Toast.makeText(getActivity(), "Logged In succesfully!",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), SecondView.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Invalid username or password!",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }

    @OnClick(R.id.regis)
    public void userSignup() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new SignupFragment(), SignupFragment.class.getSimpleName());
        fragmentTransaction.addToBackStack(BACK_STACK_ROOT_TAG).commit();
    }

    public void showPass() {
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

}
