package com.example.lorcan.palo;

import java.util.concurrent.TimeUnit;

public class Stopwatch {

    // running states
    private static final int STATE_UNSTARTED = 0;
    private static final int STATE_RUNNING = 1;
    private static final int STATE_STOPPED = 2;
    private static final int STATE_SUSPENDED = 3;

    // split states
    private static final int STATE_UNSPLIT = 10;
    private static final int STATE_SPLIT = 11;

    // current running state
    private int runningState = STATE_UNSTARTED;

    // whether the stopwatch has a split time recorded
    private int splitState = STATE_UNSPLIT;

    // the start time
    private long startTime = -1;

    // the stop time
    private long stopTime = -1;

    // Constructor
    public Stopwatch () {
        super();
    }


    /*
     * Start the stopwatch
     * This method starts a new timing session, clearing any previous values.
     */

    public void start() {
        if (this.runningState == STATE_STOPPED) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted.");
        }
        if (this.runningState != STATE_UNSTARTED) {
            throw new IllegalStateException("Stopwatch already started.");
        }
        this.stopTime = -1;
        this.startTime = System.currentTimeMillis();
        this.runningState = STATE_RUNNING;
    }


    /*
     * Stop the stopwatch
     * This method ends a new timing session, allowing the time to be retrieved.
     */

    public void stop() {
        if (this.runningState != STATE_RUNNING && this.runningState != STATE_SUSPENDED) {
            throw new IllegalStateException("Stopwatch is not running.");
        }
        if (this.runningState == STATE_RUNNING) {
            this.stopTime = System.currentTimeMillis();
        }
        this.runningState = STATE_STOPPED;
    }


    /*
     * Resets the stopwatch. Stops it if need be.
     * This method clears the internal values to allow the object to be reused.
     */

    public void reset() {
        this.runningState = STATE_UNSTARTED;
        this.splitState = STATE_UNSPLIT;
        this.startTime = -1;
        this.stopTime = -1;
    }


    /*
     * Split the time.
     * This method sets the stop time of the watch to allow a time to be extracted. The start time is unaffected,
     * enabling unsplit() to continue the timing from the original start point.
     */

    public void split() {
        if (this.runningState != STATE_RUNNING) {
            throw new IllegalStateException("Stopwatch not running.");
        }
        this.stopTime = System.currentTimeMillis();
        this.splitState = STATE_SPLIT;
    }


    /*
     * Remove a split.
     * This method clears the stop time. The start time is unaffected, enabling timing from the original start point to
     * continue.
     */

    public void unsplit() {
        if (this.splitState != STATE_SPLIT) {
            throw new IllegalStateException("Stopwatch has not been split.");
        }
        this.stopTime = -1;
        this.splitState = STATE_UNSPLIT;
    }


    /*
     * Suspend the stopwatch for later resumption.
     * This method suspends the watch until it is resumed. The watch will not include time between the suspend and
     * resume calls in the total time.
     */

    public void suspend() {
        if (this.runningState != STATE_RUNNING) {
            throw new IllegalStateException("Stopwatch must be running to be suspend.");
        }
        this.stopTime = System.currentTimeMillis();
        this.runningState = STATE_SUSPENDED;
    }


    /*
     * Resume the stopwatch after a suspend.
     * This method resumes the watch after it was suspended. The watch will not include time between the suspend and
     * resume calls in the total time.
     */

    public void resume() {
        if (this.runningState != STATE_SUSPENDED) {
            throw new IllegalStateException("Stopwatch must be suspended to be resumed.");
        }
        this.startTime += (System.currentTimeMillis() - this.stopTime);
        this.stopTime = -1;
        this.runningState = STATE_RUNNING;
    }


    /*
     * Get the time on the stopwatch.
     * This is either the time between the start and the moment this method is called, or the amount of time between
     * start and stop.
     */

    public long getTime() {
        if (this.runningState == STATE_STOPPED || this.runningState == STATE_SUSPENDED) {
            return stopTime - this.startTime;
        }
        else if (this.runningState == STATE_UNSTARTED) {
            return 0;
        }
        else if (this.runningState == STATE_RUNNING) {
            return System.currentTimeMillis() - this.startTime;
        }
        throw new IllegalStateException("Illegal running state has occured.");
    }


    /*
     * Get the split time on the stopwatch.
     * This is the time between start and latest split.
     */

    public long getSplitTime() {
        if (this.splitState != STATE_SPLIT) {
            throw new IllegalStateException("Stopwatch must be split to get the split time.");
        }
        return this.stopTime - this.startTime;
    }


    /*
     * Returns the time this stopwatch was started.
     */

    public long getStartTime() {
        if (this.runningState == STATE_UNSTARTED) {
            throw new IllegalStateException("Stopwatch has not been started.");
        }
        return this.startTime;
    }


    /*
     * Gets a summary of the time that the stopwatch recorded as a string.
     */


    public String toString(long millis) {
        //return DurationFormatUtils.formatDurationHMS(getTime());
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }



    /*
     * Gets a summary of the split time that the stopwatch recorded as a string.
     */


    public String toSplitString(long millis) {
        //return DurationFormatUtils.formatDurationHMS(getSplitTime());
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

}
