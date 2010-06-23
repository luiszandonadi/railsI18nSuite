/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbrails;

import java.util.Locale;
import java.util.ResourceBundle;
import org.openide.util.NbBundle;

/**
 *
 * @author luis
 */
public class JavaI18N {

    private static ResourceBundle bundle;
    private static Locale defaltLocale = Locale.getDefault();

    private static ResourceBundle getDefaltBundle() {

        if (!defaltLocale.toString().equals("pt_BR")) {
            defaltLocale = new Locale("en", "US");
        }
        bundle = ResourceBundle.getBundle("Bundle", defaltLocale);
        return bundle;
    }

    public static String getValue(String key) {
        return getDefaltBundle().getString(key);

    }
}
