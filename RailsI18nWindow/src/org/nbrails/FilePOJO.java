/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nbrails;

/**
 *
 * @author luis
 */
public class FilePOJO {

    private java.io.File file;

    public FilePOJO(java.io.File file) {
        this.file = file;
    }

    public java.io.File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    




}
