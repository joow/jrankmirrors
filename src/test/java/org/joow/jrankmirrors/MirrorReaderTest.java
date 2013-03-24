package org.joow.jrankmirrors;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class MirrorReaderTest {
    private static final String MIRRORS_URL = MirrorReaderTest.class.getResource("mirrors.json").toString();

    private static final int NB_HTTP_COMPLETE_MIRRORS = 127;

    @Test
    public void getMirrors() throws IOException {
        final List<Mirror> mirrors = new MirrorReader(MIRRORS_URL).readMirrorList().getHttpCompleteMirrors();

        assertEquals(NB_HTTP_COMPLETE_MIRRORS, mirrors.size());
    }
}
