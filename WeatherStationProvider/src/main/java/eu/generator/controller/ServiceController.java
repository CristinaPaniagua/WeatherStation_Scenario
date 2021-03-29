package eu.generator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aitia.demo.dto.CarRequestDTO;
import com.aitia.demo.dto.CarResponseDTO;


import eu.arrowhead.common.exception.BadPayloadException;

import java.io.IOException;

@RestController
@RequestMapping("/weatherstation")
public class ServiceController {
	
	//=================================================================================================
	// members

	
	

	//=================================================================================================
	// methods

	

	
	//-------------------------------------------------------------------------------------------------
	 @GetMapping(value="/test", produces = MediaType.TEXT_PLAIN_VALUE)
            public String index() {

                return "Ready to go";
                }
	
	//-------------------------------------------------------------------------------------------------
	
         //-------------------------------------------------------------------------------------------------   
}
