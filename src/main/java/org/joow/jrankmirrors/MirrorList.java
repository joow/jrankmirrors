package org.joow.jrankmirrors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public final class MirrorList {
    private static final int NB_THREADS = 10;

    private static final long DEFAULT_TIMEOUT = TimeUnit.SECONDS.toMillis(5);

    private List<Mirror> urls;

    List<Mirror> getHttpCompleteMirrors() {
        final List<Mirror> httpMirrors = new ArrayList<>();

        for (final Mirror mirror : urls) {
            if (mirror.isHttp() && mirror.isComplete()) {
                httpMirrors.add(mirror);
            }
        }

        return httpMirrors;
    }

    /**
     * Rates the list of http complete mirrors and returns this list of mirrors ordered by rating.
     * @return the list of http complete mirrors ordered by rating.
     */
    public List<Mirror> rates() {
        return getMirrorsOrderedByRating(rates(getHttpCompleteMirrors()));
    }

    private List<MirrorResult> rates(List<Mirror> mirrors) {
        final List<Future<MirrorResult>> futures = submitRating(mirrors);
        return getResults(futures);
    }

    private List<Future<MirrorResult>> submitRating(List<Mirror> mirrors) {
        final ExecutorService executorService = Executors.newFixedThreadPool(NB_THREADS);
        final List<Future<MirrorResult>> futures = new ArrayList<>(mirrors.size());

        for (final Mirror mirror : mirrors) {
            System.out.println(String.format("rating %s", mirror.getUrl()));
            futures.add(executorService.submit(new MirrorTester(mirror)));
        }
        executorService.shutdown();

        return futures;
    }

    private List<MirrorResult> getResults(List<Future<MirrorResult>> futures) {
        final List<MirrorResult> results = new ArrayList<>();

        for (final Future<MirrorResult> future : futures) {
            try {
                results.add(future.get(computeTimeout(results), TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                future.cancel(false);
            }
        }

        return results;
    }

    private long computeTimeout(List<MirrorResult> results) {
        if (results.size() < NB_THREADS) {
            return DEFAULT_TIMEOUT;
        } else {
            final List<MirrorResult> orderedResults = new ArrayList<>(results);
            Collections.sort(orderedResults);
            return orderedResults.get(NB_THREADS - 1).getTime();
        }
    }

    private List<Mirror> getMirrorsOrderedByRating(List<MirrorResult> results) {
        final List<Mirror> orderedMirrors = new ArrayList<>(results.size());
        Collections.sort(results);

        for (final MirrorResult mirrorResult : results) {
            orderedMirrors.add(mirrorResult.getMirror());
        }

        return orderedMirrors;
    }
}
