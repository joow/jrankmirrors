package org.joow.jrankmirrors;

import java.io.IOException;
import java.util.List;

/**
 * JRankMirrors fetches the latest ArchLinux mirrors and outputs the five best mirrors.
 */
public final class JRankMirrors {
    private static final String ARCHLINUX_MIRROR_LIST_URL = "https://www.archlinux.org/mirrors/status/json/";

    private static final int NB_MIRRORS_TO_KEEP = 5;

    private JRankMirrors() { }

    /**
     * Fetches the latest mirrors list and outputs the five best mirrors.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // To make checkstyle happy !
        new JRankMirrors().run();
    }

    private void run() throws IOException {
        final List<Mirror> ratedMirrors = new MirrorReader(ARCHLINUX_MIRROR_LIST_URL).readMirrorList().rates();

        System.out.println();
        System.out.println("Paste the five following mirrors in your /etc/pacman.d/mirrorlist");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < NB_MIRRORS_TO_KEEP; i++) {
            System.out.println(ratedMirrors.get(i));
        }
    }
}
