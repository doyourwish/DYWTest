package com.example.lambdaaws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//lambda
import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;

public class MainActivity extends AppCompatActivity {

    //lambda configure
    final private LambdaConfigure lambdaConfigure = new LambdaConfigure();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), lambdaConfigure.identityPoolId, lambdaConfigure.cognitoRegion);

        // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                lambdaConfigure.lambdaRegion, cognitoProvider);

        // Create the Lambda proxy object with a default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder.
        final MyInterface myInterface = factory.build(MyInterface.class);

        RequestClass request = new RequestClass("John", "Doe");
        // The Lambda function invocation results in a network call.
        // Make sure it is not called from the main thread.
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    if(lambdaConfigure.language == "JavaScript") {
                        return myInterface.AndroidBackendLambdaFunction(params[0]);
                    } else if (lambdaConfigure.language == "Python") {
                        return myInterface.AndroidPython(params[0]);
                    }
                    Log.e("lambdaProgramLanguage", lambdaConfigure.language + " is not found");
                    return null;

                } catch (LambdaFunctionException lfe) {
                    Log.e("LambdaFunctionException", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    Log.v("error_result","result is null");
                    return;
                }
                else {
                    // Do a toast
                    try {
                        Log.v("result.getGreetings",result.getGreetings());
                        Log.v("result.getTest",result.getTest());
                    }catch (Exception e) {
                        Log.e("e.getMessage",e.getMessage());
                    }
                    Toast.makeText(MainActivity.this, result.getGreetings(), Toast.LENGTH_LONG).show();
                }
            }
        }.execute(request);
    }
}