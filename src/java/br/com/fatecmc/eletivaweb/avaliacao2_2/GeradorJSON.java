package br.com.fatecmc.eletivaweb.avaliacao2_2;
/**
 *
 * @author william
 */

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeradorJSON extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Cliente cli = getCliente();
        
        // Jeyzon jeyzon = new Jeyzon();                
        //Jeyzon2 jeyzon2 = new Jeyzon2();
        //Jeyzon3 jeyzon3 = new Jeyzon3();
        //Jeyzon4 jeyzon4 = new Jeyzon4();
        //Jeyzon5 jeyzon5 = new Jeyzon5();
        Jeyzon6 jeyzon6 = new Jeyzon6();
        
        try {
            //out.printf(jeyzon.toString(cli));
            //out.printf(jeyzon2.toString(cli));
            //out.printf(jeyzon3.toString(cli));
            //out.printf(jeyzon4.toString(cli));
            out.printf(jeyzon6.toString(cli));
            /*
            out.printf("{\n"
            + "	\"nome\": \"%s\",\n"
            + "	\"clienteDesde\": \"%s\",\n"
            + "	\"numeroCliente\": \"%s\",\n"
            + "	\"rg\": [{\n"
            + "			\"numero\": \"%s\",\n"
            + "			\"orgaoExpeditor\": \"%s\",\n"
            + "			\"estadoExpeditor\": {\n"
            + "				\"sigla\": \"%s\",\n"
            + "				\"nome\": \"%s\"\n"
            + "			}\n"
            + "		},\n"
            + "		{\n"
            + "			\"numero\": \"%s\",\n"
            + "			\"orgaoExpeditor\": \"%s\",\n"
            + "			\"estadoExpeditor\": {\n"
            + "				\"sigla\": \"%s\",\n"
            + "				\"nome\": \"%s\"\n"
            + "			}\n"
            + "		}\n"
            + "	]\n"
            + "}", cli.getNome(),
            dateFormat.format(cli.getClienteDesde()),
            cli.getNumeroCliente(),
            cli.getRg()[0].getNumero(),
            cli.getRg()[0].getOrgaoExpeditor(),
            cli.getRg()[0].getEstadoExpeditor().getSigla(),
            cli.getRg()[0].getEstadoExpeditor().getNome(),
            cli.getRg()[1].getNumero(),
            cli.getRg()[1].getOrgaoExpeditor(),
            cli.getRg()[1].getEstadoExpeditor().getSigla(),
            cli.getRg()[1].getEstadoExpeditor().getNome());
            out.flush();
            out.close();
        */      } catch (IntrospectionException ex) {
            Logger.getLogger(GeradorJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GeradorJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GeradorJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(GeradorJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private Cliente getCliente() {
        
        ObjetoDentroObjeto objDentroObj = new ObjetoDentroObjeto();
        objDentroObj.setObjInterno("valor interno");
        
        TesteLoop testeLoop = new TesteLoop();
        testeLoop.setValor("teste de loop");
        testeLoop.setObjDentroObj(objDentroObj);
        
        Estado estadoDoRG1 = new Estado();
        estadoDoRG1.setSigla("SP");
        estadoDoRG1.setNome("Sao Paulo");

        RG rgDoCliente1 = new RG();
        rgDoCliente1.setNumero("123.456.789");
        rgDoCliente1.setOrgaoExpeditor("SSP");
        rgDoCliente1.setEstadoExpeditor(estadoDoRG1);

        Estado estadoDoRG2 = new Estado();
        estadoDoRG2.setSigla("MG");
        estadoDoRG2.setNome("Minas Gerais");

        RG rgDoCliente2 = new RG();
        rgDoCliente2.setNumero("123.456.780");
        rgDoCliente2.setOrgaoExpeditor("SSP");
        rgDoCliente2.setEstadoExpeditor(estadoDoRG2);

        Cliente cli = new Cliente();
        cli.setNome("Joao da Silva");
        cli.setNumeroCliente("12345");
        cli.setClienteDesde(new Date());
        cli.setTesteInt(123);
        cli.setTesteDouble(1.55);
        cli.setTesteChar('a');
        cli.setTesteBoolean(true);
        cli.setTesteLoop(testeLoop);
        testeLoop.setCliente(cli);
        //cli.setTesteNull("aqui é null");
        cli.setRg(new RG[]{rgDoCliente1, rgDoCliente2});
        return cli;
    }

}
