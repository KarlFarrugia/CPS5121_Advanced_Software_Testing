/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package com.uom.cps5121;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A base class for {@link SearchAlgorithm} implementations.
 *
 * @author Karl Farrugia
 */
public abstract class SearchAlgorithmAbstract implements SearchAlgorithm {

    /**
     * The URL of the virtual screen
     */
    public static String REQUEST_URL = "https://virtualscreen.azurewebsites.net/api/pixels";

    /**
     * The ID of the user used as a unique SESSION ID
     */
    public static String SESSION = "59796M";

    /**
     * The limit of when the test should be stopped
     */
    public static int REQUEST_LIMIT = 100000;

    /**
     * Variables used to print to file
     */
    public static PrintWriter WRITER;
    public static PrintWriter WRITER2;
    /**
     * An arraylist to store checked pixels
     */
    public static ArrayList checked_pixel, correct_list = new ArrayList();
    /**
     * Where the R, G, B coloured values are stored.
     */
    public static int R = 20;
    public static int G = 20;
    public static int B = 20;
    /**
     * The response given by a Unirest API call is stored in this variable
     */
    public String response = " ";
    /**
     * A random number generator
     */
    public Random rand = new Random();
    /**
     * A Pair Object used to store the X and Y coordinates of tested pixels
     */
    public Pair<Integer, Integer> pair;
    /**
     * Reccording the number of requests and current fitness to later create a 2d graph of REQUESTS VS FITNESS
     */
    public int requests, fitness;
    /**
     * Where the X and Y coordinates are stored.
     */
    public int x, y;
    /**
     * Scanner class used when an error occurs
     */
    public Scanner scanner = new Scanner(System.in);

    /**
     * Clears all variables and resets the virtual screen
     */
    public void clearVars() throws UnirestException {
        WRITER.println("Requests, Fitness");
        checked_pixel = new ArrayList();
        correct_list = new ArrayList();
        requests = 0;
        fitness = 0;
        HttpResponse<String> getResponse = deleteRequest();
        while (getResponse.getStatus() != 200) {
            System.out.println("You have a problem. Status code not equal to 200. Press any character when it is fixed");
            scanner.nextLine();
            getResponse = deleteRequest();
        }
    }

    /**
     * Generates the X and Y coordinates of an untested pixel
     */
    public void genXY() {
        x = getRandom(1600);
        y = getRandom(1200);

        pair = new Pair<>(x, y);
        while (checked_pixel.contains(pair)) {
            x = getRandom(1600);
            y = getRandom(1200);
            pair = new Pair<>(x, y);
        }
    }

    /**
     * Puts a pixel on the virtual screen and then gets it back. The result of the get request is stored in the RESPONSE variable.
     * The try catch block is implemented as timeout exceptions where crashing the test. With this implementation when an exception
     * is encountered the process will perform a recursive call until the exception works. Two checks are performed to assert whether
     * the connection to the virtual screen is still up and that the status code of the response is 200 (OK).
     *
     * @throws UnirestException
     */
    public void pixelCheck() throws UnirestException {
        try {
            HttpResponse<String> getResponse = putRequest();
            while (getResponse.getStatus() != 201) {
                System.out.println("You have a problem. Status code not equal to 201. Press any character when it is fixed");
                scanner.nextLine();
                getResponse = putRequest();
            }
            getResponse = getRequest();
            response = getResponse.getBody().toString();
            while (getResponse.getStatus() != 200) {
                System.out.println("You have a problem. Status code not equal to 200. Press any character when it is fixed");
                scanner.nextLine();
                getResponse = getRequest();
                response = getResponse.getBody().toString();
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("You have a problem. No connection. Press any character when it is fixed");
            scanner.nextLine();
            pixelCheck();
        }
    }

    /**
     * @param num the maximum random number that can be generated
     * @return the generated number
     */
    public int getRandom(int num) {
        return rand.nextInt(num);
    }

    /**
     * At the end of the execution print relevant information about the tested pixels
     */
    public void printList() {
        WRITER2.println("Number of Correct " + correct_list.size());
        WRITER2.println("Number of Incorrect " + (checked_pixel.size() - correct_list.size()));
    }

    /**
     * Clears the virtual screen
     *
     * @return the Unirest response
     * @throws UnirestException
     */
    public HttpResponse<String> deleteRequest() throws UnirestException {
        return Unirest.delete(REQUEST_URL + "?session=" + SESSION)
                .asString();
    }

    /**
     * Gets the RGB value of an XY coordinate pixel
     *
     * @return the Unirest response
     * @throws UnirestException
     */
    public HttpResponse<String> getRequest() throws UnirestException {
        return Unirest.get(REQUEST_URL + "?session=" + SESSION + "&x=" + x + "&y=" + y)
                .header("content", "application/json")
                .asString();
    }

    /**
     * Puts a pixel with coordinates XY and RGB value on the virtual screen.
     *
     * @return
     * @throws UnirestException
     */
    public HttpResponse<String> putRequest() throws UnirestException {
        return Unirest.put(REQUEST_URL)
                .header("Content-Type", "application/json")
                .body("{\n  \"session\" : \"" + SESSION + "\"," +
                        "\n  \"x\" : " + x + ",\n  \"y\" : " + y + "," +
                        "\n  \"r\" : " + R + ",\n  \"g\" : " + G + "," +
                        "\n  \"b\" : " + B + "\n}")
                .asString();
    }

    /**
     * Increments the number of requests and the fitness if the pixel is bugged. The process also adds the pixel into its relevant arraylist to keep
     * track of when has been tested and their result.
     *
     * @return <tt>true</tt> if pixel is correct <tt>false</tt> if pixel is bugged
     */
    public boolean insertList() {
        requests++;
        pair = new Pair<>(x, y);
        if (response.contains("\"r\":" + R) && response.contains("\"g\":" + G) && response.contains("\"b\":" + B)) {
            /**
             * Inserting a correct pixel and return true
             */
            if (!checked_pixel.contains(pair)) {
                WRITER.println(requests + "," + fitness);
                correct_list.add(pair);
                checked_pixel.add(pair);
            }
            return true;
        } else {
            /**
             * Inserting an incorrect pixel and return false
             */
            if (!checked_pixel.contains(pair)) {
                WRITER2.println("X: " + x + ",Y: " + y);
                fitness++;
                checked_pixel.add(pair);
                WRITER.println(requests + "," + fitness);
            }
            return false;
        }
    }
}
