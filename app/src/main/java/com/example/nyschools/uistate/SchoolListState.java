package com.example.nyschools.uistate;


/* Track the Loading state of the SchoolList page and also associated error msg(msg not currently used) */
public class SchoolListState  {

    public SchoolListLoadState loadState;
    public String errorMsg;

    public SchoolListState(SchoolListLoadState loadState,String errorMsg){
        this.loadState = loadState;
        this.errorMsg = errorMsg;
    }

}
