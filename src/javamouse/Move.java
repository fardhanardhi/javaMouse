/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamouse;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class Move extends javax.swing.JFrame {

    int posX[] = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300};
    int posY[] = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300};
    Robot robot;
    int port;

    public Move() throws AWTException {
        initComponents();
        robot = new Robot();
//        tm.start();
        this.port = 2134;
        ServerThread st = new ServerThread(this, this.port);
        st.start();
    }

    int count = 0;
    Timer tm = new Timer(100, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("x:" + MouseInfo.getPointerInfo().getLocation().getX() + "y:" + MouseInfo.getPointerInfo().getLocation().getY());
            robot.mouseMove(posX[count], posY[count]);
            count++;
            if (count > posX.length - 1) {
                tm.stop();
            }
        }
    });

    public void moveCursor(int x, int y) {
        txtX.setText("x: " + x);
        txtY.setText("y: " + y);
        
        robot.mouseMove(x,y);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtX = new javax.swing.JLabel();
        txtY = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtX.setText("x:");

        txtY.setText("y:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtX)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtY)
                        .addGap(76, 260, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(txtX)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtY)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Move.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Move().setVisible(true);
                } catch (AWTException ex) {
                    Logger.getLogger(Move.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel txtX;
    private static javax.swing.JLabel txtY;
    // End of variables declaration//GEN-END:variables
}

class ServerThread extends Thread {

    int port;
    Move move;

    public ServerThread(Move move, int port) {
        this.move = move;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buffer = new byte[3000];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length
                );
                DatagramSocket ds = new DatagramSocket(this.port);

                ds.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, data.length).trim();

                System.out.println(s);
                
                String posRaw[] = s.split("\\,", 0);
                int xPos = Integer.parseInt(posRaw[0]);
                int yPos = Integer.parseInt(posRaw[1]);
                move.moveCursor(xPos, yPos);

                ds.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
