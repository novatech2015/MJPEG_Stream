/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mjpeg_server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Mr. Mallory
 */
public class MJPEG_Server {

    /**
     * @param args the command line arguments
     */
    
    static JFrame m_application = new JFrame();
    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Socket m_client = null;
            File file = new File("capture.jpg");
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file, false);
            ServerSocket m_socketServer = new ServerSocket(9000);
            m_client = m_socketServer.accept();
            InputStream m_inputStream = m_client.getInputStream();
            OutputStream m_outputStream = m_client.getOutputStream();
            byte[] imageIn = new byte[m_inputStream.available()];
            m_outputStream.write(1);
            while(true){
                
                if(m_inputStream.available() != 0){
                    System.out.println(m_inputStream.available());
                    imageIn = Arrays.copyOf(imageIn, m_inputStream.available());
                    System.out.println("Length" + imageIn.length);
                    m_inputStream.read(imageIn);
                    file.delete();
                    file.createNewFile();
                    file.setWritable(true);
                    fout.write(imageIn);
                    //System.out.println(new String(imageIn));
                    fout.flush();
                    Thread.sleep(50);
                    m_outputStream.write(1);
                    System.out.println("Sent Image Request");
                }
                
                
                
                
            }
        } catch (IOException ex) {
            
        } catch (InterruptedException ex) {
            Logger.getLogger(MJPEG_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
