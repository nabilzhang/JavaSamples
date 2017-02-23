package me.nabil.demo.reader;

import io.swagger.models.Swagger;
import me.nabil.demo.GenerateException;

import java.util.Set;

/**
 * @author chekong on 15/4/28.
 */
public interface ClassSwaggerReader {
    Swagger read(Set<Class<?>> classes) throws GenerateException;
}
