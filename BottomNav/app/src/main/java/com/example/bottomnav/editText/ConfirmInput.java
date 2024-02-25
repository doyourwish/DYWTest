package com.example.bottomnav.editText;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ConfirmInput {
    protected Activity activity;
    private ArrayList<RuleInfo> multiRuleInfo = new ArrayList<>();

    public ConfirmInput(Activity activity){
        this.activity = activity;
    }

    public boolean addRule(InputRule inputRule, WhenChanged whenChanged){
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.inputRule = inputRule;
        ruleInfo.whenChanged = whenChanged;
        multiRuleInfo.add(ruleInfo);

        return true;
    }

    public boolean createConfirmInput(TextInputEditText editText, TextInputLayout layout){
        ArrayList<InputRule> beforeRules = new ArrayList<>();
        ArrayList<InputRule> onRules = new ArrayList<>();
        ArrayList<InputRule> afterRules = new ArrayList<>();
        for(RuleInfo ruleInfo: multiRuleInfo) {
            if (ruleInfo.whenChanged == WhenChanged.beforeText){
                beforeRules.add(ruleInfo.inputRule);
            }
            else if (ruleInfo.whenChanged == WhenChanged.onText) {
                onRules.add(ruleInfo.inputRule);
            }
            else if (ruleInfo.whenChanged == WhenChanged.afterText) {
                afterRules.add(ruleInfo.inputRule);
            }
            else {
                Log.e("Error","ruleInfo.whenChanged is illegal");
                return false;
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setErrorMessage(beforeRules,s,layout);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setErrorMessage(onRules,s,layout);
            }

            @Override
            public void afterTextChanged(Editable s) {
                setErrorMessage(afterRules,s,layout);
            }
        });

        return true;
    }

    private void setErrorMessage(ArrayList<InputRule> inputRules,CharSequence s,TextInputLayout textInputLayout){
        if (inputRules.isEmpty()) {
            return;
        }

        for (InputRule inputRule: inputRules){
            if(inputRule.getShowMessage(s) != null) {
                textInputLayout.setError(inputRule.getShowMessage(s));
                return;
            }
        }
        textInputLayout.setError(null);
    }

    public String getErrorMessage(TextInputEditText editText){
        for(RuleInfo ruleInfo: multiRuleInfo) {
            String message = ruleInfo.inputRule.getShowMessage((CharSequence) editText.getText().toString());
            if (message != null){
                return message;
            }
        }
        return null;
    }
}
