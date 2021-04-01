package eu.arrowhead.weatherStation;


import com.fazecast.jSerialComm.SerialPort;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import static java.util.function.IntUnaryOperator.identity;
import java.util.stream.Collectors;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.config.NetworkConfig;
/**
 * 
 */
public class SerialReadByEvents  {
	SerialPort comPort;
	DavisWeatherStation station;
	
	
  public void StartStationComunication() {
	  SerialReadByEvents serial;
	
	  //station = new DavisWeatherStation();
	  serial = new SerialReadByEvents();
	  List<String> portNames= serial.getPortNames();
           System.out.println(portNames.size());
          for( int i=0; i<portNames.size(); i++){
              System.out.println("Ports:");
              System.out.println(portNames.get(i));
          }
	  serial.comPort = SerialPort.getCommPorts() [0];
          //serial.comPort =SerialPort.getCommPort("COM6");
	  serial.comPort.setBaudRate(19200);
	  if(!serial.comPort.openPort()) {
		  System.out.println("Could not open port");
		  System.exit(1);
	  } else {
		  System.out.println("Serial Port is open");
	  }
	  DataListener listener = new DataListener(serial);
       
	  serial.comPort.addDataListener(listener);
          //START COAP SERVER
	  serial.station = new DavisWeatherStation(listener);
          
        
	  CoapEndpoint.Builder tmp = new CoapEndpoint.Builder();
          tmp.setPort(5555);
          serial.station.addEndpoint(tmp.build());
	  //serial.station.addEndpoints();
	  serial.station.start();
	  
	  try {
		  serial.comPort.writeBytes("\n".getBytes(), 1);
          Thread.sleep(2000);
          
		  serial.comPort.writeBytes("TEST\n".getBytes(), 5);
          Thread.sleep(2000);
          
          while(true) {
			  serial.comPort.writeBytes("LPS 1 1\n".getBytes(), 8);
			  Thread.sleep(10000);
                        
          }
          
	  } catch (Exception e) { e.printStackTrace(); }
	  serial.comPort.removeDataListener();
	  serial.comPort.closePort();
	  serial.station.stop();
  }
  
  public void changed() {
		station.changed();
	}
  
	public SerialPort getComPort() {
		return comPort;
	}
	
	/* Constructor */
	public SerialReadByEvents() {
	    
	}
        
      
public List<String> getPortNames() {
  return Arrays.stream(SerialPort.getCommPorts())
      .map(SerialPort::getSystemPortName)
      .collect(Collectors.toList());
}
        
        
        
    
        
        
}



