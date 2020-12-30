package com.example.smn_aggregator;

import java.io.Serializable;
import java.util.ArrayList;

import twitter4j.Status;

public class StatusesWrapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Status> statuses;

    public StatusesWrapper(ArrayList<Status> statuses){
        this.statuses = statuses;
    }

    public ArrayList<Status> getStatuses(){
        return statuses;
    }

}
