/**
 * 
 */
package com.n9.mini.sns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.n9.mini.sns.services.TagServices;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/tag")
public class TagController {

	@Autowired
	TagServices tagServices;

}