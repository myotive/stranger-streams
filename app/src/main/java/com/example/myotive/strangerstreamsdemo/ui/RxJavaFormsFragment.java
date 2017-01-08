package com.example.myotive.strangerstreamsdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myotive.strangerstreamsdemo.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class RxJavaFormsFragment extends Fragment {

    private static final String TAG = RxJavaFormsFragment.class.getSimpleName();

    public static RxJavaFormsFragment newInstance() {
        return new RxJavaFormsFragment();
    }

    // UI
    private EditText email, password, phoneNumber;
    private TextInputLayout emailLayout, passwordLayout, phoneNumberLayout;
    private Button registerButton;

    // RX
    private CompositeSubscription compositeSubscription;
    Observable<CharSequence> emailObservable,
                             passwordObservable,
                             phoneNumberObservable;
    private Observable<Boolean> validationObservable;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rxforms, container, false);

        compositeSubscription = new CompositeSubscription();

        SetupUI(view);

        SetupObservables();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        CreateSubscriptions();
    }

    @Override
    public void onStop() {
        super.onStop();

        compositeSubscription.unsubscribe();
    }

    private void SetupUI(View view) {
        email = (EditText)view.findViewById(R.id.email);
        emailLayout = (TextInputLayout)view.findViewById(R.id.emailLayout);
        password = (EditText)view.findViewById(R.id.password);
        passwordLayout = (TextInputLayout)view.findViewById(R.id.passwordLayout);
        phoneNumber = (EditText)view.findViewById(R.id.phone);
        phoneNumberLayout = (TextInputLayout)view.findViewById(R.id.phoneLayout);

        registerButton = (Button)view.findViewById(R.id.register);
    }

    private void SetupObservables() {
        emailObservable
                = RxTextView.textChanges(email);
        passwordObservable
                = RxTextView.textChanges(password);
        phoneNumberObservable
                = RxTextView.textChanges(phoneNumber);


        // CombineLatest works like Zip operator, except it emits whenever there's a change to
        // any of the specified observables (and sends in the most recent onNext item).
        // This validates that all of the edit text observables are valid.
        //
        // If they are all valid, emit true.
        // If they aren't all valid, emit false.
        validationObservable
                = Observable.combineLatest(emailObservable, passwordObservable, phoneNumberObservable,
                (emailText, passwordText, phoneNumberText) ->
                    isValidEmail(emailText)
                            && isValidPassword(passwordText)
                            && isValidPhoneNumber(phoneNumberText));
    }

    private void CreateSubscriptions() {

        Subscription emailSubscription
                = emailObservable
                .skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::isValidEmail)
                .subscribe(isValid -> {
                            if(!isValid){
                                emailLayout.setError("Invalid Email Address");
                                emailLayout.setErrorEnabled(true);
                            }
                            else{
                                emailLayout.setError(null);
                                emailLayout.setErrorEnabled(false);
                            }
                        },
                        error -> Log.e(TAG, "Error in email input", error));

        Subscription passwordSubscription
                = passwordObservable
                .skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::isValidPassword)
                .subscribe(isValid -> {
                            if(!isValid){
                                passwordLayout.setError("Invalid Password");
                                passwordLayout.setErrorEnabled(true);
                            }
                            else{
                                passwordLayout.setError(null);
                                passwordLayout.setErrorEnabled(false);
                            }
                        },
                        error -> Log.e(TAG, "Error in password input", error));

        Subscription phoneNumberSubscription
                = phoneNumberObservable
                .skip(1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::isValidPhoneNumber)
                .subscribe(isValid -> {
                            if(!isValid){
                                phoneNumberLayout.setError("Invalid Phone Number");
                                phoneNumberLayout.setErrorEnabled(true);
                            }
                            else{
                                phoneNumberLayout.setError(null);
                                phoneNumberLayout.setErrorEnabled(false);
                            }
                        },
                        error -> Log.e(TAG, "Error in phone number input", error));


        Subscription validateFormSubscription =
                validationObservable.subscribe(validForm -> registerButton.setEnabled(validForm));

        compositeSubscription.add(emailSubscription);
        compositeSubscription.add(passwordSubscription);
        compositeSubscription.add(phoneNumberSubscription);
        compositeSubscription.add(validateFormSubscription);
    }

    /**
     * Email validation
     * Not the strongest. The pattern is extremely liberal, but will satisfy the need for
     * this example.
     * @param value
     * @return
     */
    private boolean isValidEmail(CharSequence value) {

        return !TextUtils.isEmpty(value) && Patterns.EMAIL_ADDRESS.matcher(value).matches();
    }

    /**
     * Password Validation
     * Between 4 to 8 characters in length and must contain a digit.
     * Not very strong, but works for this example.
     * @param value
     * @return
     */
    private boolean isValidPassword(CharSequence value) {
        return value.toString().matches("^(?=.*\\d).{4,8}$");
    }

    /**
     * Simple phone number validation.
     * @param value
     * @return
     */
    private boolean isValidPhoneNumber(CharSequence value){
        return PhoneNumberUtils.isGlobalPhoneNumber(value.toString());
    }
}
