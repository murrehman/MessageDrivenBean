import javax.servlet.http.*;  
import javax.servlet.*;  
import java.io.*;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;

//@WebServlet(name = "NewServelet", urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet{  

    @Resource(mappedName = "java:app//MyQueue")
    private Queue java_appMyQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;
  
    /**
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req,HttpServletResponse res)  
throws ServletException,IOException  
{  
//res.setContentType("text/html");//setting the content type  
OutputStream out=res.getOutputStream();//get the stream to write the data 

  
String name=req.getParameter("u");
String weight=req.getParameter("p");
String height=req.getParameter("s");


if((height== null) || (height.length() ==0)) {
	throw new IllegalArgumentException ("Height is required");
}

if((weight== null) || (weight.length() ==0)) {
	throw new IllegalArgumentException ("Weight is required");
}

Float heightt= Float.valueOf(height);
Float weightt= Float.valueOf(weight);

Float BMI = weightt / (heightt * heightt);

String bmistr = String.valueOf(BMI);
String msge = name + " " + "weight " + weight + " height " + height + " BMI " + bmistr;
sendJMSMessageToMyQueue(msge);


String show = "Hello " + name + " Your Body Mass Index (BMI) is " + BMI + " kg/m2";
out.write(show.getBytes());

 }

    private void sendJMSMessageToMyQueue(String messageData) {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
            ConnectionFactory cont = (ConnectionFactory)
                    ctx.lookup("java:comp/DefaultJMSConnectionFactory");
            context = cont.createContext();
            context.createProducer().send(java_appMyQueue, messageData);
        } catch ( NamingException ex) {
            Logger.getLogger(NewServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}  
