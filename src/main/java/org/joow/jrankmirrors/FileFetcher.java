package org.joow.jrankmirrors;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;

/**
 * Class used to fetch files.
 */
class FileFetcher {
    /**
     * Fetches a file.
     * @param url url of the file to fetch.
     * @return time taken to fetch the file in milliseconds.
     * @throws IOException
     */
    public long fetch(URL url) throws IOException {
        final long begin = System.nanoTime();

        try (ReadableByteChannel channel = Channels.newChannel(url.openStream())) {
            final ByteBuffer buffer = ByteBuffer.allocate(256 * 1024);

            while (channel.read(buffer) > 0) {
                buffer.clear();
            }
        }

        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
    }
}
