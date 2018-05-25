/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * An interface to be implemented by each class that provides uses the SearchAlgorithm
 * The interface implements the Null Object Design Pattern in order to start avoiding
 * {@link NullPointerException} when using the {@link SearchAlgorithmNull} object instead
 * of attributing {@code null} to {@link SearchAlgorithm} variables.
 *
 * @author Karl Farrugia 59796M
 */

public interface SearchAlgorithm {

    /**
     * Clears the virtual screen associated with the session.
     *
     * @return Unirest API String response done on requesting to clear the virtual screen
     * @throws UnirestException
     * @pre $none
     * @post resets the virtual screen and the fitness value
     */
    HttpResponse<String> deleteRequest() throws UnirestException;

    /**
     * Retrieves the colour value of the pixel at the given coordinates.
     *
     * @return Unirest API String response done on requesting the rgb of a pixel on the
     * virtual screen
     * @throws UnirestException
     * @pre pixel assigned a colour
     * @post retreive the colour of concerned pixel
     */
    HttpResponse<String> getRequest() throws UnirestException;

    /**
     * Assigns a colour to a pixel on the virtual screen
     *
     * @return Unirest API String response done on assigning an rgb value to a pixel on the
     * virtual screen
     * @throws UnirestException
     * @pre $none
     * @post assign an rgb colour to a pixel
     */
    HttpResponse<String> putRequest() throws UnirestException;

    /**
     * Return a random number
     *
     * @param num the maximum random number that can be generated
     * @return the randomly generated number
     * @pre $none
     * @post a random number is returned
     */
    int getRandom(int num);

    /**
     * Return a random number
     *
     * @return <tt>true</tt> if pixel is not bugged <tt>false</tt> if pixel is bugged
     * @pre a put request is followed by a get request and the repsone is recorded
     * @post the pixel is added into the checked list and into either the correct or incorrect
     * pixel list
     */
    boolean insertList();

    /**
     * Prints the correct pixels and incorrect pixels at the end of an algorithm's run
     *
     * @pre $none
     * @post prints correct pixels and bugged pixels respectively
     */
    void printList();

    /**
     * Generates a new unchecked X and Y coordinates with a random RGB value
     *
     * @pre $none
     * @post coordinates (and colour) are generated
     */
    void genXY();

    /**
     * Makes an API putPixel call and an API getPixel call
     *
     * @throws UnirestException
     * @pre $none
     * @post a response regarding the pixel is made available and will be used to assert
     * whether the pixel is bugged or not.
     */
    void pixelCheck() throws UnirestException;

    /**
     * resets all global variables and calls deleteRequest to reset the virtual screen
     *
     * @throws UnirestException
     * @pre $none
     * @post all variables are cleared and a new algorithm may be tested.
     */
    void clearVars() throws UnirestException;
}
