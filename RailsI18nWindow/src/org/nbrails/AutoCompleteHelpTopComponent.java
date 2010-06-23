/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbrails;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.util.Exceptions;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.util.ImageUtilities;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.LookupListener;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.nbrails//AutoCompleteHelp//EN",
autostore = false)
public final class AutoCompleteHelpTopComponent extends TopComponent implements LookupListener {

    private static AutoCompleteHelpTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "org/nbrails/flag_green.png";
    private static final String PREFERRED_ID = "AutoCompleteHelpTopComponent";
    private Map yaml;

    public AutoCompleteHelpTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "CTL_AutoCompleteHelpTopComponent"));
        setToolTipText(NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "HINT_AutoCompleteHelpTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                Project mainProject = OpenProjects.getDefault().getMainProject();
                if (mainProject == null) {
                    setStateComponents(false);
                } else {
                    init(mainProject);
                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        OpenProjects.getDefault().addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Project mainProject = OpenProjects.getDefault().getMainProject();
                if (mainProject == null) {
                    setStateComponents(false);
                } else {
                    init(mainProject);
                }
            }
        });


        textFieldKey.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent arg0) {
                String text = textFieldKey.getText();
                if (arg0.isControlDown() && arg0.getKeyCode() == 32) {


                    String[] split = text.split("\\.");
                    int i = split.length;
                    String d = split[i - 1];
                    JPopupMenu popup = new JPopupMenu();
                    popup.setAutoscrolls(true);
                    List<String> items;
                    if (text.equals("")) {
                        items = new ArrayList<String>(yaml.keySet());
                    } else {
                        try {
                            Map valueFromYaml = (Map) Service.getInstance().getValueFromYaml(text, yaml);
                            items = new ArrayList<String>(valueFromYaml.keySet());

                        } catch (Exception e) {
                            items = new ArrayList<String>();
                        }
                    }

                    if (items.isEmpty()) {

                        String substring = text.substring(0, text.length() - 1);
                        textFieldKey.setText(substring);
                        Object valueFromYaml = Service.getInstance().getValueFromYaml(substring, yaml);
                        textFieldValue.setText(valueFromYaml != null ? valueFromYaml.toString() : "");
                    } else {


                        for (String string : items) {
                            JMenuItem item = new JMenuItem(string);
                            item.addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String old = textFieldKey.getText();
                                    JMenuItem item = (JMenuItem) e.getSource();
                                    final String valorItem = item.getText();
                                    textFieldKey.setText(old + valorItem);



                                }
                            });
                            popup.add(item);
                        }
                        popup.show(textFieldKey, 0, textFieldKey.getHeight());
                        popup.setVisible(true);

                    }
//                    jTextField1.setComponentPopupMenu(popup);

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

    }

    private void init(Project mainProject) {
        boolean contains = mainProject.toString().contains("RailsProject");
        setStateComponents(contains);
        if (contains) {
            Service.init(mainProject.toString().replace("RailsProject[", "").replace("]", ""));
            File[] files = Service.getInstance().getFiles();
            List<FilePOJO> filePOJOs = new ArrayList<FilePOJO>();
            for (File file : files) {
                filePOJOs.add(new FilePOJO(file));
            }
            jComboBox1.setModel(new DefaultComboBoxModel(filePOJOs.toArray()));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        textFieldValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textFieldKey = new javax.swing.JTextField();
        labelOutput = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.jLabel3.text")); // NOI18N

        textFieldValue.setText(org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.textFieldValue.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.jLabel4.text")); // NOI18N

        textFieldKey.setText(org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.textFieldKey.text")); // NOI18N

        labelOutput.setFont(new java.awt.Font("DejaVu Sans", 1, 12)); // NOI18N
        labelOutput.setForeground(new java.awt.Color(0, 102, 153));
        org.openide.awt.Mnemonics.setLocalizedText(labelOutput, org.openide.util.NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "AutoCompleteHelpTopComponent.labelOutput.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelOutput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(textFieldKey, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(185, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        final FilePOJO selectedItem = (FilePOJO) jComboBox1.getSelectedItem();
        try {
            yaml = Service.getInstance().getYaml(selectedItem.getFile());

        } catch (Exception e) {
            jComboBox1.removeItem(selectedItem);
            String message = NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "yaml_message_error");
            labelOutput.setText(message + selectedItem + "!!!");
            new Timer(20000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    labelOutput.setText("");
                }
            }).start();

        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FilePOJO selectedItem = (FilePOJO) jComboBox1.getSelectedItem();
        Service.getInstance().saveValueOfYaml(textFieldKey.getText(), textFieldValue.getText(), yaml, selectedItem.getFile());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void setStateComponents(boolean b) {
        if (b) {
            labelOutput.setText("");
        } else {
            String message = NbBundle.getMessage(AutoCompleteHelpTopComponent.class, "select_project");
            labelOutput.setText(message);
        }
        Component[] components = jPanel1.getComponents();
        for (Component elem : components) {
            elem.setEnabled(b);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelOutput;
    private javax.swing.JTextField textFieldKey;
    private javax.swing.JTextField textFieldValue;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized AutoCompleteHelpTopComponent getDefault() {
        if (instance == null) {
            instance = new AutoCompleteHelpTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the AutoCompleteHelpTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized AutoCompleteHelpTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(AutoCompleteHelpTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof AutoCompleteHelpTopComponent) {
            return (AutoCompleteHelpTopComponent) win;
        }
        Logger.getLogger(AutoCompleteHelpTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening

//        Project mainProject = OpenProjects.getDefault().getMainProject();
        setStateComponents(false);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    @Override
    public void resultChanged(LookupEvent le) {
        System.out.println(le.getSource());
    }
}
