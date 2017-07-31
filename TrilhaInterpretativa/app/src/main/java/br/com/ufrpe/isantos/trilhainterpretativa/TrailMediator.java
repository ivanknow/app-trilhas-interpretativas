package br.com.ufrpe.isantos.trilhainterpretativa;

import android.location.Location;

import java.util.List;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Local;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Point;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by isantos on 24-Jun-17.
 */

public class TrailMediator {

    public static Trail getCurrentTrail() {
        return null;
    }

    public static Point getPointNearByMe(double scala, Local local, Trail t, List<Point> alreadyused) {
        // Trail t = getCurrentTrail();
        for (Point p : t.getPoints()) {
            double pointLat = Math.abs(p.getLocal().getLatitude());
            double pointLon = Math.abs(p.getLocal().getLatitude());

            double cooLat = Math.abs(local.getLatitude());
            double cooLon = Math.abs(local.getLatitude());

            if(isBetween(cooLat,pointLat+scala,pointLat-scala)){
                if(isBetween(cooLon,pointLon+scala,pointLon-scala)){
                    if(p.getCheckpoint()==null)
                        return p;
                }
            }

        }
        return null;
    }

    public static Point getPointNearByMe(Local local,Location l, Trail t, List<Point> alreadyused) {
        // Trail t = getCurrentTrail();
        for (Point p : t.getPoints()) {
            Location targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(local.getLatitude());//your coords of course
            targetLocation.setLongitude(local.getLongitude());
            if(l.distanceTo(targetLocation)<2)
                    if(p.getCheckpoint()==null)
                        return p;
                }



        return null;
    }

    public static boolean isBetween(double number,double max, double min) {
        if(max>=number && min<=number){
            return true;
        }
        return false;
    }

}
