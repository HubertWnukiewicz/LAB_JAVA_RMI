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
    public Result solve(Instance instance,int alg) throws RemoteException {
        Result result=new Result();
        if(alg==0) {
            System.out.println("USED: Brute Force");
            result=bf.StartAlgorithm(instance);
        }
        else if(alg==1){
            System.out.println("USED: Random Selection");
            result=rs.StartAlgorithm(instance);
        }
        else if(alg==2){
            System.out.println("USED: Greedy Solution");
            result=gs.StartAlgorithm(instance);
        }
        else if(alg==3){
            System.out.println("USED: Dynamic Programming");
            result=bf.StartAlgorithm(instance);
        }
        else{
            System.out.println("USED: Brute Force2");
            result=bf.StartAlgorithm(instance);
        }
        return result;
    }
    public void getListOfImplementedAlgorithms() throws RemoteException{
        System.out.println("0. Brute force. ");
        System.out.println("1. Random selection. ");
        System.out.println("2. Greedy solution. ");
        System.out.println("3. Dynamic programming. ");
    }

    @Override
    public String getName() throws RemoteException {
        return os.name;
    }

    @Override
    public String getDescription() throws RemoteException {
        return os.description;
    }
    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        String name="SERVER_2";
        String description="Description2";
        Server server=new Server(name,description);
        System.out.println("NAME: "+name);
        System.out.println("DESCRIPTION: "+description);

    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }
}
