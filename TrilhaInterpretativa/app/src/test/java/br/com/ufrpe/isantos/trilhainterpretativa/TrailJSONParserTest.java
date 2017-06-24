package br.com.ufrpe.isantos.trilhainterpretativa;

import org.junit.Test;

import br.com.ufrpe.isantos.trilhainterpretativa.entity.Trail;
import br.com.ufrpe.isantos.trilhainterpretativa.utils.TrailJSONParser;

import static org.junit.Assert.assertEquals;

/**
 * Created by isantos on 23-Jun-17.
 */

public class TrailJSONParserTest {
    @Test
    public void parsesIsCorrect() throws Exception {
        String json = "{\"id\":0,\"title\":\"Trilha dois Irm\\u00e3os\",\"points\":[{\"id\":1,\"descricao\":\"Cozinha nossa pia sempre suja\",\"local\":{\"id\":0,\"latitude\":0,\"longitude\":0,\"altitude\":0.1},\"images\":[{\"id\":1,\"src\":\"res\\/image1.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 1\",\"local\":{\"id\":0,\"latitude\":0.2,\"longitude\":2,\"altitude\":0.1},\"images\":[{\"id\":2,\"src\":\"res\\/image2.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 2\",\"local\":{\"id\":3,\"latitude\":0.2,\"longitude\":2,\"altitude\":0.1},\"images\":[{\"id\":3,\"src\":\"res\\/image3.jpg\",\"point\":null}],\"trail\":null},{\"id\":1,\"descricao\":\"Predio 3\",\"local\":{\"id\":3,\"latitude\":0.2,\"longitude\":2,\"altitude\":0.1},\"images\":[{\"id\":4,\"src\":\"res\\/image4.jpg\",\"point\":null}],\"trail\":null}]}";
        Trail t = TrailJSONParser.stringToObject(json);
        assertEquals(t.getTitle(),"Trilha dois Irmãos");
        System.out.println(t.getTitle());
        assertEquals(t.getPoints().size(),4);

        assertEquals(t.getPoints().get(0).getImages().get(0).getSrc(),"res/image1.jpg");
        System.out.println(TrailJSONParser.ObjectToString(t));
        assertEquals(TrailJSONParser.ObjectToString(t),json);
    }
}
