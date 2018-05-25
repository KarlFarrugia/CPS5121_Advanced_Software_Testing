/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A Search Algorithm that randomly chooses a new untested pixel and tests whether it is bugged or not.
 *
 * @author Karl Farrugia
 */
public class SearchAlgorithmRandomStrategy extends SearchAlgorithmAbstract {

    /**
     * Creates a new SearchAlgorithmRandomStrategy object.
     *
     * @pre $none
     * @post $none
     */
    public SearchAlgorithmRandomStrategy() {
        System.out.println("Random Strategy Algorithm");
    }

    /**
     * Randomly chooses a pixel and tests it. Process is repeated until the request limit is reached.
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
            insertList();
        } while (requests < REQUEST_LIMIT);
        printList();
    }
}

