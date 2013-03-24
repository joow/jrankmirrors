package org.joow.jrankmirrors;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class MirrorTest {
    private static final String JSON_HEADER = "{\"protocol\": \"http\", \"url\": ";

    private static final String HTTP_COMPLETE_MIRROR_JSON = JSON_HEADER
            + "\"http://mirror.aarnet.edu.au/pub/archlinux/\", \"completion_pct\": 1.0}";

    private static final String HTTP_INCOMPLETE_MIRROR_JSON = JSON_HEADER
            + "\"http://archlinux.mirrors.uk2.net/\", \"completion_pct\": 0.9642857142857143}";

    private static final long TEST_TIME = 100;

    private final Mirror httpCompleteMirror = new Gson().fromJson(HTTP_COMPLETE_MIRROR_JSON, Mirror.class);

    private final Mirror httpIncompleteMirror = new Gson().fromJson(HTTP_INCOMPLETE_MIRROR_JSON, Mirror.class);

    @Test
    public void rateMirror() throws IOException {
        assertEquals(TEST_TIME, httpCompleteMirror.rate(new FileFetcher() {
            @Override
            public long fetch(URL url) {
                return TEST_TIME;
            }
        }));
    }

    @Test
    public void isHttpMirror() {
        assertTrue(httpCompleteMirror.isHttp());
    }

    @Test
    public void isCompleteMirror() {
        assertTrue(httpCompleteMirror.isComplete());
    }

    @Test
    public void isIncompleteMirror() {
        assertFalse(httpIncompleteMirror.isComplete());
    }
}
