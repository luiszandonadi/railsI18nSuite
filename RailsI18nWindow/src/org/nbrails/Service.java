/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbrails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.openide.util.Exceptions;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author luis
 */
public class Service {

    private static Service instance;
    private static String projectPath;

    public static Service getInstance() {
        return instance;
    }

    public static void init(String path) {
        instance = new Service();
        projectPath = path;
    }

    public File[] getFiles() {
        String localePath = projectPath + "/config/locales/";
        System.out.println(localePath);
        File file = new File(localePath);
        File[] listFiles = file.listFiles();
        return listFiles;
    }

    public Map getYaml(File file) {
        Map yaml = null;
        try {
            yaml = (Map) (new Yaml()).load(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return yaml;
    }

    private Map getKey(Map m, StringTokenizer t) {
        String s = t.nextToken();
        if (t.hasMoreTokens()) {
            if (m.get(s) == null) {//missing? - Create one
                m.put(s, new LinkedHashMap());
            }
            return getKey((Map) m.get(s), t);//next level
        } else { //last token
            if (m.get(s) == null) {//missing? - Create one
//                m.put(s, "");
            }
            return m;
        }
    }

    private String getLastPartOfKey(String keyname) {
        StringTokenizer st2 = new StringTokenizer(keyname, ".");
        String key = "";
        while (st2.hasMoreTokens()) {
            key = st2.nextToken();
        }
        return key;
    }

    public Object getValueFromYaml(String keyname, Map yamlmap) {
        try {
            //get the key value

            String key = getLastPartOfKey(keyname);
            StringTokenizer stringTokenizer = new StringTokenizer(keyname, ".");
            Map name = getKey(yamlmap, stringTokenizer);
            return name.get(key);

        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public void saveValueOfYaml(String keyname, String value, Map yamlmap, File file) {
        try {
            //get the key value
            String key = getLastPartOfKey(keyname);
            StringTokenizer stringTokenizer = new StringTokenizer(keyname, ".");
            Map name = getKey(yamlmap, stringTokenizer);
            name.put(key, value);
            OutputStream outputStream = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(outputStream);
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            (new Yaml(options)).dump(yamlmap, writer);
        } catch (Exception ex) {
            System.out.println(ex);
        }


    }
}
