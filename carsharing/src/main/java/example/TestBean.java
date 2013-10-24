/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import com.mysql.jdbc.log.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.richfaces.log.LogFactory;

/**
 *
 * @author Thorsten
 */
@Named
@RequestScoped
public class TestBean implements Serializable {

    private static final long serialVersionUID = 4934630753831928437L;

    private List<String> header = new ArrayList<String>();

    @PostConstruct
    public void init() {
  //      this.header.add("header1");
  //      this.header.add("header2");
    }

    public List<String> getHeader() {
        return this.header;
    }
}
