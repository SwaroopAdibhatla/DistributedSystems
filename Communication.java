import java.rmi.Remote;
import java.util.Vector;
import java.rmi.RemoteException;

public interface Communication extends Remote  {
 void receiveMessage(Vector<Integer> message) throws RemoteException; 
}
