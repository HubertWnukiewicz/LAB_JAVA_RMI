import knapsack.*;

import javax.swing.*;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements  IServer {
    private BruteForce bf;
    private DynamicProgramming dp;
    private RandomSelection rs;
    private GreedySolution gs;
    private IRegister registers;
    private OS os;


    public Server(String name,String description ) throws RemoteException, AlreadyBoundException {
        System.out.println("Server is up");
        bf = new BruteForce();
        dp = new DynamicProgramming();
        rs = new RandomSelection();
        gs = new GreedySolution();

        try {
            System.out.println("Server port: "+PortFactor.getPort());
            IServer server = (IServer) UnicastRemoteObject.exportObject(this, PortFactor.getPort());
            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            registry.bind(name, this);
            registers =(IRegister) registry.lookup("Spis");
            os=new OS(name,description);
            registers.register(os);
        } catch (AlreadyBoundException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Administrator juz uruchomiony");
            System.exit(0);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Result solve(Instance instance) throws RemoteException {
        Result result=new Result();
        result=bf.StartAlgorithm(instance);
        return result;
    }

    @Override
    public String getName() throws RemoteException {
        return os.name;
    }
    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        Server server=new Server("SERVER_2","BruteForce Solution");

    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }
}
