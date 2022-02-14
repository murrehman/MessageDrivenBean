/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mdb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author RehMan
 */
@JMSDestinationDefinition(name = "java:app//MyQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "MyQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app//MyQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {
    
    public NewMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
String record = (String) message.getBody(String.class);
File file = new File("/netbeans/records_mdb.csv");
if(!file.exists()){
file.createNewFile();
}
BufferedWriter bw = new BufferedWriter(new FileWriter(file));
bw.append(record+"\n"); 
bw.close();
} catch (JMSException ex) {
Logger.getLogger(NewMessageBean.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
Logger.getLogger(NewMessageBean.class.getName()).log(Level.SEVERE, null, ex);
}
    }
    
}
