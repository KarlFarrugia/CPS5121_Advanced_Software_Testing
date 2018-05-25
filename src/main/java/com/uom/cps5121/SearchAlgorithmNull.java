/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * A class that implements the Null Object Design Pattern for the {@link SearchAlgorithm} class.
 *
 * @author Karl Farrugia
 * @see SearchAlgorithm
 */

final class SearchAlgorithmNull implements SearchAlgorithm {
    @Override
    public HttpResponse<String> deleteRequest() throws UnirestException {
        return null;
    }

    @Override
    public HttpResponse<String> getRequest() throws UnirestException {
        return null;
    }

    @Override
    public HttpResponse<String> putRequest() throws UnirestException {
        return null;
    }

    @Override
    public int getRandom(int num) {
        return -1;
    }

    @Override
    public boolean insertList() {
        return false;
    }

    @Override
    public void printList() {/**/}

    @Override
    public void genXY() {/**/}

    @Override
    public void pixelCheck() {/**/}

    @Override
    public void clearVars() throws UnirestException {/**/}
}
