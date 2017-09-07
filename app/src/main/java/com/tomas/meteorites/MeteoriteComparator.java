package com.tomas.meteorites;

import java.util.Comparator;

/**
 * Created by Tomas on 7. 9. 2017.
 */

public class MeteoriteComparator implements Comparator<Meteorite> {
    @Override
    public int compare(Meteorite o1, Meteorite o2) {
        float  mass1 = (o1.mass == null)? Float.MAX_VALUE : Float.parseFloat(o1.mass);
        float  mass2 = (o2.mass == null)? Float.MAX_VALUE : Float.parseFloat(o2.mass);
        return Float.compare(mass1, mass2);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
