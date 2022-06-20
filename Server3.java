
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Vector;

public class Server3 extends Thread implements Communication {
	static Vector<Integer> message = new Vector<>(3);

	public Server3() {
	}

	public static void main(String args[]) {

		try {
			Server3 obj = new Server3();
			Communication stub = (Communication) UnicastRemoteObject.exportObject(obj, 0);
			for(int i=0;i<3;i++){
				message.add(i,0);
			}

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.createRegistry(1236);
			registry.bind("Vector", stub);
			Thread thread = new Thread(new Server3());
			System.err.println("Server ready");
			thread.start();

		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Enter the server you want to send message to?");
				Scanner sc = new Scanner(System.in);
				int sending = sc.nextInt();
				switch (sending) {
				case 1:Communication stub = (Communication) Naming.lookup("rmi://localhost:1234/Vector");
				       int val = message.get(2);
				       val++;
				       message.set(2, val);
				       System.out.println(message);
				       stub.receiveMessage(message);
				       break;
				case 2:Communication stub1 = (Communication) Naming.lookup("rmi://localhost:1235/Vector");
				       int vall = message.get(2);
				       vall++;
				       message.set(2, vall);
				       System.out.println(message);
				       stub1.receiveMessage(message);
				       break;
				case 3:receiveMessage(message);
				       break;
				default:System.out.println("Enter options from 1-3");
				      break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void receiveMessage(Vector<Integer> receivedVector) {
		// TODO Auto-generated method stub
		System.out.println(message);
		for (int i = 0; i < message.size(); i++) {
			int max = Math.max(message.get(i), receivedVector.get(i));
			message.set(i, max);
		}
		int value = message.get(2);
		value = value + 1;
		message.set(2, value);
		System.out.println(message);
	}

	

}