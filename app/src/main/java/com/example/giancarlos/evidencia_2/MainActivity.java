package com.example.giancarlos.evidencia_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.giancarlos.evidencia_2.R;
import com.example.giancarlos.evidencia_2.helper.InputValidation;
import com.example.giancarlos.evidencia_2.model.Curp;
import com.example.giancarlos.evidencia_2.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MainActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutPrimerApellido;
    private TextInputLayout textInputLayoutSegundoApellido;
    private TextInputLayout textInputLayoutNombre;
    private TextInputLayout textInputLayoutNacimiento;
    private TextInputLayout textInputLayoutSexo;
    private TextInputLayout textInputLayoutEntidadFederativa;

    private TextInputEditText textInputEditTextPrimerApellido;
    private TextInputEditText textInputEditTextSegundoApellido;
    private TextInputEditText textInputEditTextNombre;
    private TextInputEditText textInputEditTextNacimiento;
    private TextInputEditText textInputEditTextSexo;
    private TextInputEditText textInputEditTextEntidadFederativa;

    private AppCompatButton appCompatButtonSave;
    private AppCompatTextView appCompatTextViewListaCurp;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Curp curp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        initObjects();
        initListeners();
    }

    //Initialize Views
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutPrimerApellido = (TextInputLayout) findViewById(R.id.textInputLayoutPrimerApellido);
        textInputLayoutSegundoApellido = (TextInputLayout) findViewById(R.id.textInputLayoutSegundoApellido);
        textInputLayoutNombre = (TextInputLayout) findViewById(R.id.textInputLayoutNombre);
        textInputLayoutNacimiento = (TextInputLayout) findViewById(R.id.textInputLayoutNacimiento);
        textInputLayoutSexo = (TextInputLayout) findViewById(R.id.textInputLayoutSexo);
        textInputLayoutEntidadFederativa = (TextInputLayout) findViewById(R.id.textInputLayoutEntidadFederativa);

        textInputEditTextPrimerApellido = (TextInputEditText) findViewById(R.id.textInputEditTextPrimerApellido);
        textInputEditTextSegundoApellido = (TextInputEditText) findViewById(R.id.textInputEditTextSegundoApellido);
        textInputEditTextNombre = (TextInputEditText) findViewById(R.id.textInputEditTextNombre);
        textInputEditTextNacimiento = (TextInputEditText) findViewById(R.id.textInputEditTextNacimiento);
        textInputEditTextSexo = (TextInputEditText) findViewById(R.id.textInputEditTextSexo);
        textInputEditTextEntidadFederativa = (TextInputEditText) findViewById(R.id.textInputEditTextEntidadFederativa);

        appCompatButtonSave = (AppCompatButton) findViewById(R.id.appCompatButtonSave);
        appCompatTextViewListaCurp = (AppCompatTextView) findViewById(R.id.appCompatTextViewListaCurp);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonSave.setOnClickListener(this);
        appCompatTextViewListaCurp.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        curp = new Curp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonSave:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewListaCurp:
                Intent accountsIntent = new Intent(activity, CurpListActivity.class);
                accountsIntent.putExtra("Primer Apellido", textInputEditTextPrimerApellido.getText().toString().trim());
                accountsIntent.putExtra("Segundo Apellido", textInputEditTextSegundoApellido.getText().toString().trim());
                accountsIntent.putExtra("Nombre", textInputEditTextNombre.getText().toString().trim());
                accountsIntent.putExtra("Fecha de Nacimiento", textInputEditTextNacimiento.getText().toString().trim());
                accountsIntent.putExtra("Sexo", textInputEditTextSexo.getText().toString().trim());
                accountsIntent.putExtra("Entidad Federativa", textInputEditTextEntidadFederativa.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPrimerApellido, textInputLayoutPrimerApellido, getString(R.string.error_message_apellido))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextSegundoApellido, textInputLayoutSegundoApellido, getString(R.string.error_message_apellido))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextNombre, textInputLayoutNombre, getString(R.string.error_message_nombre))) {
            return;
        }

        //if (!inputValidation.isInputEditTextEmail(textInputEditTextNombre, textInputLayoutNombre, getString(R.string.error_message_nombre))) {
        //    return;
        //}

        if (!databaseHelper.checkUser(textInputEditTextPrimerApellido.getText().toString().trim())) {

            curp.setprimerApellido(textInputEditTextPrimerApellido.getText().toString().trim());
            curp.setsegundoApellido(textInputEditTextSegundoApellido.getText().toString().trim());
            curp.setnombre(textInputEditTextNombre.getText().toString().trim());
            curp.setnacimiento(textInputEditTextNacimiento.getText().toString().trim());
            curp.setsexo(textInputEditTextSexo.getText().toString().trim());
            curp.setentidadFederativa(textInputEditTextEntidadFederativa.getText().toString().trim());


            databaseHelper.addBeneficiary(curp);

            // Snack Bar to show success message that record saved successfully
            Intent accountsIntent = new Intent(activity, CurpListActivity.class);
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT)
                    .show();
            accountsIntent.putExtra("Primer Apellido", textInputEditTextPrimerApellido.getText().toString().trim());
            accountsIntent.putExtra("Segundo Apellido", textInputEditTextSegundoApellido.getText().toString().trim());
            accountsIntent.putExtra("Nombre", textInputEditTextNombre.getText().toString().trim());
            accountsIntent.putExtra("Fecha de Nacimiento", textInputEditTextNacimiento.getText().toString().trim());
            accountsIntent.putExtra("Sexo", textInputEditTextSexo.getText().toString().trim());
            accountsIntent.putExtra("Entidad Federativa", textInputEditTextEntidadFederativa.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText() {
        textInputEditTextPrimerApellido.setText(null);
        textInputEditTextSegundoApellido.setText(null);
        textInputEditTextNombre.setText(null);
        textInputEditTextNacimiento.setText(null);
        textInputEditTextSexo.setText(null);
        textInputEditTextEntidadFederativa.setText(null);
    }
}
