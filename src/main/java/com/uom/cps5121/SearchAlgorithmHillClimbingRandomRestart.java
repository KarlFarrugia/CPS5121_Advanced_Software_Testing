/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.util.Pair;

/**
 * A Search Algorithm that randomly chooses a new untested pixel and tests whether it is bugged or not. The algorithm
 * continues by testing neighbouring pixels until a restart condition is met. Throughout the execution a variable
 * restartVar is used which has a lower bounds of 1 and a higher bounds of 21. During the execution the restartVar
 * variable is incremented on finding a bugged pixel and decremented on finding a working pixel. The restartVar is then
 * used as an inverse probability of whether to restart the execution or continue searching.
 *
 * @author Karl Farrugia
 */
public class SearchAlgorithmHillClimbingRandomRestart extends SearchAlgorithmAbstract {

    /**
     * Creates a new SearchAlgorithmHillClimbingRandomRestart object.
     *
     * @pre $none
     * @post $none
     */
    public SearchAlgorithmHillClimbingRandomRestart() {
        System.out.println("Hill Climbing Random Restart Strategy Algorithm");
    }

    /**
     * Randomly chooses a pixel and tests it. On choosing a pixel its neighbours are tested as part of the hill climbing technique.
     * Process is repeated until the request limit is reached.
     *
     * @throws UnirestException
     * @pre $none
     * @post $none
     */
    public void randomRunner() throws UnirestException {
        clearVars();
        genXY();
        int restartVar = 10;
        do {
            pixelCheck();
            insertList();
            restartVar = neighbourTest(restartVar);

            if (restartVar < 1 & requests < REQUEST_LIMIT) {
                /**
                 * lower boundary reached invoke restart
                 */
                restartVar = 10;
                genXY();
                pixelCheck();
                insertList();
                restartVar = neighbourTest(restartVar);
            } else if (restartVar > 21) {
                /**
                 * upper boundary reached
                 */
                restartVar = 20;
            }

            /**
             * uses the @param restartVar as a driving factor to determine whether a restart is to be invoked
             */
            if (getRandom(restartVar) == 1) {
                /**
                 * randomly invoked restart
                 */
                restartVar = 10;
                genXY();
            }
        } while (requests < REQUEST_LIMIT);

        printList();
    }

    /**
     * Checks if a pixel has already been tested and if it has not it is tested. If the pixel is bugged the restartVar is incremented otherwise
     * the restartVar is decremented.
     *
     * @param restartVar current restart probability.
     * @return the newly calculated restartVar after checking the pixel and updating accordingly
     * @throws UnirestException
     */
    private int checkPixel(int restartVar) throws UnirestException {
        pair = new Pair<>(x, y);
        /**
         * Checks if pixel has already been inserted or if the limit has been reached
         */
        if (!checked_pixel.contains(pair) & requests < REQUEST_LIMIT) {
            pixelCheck();
            if (!insertList()) {
                restartVar++;
            } else {
                restartVar--;
            }
        }
        return restartVar;
    }

    /**
     * Checks the neighbouring pixels of a chosen pixel and updates the restartVar accordingly.
     *
     * @param restartVar current restart probability.
     * @return the newly calculated restartVar after checking the pixel and updating accordingly
     * @throws UnirestException
     */
    private int neighbourTest(int restartVar) throws UnirestException {
        int tempX = x;
        int tempY = y;

        if (x > 0) {
            x--;
        } else {
            x = 1599;
        }

        restartVar = checkPixel(restartVar);

        if (tempX < 1599) {
            x = tempX + 1;
        } else {
            x = 0;
        }

        restartVar = checkPixel(restartVar);

        if (x > 0) {
            x--;
        } else {
            x = 1599;
        }

        if (y > 0) {
            y--;
        } else {
            y = 1199;
        }

        restartVar = checkPixel(restartVar);

        if (tempY < 1199) {
            y = tempY + 1;
        } else {
            y = 0;
        }

        restartVar = checkPixel(restartVar);

        if (x < 1599) {
            x++;
        } else {
            x = 0;
        }

        return restartVar;
    }
}

