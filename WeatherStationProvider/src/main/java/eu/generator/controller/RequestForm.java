/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.generator.controller;



/**
 *
 * @author cripan
 */
public class RequestForm {

 
  private String requesterSystem;
  private String requestedService;
  //private Map<String, Boolean> orchestrationFlags = new HashMap<>();
  private String preferredProviders;

    public RequestForm() {
    }

    public RequestForm(String requesterSystem, String requestedService, String preferredProviders) {
        this.requesterSystem = requesterSystem;
        this.requestedService = requestedService;
        this.preferredProviders = preferredProviders;
    }

    public void setRequesterSystem(String requesterSystem) {
        this.requesterSystem = requesterSystem;
    }

    public void setRequestedService(String requestedService) {
        this.requestedService = requestedService;
    }

    public void setPreferredProviders(String preferredProviders) {
        this.preferredProviders = preferredProviders;
    }

    public String getRequesterSystem() {
        return requesterSystem;
    }

    public String getRequestedService() {
        return requestedService;
    }

    public String getPreferredProviders() {
        return preferredProviders;
    }
 

 

 
  

}
