/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.weatherStation;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author cripan
 */
class DavisWeatherStation extends CoapServer{
	
	ArrayList<CoapResource> resourceCollection = new ArrayList<CoapResource>();
        
        
	public DavisWeatherStation(DataListener provider) {
            
           
            
		CoapResource baseResource = new CoapResource("weatherstation");

		resourceCollection.add(new IndoorTemperatureResource(provider));
		resourceCollection.add(new OutdoorTemperatureResource(provider));
		resourceCollection.add(new IndoorHumidityResource(provider));
		resourceCollection.add(new OutdoorHumidityResource(provider));
		resourceCollection.add(new WindSpeedResource(provider));
		resourceCollection.add(new SolarRadiationResource(provider));
		
		for(CoapResource childResource : resourceCollection) {
			baseResource.add( childResource);
		}
		
		resourceCollection.add(baseResource);
		add(baseResource);
	}
	
	public void changed() {
		
		for(CoapResource childResource : resourceCollection) {
			childResource.changed();
		}
	}
        
       public void addEndpoints() {
       
        /* //THIS CODE ONLY WORK WITH THE CALIFORNIUM CORE 1.0.1-M1 WHICH IS DEPRECATED
        InetAddress addr=EndpointManager.getEndpointManager().getNetworkInterfaces().iterator().next();
            // only binds to IPv4 addresses and localhost
            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
                System.out.println("addr: "+addr.toString());
                InetSocketAddress bindToAddress = new InetSocketAddress(addr, 5555);
                System.out.println("bindToAddress: "+bindToAddress.toString());
                addEndpoint(new CoAPEndpoint(bindToAddress));
            
                }
            */
        
        // CODE WITH NEW VERSION 
       
        
    }

}

class IndoorTemperatureResource extends CoapResource {
	DataListener provider;
	public IndoorTemperatureResource(DataListener provider) {
		super("indoortemperature");
		this.provider = provider;
		
		//set content type
		//change json payload to up-to-date SenML (no 'e' and upper level is an array and URN basename).
		
//		setObservable(true);
//		getAttributes().setObservable();
//		setObserveType(Type.NON);
	}
	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("{\"n\":\"indoortemperature\",\"v\":" + provider.getIndoorTemperatureIndegC() + ",\"u\":\"Celsius\"}");
		} else {
			exchange.respond("nak - provider not available");
		}
	}
}




class OutdoorTemperatureResource extends CoapResource {
	DataListener provider;

	public OutdoorTemperatureResource(DataListener provider) {
		super("outdoortemperature");
		this.provider = provider;
//		setObservable(true);
//		getAttributes().setObservable();
//		setObserveType(Type.NON);
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("[{\"n\":\"outdoortemperature\",\"v\":" + provider.getOutdoorTemperatureIndegC() + ",\"u\":\"degC\"}]");
		} else {
			exchange.respond("nak - provider not available");
		}
	}

}

class IndoorHumidityResource extends CoapResource {
	DataListener provider;

	public IndoorHumidityResource(DataListener provider) {
		super("indoorhumidity");
		this.provider = provider;
//		setObservable(true);
//		getAttributes().setObservable();
//		setObserveType(Type.NON);
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("[{\"n\":\"indoorhumidity\",\"v\":" + provider.getIndoorHumidity() + ",\"u\":\"%RH\"}]");
		} else {
			exchange.respond("nak - provider not available");
		}
	}

}

class OutdoorHumidityResource extends CoapResource {
	DataListener provider;

	public OutdoorHumidityResource(DataListener provider) {
		super("outdoorhumidity");
		this.provider = provider;
//		setObservable(true);
//		getAttributes().setObservable();
//		setObserveType(Type.NON);

	}

	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("[{\"n\":\"outdoorhumidity\",\"v\":" + provider.getOutdoorHumidity() + ",\"u\":\"%RH\"}]");
		} else {
			exchange.respond("nak - provider not available");
		}
	}

}
class WindSpeedResource extends CoapResource {
	DataListener provider;

	public WindSpeedResource(DataListener provider) {
		super("windspeed");
		this.provider = provider;
		setObservable(true);
		getAttributes().setObservable();
		setObserveType(CoAP.Type.NON);
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("[{\"n\":\"windspeed\",\"v\":" + provider.getWindspeed() + ",\"u\":\"m/s\"}]");
		} else {
			exchange.respond("nak - provider not available");
		}
	}

}

class SolarRadiationResource extends CoapResource {
	DataListener provider;

	public SolarRadiationResource(DataListener provider) {
		super("solar");
		this.provider = provider;
		setObservable(true);
		getAttributes().setObservable();
		setObserveType(CoAP.Type.NON);
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		if(provider!=null) {
			exchange.respond("[{\"n\":\"solar\",\"v\":" + provider.getSolar() + ",\"u\":\"w/m2\"}]");
		} else {
			exchange.respond("nak - provider not available");
		}
	}

}
