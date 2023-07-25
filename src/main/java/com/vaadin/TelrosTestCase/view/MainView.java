package com.vaadin.TelrosTestCase.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import jakarta.annotation.security.PermitAll;


@Route("/")
@PermitAll
public class MainView extends AppLayout implements RouterLayout {

    MainView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label label = new Label("Welcome to my Vaadin Web application");
        verticalLayout.add(label);
        addToNavbar(verticalLayout);
    }


}
