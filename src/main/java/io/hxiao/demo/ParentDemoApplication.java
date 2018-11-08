package io.hxiao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

@SpringBootApplication
@PropertySource("classpath:/config.properties")
public class ParentDemoApplication {

    public static void main(String[] args) {

        ClassLoader topClassLoader = Thread.currentThread().getContextClassLoader();
        AppClassLoader appClassLoader = new AppClassLoader(new URL[]{}, topClassLoader);
        Thread.currentThread().setContextClassLoader(appClassLoader);

        SpringApplication context = new SpringApplicationBuilder().sources(ParentDemoApplication.class).web(WebApplicationType.SERVLET).build();
        System.out.println(context.getClassLoader());
        context.run(ParentDemoApplication.class, args);


        System.out.println(context);

        String cpCMD = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("input:");
            while ((cpCMD = reader.readLine()) != null) {
                System.out.println(cpCMD);
                String[] cmd = cpCMD.split("@");
                appClassLoader.addURL(new File(cmd[1]).toURI().toURL());
                System.out.println(appClassLoader.loadClass("io.hxiao.demo.controller.ChildController"));
                Class<?> contextClz = appClassLoader.loadClass(cmd[0]);
                System.out.println(contextClz);

               // System.out.println(annotationConfigApplicationContext.getBean(Class.forName("io.hxiao.demo.controller.ChildController")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}

class AppClassLoader extends URLClassLoader {

    public AppClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
