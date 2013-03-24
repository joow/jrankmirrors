package org.joow.jrankmirrors;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Rates a mirror and returns a MirrorResult.
 */
class MirrorTester implements Callable<MirrorResult> {
    private final Mirror mirrorUnderTest;

    MirrorTester(Mirror mirror) {
        mirrorUnderTest = mirror;
    }

    @Override
    public MirrorResult call() throws IOException {
        final long time = mirrorUnderTest.rate(new FileFetcher());
        return new MirrorResult(mirrorUnderTest, time);
    }
}
