/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mjpeg_client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author Mr. Mallory
 */
public class MJPEG_Client {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Socket m_socket = null;
        OutputStream m_socketOut = null;
        InputStream m_socketIn = null;
        FileInputStream imageFile = null;
        File file = new File("capture.jpg");
        try {
            imageFile = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MJPEG_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            m_socket = new Socket("127.0.0.1", 9000);
            m_socketOut = m_socket.getOutputStream();
            m_socketIn = m_socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(MJPEG_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // TODO code application logic here
            
            FrameGrabber grabber = FrameGrabber.createDefault(0);
            System.out.println("Created");
            grabber.start();
            System.out.println("Start");
            IplImage grabbedImage = null;
            byte[] imageBytes = new byte[100];
            byte[] buffer = new byte[1024];
            int i = 0;
            while(true){
                System.out.println(0);
                if(m_socketIn.read() != 0){
                    imageFile = new FileInputStream(file);
                    System.out.println("1");
                    grabbedImage = grabber.grab();
                    if(grabbedImage != null){
                        opencv_highgui.cvSaveImage("capture.jpg",grabbedImage);
                        imageBytes = Arrays.copyOf(imageBytes, imageFile.available());
                        imageFile.read(imageBytes);
                        m_socketOut.write(imageBytes);

                    }
                    System.out.println("Sent Image");
                    System.out.println("9");
                    i++;
                    Thread.sleep(100);
                }
            }
            
        } catch (FrameGrabber.Exception ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MJPEG_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
