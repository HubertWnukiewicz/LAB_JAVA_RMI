import javax.swing.*;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Spis implements IRegister{
    private ArrayList<OS> registeredServers;

    public Spis(ArrayList<OS> registeredServers)
    {
        this.registeredServers=registeredServers;
    }
    public Spis(){

        registeredServers=new ArrayList<>();
        try {
            System.out.println("Spis port: "+PortFactor.getPort());
            IRegister spis = (IRegister) UnicastRemoteObject.exportObject(this, PortFactor.getPort());
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("Spis", this);
        } catch (AlreadyBoundException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Spis juz uruchomiony");
            System.exit(0);
        }
    }

    @Override
    public boolean register(OS os) throws RemoteException {
        if(os!=null)
        {
            System.out.println("Available servers:");
            registeredServers.add(os);
            printAllServers();
            if(registeredServers.contains(os))
                return true;
        }
        return false;
    }

    @Override
    public ArrayList<OS> getServers() throws RemoteException {
        return registeredServers;
    }
    public static void main(String[] args){
        Spis spis=new Spis();

    }
    public ArrayList<OS> getRegisteredServers() {
        return registeredServers;
    }
    public void printAllServers()
    {
        for(int j=0;j<this.getRegisteredServers().size();j++)
        {
            System.out.println(this.getRegisteredServers().get(j).name);
        }
    }
}



