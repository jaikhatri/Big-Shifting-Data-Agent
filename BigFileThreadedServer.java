import java.net.*;
import java.io.*;

class BigFileThreadedServer{
    public static void main(String arg[])throws Exception{
        ServerSocket server = new ServerSocket(9090);
       
        do{

            Socket socket = server.accept();
            System.out.println("Client Connected");
            
            HandleClient ob = new HandleClient(socket);
            ob.start();

        }while(true);
    }
}

class HandleClient extends Thread{

    Socket socket;

    HandleClient(Socket socket){
        this.socket = socket;
    }

    public void run(){

        try{
        DataInputStream in = new DataInputStream(socket.getInputStream());
        PrintStream out = new PrintStream(socket.getOutputStream());
        
        String filename = in.readLine();

        int size = Integer.parseInt(in.readLine());
        
        long totalPackets = Long.parseLong(in.readLine());
        long remPackets = Long.parseLong(in.readLine());

        System.out.println("totalPackets: " + totalPackets);

        byte data[] = new byte[size];
        
        FileOutputStream f = new FileOutputStream(filename);

        for(int i=1; i<=totalPackets; i++){

        in.readFully(data,0,size);
        f.write(data,0,size);
        System.out.println("Loop OF Server: " + i);
        }
         System.out.println("rem packet");
        in.readFully(data,0,(int)remPackets);
        f.write(data,0,(int)remPackets);
       
        System.out.println("File Writed");
        f.close();
        in.close();
        out.close();
        socket.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }
}
