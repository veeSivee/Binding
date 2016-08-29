package com.example.vi.binding;

import android.database.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vi.binding.api.ApiRequest;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit.HttpException;
//import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.btn_cek)
    Button btn_cek;

    @BindView(R.id.tv_note)
    TextView tv_note;

    Subscription etSub;
    Subscription buttonSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        init();
    }

    public void init(){

        ApiRequest apiRequest = new ApiRequest();
        CheckRepo(apiRequest);

        buttonSub =
                RxView.clicks(btn_cek).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        et_name.setText("veesivee");
                    }
                });

    }

    private void CheckRepo(final ApiRequest apiRequest){

        etSub = RxTextView.textChanges(et_name)
                .debounce(DataRequest.DEBOUNCE, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .map(String::trim)
                .switchMap(username->apiRequest.getUserRepo(username)
                        .doOnError(this::errorInputUser)
                        .onErrorResumeNext(rx.Observable.empty()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDataRepo,this::errorInputUser);
    }

    private void updateDataRepo(List data){
        tv_note.setText(data.toString());
    }

    private void errorInputUser(Throwable e){
        tv_note.setText("ERROR!!! " + e.toString());
    }

    @Override
    protected void onDestroy() {
        etSub.unsubscribe();
        buttonSub.unsubscribe();

        super.onDestroy();
    }
}
