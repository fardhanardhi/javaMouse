/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamouse;

import com.sun.javafx.geom.Curve;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;

/**
 *
 * @author adan
 */
public class JavaMouse {

    Timer tm;

    public JavaMouse() {
//        tm = new javax.swing.Timer(1000, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("x:" + MouseInfo.getPointerInfo().getLocation().getX() + "y:" + MouseInfo.getPointerInfo().getLocation().getY());
//
//            }
//        });
    }
    int tampung = 0;

//    public static void main(String[] args) throws IOException,
//            InterruptedException,
//            AWTException {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Robot robot = new Robot();
//
//        robot.mouseMove(0, 0);
//        Thread.sleep(1000);
//        robot.mouseMove(1000, 1000);
//        Thread.sleep(1000);
//        robot.mouseMove(500, 100);
//        Thread.sleep(500);
//        robot.mouseMove(700, 100);
//        Thread.sleep(500);
//        
//
//    }
}
