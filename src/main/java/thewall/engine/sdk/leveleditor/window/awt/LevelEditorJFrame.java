/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package thewall.engine.sdk.leveleditor.window.awt;

import thewall.engine.sdk.leveleditor.window.WindowManager;

import java.awt.EventQueue;

/**
 *
 * @author many
 */
public class LevelEditorJFrame extends javax.swing.JFrame implements WindowManager {
    
    public LevelEditorJFrame() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 754, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    @Override
    public void createWindow(int width, int height) {
        resizeWindow(width, height);
        EventQueue.invokeLater(this::showWindow);

    }

    @Override
    public void hideWindow() {
        this.setVisible(false);
    }

    @Override
    public void showWindow() {
        this.setVisible(true);
    }

    @Override
    public void setWindowTitle(String title) {
        this.setTitle(title);
    }

    @Override
    public void closeWindow() {
        this.dispose();
    }

    @Override
    public void resizeWindow(int width, int height) {
        this.setSize(width, height);
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
