package sg.com.ncs.backingBean;


import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 17/6/13
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("session")
public class MenuBean implements Serializable {

    Logger log = LoggerFactory.getLogger(MenuBean.class);
    private MenuModel model;

    private String indexPage = "/pages/common/layout/index.xhtml";


    private String page1 = "/pages/page1.xhtml";
    private String page2 = "/pages/page2.xhtml";
    private String page3 = "/pages/page3.xhtml";

    private String currentPage = "page1";
    @PostConstruct
    protected void initialize() {

        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = facesCtx.getELContext();
        ExpressionFactory expFact = facesCtx.getApplication().getExpressionFactory();

        model = new DefaultMenuModel();
        // first submenu
        Submenu submenu = new Submenu();
        submenu.setLabel("Menu 1");
        // menu items
        MenuItem item = new MenuItem();
        item.setValue("Deploy");
        item.setUrl("/pages/deploy.xhtml");
        //item.setActionExpression(expFact.createMethodExpression(elCtx, "#{menuBean.goToPage}", Void.class, new Class[0]));
        //item.setAjax(true);
        //item.setUpdate(":content");
        //item.setProcess(":displayForm");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setValue("File Browser");
        item.setUrl("/pages/filebrowser.xhtml");
        //item.setUrl("http://yuilibrary.com");
        submenu.getChildren().add(item);

        model.addSubmenu(submenu);

        submenu = new Submenu();
        submenu.setLabel("Menu 2");
        item = new MenuItem();
        item.setValue("item 1");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setValue("item 2");
        submenu.getChildren().add(item);

        model.addSubmenu(submenu);

        MenuItem logout = new MenuItem();
        logout.setStyle("position: absolute; right: 4px;");
        logout.setValue("Logout");
        logout.setActionExpression(expFact.createMethodExpression(elCtx, "#{logoutBean.logoutAction}", Void.class, new Class[0]));
        logout.setAjax(false);
        model.addMenuItem(logout);

    }

    public MenuModel getMenu() {
        return model;
    }

    public String goToPage() {
        log.info("goToPage");
        currentPage = page2;
        log.info("currentPage -> " + page2);
        return currentPage;

    }

    public String getIndexPage() {
        log.info("indexPage -> " + indexPage);
        return indexPage;
    }

    public String getCurrentPage() {
        log.info("getCurrentPage() -> " + currentPage);
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        log.info("setCurrentPage() -> " + currentPage);
        this.currentPage = currentPage;
    }
     /*
    public String getPage1() {
        return page1;
    }

    public String getPage2() {
        return page2;
    }

    public String getPage3() {
        return page3;
    }
       */
    public String page2(ActionEvent e) {
        log.info("page2 = "  + page2);
        log.info("b4 currentPage = " + currentPage);
        this.currentPage = page2;
        log.info("af currentPage = " + currentPage);
        return currentPage;
    }

    public String currentPage() {
        log.info("currentPage - > " + currentPage);
        return this.currentPage;
    }

}
