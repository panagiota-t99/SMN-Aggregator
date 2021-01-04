package com.example.smn_aggregator;

import java.io.Serializable;
import java.util.ArrayList;

import twitter4j.Status;

//This class was created in order the Status list to be retrievable from the intent
public class StatusesWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Status> statuses;
    //private Status status;

    //public StatusesWrapper(Status status) {this.status = status;}
    public StatusesWrapper(ArrayList<Status> statuses){
        this.statuses = statuses;
    }

    public ArrayList<Status> getStatuses(){
        return statuses;
    }

}
