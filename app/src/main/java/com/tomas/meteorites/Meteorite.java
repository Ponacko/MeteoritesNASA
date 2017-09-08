package com.tomas.meteorites;

import android.support.annotation.NonNull;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Tomas on 6. 9. 2017.
 */

public class Meteorite extends RealmObject{
    public String name;
    public String mass;
    public String year;
    public String reclat;
    public String reclong;
}
