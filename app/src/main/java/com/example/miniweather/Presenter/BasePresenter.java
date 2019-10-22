package com.example.miniweather.Presenter;

import com.example.miniweather.Model.BaseModel;
import com.example.miniweather.View.BaseView;

public abstract class BasePresenter<View extends BaseView,Model extends BaseModel> {
    protected View view;
    protected Model model;
    public BasePresenter(){

    }
    public void attachView(View view){
        this.view = view;
    }
    public void detachView(){
        this.view = null;
    }
    public void attachModel(Model model){
        this.model = model;
    }
    public void detachModel(){
        this.model = null;
    }


}