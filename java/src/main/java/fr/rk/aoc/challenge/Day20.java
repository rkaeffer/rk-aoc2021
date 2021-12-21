package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public final class Day20 {

    /**
     * Calculate number of lit pixel after N image enhancement
     *
     * @param input enhancement reference and image
     * @param nbEnhancement number of enhancement to apply on the image
     * @return number of lit pixel after N image enhancement
     */
    public static long getLitPixelAfterNEnhancement(List<String> input, long nbEnhancement) {
        //Parse ref
        boolean[] enhancementRef = getEnhancementImage(input.get(0));
        //Parse image
        boolean[][] image = parseInitialImageToEnhance(input);
        //boolean to check if surroundig pixel will lit or not after each enhancement (if enhancementRef[0] = true)
        boolean surroundingLit = false;
        //Do the enhancement
        for(int i=0; i<nbEnhancement; i++) {
            image = addMissingSurroundingPixel(image, surroundingLit);
            image = enhanceImage(image, enhancementRef);
            if(enhancementRef[0]) {
                surroundingLit = !surroundingLit;
            }
        }
        //Calculate number of lit pixel
        long cnt = 0;
        for (boolean[] booleans : image) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /**
     * Enhance an image, based on enhancement reference
     *
     * @param image to enhance
     * @param enhancementRef enhancement reference
     * @return image enhance
     */
    private static boolean[][] enhanceImage(boolean[][] image, boolean[] enhancementRef) {
        boolean [][] enhanceImage = new boolean[image.length - 2][image[0].length - 2];
        for(int i=1; i<image.length - 1; i++) {
            for(int j=1; j<image[i].length - 1; j++) {
                String binary = String.valueOf(booleanToBinary(image[i - 1][j - 1])) +
                        booleanToBinary(image[i - 1][j]) +
                        booleanToBinary(image[i - 1][j + 1]) +
                        booleanToBinary(image[i][j - 1]) +
                        booleanToBinary(image[i][j]) +
                        booleanToBinary(image[i][j + 1]) +
                        booleanToBinary(image[i + 1][j - 1]) +
                        booleanToBinary(image[i + 1][j]) +
                        booleanToBinary(image[i + 1][j + 1]);
                enhanceImage[i-1][j-1] = enhancementRef[Integer.parseInt(binary,2)];
            }
        }
        return enhanceImage;
    }

    /**
     * Convert a boolean to binary number
     *
     * @param b boolean to convert
     * @return 1 if b is true, 0 otherwise
     */
    private static char booleanToBinary(boolean b) {
        return b ? '1' : '0';
    }

    /**
     * Add missing pixel to an image to be able to enhance it
     *
     * @param image to add missing pixel
     * @param lit if surrounding must be lit or not
     * @return the image with missing pixel to be able to enhance it
     */
    private static boolean[][] addMissingSurroundingPixel(boolean[][] image, boolean lit) {
        boolean[][] newImage = new boolean[image.length + 4][image[0].length + 4];
        if(lit) {
            for (boolean[] booleans : newImage) {
                Arrays.fill(booleans, true);
            }
        }
        for(int i=0; i<image.length; i++) {
            System.arraycopy(image[i], 0, newImage[i + 2], 2, image[0].length);
        }
        return newImage;
    }

    /**
     * Parse initial image to enhance
     *
     * @param input initial image as list of string
     * @return initial image as boolean[][]
     */
    private static boolean[][] parseInitialImageToEnhance(List<String> input) {
        input.remove(0);
        input.remove(0);
        boolean[][] image = new boolean[input.size()][input.get(0).length()];
        for(int i=0; i<image.length; i++) {
            for(int j=0; j<image[i].length; j++) {
                image[i][j] = input.get(i).charAt(j) == '#';
            }
        }
        return image;
    }

    /**
     * Parse enhancement reference to be able to enhance an image
     *
     * @param enhancement enhancement reference as String
     * @return enhancement reference as boolean[]
     */
    private static boolean[] getEnhancementImage(String enhancement) {
        boolean[] enhancementRef = new boolean[enhancement.length()];
        for(int i=0; i<enhancement.length(); i++) {
            enhancementRef[i] = enhancement.charAt(i) == '#';
        }
        return enhancementRef;
    }


}
