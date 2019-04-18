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


    public Server(String name ) throws RemoteException, AlreadyBoundException {
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
            os=new OS(name,"BruteForce Solution");
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
    public int addition(int x) throws RemoteException {
        System.out.println("Metoda wywolana");
        return x*5;
    }
}
