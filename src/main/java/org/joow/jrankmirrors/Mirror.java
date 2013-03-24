package org.joow.jrankmirrors;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Arch Linux mirror. See https://www.archlinux.org/mirrors/status/json/ for more details.
 */
public final class Mirror {
    private static final String HTTP_PROTOCOL = "http";

    private static final String FILE_TO_FETCH_URL_FORMAT = "%score/os/x86_64/core.db.tar.gz";

    private static final String TO_STRING_FORMAT = "Server = %s$repo/os/$arch";

    private String protocol;

    private String url;

    @SerializedName("completion_pct")
    private double completionPct;

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, url);
    }

    public String getUrl() {
        return url;
    }

    public boolean isHttp() {
        return HTTP_PROTOCOL.equals(protocol);
    }

    public boolean isComplete() {
        return completionPct == 1.0;
    }

    /**
     * Rates a mirror by fetching the file core.db.tar.gz located at url $url/core/os/x86_64/.
     * @return time in milliseconds to fetch the file.
     * @throws IOException if fetching the file fails.
     */
    public long rate(FileFetcher fileFetcher) throws IOException {
        return fileFetcher.fetch(generateUrl());
    }

    private URL generateUrl() {
        try {
            return new URL(String.format(FILE_TO_FETCH_URL_FORMAT, url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
