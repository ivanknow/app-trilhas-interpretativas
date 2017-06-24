package br.com.ufrpe.isantos.trilhainterpretativa;

import android.location.Location;

import org.junit.Test;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Local;
import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by isantos on 23-Jun-17.
 */

public class TrailMediatorTest {
    @Test
    public void parsesIsCorrect() throws Exception {
        String json = "{\"id\":0,\"title\":\"Trilha dois Irm\\u00e3os\",\"points\":[{\"id\":1,\"descricao\":\"Cozinha nossa pia sempre suja\",\"local\":{\"id\":0,\"latitude\":-27.5752847,\"longitude\":-48.5105891,\"altitude\":0},\"images\":[{\"id\":1,\"src\":\"res\\/image1.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 1\",\"local\":{\"id\":0,\"latitude\":-27.5752663,\"longitude\":-48.510453,\"altitude\":0},\"images\":[{\"id\":2,\"src\":\"res\\/image2.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 2\",\"local\":{\"id\":3,\"latitude\":-27.5750697,\"longitude\":-48.5104753,\"altitude\":0},\"images\":[{\"id\":3,\"src\":\"res\\/image3.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 3\",\"local\":{\"id\":3,\"latitude\":-27.5751929,\"longitude\":-48.5105472,\"altitude\":0.1},\"images\":[{\"id\":4,\"src\":\"res\\/image4.jpg\",\"point\":null}],\"trail\":null}]}";
        double scala = 0.000001;
        Trail t = TrailJSONParser.stringToObject(json);
        double max = (Math.abs(t.getPoints().get(0).getLocal().getLatitude())+0.00005);
        double min = (Math.abs(t.getPoints().get(0).getLocal().getLatitude())-0.00005);
        System.out.println(max);
        System.out.println(min);
        System.out.println(max==27.5753847);
        System.out.println(max>=27.5753847);
        System.out.println(min==27.5753847);
        System.out.println(min<=27.5753847);
        System.out.println(max>=27.5753847 && min<=27.5753847);
        assertFalse(TrailMediator.isBetween(0,max,min));
        assertTrue(TrailMediator.isBetween(27.575334700000003,max,min));
        /*assertTrue(TrailMediator.isBetween(27.5751847,max,min));
        assertTrue(TrailMediator.isBetween(27.5752847,max,min));
        assertFalse(TrailMediator.isBetween(27.5754847,max,min));*/



        assertNull(TrailMediator.getPointNearByMe(scala,new Local(),t));
        assertNotNull(TrailMediator.getPointNearByMe(scala,new Local(-27.5752847,-48.5105891,0),t));
        System.out.println(TrailMediator.getPointNearByMe(scala,new Local(-27.5752847,-48.5105891,0),t).getDescricao());
        assertEquals(t.getPoints().get(0).getDescricao(),TrailMediator.getPointNearByMe(scala,new Local(-27.5752847,-48.5105891,0),t).getDescricao());
        System.out.println(TrailMediator.getPointNearByMe(scala,new Local(-27.5752663,-48.510453,0),t).getDescricao());
        assertEquals(t.getPoints().get(1).getDescricao(),TrailMediator.getPointNearByMe(scala,new Local(-27.5752663,-48.510453,0),t).getDescricao());
    }
}
