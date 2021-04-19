package eu.consumer;
 


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.OrchestrationFlags.Flag;
import eu.arrowhead.common.dto.shared.OrchestrationFormRequestDTO;
import eu.arrowhead.common.dto.shared.OrchestrationFormRequestDTO.Builder;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.ServiceInterfaceResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceQueryFormDTO;
import eu.arrowhead.common.exception.InvalidParameterException;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE,"eu.consumer"})
public class ConsumerMain implements ApplicationRunner {
    
    //=================================================================================================
	// members
	
    @Autowired
	private ArrowheadService arrowheadService;
    
    @Autowired
	protected SSLProperties sslProperties;
    
    private final Logger logger = LogManager.getLogger(ConsumerMain.class);
    
    //=================================================================================================
	// methods

	//------------------------------------------------------------------------------------------------
    public static void main( final String[] args ) {
    	SpringApplication.run(ConsumerMain.class, args);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
	public void run(final ApplicationArguments args) throws Exception {
    	gettemp();
	}
    
    //-------------------------------------------------------------------------------------------------
   
    //-------------------------------------------------------------------------------------------------
    public void gettemp() {
    	logger.info("Orchestration request for " + "indoortemperature" + " service:");
    	final ServiceQueryFormDTO serviceQueryForm = new ServiceQueryFormDTO.Builder("indoortemperature")
    									.interfaces("HTTP-INSECURE-JSON")
    									.build();
    	
		final Builder orchestrationFormBuilder = arrowheadService.getOrchestrationFormBuilder();
		final OrchestrationFormRequestDTO orchestrationFormRequest = orchestrationFormBuilder.requestedService(serviceQueryForm)
												.flag(Flag.OVERRIDE_STORE, true)
												.build();
		
		printOut(orchestrationFormRequest);		
		
		final OrchestrationResponseDTO orchestrationResponse = arrowheadService.proceedOrchestration(orchestrationFormRequest);
		
		logger.info("Orchestration response:");
		printOut(orchestrationResponse);		
		
		if (orchestrationResponse == null) {
			logger.info("No orchestration response received");
		} else if (orchestrationResponse.getResponse().isEmpty()) {
			logger.info("No provider found during the orchestration");
		} else {
			final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
			//validateOrchestrationResult(orchestrationResult, "indoortemperature");
			
			//final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get("HTTP-INSECURE-JSON");
			//@SuppressWarnings("unchecked")
			//final String response = arrowheadService.consumeServiceHTTP(String.class, HttpMethod.valueOf(orchestrationResult.getMetadata().get("http-method")),
					//orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
					//	"HTTP-INSECURE-JSON", null, null, new String[0]);
			//printOut(response);
			
			
                        
                        
                        try {
             
            
                            String result= sendGET("http://"+orchestrationResult.getProvider().getAddress()+":"+ orchestrationResult.getProvider().getPort()+"/"+orchestrationResult.getServiceUri());
                        //String result= sendGET("http://192.168.1.36:8088/weatherstation/indoortemperature");
        
            
                      } catch (IOException e) {
                       e.printStackTrace();
                         }
                        
                        
                        
		}
    }
    
    //=================================================================================================
	// assistant methods
    
    //-------------------------------------------------------------------------------------------------
 private static String sendGET(String url) throws IOException {
        
     CloseableHttpClient httpClient = HttpClients.createDefault();
      String result="";
        try {

            HttpGet request = new HttpGet(url);

            // add request headers
            //request.addHeader("custom-key", "mkyong");
            //request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                     result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
    
    //-------------------------------------------------------------------------------------------------
    private void validateOrchestrationResult(final OrchestrationResultDTO orchestrationResult, final String serviceDefinitin) {
    	if (!orchestrationResult.getService().getServiceDefinition().equalsIgnoreCase(serviceDefinitin)) {
			throw new InvalidParameterException("Requested and orchestrated service definition do not match");
		}
    	
    	boolean hasValidInterface = false;
    	for (final ServiceInterfaceResponseDTO serviceInterface : orchestrationResult.getInterfaces()) {
			if (serviceInterface.getInterfaceName().equalsIgnoreCase("HTTP-INSECURE-JSON")) {
				hasValidInterface = true;
				break;
			}
		}
    	if (!hasValidInterface) {
    		throw new InvalidParameterException("Requested and orchestrated interface do not match");
		}
    }
    
    //-------------------------------------------------------------------------------------------------
    private void printOut(final Object object) {
    	System.out.println(Utilities.toPrettyJson(Utilities.toJson(object)));
    }
}
