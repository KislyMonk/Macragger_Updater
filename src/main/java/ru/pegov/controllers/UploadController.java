/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.pegov.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.pegov.DB.TTManager;
import ru.pegov.DB.TTManagerDAO;
import ru.pegov.SigmaParser.SigmaReportParser;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 *
 * @author Андрей
 */

@Controller
@RequestMapping(value= "/upload")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    
    //default getter, nothing to do
    @GetMapping()
    public ModelAndView startState(){
        ModelAndView mav = new ModelAndView("UploadReport");

        mav.addObject("message", "Для обновления/добавления данных выбери файл");

        return mav;
    }
    
    @PostMapping()
    public ModelAndView upload(@RequestParam("file") MultipartFile[] file){
        ModelAndView mav = new ModelAndView("UploadReport");
        SigmaReportParser srp = new SigmaReportParser();
        TTManager ttManager = new TTManagerDAO();

        for (MultipartFile multipartFile : file) {
            try {

                ttManager.addArrayTT(srp.getTTsList(multipartFile.getInputStream()));

            } catch (IOException ex) {
                LOGGER.error("Can't use file " + multipartFile.getName());
            }
            HashMap<String, String> preResult = new HashMap<>();
            preResult.put("name", multipartFile.getOriginalFilename());
            preResult.put("add", srp.getAdded() + "");
            preResult.put("notAdd", srp.getNotAdd() + "");
            mav.addObject("result", preResult);
        }

        return mav;
    }
}
