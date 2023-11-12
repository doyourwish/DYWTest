package com.example.bottomnav;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHandler extends Fragment{

    private FragmentManager fragmentManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // fragmentがactivityと関連付けする前に、getFragmentManagerが呼び出せない？ので
        // onAttach実行時に初期化
        fragmentManager = getParentFragmentManager();
    }

    public boolean changeFragment(int fragment_id){
        // fragmentTransactionをメンバ変数に持たせると何故か遷移時にエラーとなるので、ここで毎回初期化
        // エラー文 : java.lang.IllegalStateException: commit already called
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 画面遷移実行
        if(R.id.home == fragment_id){
            fragmentTransaction.replace(R.id.container, new HomeFragment());
            fragmentTransaction.commit();
            return true;
        }
        else if(R.id.closet == fragment_id){
            fragmentTransaction.replace(R.id.container, new ClosetFragment());
            fragmentTransaction.commit();
            return true;
        }
        else if(R.id.calendar == fragment_id){
            fragmentTransaction.replace(R.id.container, new CalendarFragment());
            fragmentTransaction.commit();
            return true;
        }
        else {
            Log.e("changeFragment","fragment_id is not match");
            return false;
        }
    }

    public boolean changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        return true;
    }

    public boolean setFragmentManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        return true;
    }
}
