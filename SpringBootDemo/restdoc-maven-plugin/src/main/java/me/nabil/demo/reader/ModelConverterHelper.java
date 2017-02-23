/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.nabil.demo.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.converter.ModelConverter;
import io.swagger.jackson.AbstractModelConverter;

/**
 *
 * @author andrewbird
 */
public class ModelConverterHelper extends AbstractModelConverter implements ModelConverter {

    public ModelConverterHelper(ObjectMapper mapper) {
        super(mapper);
    }
}
