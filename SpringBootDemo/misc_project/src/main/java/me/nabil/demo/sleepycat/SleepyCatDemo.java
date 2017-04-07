package me.nabil.demo.sleepycat;

import com.google.common.collect.Lists;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * TODO: 请添加描述
 *
 * @author ZHANGBI617
 * @date 2017/4/6
 * @since 1.0.0
 */
public class SleepyCatDemo {

    private Environment exampleEnv;
    private StoredClassCatalog catalog;
    Database store;

    private Map<String, List<ETLPictureDTO>> map;

    public static void main(String[] args) {
        new SleepyCatDemo().test();
    }

    private void test() {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(false);
        envConfig.setAllowCreate(true);

        File dir = new File("D:/sleepy");
        dir.mkdirs();

        exampleEnv = new Environment(dir, envConfig);


        String databaseName = "data.db";
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setTransactional(false);

        dbConfig.setSortedDuplicates(false);
        Database myClassDb = exampleEnv.openDatabase(null, "classDb", dbConfig);
        catalog = new StoredClassCatalog(myClassDb);
        TupleBinding keyBinding = TupleBinding.getPrimitiveBinding(String.class);
        SerialBinding valueBinding = new SerialBinding(catalog, List.class);

        store = exampleEnv.openDatabase(null, databaseName, dbConfig);
        this.map = new StoredSortedMap(store, keyBinding, valueBinding, true);

        this.map.put("a", Lists.newArrayList(new ETLPictureDTO()));
        System.out.println(this.map.get("a"));
        exampleEnv.sync();
        exampleEnv.removeDatabase(null, databaseName);
//        exampleEnv.cleanLog();
    }
}
