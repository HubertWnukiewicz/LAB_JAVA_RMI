import knapsack.BruteForce;
import knapsack.Instance;
import knapsack.Item;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {

        Client client2=new Client("SERVER_1");
        client2.solveInstance();

        //System.out.println("PO2: "+ spis.getRegisteredServers().size());



    }

}
