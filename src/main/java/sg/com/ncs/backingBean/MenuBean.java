package sg.com.ncs.backingBean;


import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.separator.Separator;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
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

    private MenuModel model;

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
        item.setValue("item 1");
        //item.setUrl("http://jquery.com");
        submenu.getChildren().add(item);
        item = new MenuItem();
        item.setValue("item 2");
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
}
