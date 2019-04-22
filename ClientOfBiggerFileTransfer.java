import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
class ClientOfBiggerFileTransfer implements ActionListener{
    
    JFrame win = new JFrame("File Sending");
    
    JButton b1 = new JButton("Browse");
    JButton b3 = new JButton("Transimite");
    JLabel label1 = new JLabel("IP Address");
    JLabel label2 = new JLabel("Port");

    long size=0;
    String fileNameFrom = "";
    String dir1 = "";
    String fileNameTo = "";
    String dir2 = "";
    String ipAddress = "";
    int port = 0;
    String p = "";
    long packetSize = 0;
    long totalPackets = 0;
    long remPacketSize = 0;

    JTextField text1 = new JTextField();
    JTextField text2 = new JTextField();
    JTextField text3 = new JTextField();
    
    ClientOfBiggerFileTransfer(){
        //setBounds(x,y,width,height);
        win.setLayout(null);
        
        win.setBounds(300,50,500,500);

        text1.setBounds(50,100,250,30);
        text2.setBounds(120,150,200,30);
        text3.setBounds(120,200,100,30);
         
        b1.setBounds(320,100,80,30);
        label1.setBounds(50,150,80,30);
        label2.setBounds(80,200,70,30);
        b3.setBounds(150,250,150,30);

        b1.addActionListener(this);
        b3.addActionListener(this);

          win.add(b1);
          win.add(text1);
          win.add(label2);
          win.add(text3);
          win.add(label1);
          win.add(text2);
          win.add(b3);

          text1.setEditable(false);

        win.setVisible(true);
     
    }
     
    class SocketConnect extends Thread{

        public void run(){
            try{
              
                try{
                ipAddress = text2.getText();
                p = text3.getText();
                port = Integer.parseInt(p);
                }catch(NumberFormatException exe){exe.printStackTrace();}           

                Socket socket = new Socket(ipAddress,port);
                System.out.println("Connected");
                FileInputStream fRead = new FileInputStream(dir1+fileNameFrom);
                PrintStream out = new PrintStream(socket.getOutputStream());
                DataInputStream out1 = new DataInputStream(fRead);
                byte[] packet = new byte[(int)packetSize];
               
                
                out.println(fileNameFrom);
                out.println(packetSize);
                out.println(totalPackets); 
                out.println(remPacketSize);
                System.out.println("total Packets: " + totalPackets);
                for(int i=1; i<=totalPackets; i++){

                out1.readFully(packet);
                out.write(packet,0,(int)packetSize);
                System.out.println("Loop of Client: " + i);

                }

                out1.readFully(packet,0,(int)remPacketSize);
                out.write(packet,0,(int)remPacketSize);

                JOptionPane.showMessageDialog(null,"File Uploaded");
                out.close();
                socket.close();
               // System.out.println(data);
      
          
                 }catch(Exception ev){
                 ev.printStackTrace();
                 }
        }
    }

    public void actionPerformed(ActionEvent e){

        if(b1 == e.getSource()){
        try{
        FileDialog d1 = new FileDialog(win, "Got It..");; 
        d1.show();
        
        fileNameFrom = d1.getFile();
        dir1 = d1.getDirectory();
                   
        FileInputStream fRead = new FileInputStream(dir1+fileNameFrom);
         
        text1.setText(dir1+fileNameFrom);
        File ff = new File(dir1+fileNameFrom);
        
        size = ff.length();
        packetSize = 10000;
        totalPackets = size/packetSize;
        remPacketSize = size%packetSize;

        System.out.println("Size of the File: " + size);
        }catch(Exception ev){
            ev.printStackTrace();
        }
        }      

       if(b3 == e.getSource()){
        
        SocketConnect socketconnect = new SocketConnect();
        socketconnect.start();
        }
    }
    public static void main(String arg[])throws Exception{
        ClientOfBiggerFileTransfer ob = new ClientOfBiggerFileTransfer();
    }
}

