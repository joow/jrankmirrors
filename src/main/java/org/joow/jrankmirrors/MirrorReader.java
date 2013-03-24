package org.joow.jrankmirrors;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reads a mirrors list in JSON format.
 */
final class MirrorReader {
    private final URL url;

    public MirrorReader(String url) {
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null.");
        }

        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public MirrorList readMirrorList() throws IOException {
        return new Gson().fromJson(readFileContent(), MirrorList.class);
    }

    private String readFileContent() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            final StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }

            return fileContent.toString();
        }
    }
}
