package org.vosk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    SplitInstallManager splitInstallManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splitInstallManager = SplitInstallManagerFactory.create(this);
        Set<String> installedModules = splitInstallManager.getInstalledModules();

        if (!installedModules.contains("dynamicfeature")) {
            installDynamicModule();
        } else {
            Toast.makeText(this, "Dynamic module already installed", Toast.LENGTH_SHORT).show();
            startDynamicModuleActivity();
        }
    }

    private void installDynamicModule() {
        // Creates a request to install a module.
        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        // You can download multiple on demand modules per
                        // request by invoking the following method for each
                        // module you want to install.
                        .addModule("dynamicfeature")
                        .build();

        Toast.makeText(this, "Starting to install dynamic module", Toast.LENGTH_SHORT).show();
        splitInstallManager
                // Submits the request to install the module through the
                // asynchronous startInstall() task. Your app needs to be
                // in the foreground to submit the request.
                .startInstall(request)
                // You should also be able to gracefully handle
                // request state changes and errors. To learn more, go to
                // the section about how to Monitor the request state.
                .addOnSuccessListener(sessionId -> {
                    Toast.makeText(this, "Dynamic module installed", Toast.LENGTH_SHORT).show();
                    startDynamicModuleActivity();
                })
                .addOnFailureListener(exception -> {
                });
    }

    private void startDynamicModuleActivity() {
        startActivity(
                new Intent().setClassName(
                        "com.example",
                        "com.example.dynamicfeature.VoskActivity"
                )
        );
    }
}