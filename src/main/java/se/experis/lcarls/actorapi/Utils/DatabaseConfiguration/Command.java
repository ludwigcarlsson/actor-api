package se.experis.lcarls.actorapi.Utils.DatabaseConfiguration;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Command {

    public Date date = new Date();
    public HttpStatus res;

    public String reqMethod = "UNK";
    public String reqIP = "UNK";
    public String reqURI = "UNK";

    public Command(HttpServletRequest req) {
        this.reqMethod = req.getMethod();
        this.reqIP = req.getRemoteAddr();
        this.reqURI = req.getRequestURI();
    }

   public void setRes(HttpStatus res) {
        this.res = res;
   }

   public String toString() {
       StringBuilder strb = new StringBuilder();

       SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy Hh:mm:ss");
       strb.append(String.format("%1$-25s", format.format(date)));

       strb.append(String.format("%1$-20s", reqIP));

       strb.append(String.format("%1$-10s", reqMethod));

       strb.append(String.format("%1$-30s", reqURI));

       strb.append(String.format("%1$-20s", res));

       return strb.toString();
   }
}
