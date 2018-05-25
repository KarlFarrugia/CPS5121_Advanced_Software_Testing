/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.util.Pair;

/**
 * A Search Algorithm that randomly chooses a new untested pixel and tests whether it is bugged or not. If it is bugged the algorithm makes use
 * of the knowledge that the bugged pixels are in rectangular patches and attempts to find the North, South, East and West boundaries. On finding
 * these boundaries the algorithm could stop as it would have found the boundaries of the rectangle but for the sake of increasing the fitness
 * accordingly these known bugged pixels are tested to make sure they are as the developer said.
 *
 * @author Karl Farrugia
 */
public class SearchAlgorithmDomainSpecificOptimisedStrategy extends SearchAlgorithmAbstract {

    /**
     * Creates a new SearchAlgorithmDomainSpecificStrategy object.
     *
     * @pre $none
     * @post $none
     */
    public SearchAlgorithmDomainSpecificOptimisedStrategy() {
        System.out.println("Domain Specific Strategy Algorithm");
    }

    /**
     * Randomly chooses a pixel and tests it. On choosing a pixel which is bugged a boundary check is performed. Otherwise
     * a newly random generated pixel is tested. This process is repeated until the preset request limit is reached.
     *
     * @throws UnirestException
     * @pre $none
     * @post $none
     */
    public void randomRunner() throws UnirestException {
        clearVars();
        do {
            genXY();
            pixelCheck();
            boundaryCheck();
        } while (requests < REQUEST_LIMIT);
        printList();
    }

    /**
     * The boundaries of the bugged rectangular area is tested. Afterwards a newly randomly generated pixel is examined.
     *
     * @throws UnirestException
     * @pre a random pixel is generated.
     * @post the boundaries of the rectangle are found and the entire area is tested.
     */
    @SuppressWarnings("Duplicates")
    private void boundaryCheck() throws UnirestException {
        boolean isBugged = !insertList();
        if (isBugged & requests < REQUEST_LIMIT) {
            int northPixel = y + 1;
            int southPixel = y - 1;
            int eastPixel = x + 1;
            int westPixel = x - 1;

            /**
             * Finds the north cardinal point of the rectangular bugged area by iterating upwards until a correct pixel or the screen boundary is found
             */
            if (northPixel < 1199 & requests < REQUEST_LIMIT) {
                y = northPixel;
                pixelCheck();
                isBugged = !insertList();
                while ((isBugged & requests < REQUEST_LIMIT) & northPixel <= 1199) {
                    y = ++northPixel;
                    if (northPixel != 1200) {
                        pixelCheck();
                        isBugged = !insertList();
                    }
                }
                northPixel--;
            }

            /**
             * Finds the south cardinal point of the rectangular bugged area by iterating downwards until a correct pixel or the screen boundary is found
             */
            if (southPixel > 0 & requests < REQUEST_LIMIT) {
                y = southPixel;
                pixelCheck();
                isBugged = !insertList();
                while ((isBugged & requests < REQUEST_LIMIT) & southPixel >= 0) {
                    y = --southPixel;
                    if (southPixel != -1) {
                        pixelCheck();
                        isBugged = !insertList();
                    }
                }
                southPixel++;
            }

            y = southPixel;

            /**
             * Finds the east cardinal point of the rectangular bugged area by iterating to the right until a correct pixel or the screen boundary is found
             */
            if (eastPixel < 1199 & requests < REQUEST_LIMIT) {
                x = eastPixel;
                pixelCheck();
                isBugged = !insertList();
                while ((isBugged & requests < REQUEST_LIMIT) & eastPixel <= 1599) {
                    x = ++eastPixel;
                    if (eastPixel != 1600) {
                        pixelCheck();
                        isBugged = !insertList();
                    }
                }
                eastPixel--;
            }

            /**
             * Finds the west cardinal point of the rectangular bugged area by iterating to the left until a correct pixel or the screen boundary is found
             */
            if (westPixel > 0 & requests < REQUEST_LIMIT) {
                x = westPixel;
                pixelCheck();
                isBugged = !insertList();
                while ((isBugged & requests < REQUEST_LIMIT) & westPixel >= 0) {
                    x = --westPixel;
                    if (westPixel != -1) {
                        pixelCheck();
                        isBugged = !insertList();
                    }
                }
                westPixel++;
            }

            /**
             * Iterates from the south pixel to the north pixel while iterating from the west pixel to the east pixel on each row
             */
            for (y = southPixel; y < northPixel; y++) {
                for (x = westPixel; x < eastPixel; x++) {
                    pair = new Pair<>(x, y);
                    /**
                     * makes sure the pixel is not already checked before inserting it.
                     */
                    if (!checked_pixel.contains(pair) & requests < REQUEST_LIMIT) {
                        WRITER2.println("X: " + x + ",Y: " + y);
                        fitness++;
                        checked_pixel.add(pair);
                    }
                }
            }
        }
    }
}