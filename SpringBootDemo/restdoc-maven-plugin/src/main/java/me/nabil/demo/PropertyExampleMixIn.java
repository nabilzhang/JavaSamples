package me.nabil.demo;

import com.fasterxml.jackson.annotation.JsonRawValue;

abstract class PropertyExampleMixIn {
    PropertyExampleMixIn() { }
    
    @JsonRawValue abstract String getExample();
}
