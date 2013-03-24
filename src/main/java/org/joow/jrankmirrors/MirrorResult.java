package org.joow.jrankmirrors;

class MirrorResult implements Comparable<MirrorResult> {
    private final Mirror mirror;

    private final long time;

    MirrorResult(Mirror mirror, long time) {
        this.mirror = mirror;
        this.time = time;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public long getTime() {
        return time;
    }

    @Override
    public int compareTo(MirrorResult mirrorResult) {
        if (time <= mirrorResult.time) {
            return -1;
        } else {
            return 1;
        }
    }
}
